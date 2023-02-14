import data.*
import kotlinx.coroutines.runBlocking
import presentation.LocalDateMapper
import presentation.NewsToUiMapper
import presentation.Show

fun main() {

    val urlJson = "https://api2.kiparo.com/static/it_news.json"
    val urlXml = "https://api2.kiparo.com/static/it_news.xml"
    val datePattern = "yyyy-MM-dd HH:mm:ss Z"
    val show = Show.Base()

    show.show("Input 1 for downloading Json or 2 for xml: ")
    val cloudDataSource = if (readln().toInt() == 1)
        CloudDataSource.Base(Parser.Json(), urlJson)
    else
        CloudDataSource.Base(Parser.Xml(), urlXml)

    val repository = Repository.Base(
        cloudDataSource,
        NewsItemsMapper.Base(
            NewsItemMapper.Base(
                LocalDateMapper.Base(datePattern), NewsToUiMapper.Base(datePattern)
            )
        )
    )

    runBlocking {
        repository.fetchNews().map(show)
    }

    show.show("\nPress any key for sort data")
    readln()

    repository.sortNews().map(show)
    while (true) {
        show.show("\nSearch by word: ")
        repository.searchNews(readln()).map(show)
    }
}