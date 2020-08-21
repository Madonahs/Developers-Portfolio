/*Copyright (c) 2018 Madona Syombua
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.madonasyombua.growwithgoogleteamproject.util

import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ThumbnailUtils
import android.os.AsyncTask
import android.util.Base64
import java.io.ByteArrayOutputStream
import kotlin.math.min

//This will help us Compresses, encode and scale bitmaps.
class BitmapHandler(private val mListener: OnPostExecuteListener) : AsyncTask<Any?, Void?, Void?>() {
    private var bitmap: Bitmap? = null
    private var encodedImage: String? = null
    /**
     * Compresses the bitmap and base64 encodes
     *
     * @param bitmap The bitmap to process
     */
    fun process(bitmap: Bitmap) {
        this.bitmap = getCompressedBitmap(bitmap)
        execute()
    }

    /**
     * Extracts a small thumbnail from a bitmap.
     *
     * @param bitmap The bitmap to extract the thumbnail from
     * @return The extracted thumbnail
     */
    fun getThumbnail(bitmap: Bitmap?): Bitmap {
        return ThumbnailUtils.extractThumbnail(bitmap, THUMB_SIZE, THUMB_SIZE)
    }

    /**
     * Scales a bitmap to twice the size.
     * @param bitmap The bitmap to scale
     * @return The scaled bitmap
     */
    fun getLarger(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, bitmap.width * 2, bitmap.height * 2, false)
    }

    /**
     * Compresses a bitmap to reduce storage space.
     *
     * @param bitmap The bitmap to compress
     * @return The compressed bitmap
     */
    private fun getCompressedBitmap(bitmap: Bitmap): Bitmap {
        if (bitmap.width > MAX_WIDTH || bitmap.height > MAX_HEIGHT) {
            val scale = min(MAX_WIDTH.toFloat() / bitmap.width,
                    MAX_HEIGHT.toFloat() / bitmap.height)
            val matrix = Matrix()
            matrix.postScale(scale, scale)
            val resizedImage = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            return resizedImage
        }
        return bitmap
    }



    override fun onPostExecute(aVoid: Void?) {
        mListener.onPostExecute(encodedImage)
    }

    interface OnPostExecuteListener {
        fun onPostExecute(encodedImage: String?)
    }

    companion object {
        private const val THUMB_SIZE = 128
        private const val IMAGE_QUALITY = 75 // 0 - 100
        private const val MAX_WIDTH = 960
        private const val MAX_HEIGHT = 540
    }

    override fun doInBackground(vararg p0: Any?): Void? {

        val stream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, stream)
        val array = stream.toByteArray()
        encodedImage = Base64.encodeToString(array, 0)
        return null

    }



}