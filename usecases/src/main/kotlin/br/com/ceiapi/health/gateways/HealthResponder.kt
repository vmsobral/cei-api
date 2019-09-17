package br.com.ceiapi.health.gateways

interface HealthResponder {

    val name: String

    fun isHealth(): Boolean
}