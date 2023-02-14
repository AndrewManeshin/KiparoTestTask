package data

import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface CloudDataSource {

    suspend fun fetchNewsJson(): NewsItemsData

    suspend fun fetchNewsXml(): NewsItemsData

    class Base(
        private val jsonParser: JsonParser<NewsItemsData>,
        private val xmlParser: XmlParser<NewsItemsData>
    ) : CloudDataSource {

        override suspend fun fetchNewsJson(): NewsItemsData {
            val connection = URL(URL_JSON).openConnection() as HttpsURLConnection
            val newsItemsData = jsonParser.decodeFromStream(connection.inputStream)
            connection.disconnect()
            return newsItemsData
        }

        override suspend fun fetchNewsXml(): NewsItemsData {
            val connection = URL(URL_XML).openConnection() as HttpsURLConnection
            val newsItemsData = xmlParser.decodeFromStream(connection.inputStream)
            connection.disconnect()
            return newsItemsData
        }

        companion object {
            private const val URL_JSON = "https://api2.kiparo.com/static/it_news.json"
            private const val URL_XML = "https://api2.kiparo.com/static/it_news.xml"
        }
    }
}