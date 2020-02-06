package com.github.sylux6.watanabot.utils

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.Request

@Throws(IOException::class)
fun getRequest(client: OkHttpClient, url: String, vararg values: String): String {

    val s = StringBuilder("$url?")
    for (`val` in values)
        s.append("$`val`&")

    val request = Request.Builder()
        .url(s.toString())
        .build()

    val response = client.newCall(request).execute()
    return response.body()!!.string()
}
