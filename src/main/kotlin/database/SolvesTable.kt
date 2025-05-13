package com.database

import com.model.Events
import com.model.Penalty
import com.model.Solve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object SolvesTable: IntIdTable("solves1") {
    val result = integer("result")
    val scramble = varchar("scramble", 300)
    val date = varchar("date", 50)
    val comment = varchar("comment", 50)
    val groupToken = varchar("groupToken", 36)
    val event = integer("event")
    val penalty = integer("penalty")

    fun insert(solves: List<Solve>, uniqueToken: String) {
        transaction {
            SolvesTable.batchInsert(solves) { solve ->
                this[SolvesTable.result] = solve.result
                this[SolvesTable.comment] = solve.comment
                this[SolvesTable.scramble] = solve.scramble
                this[SolvesTable.penalty] = Penalty.entries.indexOf(solve.penalty)
                this[SolvesTable.date] = solve.date
                this[SolvesTable.event] = Events.entries.indexOf(solve.event)
                this[SolvesTable.groupToken] = uniqueToken
            }
        }
    }

    fun fetchSolves(groupToken: String): List<Solve> = transaction {
        SolvesTable
            .select { SolvesTable.groupToken eq groupToken}
            .map {
                Solve(
                    result = it[result],
                    scramble = it[scramble],
                    penalty = Penalty.entries[it[penalty]],
                    event = Events.entries[it[event]],
                    comment = it[comment],
                    date = it[date]
                )
            }
        }
    }
