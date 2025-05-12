package com

import com.database.SolvesTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
    initDatabase()
}


fun initDatabase() {
    Database.connect(
        url = "jdbc:postgresql://dpg-d0h6f1re5dus73bcl980-a.frankfurt-postgres.render.com/cuberush?ssl=false",
        driver = "org.postgresql.Driver",
        user = "cuberush_user",
        password = "nIEmbhuKFEG6w1Ugqu7XSRLrfngTQxBF"
    )
    transaction {
        SchemaUtils.create(SolvesTable)
    }
}


fun Application.module() {
    initDatabase()
    configureMonitoring()
    configureSerialization()
    configureHTTP()
    configureRouting()
}
