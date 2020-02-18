package ru.olegry.picloader.internal

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 *
 * @author Олег Рябцев
 */
internal interface RxSchedulers {

    val io: Scheduler
    val ui: Scheduler
}

internal class DefaultRxSchedulers : RxSchedulers {

    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
}
