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
        url = "jdbc:postgresql://localhost:5432/cuberush",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "anatolij"
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
