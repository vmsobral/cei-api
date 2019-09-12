package br.com.guiabolso.quickstart.repository

import br.com.guiabolso.quickstart.application.example.model.ExampleEntity
import br.com.guiabolso.quickstart.application.example.repository.ExampleRepository
import br.com.guiabolso.quickstart.entity.ExampleTable
import org.springframework.stereotype.Repository

@Repository
class ExampleTableRepositoryImpl(private val repository: IExampleTableRepository) : ExampleRepository {

    override fun save(example: ExampleEntity) {
        val exampleTable = ExampleTable().apply {
            columnNumber1 = example.columnNumber1
            columnNumber2 = "Example"
            columnNumber3 = example.columnNumber3
        }

        repository.save(exampleTable)
    }
}