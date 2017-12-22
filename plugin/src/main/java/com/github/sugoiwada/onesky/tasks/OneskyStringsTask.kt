package com.github.sugoiwada.onesky.tasks

import com.github.sugoiwada.onesky.networking.OneskyClient

open class OneskyStringsTask : OneskyBaseTask() {
    val oneskyClient by lazy {
        val apiKey = oneskyExtension.apiKey
        val apiSecret = oneskyExtension.apiSecret
        val projectId = oneskyExtension.projectId
        OneskyClient(apiKey, apiSecret, projectId)
    }
}

