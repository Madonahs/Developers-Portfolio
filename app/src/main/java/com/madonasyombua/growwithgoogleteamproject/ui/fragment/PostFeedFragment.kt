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

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUriExposedException
import android.provider.MediaStore
import android.transition.TransitionManager
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.madonasyombua.growwithgoogleteamproject.data.models.Post
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentPostFeedBinding
import kotlinx.android.synthetic.main.feeds_list_item.*
import kotlinx.android.synthetic.main.fragment_post_feed.*

class PostFeedFragment : DialogFragment() {

    private var bitmapHandler: BitmapFactory? = null
    private var uri: FileUriExposedException? = null
    private var reference: DatabaseReference? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = FirebaseAuth.getInstance().currentUser
        val database = FirebaseDatabase.getInstance()
        reference = database.reference.child("feeds")
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference.child("feeds_photos")
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostFeedBinding.bind(view)

        binding.header.text = arguments?.getString("title")
        binding.postingAs.text = arguments?.getString("postingAs")
        binding.name.text = arguments?.getString("username")
        binding.closeButton.setOnClickListener { dismiss() }
        binding.imageButton.setOnClickListener {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE)
        }
        binding.cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(cameraIntent, RESULT_CAMERA)
        }
        binding.sendButton.setOnClickListener { uploadImageToServer() }
        binding.attachment.visibility = View.INVISIBLE
        binding.attachmentCloseButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                TransitionManager.endTransitions(binding.attachment)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(binding.attachment)
            }
            binding.attachedImage.setImageBitmap(null)
            binding.attachedImageName.text = ""
            binding.attachment.visibility = View.INVISIBLE
        }
        binding.post.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        setEnabled(true)

    }

    private fun setEnabled(enabled: Boolean) {
        progressBar.visibility = if (enabled) View.GONE else View.VISIBLE
        sendButton.visibility = if (enabled) View.VISIBLE else View.GONE
        imageButton.isEnabled = enabled
        cameraButton.isEnabled = enabled
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            println("resultCode: $resultCode")
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    TransitionManager.endTransitions(attachment)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(attachment)
                }
                attachment.visibility = View.VISIBLE
                val imageToUpload: Bitmap

            }
        } catch (e: Exception) {

        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri?)
        fun onDialogSubmit()
        fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
        fun onDialogSubmit(dialog: PostFeedFragment?, text: String?, fileName: String?)
    }

    private fun uploadImageToServer() {

        val post = Post(text.text.toString(), currentUser?.displayName, null)
        reference?.push()?.setValue(post) { databaseError: DatabaseError?, _: DatabaseReference? ->
            //String error = databaseError.toString();
            if (databaseError != null) {
                val error = databaseError.toString()
            }
        }
        text.text = ""
        Toast.makeText(context, "Sending Feeds", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onResume() {
        val params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params

        super.onResume()
    }

    companion object {
        private const val RESULT_LOAD_IMAGE = 1
        private const val RESULT_CAMERA = 2
        fun newInstance(
            title: String?,
            postingAs: String?,
            username: String?,
            name: String?
        ): PostFeedFragment {
            val fragment = PostFeedFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("postingAs", postingAs)
            args.putString("username", username)
            args.putString("name", name)
            fragment.arguments = args
            return fragment
        }
    }
}