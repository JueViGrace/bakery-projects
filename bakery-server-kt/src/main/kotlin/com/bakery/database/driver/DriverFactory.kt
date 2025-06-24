package com.bakery.database.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application

object DriverFactory {

    private lateinit var JDBC_URL: String
    private lateinit var JDBC_CLASS_NAME: String
    private lateinit var USERNAME: String
    private lateinit var PASSWORD: String
    private lateinit var POOL_SIZE: String
    private lateinit var IS_AUTO_COMMIT: String
    private lateinit var TRANSACTION_ISOLATION: String

    private lateinit var dataSource: HikariDataSource

    fun Application.configureDatabase() {
        JDBC_URL = environment.config.property("ktor.database.jdbc_url").getString()
        JDBC_CLASS_NAME = environment.config.property("ktor.database.jdbc_class_name").getString()
        USERNAME = environment.config.property("ktor.database.username").getString()
        PASSWORD = environment.config.property("ktor.database.password").getString()
        POOL_SIZE = environment.config.property("ktor.database.pool_size").getString()
        IS_AUTO_COMMIT = environment.config.property("ktor.database.auto_commit").getString()
        TRANSACTION_ISOLATION = environment.config.property("ktor.database.transaction_isolation").getString()

        dataSource = HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = JDBC_URL
                driverClassName = JDBC_CLASS_NAME
                username = USERNAME
                password = PASSWORD
                maximumPoolSize = POOL_SIZE.toInt()
                isAutoCommit = IS_AUTO_COMMIT.toBoolean()
                transactionIsolation = TRANSACTION_ISOLATION
                // validate()
            }
        )
    }

    fun initDatabase(): SqlDriver {
        return dataSource.asJdbcDriver()
    }
}
