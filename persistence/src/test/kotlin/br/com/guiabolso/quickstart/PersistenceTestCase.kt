package br.com.guiabolso.quickstart

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import javax.sql.DataSource

@ExtendWith(SpringExtension::class)
@SpringJUnitConfig(classes = [(PersistenceTestSetup::class)])
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
abstract class PersistenceTestCase {

    @Autowired
    protected lateinit var dataSource: DataSource

    @Autowired
    private lateinit var appContext: ApplicationContext

    fun setUpPersistence(vararg scripts: String) {
        scripts.forEach {
            val script = appContext.getResource(it)
            ResourceDatabasePopulator(script).execute(dataSource)
        }
    }
}