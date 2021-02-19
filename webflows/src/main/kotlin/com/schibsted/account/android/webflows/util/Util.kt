package com.schibsted.account.android.webflows.util

import java.net.URLDecoder
import kotlin.random.Random

internal object Util {
    fun String.utf8(): String = java.net.URLEncoder.encode(this, "UTF-8")

    fun queryEncode(params: Map<String, String>): String {
        return params.map { (k: String, v: String) -> "${k.utf8()}=${v.utf8()}" }.joinToString("&")
    }

    fun parseQueryParameters(queryString: String): Map<String, String> {
        return queryString.split("&").map {
            val splitted = it.split("=")
            val key = URLDecoder.decode(splitted[0], "UTF-8")
            val value = URLDecoder.decode(splitted[1], "UTF-8")
            key to value
        }.toMap()
    }

    fun randomString(length: Int): String {
        val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val result =
            (1..length).map {
                Random.nextInt(letters.length)
            }.map(letters::get)
                .joinToString("")

        return result;
    }

    fun removeJwtSignature(jwt: String?): String {
        jwt ?: return ""

        val split = jwt.split(".")

        if (split.count() < 2) {
            // not a properly formatted JWT, just return prefix to avoid exposing full token
            return jwt.substring(0, 3)
        }

        return "${split[0]}.${split[1]}"
    }
}