package ru.olegry.picloader.api

import ru.olegry.picloader.internal.core.DefaultPicLoader

/**
 * Special provider object for getting an instance of [PicLoader]
 *
 * @author Oleg Ryabtsev
 */
object PicLoaderProvider {

    private val instance: PicLoader by lazy { DefaultPicLoader() }

    @JvmStatic
    fun get() = instance
}