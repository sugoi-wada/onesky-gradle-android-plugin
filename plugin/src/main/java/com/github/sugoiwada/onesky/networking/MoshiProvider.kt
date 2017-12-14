package com.github.sugoiwada.onesky.networking

import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.rx.rx_object
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()!!

inline fun <reified T : Any> Request.rx_object() = rx_object(moshiDeserializerOf<T>())

inline fun <reified T : Any> moshiDeserializerOf() = object : ResponseDeserializable<T> {

    override fun deserialize(content: String): T? =
            moshi.adapter(T::class.java).fromJson(content)
}
