package com.example.model.pelicula

object GestionPeliculas {
    private val peliculas = mutableListOf(
        Pelicula(
            1,
            "El Padrino",
            "La historia de la familia Corleone",
            175,
            listOf(Genero.DRAMA, Genero.MISTERIO),
            listOf(Pais.EEUU),
            "15/03/1972",
            listOf(Director.Scorsesse),
            listOf(Guionista.Guionista1),
            listOf(Actor.Actor1, Actor.Actor2)
        ),
        Pelicula(
            2,
            "El Padrino II",
            "La historia de la familia Corleone",
            202,
            listOf(Genero.DRAMA, Genero.MISTERIO),
            listOf(Pais.EEUU),
            "15/03/1974",
            listOf(Director.Scorsesse),
            listOf(Guionista.Guionista1),
            listOf(Actor.Actor1, Actor.Actor2)
        ),
        Pelicula(
            3,
            "El Padrino III",
            "La historia de la familia Corleone",
            162,
            listOf(Genero.DRAMA, Genero.MISTERIO),
            listOf(Pais.EEUU),
            "15/03/1990",
            listOf(Director.Scorsesse),
            listOf(Guionista.Guionista1),
            listOf(Actor.Actor1, Actor.Actor2)
        )
    )

    fun getPeliculas() = peliculas

    fun getPeliculasByGenero(generoParam: Genero) = peliculas.filter {
        it.genero.contains(generoParam)
    }

    fun getPeliculaByTittle(titulo: String) = peliculas.find {
        it.titulo.equals(titulo, ignoreCase = true)
    }

    fun getPeliculaById(id: Int) = peliculas.find {
        it.id == id
    }


    fun newPelicula(pelicula: Pelicula): Pelicula {
        if (pelicula.id != null) {
            val peliculaTmp = getPeliculaById(pelicula.id)
            if (peliculaTmp != null) {
                throw IllegalArgumentException("El id ya existe")
            } else {
                peliculas.add(pelicula)
            }
        } else {
            pelicula.id = peliculas.maxWith(Comparator.comparingInt { it.id }).id + 1
            peliculas.add(pelicula)
        }
        return pelicula
    }

}
