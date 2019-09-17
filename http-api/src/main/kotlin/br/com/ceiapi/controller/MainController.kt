package br.com.ceiapi.controller

import br.com.ceiapi.retrieve.RetrieveService
import br.com.ceiapi.retrieve.vo.RequestInput
import org.springframework.stereotype.Component
import ro.pippo.controller.Controller
import ro.pippo.controller.POST
import ro.pippo.controller.Path
import ro.pippo.controller.Produces

@Path("/")
@Component
class MainController(private val retrieveService: RetrieveService) : Controller() {

    @POST("/retrieve")
    @Produces(Produces.JSON)
    fun retrieve(): String {
        val input = request.body.toRequestInput()
        return retrieveService.retrieve(input)
    }

    fun String.toRequestInput(): RequestInput =
        RequestInput(
            "",
            ""
        )
}