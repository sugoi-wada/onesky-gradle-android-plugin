package com.github.sugoiwada.onesky.tasks

import com.github.sugoiwada.onesky.OneskyExtension
import com.github.sugoiwada.onesky.networking.OneskyClient
import org.gradle.api.DefaultTask

open class OneskyStringsTask : DefaultTask() {
    private val oneskyExtension by lazy {
        project.extensions.findByType(OneskyExtension::class.java)!!
    }

    val oneskyClient by lazy {
        val apiKey = oneskyExtension.apiKey
        val apiSecret = oneskyExtension.apiSecret
        val projectId = oneskyExtension.projectId
        OneskyClient(apiKey, apiSecret, projectId)
    }
}

