package br.com.guiabolso.quickstart.controller

import br.com.guiabolso.events.server.EventProcessor
import org.springframework.stereotype.Component
import ro.pippo.controller.Controller
import ro.pippo.controller.POST
import ro.pippo.controller.Path
import ro.pippo.controller.Produces

@Path("/")
@Component
class EventController(private val eventProcessor: EventProcessor) : Controller() {

    @POST("/events")
    @Produces(Produces.JSON)
    fun eventHandler(): String {
        return eventProcessor.processEvent(request.body)
    }

}