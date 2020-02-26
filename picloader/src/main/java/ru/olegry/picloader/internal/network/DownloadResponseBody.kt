package ru.olegry.picloader.internal.network

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import okio.Source
import ru.olegry.picloader.internal.common.DownloadProgressListener

/**
 * An OkHttp [ResponseBody] which allows us to track
 * loading progress
 *
 * @property responseBody original [ResponseBody]
 * @property downloadProgressListener an instance of listener for passing progress values
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
            private var totalBytesRead =
                NO_BYTES

            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) {
                    bytesRead
                } else {
                    NO_BYTES
                }
                val percent = if (bytesRead == -1L) {
                    DOWNLOAD_FINISHED_PERCENT
                } else {
                    ((totalBytesRead.toFloat() / responseBody.contentLength().toFloat()) * 100f).toInt()
                }
                downloadProgressListener.update(percent)
                return bytesRead
            }
        }
    }

    companion object {

        private const val DOWNLOAD_FINISHED_PERCENT = 100
        private const val NO_BYTES = 0L
    }
}