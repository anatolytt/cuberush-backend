package com

import com.database.SolvesTable
import com.model.Solve
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.slf4j.event.*
import java.util.UUID
import kotlin.uuid.Uuid


fun Application.configureRouting() {
    routing {
        post("/addsolves") {
            val solves = call.receive<List<Solve>>()
            try {
                val token = UUID.randomUUID().toString()
                SolvesTable.insert(solves, token)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = token
                )
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = e.message.toString()
                )
            }
        }
        get("/solves/{token}") {
            val token = call.parameters["token"]
            try {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = SolvesTable.fetchSolves(token!!)
                )
            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = e.message.toString()
                )
            }

        }
    }
}