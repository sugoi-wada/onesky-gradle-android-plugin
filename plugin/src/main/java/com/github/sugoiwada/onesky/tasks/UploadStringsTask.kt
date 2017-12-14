package com.github.sugoiwada.onesky.tasks

import com.github.sugoiwada.onesky.ext.resDir
import io.reactivex.rxkotlin.toObservable
import org.gradle.api.tasks.TaskAction

open class UploadStringsTask : OneskyStringsTask() {
    init {
        group = "Translation"
        description = "Upload translation files (values/strings.xml)"
    }

    @TaskAction
    fun uploadStrings() {
        oneskyClient.listFile()
                .flatMapObservable { it.get().data.map { it.fileName }.toObservable() }
                .concatMap { fileName ->
                    oneskyClient.languages()
                            .flatMapObservable { it.get().data.toObservable() }
                            .filter { it.isReadyToPublish }
                            .map { lang -> lang to fileName }
                }
                .flatMapSingle { (lang, fileName) ->
                    val translationFile = lang.targetStringsFile(project.resDir, fileName)
                    print("Uploading ${lang.locale} translation file ${translationFile.absolutePath}... ")
                    oneskyClient.upload(translationFile)
                }
                .subscribe({
                    println("Done!")
                }, { ex ->
                    println("Failed!")
                    throw ex
                })
    }
}

