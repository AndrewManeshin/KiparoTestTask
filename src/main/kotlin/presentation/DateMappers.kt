package presentation

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

interface DateToUiMapper {

    fun map(date: String): String

    class Base(
        private val datePattern: String
    ) : DateToUiMapper {

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