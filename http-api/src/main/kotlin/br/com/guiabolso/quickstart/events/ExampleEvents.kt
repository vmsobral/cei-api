package br.com.guiabolso.quickstart.events

import br.com.guiabolso.events.builder.EventBuilder
import br.com.guiabolso.events.server.handler.SimpleEventHandlerRegistry
import br.com.guiabolso.quickstart.application.example.ExampleService
import br.com.guiabolso.quickstart.application.example.event.ExampleInput
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class ExampleEvents(
        private val discovery: SimpleEventHandlerRegistry,
        private val exampleService: ExampleService) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        discovery.add("example:status:check", 1) { requestEvent ->
            val request = requestEvent.payloadAs<ExampleInput>()

            EventBuilder.responseFor(requestEvent) {
                payload = exampleService.statusCheck(request)
            }
        }
    }
}