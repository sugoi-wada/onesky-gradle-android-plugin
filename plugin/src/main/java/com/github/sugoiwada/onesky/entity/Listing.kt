package com.github.sugoiwada.onesky.entity

import com.squareup.moshi.Json

data class ListingResponse(@Json(name = "data") val data: Listing)

data class Listing(
        @Json(name = "TITLE") val title: String,
        @Json(name = "SHORT_DESCRIPTION") val shortDescription: String,
        @Json(name = "DESCRIPTION") val description: String
)