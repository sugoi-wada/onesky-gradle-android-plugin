package com.github.sugoiwada.onesky

import com.github.sugoiwada.onesky.entity.Language
import com.github.sugoiwada.onesky.ext.localeFromValuesDirName
import com.github.sugoiwada.onesky.ext.valuesDirNameFromLanguage
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class FilesTest {
    @Test
    fun testLocalFromValuesDirName() {
        listOf(
                "values-ar" to "ar",
                "values-es" to "es",
                "values-in" to "id",
                "values-pt-rBR" to "pt-BR",
                "values-zh-rTW" to "zh-TW"
        ).forEach {
            val actual = localeFromValuesDirName(it.first)
            assertThat(actual).isEqualTo(it.second)
        }
    }

    @Test
    fun testValuesDirName() {
        listOf(
                Language(false, true, null, "ar", "") to "values-ar",
                Language(false, true, null, "en", "US") to "values-en-US",
                Language(false, true, null, "id", "") to "values-in",
                Language(false, true, "pt-rBR", "pt", "BR") to "values-pt-rBR",
                Language(false, true, "zh-rTW", "zh", "TW") to "values-zh-rTW",
                Language(true, true, "zh-rTW", "zh", "TW") to "values",
                Language(true, true, null, "en", "US") to "values"
        ).forEach {
            val actual = valuesDirNameFromLanguage(it.first)
            assertThat(actual).isEqualTo(it.second)
        }
    }
}

