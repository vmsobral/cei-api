package br.com.guiabolso.quickstart.gateways.health

import br.com.guiabolso.quickstart.health.gateways.HealthResponder
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class PersistenceHealthResponder(private val dataSource: DataSource) : HealthResponder {

    override val name = "DatabaseName"

    override fun isHealth(): Boolean {
        return try {
            dataSource.connection.use {
                it.prepareCall("SELECT 1").execute()
            }
            true
        } catch (ignored: Throwable) {
            false
        }
    }
}