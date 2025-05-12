package com.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getSolves() {
    get("/solves") {
        call.respondText("SomeSolve")
    }

};