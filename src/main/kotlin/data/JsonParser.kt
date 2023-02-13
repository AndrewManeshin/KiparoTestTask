package data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream

interface JsonParser<T> {
    fun decodeFromStream(stream: InputStream): T

    class Base : JsonParser<NewsItemsData> {

        private val json = Json {
            ignoreUnknownKeys = true
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun decodeFromStream(stream: InputStream): NewsItemsData = json.decodeFromStream(stream)
    }
}