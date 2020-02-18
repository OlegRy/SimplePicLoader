package ru.olegry.picloader.internal

import ru.olegry.picloader.api.PicLoader
import ru.olegry.picloader.api.RequestBuilder

/**
 *
 *
 * @author Олег Рябцев
 */
internal class DefaultPicLoader : PicLoader {
    override fun load(url: String): RequestBuilder = RequestBuilder(url)
}