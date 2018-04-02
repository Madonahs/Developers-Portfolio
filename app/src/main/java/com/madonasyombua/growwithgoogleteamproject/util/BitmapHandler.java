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
package com.madonasyombua.growwithgoogleteamproject.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by madon on 3/10/2018.
 */
//This will help us Compresses, encode and scale bitmaps.
public class BitmapHandler extends AsyncTask<Void, Void, Void> {

    public static int THUMB_SIZE        = 128;
    public static int IMAGE_QUALITY     = 75;  // 0 - 100
    public static int MAX_WIDTH         = 960;
    public static int MAX_HEIGHT        = 540;

    private OnPostExecuteListener mListener;

    private Bitmap bitmap;
    private String encodedImage;


    public BitmapHandler(OnPostExecuteListener listener) {
        mListener = listener;
    }

    /**
     * Compresses the bitmap and base64 encodes
     *
     * @param bitmap The bitmap to process
     */
    public void process(Bitmap bitmap) {
        this.bitmap = getCompressedBitmap(bitmap);
        execute();
    }

    /**
     * Extracts a small thumbnail from a bitmap.
     *
     * @param bitmap The bitmap to extract the thumbnail from
     * @return The extracted thumbnail
     */
    public Bitmap getThumbnail(Bitmap bitmap) {
        return ThumbnailUtils.extractThumbnail(bitmap, THUMB_SIZE, THUMB_SIZE);
    }

    /**
     * Scales a bitmap to twice the size.
     * @param bitmap The bitmap to scale
     * @return The scaled bitmap
     */
    public Bitmap getLarger(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 2, bitmap.getHeight() * 2, false);
    }

    /**
     * Compresses a bitmap to reduce storage space.
     *
     * @param bitmap The bitmap to compress
     * @return The compressed bitmap
     */
    public Bitmap getCompressedBitmap(Bitmap bitmap) {
        if (bitmap.getWidth() > MAX_WIDTH || bitmap.getHeight() > MAX_HEIGHT) {
            float scale = Math.min(((float) MAX_WIDTH / bitmap.getWidth()),
                    ((float) MAX_HEIGHT / bitmap.getHeight()));
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            Bitmap resizedImage = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return resizedImage;
        }
        return bitmap;
    }

    @Override
    protected Void doInBackground(Void... params) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, stream);

        byte[] array = stream.toByteArray();
        encodedImage = Base64.encodeToString(array, 0);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.onPostExecute(encodedImage);
    }

    public interface OnPostExecuteListener {
        void onPostExecute(String encodedImage);
    }

}