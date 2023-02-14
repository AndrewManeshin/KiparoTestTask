import data.*
import kotlinx.coroutines.runBlocking
import presentation.LocalDateMapper
import presentation.NewsToUiMapper
import presentation.Show

fun main() {

    val datePattern = "yyyy-MM-dd HH:mm:ss Z"
    val repository = Repository.Base(
        CloudDataSource.Base(
            JsonParser.Base(),
            XmlParser.Base()
        ),
        NewsItemsMapper.Base(
            NewsItemMapper.Base(
                LocalDateMapper.Base(datePattern), NewsToUiMapper.Base(datePattern)
            )
        )
    )
    val show = Show.Base()

    show.show("Input 1 for downloading Json or 2 for xml: ")
    if (readln().toInt() == 1) {
        runBlocking {
            repository.fetchNewsJson().map(show)
        }
    } else {
        runBlocking {
            repository.fetchNewsXml().map(show)
        }
    }
    show.show("\nPress any key for sort data")
    readln()
    repository.sortNews().map(show)
    while (true) {
        show.show("\nSearch by word: ")
        repository.searchNews(readln()).map(show)
    }
}