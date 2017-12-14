package com.github.sugoiwada.onesky.client

import com.github.kittinunf.fuel.core.Client
import com.github.kittinunf.fuel.core.FuelManager
import com.github.sugoiwada.onesky.networking.OneskyClient
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import java.io.File

class OneskyTest {

    private val onesky: OneskyClient by lazy {
        val apiKey = "<api-key>"
        val apiSecret = "<api-secret>"
        val projectId = 123456789
        OneskyClient(apiKey, apiSecret, projectId)
    }

    @Test
    fun testDownload() {
        val client = mockk<Client>()

        val res = javaClass.classLoader.getResource("strings.xml")

        val answer = res.openStream().reader().readText()

        every { client.executeRequest(any()).statusCode } returns 200
        every { client.executeRequest(any()).responseMessage } returns "OK"
        every { client.executeRequest(any()).dataStream } returns res.openStream()
        every { client.executeRequest(any()).data } returns answer.toByteArray()

        FuelManager.instance.client = client

        onesky.download("en", "strings2.xml", File(File(res.file).parentFile, "strings2.xml"))
                .test()
                .assertNoErrors()
                .assertValue {
                    it.get() == answer
                }
    }
}
