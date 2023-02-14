package data

import java.io.InputStream
import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller

interface XmlParser<T> {

    fun decodeFromStream(stream: InputStream): T

    class Base : XmlParser<NewsItemsData> {
        override fun decodeFromStream(stream: InputStream): NewsItemsData {
            val context: JAXBContext = JAXBContext.newInstance(NewsItemsData::class.java)
            val unmarshaller: Unmarshaller = context.createUnmarshaller()
            return unmarshaller.unmarshal(stream) as (NewsItemsData)
        }
    }
}