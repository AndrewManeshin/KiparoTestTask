package data

import kotlinx.serialization.Serializable
import presentation.NewsUi
import javax.xml.bind.annotation.*

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
data class NewsItemsData(
    @XmlElement(name = "name")
    private val name: String = "",
    @XmlElement(name = "location")
    private val location: String = "",
    @XmlElementWrapper(name = "news")
    @field:XmlElement(name = "element")
    private val news: ArrayList<NewsItemData> = ArrayList()
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