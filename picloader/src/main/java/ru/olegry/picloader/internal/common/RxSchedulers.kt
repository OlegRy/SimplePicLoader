package ru.olegry.picloader.internal.common

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * An interface for providing RxJava schedulers
 *
 * @author Oleg Ryabtsev
 */
internal interface RxSchedulers {

    val io: Scheduler
    val ui: Scheduler
}

/**
 * Default implementation for [RxSchedulers]
 */
internal class DefaultRxSchedulers : RxSchedulers {

    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
}
