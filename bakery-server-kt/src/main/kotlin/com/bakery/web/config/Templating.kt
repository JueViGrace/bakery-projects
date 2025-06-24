package com.bakery.web.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing
import io.ktor.server.thymeleaf.Thymeleaf
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.FileTemplateResolver

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(
            (
                if (developmentMode) {
                    FileTemplateResolver().apply {
                        cacheManager = null
                        prefix = "server/src/main/resources/templates/"
                    }
                } else {
                    ClassLoaderTemplateResolver().apply {
                        prefix = "templates/"
                    }
                }
                ).apply {
                suffix = ".html"
                characterEncoding = "utf-8"
            }
        )
    }

    routing {
        staticResources("/static", "static")
        staticResources("/scripts", "scripts")
    }
}
