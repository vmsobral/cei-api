package br.com.guiabolso.quickstart

import br.com.guiabolso.events.server.EventProcessor
import br.com.guiabolso.events.server.exception.ExceptionHandlerRegistry
import br.com.guiabolso.events.server.handler.EventHandlerDiscovery
import br.com.guiabolso.events.server.handler.SimpleEventHandlerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventProcessorConfiguration {

    @Bean
    fun eventHandlerDiscovery() = SimpleEventHandlerRegistry()

    @Bean
    fun eventExceptionHandlerRegistry() = ExceptionHandlerRegistry()

    @Bean
    fun eventProcessor(eventHandlerDiscovery: EventHandlerDiscovery, exceptionHandlerRegistry: ExceptionHandlerRegistry) =
            EventProcessor(eventHandlerDiscovery, exceptionHandlerRegistry)

}