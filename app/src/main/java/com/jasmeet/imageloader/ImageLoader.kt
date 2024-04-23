package com.jasmeet.imageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object ImageLoader2 {

    private val handler = Handler(Looper.getMainLooper())

    fun loadImageFromUrl(
        url: String,
        onSuccess: (Bitmap) -> Unit,
        onError: (String) -> Unit,
        onLoading: () -> Unit
    ) {

        thread(start = true) {
            try {
                handler.post { onLoading() }

                val connection = URL(url).openConnection() as HttpURLConnection
                connection.apply {
                    doInput = true
                    connect()
                }

                val input: InputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(input)

                if (bitmap != null) {
                    handler.post { onSuccess(bitmap) }
                } else {
                    handler.post { onError("Error in connection") }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { onError("Error: ${e.message}") }
            }
        }
    }

}





