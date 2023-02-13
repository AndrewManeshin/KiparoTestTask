package data

import presentation.News
import kotlinx.serialization.Serializable

@Serializable
data class NewsItemsData(
    private val name: String, private val location: String, private val news: ArrayList<NewsItemData>
) {

    fun <T> map(mapper: NewsItemsMapper<T>): T = mapper.map(news)
}

interface NewsItemsMapper<T> {
    fun map(news: ArrayList<NewsItemData>): T

    class Base(private val newsItemMapper: NewsItemMapper<News>) : NewsItemsMapper<List<News>> {
        override fun map(news: ArrayList<NewsItemData>): List<News> = news.map { newsItemData ->
            newsItemData.map(newsItemMapper)
        }
    }
}