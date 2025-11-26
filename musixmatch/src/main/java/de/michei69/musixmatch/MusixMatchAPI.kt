package de.michei69.musixmatch

import de.michei69.musixmatch.models.MessageSchema
import de.michei69.musixmatch.models.UserToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.filter
import kotlinx.serialization.json.Json
import java.net.URLEncoder

object MusixMatchAPI {
    var initialized = false
    var token = ""
//    val headerss = mutableSetOf(
//        "Authority" to "apic-desktop.musixmatch.com"
//    )
    var cookie = "x-mxm-user-id="

    val client by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(contentType = ContentType.Any, json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    coerceInputValues = true
                })
            }

            defaultRequest {
                url("https://apic-desktop.musixmatch.com")
            }

            expectSuccess = true
        }
    }

    suspend fun reinit() {
        cookie = "x-mxm-user-id="
        initialized = false
        init()
    }
    suspend fun init() {
        if (initialized) return
        if (token.isEmpty())
            getToken()
        if (!token.isEmpty())
            initialized = true
    }
    private suspend fun getToken() {
        try {
            val req = client.get("/ws/1.1/token.get?app_id=web-desktop-app-v1.0") {
                headers {
                    header("Cookie", cookie)
                    headers.entries().forEach { header(it.key, it.value) }
                }
            }
            cookie = req.headers.getAll("set-cookie")?.filter { it.startsWith("x-mxm-user-id=") }!![0]
            token = req.body<MessageSchema<UserToken>>().message.body?.userToken ?: ""
        } catch (_: Exception) {
            return
        }
    }

    suspend inline fun <reified T>query(endpoint: String, params: Map<String, String>): T? {
        if (!initialized) throw Error("Not initialized")
        if (token.isEmpty()) throw Error("No token. Have you initialized first?")
        val url = "/ws/1.1/" + endpoint + "?app_id=web-desktop-app-v1.0&format=json&usertoken=$token&" + params.entries.joinToString("&") { "${URLEncoder.encode(it.key)}=${URLEncoder.encode(it.value)}" }
        val req = client.get(url) {
            headers {
                header("Cookie", cookie)
            }
        }
        cookie = req.headers.getAll("set-cookie")?.filter { it.startsWith("x-mxm-user-id=") }!![0]
        if (req.status == HttpStatusCode.Unauthorized) {
            reinit()
            return null
        }

        val response = req.body<MessageSchema<T>>()
        if (response.message.header.statusCode == 401) {
            reinit()
            return null
        }

        return response.message.body
    }
}