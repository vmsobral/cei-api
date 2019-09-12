package br.com.guiabolso.quickstart.health

import br.com.guiabolso.quickstart.health.gateways.HealthResponder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HealthInteractorTest {

    private val responders: List<HealthResponder> = listOf(mock(), mock())
    private val interactor = HealthInteractor(responders)

    @Test
    fun `should return all responders`() {
        responders.forEachIndexed { index, healthResponder ->
            val isHealth = index == 0
            whenever(healthResponder.name).thenReturn("Responder #$index")
            whenever(healthResponder.isHealth()).thenReturn(isHealth)
        }

        val expected = listOf(
            ComponentHealth(name = "Responder #0", status = true),
            ComponentHealth(name = "Responder #1", status = false)
        )

        val result = interactor.retrieveHealthStatuses()

        assertEquals(expected, result)
    }
}