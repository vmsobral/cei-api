package br.com.ceiapi.misc.tracing

import br.com.tracing.factory.TracerFactory

object Tracer {

    val tracer = TracerFactory.createTracer()
}