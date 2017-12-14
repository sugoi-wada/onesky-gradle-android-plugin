package com.github.sugoiwada.onesky

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class TestTask: DefaultTask() {

    private val oneskyExtension by lazy {
        project.extensions.findByType(OneskyExtension::class.java)
    }

    init {
        group = "Test"
        description = "Test Task"
    }

    @TaskAction
    fun test() {
        println("This is a Test! $oneskyExtension")
    }
}