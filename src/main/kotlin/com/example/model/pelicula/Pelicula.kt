package com.example.model.pelicula

import kotlinx.serialization.Serializable

@Serializable
data class Pelicula(
    var id: Int,
    val titulo: String,
    val sinopsis: String,
    val duracion: Int,//numero de minutos
    val genero: List<Genero>,
    val pais: List<Pais>,
    val fechaEstreno: String,
    val directores: List<Director>,
    val guionista: List<Guionista>,
    val elenco: List<Actor>
) {

}

enum class Genero {
    ACCION, AVENTURA, ANIMACION, COMEDIA, TERROR, MISTERIO, DRAMA, SCI_FI
}

enum class Pais {
    ESPAÑA, EEUU, UK, FRANCIA, ITALIA, INDIA, COREA, JAPON, ARGENTINA
}

enum class Director {
    Scorsesse, Spielberg, RidleyScott
}

enum class Guionista {
    Guionista1, Guionista2, Guionista3
}

enum class Actor {
    Actor1, Actor2, Actriz1, Actriz2, Actor3, Actriz3
}

