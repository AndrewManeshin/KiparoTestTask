package data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream
import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller

interface Parser<T> {

    fun decodeFromStream(stream: InputStream): T

    class Xml : Parser<NewsItemsData> {

        override fun decodeFromStream(stream: InputStream): NewsItemsData {
            val context: JAXBContext = JAXBContext.newInstance(NewsItemsData::class.java)
            val unmarshaller: Unmarshaller = context.createUnmarshaller()
            return unmarshaller.unmarshal(stream) as (NewsItemsData)
        }
    }

    class Json : Parser<NewsItemsData> {

        private val json = Json {
            ignoreUnknownKeys = true
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun decodeFromStream(stream: InputStream): NewsItemsData = json.decodeFromStream(stream)
    }
}