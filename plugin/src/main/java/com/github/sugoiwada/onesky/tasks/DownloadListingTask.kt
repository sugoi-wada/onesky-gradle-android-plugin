package com.github.sugoiwada.onesky.tasks

import com.github.sugoiwada.onesky.ext.playDir
import io.reactivex.rxkotlin.toObservable
import org.gradle.api.tasks.TaskAction
import java.io.File

open class DownloadListingTask : OneskyListingTask() {
    init {
        group = "Translation"
        description = "Download the Listing files (App Description)"
    }

    @TaskAction
    fun downloadListing() {
        oneskyClient.languages()
                .flatMapObservable { it.get().data.toObservable() }
                .filter { it.isReadyToPublish }
                .flatMapSingle { lang ->
                    print("Downloading ${lang.locale} translation listing...  ")
                    oneskyClient.listing(lang.locale).map { lang to it }
                }
                .doOnNext { (lang, listing) ->
                    val listingDir = lang.targetListingDir(project.playDir)
                    listingDir.apply {
                        titleFile.writeText(listing.title)
                        shortDescFile.writeText(listing.shortDescription)
                        descFile.writeText(listing.description)
                    }
                }
                .subscribe({ (_, _) ->
                    println("Done!")
                }, { ex ->
                    println("Failed!")
                    throw ex
                })
    }

    private val File.titleFile: File
        get() {
            val f = File(this, "title")
            if (f.exists().not()) {
                f.createNewFile()
            }
            return f
        }

    private val File.shortDescFile: File
        get() {
            val f = File(this, "shortdescription")
            if (f.exists().not()) {
                f.createNewFile()
            }
            return f
        }

    private val File.descFile: File
        get() {
            val f = File(this, "fulldescription")
            if (f.exists().not()) {
                f.createNewFile()
            }
            return f
        }
}
