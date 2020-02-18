package ru.olegry.picloader.api

import ru.olegry.picloader.internal.DefaultPicLoader

/**
 *
 *
 * @author Олег Рябцев
 */
object PicLoaderProvider {

    private val instance: PicLoader by lazy { DefaultPicLoader() }

    @JvmStatic
    fun get() = instance
}