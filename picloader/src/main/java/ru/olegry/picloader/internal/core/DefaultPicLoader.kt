package ru.olegry.picloader.internal.core

import ru.olegry.picloader.api.PicLoader
import ru.olegry.picloader.api.RequestBuilder

/**
 * A default implementation for API [PicLoader]
 *
 * @author Oleg Ryabtsev
 */
internal class DefaultPicLoader : PicLoader {
    override fun load(url: String): RequestBuilder = RequestBuilder(url)
}