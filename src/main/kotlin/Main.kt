import data.JsonParser
import data.NewsItemMapper
import data.NewsItemsMapper
import data.Repository
import data.CloudDataSource
import presentation.NewsToUiMapper
import presentation.LocalDateMapper
import presentation.Show

fun main() {

    val datePattern = "yyyy-MM-dd HH:mm:ss Z"
    val repository = Repository.Base(
        CloudDataSource.Base(JsonParser.Base()),
        NewsItemsMapper.Base(
            NewsItemMapper.Base(
                LocalDateMapper.Base(datePattern),
                NewsToUiMapper.Base(datePattern)
            )
        )
    )
    val show = Show.Base()

    show.show("Input 1 for downloading Json or 2 for xml: ")
    if (readln().toInt() == 1) {
        repository.fetchNewsFromJson()
    }
    repository.sortNews().map(show)
    while (true) {
        show.show("Search by word: ")
        repository.searchNews(readln()).map(show)
    }
}