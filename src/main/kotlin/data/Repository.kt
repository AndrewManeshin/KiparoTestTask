package data

import presentation.NewsUi
import presentation.NewsUiState
import java.lang.Exception
import java.net.UnknownHostException

interface Repository {

    suspend fun fetchNewsFromJson(): NewsUiState

    fun sortNews(): NewsUiState

    fun searchNews(kewWord: String): NewsUiState

    class Base(
        private val cloudDataSource: CloudDataSource, private val mapper: NewsItemsMapper<List<NewsUi>>
    ) : Repository {

        private val news = mutableListOf<NewsUi>()

        override suspend fun fetchNewsFromJson(): NewsUiState = try {
            news.clear()
            news.addAll(cloudDataSource.fetchNewsJson().map(mapper))
            NewsUiState.Success(news)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> NewsUiState.Error("No internet connection")
                else -> NewsUiState.Error("Service unavailable")
            }
        }

        override fun sortNews(): NewsUiState {
            return if (news.isEmpty())
                NewsUiState.Empty
            else
                NewsUiState.Success(news.sortedByDescending { newsDomain ->
                    newsDomain.localDate()
                })
        }

        override fun searchNews(kewWord: String): NewsUiState {
            return if (news.isEmpty())
                NewsUiState.Empty
            else
                NewsUiState.Success(news.filter { news -> news.containsKeyWord(kewWord) })
        }
    }
}