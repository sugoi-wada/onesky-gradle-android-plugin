package com.github.sugoiwada.onesky.tasks

import com.android.builder.model.ProductFlavor
import com.github.sugoiwada.onesky.OneskyExtension
import com.github.sugoiwada.onesky.networking.OneskyClient
import org.gradle.api.DefaultTask

open class OneskyBaseTask : DefaultTask() {
    protected val oneskyExtension by lazy {
        project.extensions.findByType(OneskyExtension::class.java)!!
    }
}

