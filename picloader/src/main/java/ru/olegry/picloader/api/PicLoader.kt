package ru.olegry.picloader.api

/**
 *
 *
 * @author Олег Рябцев
 */
interface PicLoader {
    fun load(url: String): RequestBuilder
}