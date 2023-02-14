package presentation

import java.time.LocalDate

class NewsUi(
    private val title: String,
    private val description: String,
    private val date: String,
    private val keywords: ArrayList<String>,
    private val localDateParser: LocalDateMapper,
    private val dateToUiMapper: DateToUiMapper
) {

    fun localDate(): LocalDate = localDateParser.map(date)
    fun containsKeyWord(keyWord: String) = keywords.contains(keyWord)
    fun map(show: Show) {
        show.show(title, description, dateToUiMapper.map(date))
    }
}