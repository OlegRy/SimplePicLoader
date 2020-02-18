package ru.olegry.picloader.internal

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 *
 *
 * @author Олег Рябцев
 */
internal interface DownloadProgressListener {

    val progress: Observable<Int>
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}

internal class DefaultDownloadProgressListener : DownloadProgressListener {

    private val progressSubject = PublishSubject.create<Int>()

    override val progress: Observable<Int> = progressSubject.hide()

    override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
        progressSubject.onNext(((bytesRead * 100) / contentLength).toInt())
    }
}
