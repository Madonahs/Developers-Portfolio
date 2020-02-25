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
package com.madonasyombua.growwithgoogleteamproject.ui.fragment

import android.app.DialogFragment
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.madonasyombua.growwithgoogleteamproject.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ImageDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        val view = inflater.inflate(R.layout.fragment_image_dialog, container, false)
        val image = view.findViewById<ImageView>(R.id.image)
        val bitmap = arguments.getParcelable<Bitmap>("bitmap")
        image.setImageBitmap(bitmap)
        val closeButton = view.findViewById<ImageView>(R.id.closeButton)
        closeButton.setOnClickListener { v: View? -> dismiss() }
        Objects.requireNonNull(dialog.window)!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return view
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onResume() { // Get existing layout params for the window
        val params = Objects.requireNonNull(dialog.window)?.attributes
        // Assign window properties to fill the parent
        params!!.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = params
        // Call super onResume after sizing
        super.onResume()
    }

    fun show(fm: FragmentManager?, fragment_image_dialog: String?) {}

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param bitmap The image to display.
         * @return A new instance of fragment PostDialog.
         */
        @JvmStatic
        fun newInstance(bitmap: Bitmap?): ImageDialog {
            val fragment = ImageDialog()
            val args = Bundle()
            args.putParcelable("bitmap", bitmap)
            fragment.arguments = args
            return fragment
        }
    }
}