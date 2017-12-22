package com.github.sugoiwada.onesky

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.builder.model.ProductFlavor
import com.github.sugoiwada.onesky.tasks.DownloadListingTask
import com.github.sugoiwada.onesky.tasks.DownloadStringsTask
import com.github.sugoiwada.onesky.tasks.UploadStringsTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class OneskyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            if (plugins.any { it is AppPlugin }.not()) {
                throw IllegalStateException("The 'com.android.application' plugin is required.")
            }

            extensions.create("onesky", OneskyExtension::class.java)

            val android = extensions.getByType(AppExtension::class.java)

            var flavor: ProductFlavor? = null
            android.productFlavors.whenObjectAdded { flavor = it }

            val downloadStringsTaskName = "downloadStrings"
            val uploadStringsTaskName = "uploadStrings"

            tasks.create(downloadStringsTaskName, DownloadStringsTask::class.java)
            tasks.create(uploadStringsTaskName, UploadStringsTask::class.java)

            android.applicationVariants.whenObjectAdded { variant ->
                if (variant.buildType.isDebuggable) {
                    project.logger.debug("Skipping debuggable build type ${variant.buildType.name}.")
                    return@whenObjectAdded
                }

                val downloadListingTaskName = "download${variant.name.capitalize()}Listing"
                tasks.create(downloadListingTaskName, DownloadListingTask::class.java).let { task ->
                    task.flavor = flavor
                }
            }
        }
    }
}