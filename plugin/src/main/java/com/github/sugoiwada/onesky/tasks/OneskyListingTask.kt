package com.github.sugoiwada.onesky.tasks

import com.github.sugoiwada.onesky.OneskyExtension
import com.github.sugoiwada.onesky.networking.OneskyClient
import org.gradle.api.DefaultTask

open class OneskyListingTask : DefaultTask() {
    private val oneskyExtension by lazy {
        project.extensions.findByType(OneskyExtension::class.java)!!
    }

    val oneskyClient by lazy {
        val apiKey = oneskyExtension.apiKey
        val apiSecret = oneskyExtension.apiSecret
        val projectId = oneskyExtension.playProjectId
                ?: throw IllegalStateException("onesky.playProjectId is empty. Please specify your OneSky Google Play project id.")
        OneskyClient(apiKey, apiSecret, projectId)
    }
}

