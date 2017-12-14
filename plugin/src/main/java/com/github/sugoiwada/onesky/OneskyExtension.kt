package com.github.sugoiwada.onesky

open class OneskyExtension {
    var apiKey: String = ""
    var apiSecret: String = ""
    var projectId: Int = 0
    var playProjectId: Int? = null

    override fun toString(): String {
        return "apiKey = $apiKey, apiSecret = $apiSecret, projectId = $projectId, playProjectId = $playProjectId"
    }
}
