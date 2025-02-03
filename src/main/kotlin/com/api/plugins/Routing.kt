package com.api.plugins

import com.api.auth.LoginRequest
import com.api.auth.TokenResponse
import com.api.auth.createToken
import com.api.model.alumno.Alumno
import com.api.model.alumno.Curso
import com.api.model.alumno.GestionAlumnos
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.http.content.staticResources
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")

        get("/") {
            call.respondText("¡HOLA CLASE!")
        }
        post("/login") {
                val loginRequest = call.receive<LoginRequest>()
                if (loginRequest.username == "admin" && loginRequest.password == "password") {
                    val token = createToken(loginRequest.username)
                    call.respond(TokenResponse(token))
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Credenciales inválidas")
                }
        }

        authenticate {
                // Ruta común
            route("/students") {
                get {
                    val principal = call.principal<JWTPrincipal>()
                    val username = principal!!.payload.getClaim("username").asString()

                    call.respond(GestionAlumnos.getAlumnos())
                }
                get("/curso/{curso}") {

                        val cursoTxt = call.parameters["curso"]
                        val curso = Curso.valueOf(cursoTxt!!)
                        val alumnos = GestionAlumnos.getAlumnoCurso(curso)
                        if (alumnos.isEmpty()) {
                            call.respondText("No se han encontrado resultados")
                        } else {
                            call.respond(alumnos)
                        }
                }

                get("/nombre/{paramNombre}") {
                        val nombre = call.parameters["paramNombre"]
                        if (nombre == null) {
                            call.respondText("El parámetro de búsqueda es obligatorio")
                        } else {
                            val alumno = GestionAlumnos.getAlumnoNombre(nombre)
                            if (alumno == null) {
                                call.respondText("No se ha encontrado ningún resultado.")
                            } else {
                                call.respond(alumno)
                            }
                        }
                }

                    // Se recoge la llamada post para insertar un nuevo alumno:
                post {
                        try {
                            val alumno = call.receive<Alumno>()
                            val alumnoInsertado = GestionAlumnos.nuevoAlumno(alumno)
                            call.respond(alumnoInsertado)
                        } catch (json: JsonConvertException) {
                            call.respondText("¡Datos inválidos!")
                        } catch (ise: IllegalStateException) {
                            call.respondText(ise.message.toString())
                        }
                }

                get("/eliminar/{idAlumno}") {
                        val idAlumno = call.parameters["idAlumno"]
                        if (idAlumno != null) {
                            if (GestionAlumnos.borrarAlumno(idAlumno.toInt())) {
                                call.respondText("Alumno con id $idAlumno eliminado correctamente.")
                            } else {
                                call.respondText("No se ha encontrado ningún alumno con el id = $idAlumno")
                            }
                        } else {
                            call.respondText("idAlumno campo obligatorio")
                        }
                }

                delete("/eliminar/{idAlumno}") {
                        val idAlumno = call.parameters["idAlumno"]
                        if (idAlumno != null) {
                            if (GestionAlumnos.borrarAlumno(idAlumno.toInt())) {
                                call.respondText("Alumno con id $idAlumno eliminado correctamente.")
                            } else {
                                call.respondText("No se ha encontrado ningún alumno con el id = $idAlumno")
                            }
                        } else {
                            call.respondText("idAlumno campo obligatorio")
                        }
                }

                delete("/eliminar") {
                        val idAlumno = call.receive<Int>()
                        if (idAlumno != null) {
                            if (GestionAlumnos.borrarAlumno(idAlumno.toInt())) {
                                call.respondText("Alumno con id $idAlumno eliminado correctamente.")
                            } else {
                                call.respondText("No se ha encontrado ningún alumno con el id = $idAlumno")
                            }
                        } else {
                            call.respondText("idAlumno campo obligatorio")
                        }
                }
            }
        }
    }
}

