package ru.olegry.picloader.internal.common

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * An interface for passing loading progress to subscribers
 *
 * @author Oleg Ryabtsev
 */
internal interface DownloadProgressListener {

    /**
     * Returns [Flowable]. Clients can subscribe to it to track
     * downloading progress and make their own logic using this
     */
    val progress: Flowable<Int>

    /**
     * A method for pushing loading progress updates to all
     * subscribers
     */
    fun update(percent: Int)
}

/**
 * A default implementation for [DownloadProgressListener]
 */
internal class DefaultDownloadProgressListener :
    DownloadProgressListener {

    private val progressSubject = PublishSubject.create<Int>()

    override val progress: Flowable<Int> = progressSubject.toFlowable(BackpressureStrategy.BUFFER)

    override fun update(percent: Int) = progressSubject.onNext(percent)
}
