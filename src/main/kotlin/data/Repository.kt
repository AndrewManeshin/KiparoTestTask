package data

import presentation.NewsUi
import presentation.NewsUiState
import java.lang.Exception
import java.net.UnknownHostException

interface Repository {

    suspend fun fetchNews(): NewsUiState

    fun sortNews(): NewsUiState

    fun searchNews(kewWord: String): NewsUiState

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val mapper: NewsItemsMapper<List<NewsUi>>
    ) : Repository {

        private val news = mutableListOf<NewsUi>()

        override suspend fun fetchNews(): NewsUiState = try {
            news.clear()
            news.addAll(cloudDataSource.fetchNews().map(mapper))
            NewsUiState.Success(news)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> NewsUiState.Error(NO_INTERNET_MESSAGE)
                else -> NewsUiState.Error(SOMETHING_WENT_WRONG_MESSAGE)
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
            val news = news.filter { news -> news.containsKeyWord(kewWord) }
            return if (news.isEmpty())
                NewsUiState.Empty
            else
                NewsUiState.Success(news)
        }

        private companion object {
            const val NO_INTERNET_MESSAGE = "No internet connection"
            const val SOMETHING_WENT_WRONG_MESSAGE = "Service unavailable"
        }
    }
}