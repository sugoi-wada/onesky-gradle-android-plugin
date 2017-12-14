package com.github.sugoiwada.onesky.networking

import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.rx.rx_string
import com.github.kittinunf.result.Result
import com.github.sugoiwada.onesky.entity.LanguagesResponse
import com.github.sugoiwada.onesky.entity.ListFileResponse
import com.github.sugoiwada.onesky.entity.Listing
import com.github.sugoiwada.onesky.entity.ListingResponse
import io.reactivex.Single
import java.io.File
import java.security.MessageDigest

class OneskyClient(private val apiKey: String, private val apiSecret: String, projectId: Int) {
    private val endpoint = "https://platform.api.onesky.io"
    private val version = 1

    init {
        FuelManager.instance.basePath = "$endpoint/$version/projects/$projectId"
    }

    fun download(locale: String, sourceFileName: String, saveTo: File): Single<Result<String, FuelError>> {
        val params = authParams()
        params.add("source_file_name" to sourceFileName)
        params.add("locale" to locale)

        return "/translations".httpDownload(params, saveTo).rx_string()
    }

    fun listing(locale: String): Single<Listing> {
        val params = authParams()
        params.add("locale" to locale)

        return "/translations/app-descriptions".httpGet(params).rx_object<ListingResponse>().map { it.get().data }
    }

    fun listFile() = "/files".httpGet(authParams()).rx_object<ListFileResponse>()

    fun upload(translationFile: File): Single<Result<String, FuelError>> {
        val params = authParams()
        params.add("file_format" to "ANDROID_XML")

        return "/files".httpUpload(Method.POST, params, translationFile).rx_string()
    }

    fun languages() = "/languages".httpGet(authParams()).rx_object<LanguagesResponse>()

    private fun authParams(): MutableList<Pair<String, String>> {
        val md = MessageDigest.getInstance("MD5")
        val timestamp = (System.currentTimeMillis() / 1000L).toString()
        val devHash = md.digest((timestamp + apiSecret).toByteArray()).toHex()

        return mutableListOf(
                "api_key" to apiKey,
                "dev_hash" to devHash,
                "timestamp" to timestamp
        )
    }
}

private fun String.httpUpload(method: Method = Method.POST, parameters: List<Pair<String, Any?>>? = null, file: File): Request {
    return Fuel.upload(this, method, parameters).source { _, _ -> file }.name { "file" }
}

private fun String.httpDownload(parameter: List<Pair<String, Any?>>? = null, file: File): Request {
    return Fuel.download(this, parameter).destination { _, _ -> file }
}

private fun ByteArray.toHex(): String = joinToString("") { String.format("%02X", it).toLowerCase() }
