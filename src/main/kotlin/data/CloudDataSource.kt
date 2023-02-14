package data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface CloudDataSource {

    suspend fun fetchNews(): NewsItemsData

    class Base(
        private val parser: Parser<NewsItemsData>,
        private val url: String
    ) : CloudDataSource {

        override suspend fun fetchNews(): NewsItemsData {
            val connection = withContext(Dispatchers.IO) {
                URL(url).openConnection()
            } as HttpsURLConnection
            val newsItemsData = parser.decodeFromStream(connection.inputStream)
            connection.disconnect()
            return newsItemsData
        }
    }
}