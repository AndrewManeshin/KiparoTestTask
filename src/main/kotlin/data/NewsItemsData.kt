package data

import kotlinx.serialization.Serializable
import presentation.NewsUi

@Serializable
data class NewsItemsData(
    private val name: String, private val location: String, private val news: ArrayList<NewsItemData>
) {

    fun <T> map(mapper: NewsItemsMapper<T>): T = mapper.map(news)
}

interface NewsItemsMapper<T> {
    fun map(news: ArrayList<NewsItemData>): T

    class Base(private val newsItemMapper: NewsItemMapper<NewsUi>) : NewsItemsMapper<List<NewsUi>> {
        override fun map(news: ArrayList<NewsItemData>): List<NewsUi> = news.map { newsItemData ->
            newsItemData.map(newsItemMapper)
        }
    }
}