package jaju.com.webchat.plugins

import io.ktor.events.EventDefinition
import io.ktor.server.application.*
import io.ktor.util.*
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(KoinCustomPlugin) {
        slf4jLogger(Level.DEBUG)
    }
}

//Koin dont migrate to ktor 2.0 yet
object KoinCustomPlugin : BaseApplicationPlugin<Application, KoinApplication, Unit> {
    private val KoinApplicationStarted = EventDefinition<KoinApplication>()
    private val KoinApplicationStopPreparing = EventDefinition<KoinApplication>()
    private val KoinApplicationStopped = EventDefinition<KoinApplication>()

    override val key: AttributeKey<Unit>
        get() = AttributeKey("Koin")

    override fun install(pipeline: Application, configure: KoinApplication.() -> Unit) {
        val monitor = pipeline.environment.monitor
        val koinApplication = startKoin(appDeclaration = configure)
        monitor.raise(KoinApplicationStarted, koinApplication)

        monitor.subscribe(ApplicationStopping) {
            monitor.raise(KoinApplicationStopPreparing, koinApplication)
            stopKoin()
            monitor.raise(KoinApplicationStopped, koinApplication)
        }
    }
}

inline fun <reified T : Any> inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
) = lazy { get<T>(qualifier, parameters) }

inline fun <reified T : Any> get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
) = getKoin().get<T>(qualifier, parameters)