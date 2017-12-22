package com.github.sugoiwada.onesky.ext

import com.android.builder.model.ProductFlavor
import com.github.sugoiwada.onesky.entity.Language
import org.gradle.api.Project
import java.io.File

internal fun valuesDirNameFromLanguage(lang: Language): String =
        if (lang.isBaseLanguage) "values"
        else "values-${lang.locale.replace("id", "in")}"

internal fun localeFromValuesDirName(dirName: String): String =
        dirName.replace("values-", "").replace("in", "id").replace("-r", "-")

internal fun listingDirNameFromLanguage(lang: Language): String =
         lang.locale.replace("id", "in").replace("ja", "ja-JP").replace("-r", "-")

internal val Project.resDir get() = File("${projectDir.absolutePath}/src/main/res")

internal fun Project.playDir(flavorName: String?) = File("${projectDir.absolutePath}/src/${flavorName ?: "main"}/play")