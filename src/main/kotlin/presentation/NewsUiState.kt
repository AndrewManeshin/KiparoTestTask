package presentation

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