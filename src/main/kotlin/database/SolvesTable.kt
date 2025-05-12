package com.database

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

object SolvesTable: IntIdTable("solves") {
    val result = integer("result")
    val scramble = varchar("scramble", 300)
    val date = varchar("date", 50)
    val comment = varchar("comment", 50)
    val groupToken = varchar("groupToken", 36)
    val penalty = integer("penalty")  // 0 DNF, 1 NONE, 2 PLUS2

    fun insert(solves: List<Solve>, uniqueToken: String) {
        transaction {
            SolvesTable.batchInsert(solves) { solve ->
                this[SolvesTable.result] = solve.result
                this[SolvesTable.comment] = solve.comment
                this[SolvesTable.scramble] = solve.scramble
                this[SolvesTable.penalty] = solve.penalty
                this[SolvesTable.date] = solve.date
                this[SolvesTable.groupToken] = uniqueToken
            }
        }
    }

    fun fetchSolves(groupToken: String): List<Solve> = transaction {
        SolvesTable.select { SolvesTable.groupToken eq groupToken}
            .map {
                Solve(
                    result = it[result],
                    scramble = it[scramble],
                    penalty = it[penalty],
                    comment = it[comment],
                    date = it[date]
                )
            }
        }
    }
