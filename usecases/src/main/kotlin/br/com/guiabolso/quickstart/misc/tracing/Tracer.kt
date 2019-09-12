package br.com.guiabolso.quickstart.misc.tracing

import br.com.guiabolso.tracing.factory.TracerFactory

object Tracer {

    val tracer = TracerFactory.createTracer()
}