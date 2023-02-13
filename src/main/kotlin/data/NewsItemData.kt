package data

import presentation.NewsToUiMapper
import presentation.LocalDateMapper
import kotlinx.serialization.Serializable
import presentation.NewsUi

@Serializable
data class NewsItemData(
    private val id: Int = -1,
    private val title: String = "",
    private val description: String = "",
    private val date: String = "",
    private val keywords: ArrayList<String> = ArrayList(),
    private val visibility: Boolean = false
) {

    fun <T> map(mapper: NewsItemMapper<T>): T = mapper.map(id, title, description, date, keywords)
}

interface NewsItemMapper<T> {
    fun map(id: Int, title: String, description: String, date: String, kewWords: ArrayList<String>): T

    class Base(
        private val localDateParser: LocalDateMapper,
        private val dateParserToUi: NewsToUiMapper
    ) : NewsItemMapper<NewsUi> {
        override fun map(
            id: Int,
            title: String,
            description: String,
            date: String,
            kewWords: ArrayList<String>
        ) = NewsUi(title, description, date, kewWords, localDateParser, dateParserToUi)
    }
}