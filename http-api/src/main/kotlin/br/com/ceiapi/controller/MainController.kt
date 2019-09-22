package br.com.ceiapi.controller

import br.com.ceiapi.retrieve.RetrieveService
import br.com.ceiapi.retrieve.vo.RequestInput
import br.com.ceiapi.retrieve.vo.RequestOutput
import org.springframework.stereotype.Component
import ro.pippo.controller.Controller
import ro.pippo.controller.POST
import ro.pippo.controller.Path
import ro.pippo.controller.Produces
import ro.pippo.controller.extractor.Body

@Path("/")
@Component
class MainController(private val retrieveService: RetrieveService) : Controller() {

    @POST("/retrieve")
    @Produces(Produces.JSON)
    fun retrieve(@Body requestInput: RequestInput): RequestOutput {
        return retrieveService.retrieve(requestInput)
    }
}