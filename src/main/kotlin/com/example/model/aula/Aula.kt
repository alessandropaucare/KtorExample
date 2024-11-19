package com.example.model.aula

import com.example.model.alumno.Curso
import kotlinx.serialization.Serializable

@Serializable
data class Aula(
    val id: String,
    val curso: Set<Curso>,
    val piso: Int,
    val pabellon: String,
)

