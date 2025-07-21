package com.bakery.auth.database.helper

import com.bakery.database.BakeryDB
import com.bakery.database.LocalDataSource
import com.bakery.database.helper.DbHelper
import kotlinx.coroutines.flow.firstOrNull
import migrations.Session

interface AuthHelper : LocalDataSource {
    suspend fun getSession(): Session?
    suspend fun createSession(session: Session)
    suspend fun updateActive(active: Boolean, id: String)
    suspend fun deleteSession()
}

class DefaultAuthHelper(override val dbHelper: DbHelper<BakeryDB>) : AuthHelper {
    override suspend fun getSession(): Session? = dbHelper.withDatabase {
        executeOneAsFlow(
            db.sessionQueries.findSession(),
        )
    }?.firstOrNull()

    override suspend fun createSession(session: Session) {
        dbHelper.withDatabase {
            db.transaction {
                db.sessionQueries.saveSession(session)
            }
        }
    }

    override suspend fun updateActive(active: Boolean, id: String) {
        dbHelper.withDatabase {
            db.transaction {
                db.sessionQueries.updateActive(
                    active = active,
                    id = id,
                )
            }
        }
    }

    override suspend fun deleteSession() {
        dbHelper.withDatabase {
            db.sessionQueries.deleteSession()
        }
    }
}
