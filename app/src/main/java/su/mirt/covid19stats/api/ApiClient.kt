package su.mirt.covid19stats.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import su.mirt.covid19stats.util.SingletonHolder

class ApiClient private constructor(any: Any) {
    private val client = HttpClient(Android) {
        engine {
            connectTimeout = 30_000
            connectTimeout = 30_000
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json(JsonConfiguration.Stable.copy(
                    ignoreUnknownKeys = true)))
        }
    }

    suspend fun summary() =
        client.get<Summary>("$BASE_URL/summary")

    companion object : SingletonHolder<ApiClient, Any>(::ApiClient) {
        const val BASE_URL = "https://api.covid19api.com"


    }
}
