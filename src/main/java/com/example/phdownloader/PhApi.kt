package com.example.phdownloader

import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup
import org.springframework.web.client.RestTemplate

/**
 * @author zhangxinkun
 */
object PhApi {

    val url = "https://cn.pornhub.com"

    private fun getHtml(list:String): String? {
        val response = RestTemplate().getForEntity("$url/playlist/$list", String::class.java)
        if (response.statusCode.is2xxSuccessful) {
            return response.body
        } else {
            return null
        }
    }

    fun playList(platList:String): List<String> {
        val html = getHtml(platList)
        val result = html?.let {
            val videoUrls = Jsoup.parse(it).select("#playlistWrapper ul li a[href]")
            return@let videoUrls.map { url -> url.attr("href") }
                .filter { href -> StringUtils.containsIgnoreCase(href, "view_video") }
                .map { link -> "$url$link" }.distinct()
        }
        return result ?: emptyList()
    }


}