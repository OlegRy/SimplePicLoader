package ru.olegry.picloader.api

/**
 * The main interface which starts image loading
 *
 * @author Oleg Ryabtsev
 */
interface PicLoader {

    /**
     * Creates a new [RequestBuilder] instance. [RequestBuilder] allows you
     * to provide different options for loading such as crop, border and placeholder
     */
    fun load(url: String): RequestBuilder
}