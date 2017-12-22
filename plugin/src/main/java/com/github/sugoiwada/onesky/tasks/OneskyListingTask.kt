package com.github.sugoiwada.onesky.tasks

import com.android.builder.model.ProductFlavor
import com.github.sugoiwada.onesky.networking.OneskyClient

open class OneskyListingTask : OneskyBaseTask() {
    var flavor: ProductFlavor? = null

    val oneskyClient by lazy {
        val apiKey = oneskyExtension.apiKey
        val apiSecret = oneskyExtension.apiSecret
        val projectId = oneskyExtension.playProjectId
                ?: throw IllegalStateException("onesky.playProjectId is empty. Please specify your OneSky Google Play project id.")
        OneskyClient(apiKey, apiSecret, projectId)
    }
}

