package ru.olegry.picloader.internal

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import okio.Source

/**
 *
 *
 * @author Олег Рябцев
 */
internal class DownloadResponseBody(
    private val responseBody: ResponseBody,
    private val downloadProgressListener: DownloadProgressListener
) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentLength(): Long = responseBody.contentLength()

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            private var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) {
                    bytesRead
                } else {
                    0
                }
                downloadProgressListener.update(bytesRead, responseBody.contentLength(), bytesRead == -1L)
                return super.read(sink, byteCount)
            }
        }
    }
}