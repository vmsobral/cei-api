package br.com.guiabolso.quickstart.application.example.repository

import br.com.guiabolso.quickstart.application.example.model.ExampleEntity

interface ExampleRepository {
    fun save(example: ExampleEntity)
}