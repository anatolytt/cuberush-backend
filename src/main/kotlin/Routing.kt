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

        get("/.well-known/assetlinks.json") {
            call.respondText(
                text = """[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.example.cubetime",
    "sha256_cert_fingerprints":
    ["BF:67:AB:A2:87:C9:3C:AF:E9:72:8B:FB:19:BA:64:65:23:47:36:47:B1:48:36:3E:94:67:52:AB:31:FC:CF:70"]
  }
}]""",
                contentType = ContentType.Application.Json
            )
        }
    }
}