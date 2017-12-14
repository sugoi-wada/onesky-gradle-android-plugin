package com.github.sugoiwada.onesky.tasks

import com.github.sugoiwada.onesky.ext.resDir
import io.reactivex.rxkotlin.toObservable
import org.gradle.api.tasks.TaskAction

open class DownloadStringsTask : OneskyStringsTask() {
    init {
        group = "Translation"
        description = "Download specified translation files (values-*/strings.xml)"
    }

    @TaskAction
    fun downloadStrings() {
        oneskyClient.listFile()
                .flatMapObservable { it.get().data.map { it.fileName }.toObservable() }
                .concatMap { fileName ->
                    oneskyClient.languages()
                            .flatMapObservable { it.get().data.toObservable() }
                            .filter { it.isReadyToPublish }
                            .map { lang -> lang to fileName }
                }
                .flatMapSingle { (lang, fileName) ->
                    val saveFile = lang.targetStringsFile(project.resDir, fileName)
                    print("Downloading ${lang.locale} translation file $fileName into ${saveFile.absolutePath}...  ")
                    oneskyClient.download(lang.locale, fileName, saveFile)
                }
                .subscribe({
                    println("Done!")
                }, { ex ->
                    println("Failed!")
                    throw ex
                })
    }
}
