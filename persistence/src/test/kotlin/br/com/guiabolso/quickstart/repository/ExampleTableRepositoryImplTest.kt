package br.com.guiabolso.quickstart.repository

import br.com.guiabolso.quickstart.application.example.model.ExampleEntity
import br.com.guiabolso.quickstart.entity.ExampleTable
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import java.time.LocalDateTime

class ExampleTableRepositoryImplTest {

    private lateinit var exampleTableRepositoryImpl: ExampleTableRepositoryImpl
    private lateinit var repository: IExampleTableRepository

    @BeforeEach
    fun setUp() {
        repository = mock {}
        exampleTableRepositoryImpl = ExampleTableRepositoryImpl(repository)
    }

    @Test
    fun save() {
        val et = ExampleEntity(1, 1, LocalDateTime.now())
        exampleTableRepositoryImpl.save(et)
        verify(repository, times(1)).save(ArgumentMatchers.any(ExampleTable::class.java))
    }
}