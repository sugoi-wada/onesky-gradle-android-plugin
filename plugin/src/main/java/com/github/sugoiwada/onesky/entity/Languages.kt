package com.github.sugoiwada.onesky.entity

import com.squareup.moshi.Json
import com.github.sugoiwada.onesky.ext.listingDirNameFromLanguage
import com.github.sugoiwada.onesky.ext.valuesDirNameFromLanguage
import java.io.File

data class LanguagesResponse(@Json(name = "data") val data: List<Language>)

data class Language(
        @Json(name = "is_base_language") val isBaseLanguage: Boolean,
        @Json(name = "is_ready_to_publish") val isReadyToPublish: Boolean,
        @Json(name = "custom_locale") private val customLocale: String?,
        @Json(name = "locale") private val language: String,
        @Json(name = "region") private val region: String
) {
    val locale = when {
        customLocale.isNullOrBlank() && region.isBlank() -> language
        customLocale.isNullOrBlank() -> "$language-$region"
        else -> customLocale!!
    }

    fun targetStringsFile(resDir: File, fileName: String): File {
        val valuesDir = File("${resDir.absolutePath}/${valuesDirNameFromLanguage(this)}")
        if (!valuesDir.exists()) {
            valuesDir.mkdir()
        }
        val stringsFile = File("${valuesDir.absolutePath}/$fileName")
        if (!stringsFile.exists()) {
            stringsFile.createNewFile()
        }
        return stringsFile
    }

    fun targetListingDir(playDir: File): File {
        val listingDir = File("$playDir/${listingDirNameFromLanguage(this)}/listing")
        if (listingDir.exists().not()) {
            listingDir.mkdirs()
        }
        return listingDir
    }
}