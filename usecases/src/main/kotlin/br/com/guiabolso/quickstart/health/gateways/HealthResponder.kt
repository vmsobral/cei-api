package br.com.guiabolso.quickstart.health.gateways

interface HealthResponder {

    val name: String

    fun isHealth(): Boolean
}