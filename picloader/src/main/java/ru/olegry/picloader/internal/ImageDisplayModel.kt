package ru.olegry.picloader.internal

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.io.InputStream

/**
 *
 *
 * @author Олег Рябцев
 */
internal class ImageDisplayModel {

    private val displaySubject = PublishSubject.create<Bitmap>()
    private val placeholderSubject = PublishSubject.create<Drawable>()
    private val placeholderProgressSubject = PublishSubject.create<Int>()
    private val schedulers: RxSchedulers = Dependencies.schedulers
    private val disposables: CompositeDisposable = CompositeDisposable()

    val displayObservable: Observable<Bitmap>
        get() = displaySubject.hide()

    val placeholderObservable: Observable<Drawable>
        get() = placeholderSubject.hide()

    val placeholderProgressObservable: Observable<Int>
        get() = placeholderProgressSubject.hide()

    fun load(request: Request) {
        Dependencies.loaderApi
            .getImage(request.url)
            .determineProgress { updatePlaceholderProgress(request.options.hasBorder, it) }
            .flatMap(this::handleResponse)
            .map { decodeImageByteArray(it, request.options) }
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
            .doOnSubscribe { showPlaceholder(request.options) }
            .subscribe(
                { onLoad(it, request.options) },
                { error ->
                    error.printStackTrace()
                    showPlaceholder(request.options)
                })
            .connect()
    }

    private fun onLoad(optionalBitmap: Optional<Bitmap>, loadOptions: LoadOptions) {
        if (optionalBitmap.value != null) {
            displaySubject.onNext(optionalBitmap.value)
        } else {
            showPlaceholder(loadOptions)
        }
    }

    private fun handleResponse(response: Response<ResponseBody>): Single<InputStream> = if (response.isSuccessful) {
        Single.just(response.body())
            .map { it.byteStream() }
    } else {
        Single.error<InputStream>(IOException())
    }

    private fun map(pictureStream: InputStream, options: LoadOptions): Bitmap {
        TODO()
    }

    private fun showPlaceholder(loadOptions: LoadOptions) {
        if (loadOptions.placeholder != null) {
            val drawable = if (loadOptions.hasBorder) {
                BorderDrawable(loadOptions.placeholder)
            } else {
                loadOptions.placeholder
            }
            placeholderSubject.onNext(drawable)
        }
    }

    private fun updatePlaceholderProgress(hasBorder: Boolean, progress: Int) {
        if (hasBorder) {
            Log.d("ImageDisplay", "progress: $progress")
            placeholderProgressSubject.onNext(progress)
        }
    }

    private fun <T> Single<T>.determineProgress(onUpdate: (Int) -> Unit): Single<T> {
        Dependencies.downloadProgressListener
            .progress
            .observeOn(schedulers.ui)
            .subscribe { onUpdate.invoke(it) }
            .connect()
        return this
    }

    private fun Disposable.connect() {
        disposables.add(this)
    }
}