package data

import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface CloudDataSource {

    suspend fun fetchNewsJson(): NewsItemsData

    class Base(
        private val jsonParser: JsonParser<NewsItemsData>
    ) : CloudDataSource {

        override suspend fun fetchNewsJson(): NewsItemsData {
            val connection = URL(URL_JSON).openConnection() as HttpsURLConnection
            val newsItemsData = jsonParser.decodeFromStream(connection.inputStream)
            connection.disconnect()
            return newsItemsData
        }

        companion object {
            private const val URL_JSON = "https://api2.kiparo.com/static/it_news.json"
        }
    }
}