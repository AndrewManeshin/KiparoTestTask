package presentation

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NewsUi(
    private val title: String,
    private val description: String,
    private val date: String,
    private val keywords: ArrayList<String>,
    private val localDateParser: LocalDateMapper,
    private val dateParserToUi: NewsToUiMapper
) {

    fun localDate(): LocalDate = localDateParser.map(date)
    fun containsKeyWord(keyWord: String) = keywords.contains(keyWord)
    fun map(show: Show) {
        show.show(title, description, dateParserToUi.map(date))
    }
}

sealed class NewsUiState {

    abstract fun map(show: Show)

    object Empty : NewsUiState() {
        override fun map(show: Show) = show.show("List is empty")
    }

    class Success(private val news: List<NewsUi>) : NewsUiState() {
        override fun map(show: Show) = news.forEach { it.map(show) }
    }

    class Error(private val message: String) : NewsUiState() {
        override fun map(show: Show) = show.show(message)
    }
}

interface NewsToUiMapper {

    fun map(date: String): String

    class Base(
        private val datePattern: String
    ) : NewsToUiMapper {
        override fun map(date: String): String {
            val dateParser = SimpleDateFormat(datePattern)
            val dateFormatter = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale("ru"))
            return dateFormatter.format(dateParser.parse(date))
        }
    }
}

interface LocalDateMapper {

    fun map(date: String): LocalDate

    class Base(
        private val datePattern: String
    ) : LocalDateMapper {
        override fun map(date: String): LocalDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern))
    }
}