package br.com.guiabolso.quickstart.repository

import br.com.guiabolso.quickstart.entity.ExampleTable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IExampleTableRepository : CrudRepository<ExampleTable, Long> {
    fun findTopByOrderByIdDesc(): ExampleTable?
}