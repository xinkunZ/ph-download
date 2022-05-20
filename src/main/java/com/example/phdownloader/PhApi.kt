package com.example.phdownloader

import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup
import org.springframework.http.HttpHeaders
import org.springframework.http.RequestEntity
import org.springframework.web.client.RestTemplate
import java.io.File
import java.net.URI
import java.util.*
import java.util.regex.Pattern

/**
 * @author zhangxinkun
 */
object PhApi {

    private const val url = "https://www.pornhub.com"

    private fun getHtml(uri: URI): String? {
        Locale.setDefault(Locale.US)
        val file = File("./cookie.txt");

        val entity = RequestEntity.get(uri)
            .header(
                HttpHeaders.COOKIE,
                file.readText()
            ).build()
        val response = RestTemplate().exchange(entity, String::class.java)
        return if (response.statusCode.is2xxSuccessful) {
            StringUtils.trimToNull(response.body)
        } else {
            null
        }
    }

    data class VideoName(val url: String, val name: String)

    fun playList(playList: String): List<VideoName> {
        val html = getHtml(URI("$url/playlist/$playList")) ?: return emptyList()
        val pattern = Pattern.compile("lazyloadUrl = \"(.*)\"")
        val matcher = pattern.matcher(html)

        var i = 1
        val result = arrayListOf<VideoName>()
        result.addAll(html.parseVideo("#playlistWrapper ul li a[href]"))
        val lazyUrl = if (matcher.find()) matcher.group(1) else null
        if (lazyUrl != null) {
            while (true) {
                val lazyLoad = getHtml(URI("$url$lazyUrl&page=$i")) ?: break
                result.addAll(lazyLoad.parseVideo("li a[href]"))
                i++
            }
        }
        return result.distinctBy { it.url }
    }

    private fun String.parseVideo(path: String): List<VideoName> {
        val videoUrls = Jsoup.parse(this).select(path)
        return videoUrls
            .filter { url -> StringUtils.containsIgnoreCase(url.attr("href"), "view_video") }
            .map { l ->
                VideoName(
                    "$url${l.attr("href")}",
                    StringUtils.trimToEmpty(l.attr("title"))
                )
            }.distinctBy { it.url }
    }

}