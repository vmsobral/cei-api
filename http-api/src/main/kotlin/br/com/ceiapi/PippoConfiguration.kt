package br.com.ceiapi

import br.com.ceiapi.config.propertiesconfig.ConfigService
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ro.pippo.controller.Controller
import ro.pippo.controller.ControllerApplication
import ro.pippo.core.Pippo


@Configuration
class PippoConfiguration {

    @Bean
    fun startPippo(beanFactory: ListableBeanFactory, configService: ConfigService): Pippo {
        val controllers = beanFactory.getBeansOfType(Controller::class.java).values

        val app = object : ControllerApplication() {
            override fun onInit() {
                router.ignorePaths("/favicon.ico")
                controllers.forEach { addControllers(it) }
            }
        }

        injectRequiredConfig(configService, app, "application.name")
        injectRequiredConfig(configService, app, "application.version")
        injectRequiredConfig(configService, app, "application.languages")
        injectRequiredConfig(configService, app, "application.name")

        injectRequiredConfig(configService, app, "server.port")
        injectRequiredConfig(configService, app, "server.host")
        injectRequiredConfig(configService, app, "server.contextPath")

        injectRequiredConfig(configService, app, "jetty.minThreads")
        injectRequiredConfig(configService, app, "jetty.maxThreads")
        injectRequiredConfig(configService, app, "jetty.idleTimeout")

        val pippo = Pippo(app)
        pippo.start()

        return pippo
    }

    private fun injectRequiredConfig(configService: ConfigService, app: ControllerApplication, property: String) {
        app.pippoSettings.overrideSetting(property, configService.getRequiredString(property))
    }

}
