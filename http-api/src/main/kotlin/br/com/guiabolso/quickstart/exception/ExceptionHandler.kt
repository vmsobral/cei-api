package br.com.guiabolso.quickstart.exception

import ro.pippo.core.Pippo

@org.springframework.stereotype.Component
class ExceptionHandler(pippo: Pippo) {

    val logger = org.slf4j.LoggerFactory.getLogger(ExceptionHandler::class.java)!!

    init {
        pippo.application.apply {
            errorHandler.setExceptionHandler(IllegalArgumentException::class.java, { exception, routeContext ->
                logger.warn(exception.message, exception)
                routeContext.status(400)
                routeContext.json().send(mapOf("error" to exception.message))
            })

            errorHandler.setExceptionHandler(ro.pippo.core.PippoRuntimeException::class.java, { exception, routeContext ->
                logger.warn(exception.message, exception)
                routeContext.status(400)
                routeContext.json().send(mapOf("error" to exception.message))
            })

            errorHandler.setExceptionHandler(Exception::class.java, { exception, routeContext ->
                logger.error(exception.message, exception)
                routeContext.status(500)
                routeContext.json().send(mapOf("error" to exception.message))
            })
        }
    }

}