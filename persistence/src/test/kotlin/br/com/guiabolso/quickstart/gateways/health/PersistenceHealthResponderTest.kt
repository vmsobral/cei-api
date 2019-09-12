package br.com.guiabolso.quickstart.gateways.health

import br.com.guiabolso.quickstart.PersistenceTestCase
import com.zaxxer.hikari.HikariDataSource
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class PersistenceHealthResponderTest : PersistenceTestCase() {

    @Autowired
    lateinit var responder: PersistenceHealthResponder

    @Test
    fun `should return true when database is available`() {
        assertTrue(responder.isHealth())
    }

    @Test
    fun `should return false when database is unavailable`() {
        dataSource.unwrap(HikariDataSource::class.java).close()

        assertFalse(responder.isHealth())
    }
}