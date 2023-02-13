package data

import presentation.News
import presentation.NewsWrapper

interface Repository {

    fun fetchNewsFromJson(): NewsWrapper

    fun sortNews(): NewsWrapper

    fun searchNews(kewWord: String): NewsWrapper

    class Base(
        private val cloudDataSource: CloudDataSource, private val mapper: NewsItemsMapper<List<News>>
    ) : Repository {

        private val news = mutableListOf<News>()

        override fun fetchNewsFromJson(): NewsWrapper {
            news.clear()
            news.addAll(cloudDataSource.fetchNewsJson().map(mapper))
            println(news.toString())
            return NewsWrapper(news)
        }

        override fun sortNews() = NewsWrapper(news.sortedByDescending { newsDomain ->
            newsDomain.localDate()
        })

        override fun searchNews(kewWord: String) = NewsWrapper(news.filter { news -> news.containsKeyWord(kewWord) })
    }
}