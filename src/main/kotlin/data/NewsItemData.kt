package data

import presentation.DateToUiMapper
import presentation.LocalDateMapper
import kotlinx.serialization.Serializable
import presentation.NewsUi
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper

@Serializable
@XmlAccessorType(XmlAccessType.FIELD)
data class NewsItemData(
    private val id: Int = -1,
    private val title: String = "",
    private val description: String = "",
    private val date: String = "",
    @XmlElementWrapper(name = "keywords")
    @field:XmlElement(name = "element")
    private val keywords: ArrayList<String> = ArrayList(),
    private val visible: Boolean = false
) {

    fun <T> map(mapper: NewsItemMapper<T>): T = mapper.map(id, title, description, date, keywords)
}

interface NewsItemMapper<T> {

    fun map(id: Int, title: String, description: String, date: String, kewWords: ArrayList<String>): T

    class Base(
        private val localDateParser: LocalDateMapper,
        private val dateParserToUi: DateToUiMapper
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