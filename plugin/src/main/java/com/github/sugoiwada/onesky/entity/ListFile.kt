package com.github.sugoiwada.onesky.entity

import com.squareup.moshi.Json

data class ListFileResponse(@Json(name = "data") val data: List<ListFile>)

data class ListFile(@Json(name = "file_name") val fileName: String)