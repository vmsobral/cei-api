package br.com.ceiapi.controller

import br.com.ceiapi.health.HealthInteractor
import datadog.trace.api.Trace
import org.springframework.stereotype.Component
import ro.pippo.controller.Controller
import ro.pippo.controller.GET
import ro.pippo.controller.Path
import ro.pippo.controller.Produces

@Path("/")
@Component
class HealthController(private val interactor: HealthInteractor) : Controller() {

    @GET("/health")
    @Produces(Produces.JSON)
    @Trace(operationName = "health")
    fun handle() {
        val healthStatuses = interactor.retrieveHealthStatuses()

        response.status(if (healthStatuses.any { !it.status }) 500 else 200).send(healthStatuses)
    }
}