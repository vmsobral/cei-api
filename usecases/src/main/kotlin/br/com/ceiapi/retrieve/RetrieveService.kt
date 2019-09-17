package br.com.ceiapi.retrieve

import br.com.ceiapi.retrieve.connector.ConnectorFactory
import br.com.ceiapi.retrieve.vo.RequestInput
import datadog.trace.api.Trace
import org.springframework.stereotype.Service

@Service
class RetrieveService(
    val connectorFactory: ConnectorFactory
){

    @Trace(operationName = "ExampleService:statusCheck")
    fun retrieve(requestInput: RequestInput): String {
        var connectorFactory = connectorFactory.create()

    }

    companion object {
        const val PAGE_LOAD_TIMEOUT = 10L
    }
}