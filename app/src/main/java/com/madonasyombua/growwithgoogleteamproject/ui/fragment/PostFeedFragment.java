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
package com.madonasyombua.growwithgoogleteamproject.ui.fragment;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.data.models.Post;
import com.madonasyombua.growwithgoogleteamproject.util.BitmapHandler;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madona on 5/8/18.
 * A simple {@link Fragment} subclass.
 */
public class PostFeedFragment extends DialogFragment {

    public static final int RESULT_LOAD_IMAGE = 1;
    public static final int RESULT_CAMERA = 2;

    private OnFragmentInteractionListener mListener;
    private static final String TAG = PostFeedFragment.class.getName();
    @BindView(R.id.post)
    EditText postText;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.postingAs)
    TextView postingAs;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.attachedImageName)
    TextView attachedImageName;
    @BindView(R.id.closeButton)
    ImageView closeButton;
    @BindView(R.id.sendButton)
    ImageView sendButton;
    @BindView(R.id.imageButton)
    ImageView imageButton;
    @BindView(R.id.cameraButton)
    ImageView cameraButton;
    @BindView(R.id.attachedImage)
    ImageView attachedImage;
    @BindView(R.id.attachmentCloseButton)
    ImageView attachmentCloseButton;
    @BindView(R.id.attachment)
    RelativeLayout attachment;
    @BindString(R.string.camera_image) String stringCameraImage;
    @BindString(R.string.something_went_wrong)String stringSomethingWentWrong;
    private View view;
    private ProgressBar progressBar;
    private Uri fileUri;
    private BitmapHandler bitmapHandler;

    private DatabaseReference reference;
    private FirebaseUser currentUser;


    public PostFeedFragment() {
        // Empty constructor required for DialogFragment
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param title     Title.
     * @param postingAs Label.
     * @param username  The posting user's username.
     * @param name      The posting user's name.
     * @return A new instance of fragment PostDialog.
     */
    public static PostFeedFragment newInstance(String title, String postingAs, String username, String name) {
        PostFeedFragment fragment = new PostFeedFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("postingAs", postingAs);
        args.putString("username", username);
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("feeds");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("feeds_photos");


        /*bitmapHandler = new BitmapHandler(new BitmapHandler.OnPostExecuteListener() {
            @Override
            public void onPostExecute(String encodedImage) {
                uploadImageToServer();
            }
        });*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_feed, container, false);
        ButterKnife.bind(this, view);
        assert getArguments() != null;
        header.setText(getArguments().getString("title"));
        postingAs.setText(getArguments().getString("postingAs"));
        name.setText(getArguments().getString("username"));

        closeButton.setOnClickListener(
                v -> dismiss()
        );

        imageButton.setOnClickListener(
                v -> {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                }
        );

        cameraButton.setOnClickListener(
                v -> {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //fileUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +
                   //  File.separator + ""));
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(cameraIntent, RESULT_CAMERA);
                }
        );

        progressBar = view.findViewById(R.id.progressBar);
        sendButton.setOnClickListener(v -> uploadImageToServer());

        attachment.setVisibility(View.INVISIBLE);
        attachmentCloseButton.setOnClickListener(
                v -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        TransitionManager.endTransitions(attachment);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(attachment);
                    }
                    attachedImage.setImageBitmap(null);
                    attachedImageName.setText("");
                    attachment.setVisibility(View.INVISIBLE);
                }
        );

        postText.requestFocus();
        Objects.requireNonNull(getDialog().getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        setEnabled(true);

        return view;
    }

    public void setEnabled(boolean enabled) {
        progressBar.setVisibility(enabled ? View.GONE : View.VISIBLE);
        sendButton.setVisibility(enabled ? View.VISIBLE : View.GONE);
        imageButton.setEnabled(enabled);
        cameraButton.setEnabled(enabled);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            System.out.println("resultCode: " + resultCode);
            if (resultCode == Activity.RESULT_OK && data != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    TransitionManager.endTransitions(attachment);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(attachment);
                }

                attachment.setVisibility(View.VISIBLE);
                Bitmap imageToUpload;
                if (requestCode == RESULT_LOAD_IMAGE) {
                    // Get the Image from data

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    assert selectedImage != null;
                    Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    // Set the Image in ImageView after decoding the String
                    imageToUpload = BitmapFactory.decodeFile(imgDecodableString);
                    Bitmap thumbnail = bitmapHandler.getThumbnail(imageToUpload);
                    attachedImage.setImageBitmap(thumbnail);

                    String fileName;
                    if (imgDecodableString.contains("/")) {
                        String[] split = imgDecodableString.split("/");
                        fileName = split[split.length - 1];
                    } else {
                        fileName = imgDecodableString;
                    }
                    attachedImageName.setText(fileName);
                } else if (requestCode == RESULT_CAMERA) {
                    imageToUpload = BitmapFactory.decodeFile(fileUri.getPath());
                    Bitmap thumbnail = bitmapHandler.getThumbnail(imageToUpload);
                    attachedImage.setImageBitmap(thumbnail);
                    attachedImageName.setText(stringCameraImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(view, stringSomethingWentWrong, Snackbar.LENGTH_LONG).show();
            attachment.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);

        void onDialogSubmit();

        void onItemClick(AdapterView<?> parent, View view, int position, long id);

        void onDialogSubmit(final PostFeedFragment dialog, final String text, final String fileName);
    }

    public void uploadImageToServer() {

       //TODO 1: Enable sending images to DataBase
        //TODO 2: ensure we get the following

        Post post = new Post(postText.getText().toString(), currentUser.getDisplayName(), null);
            reference.push().setValue(post, (databaseError, dataReference) -> {
                //String error = databaseError.toString();
                if(databaseError != null) {
                    String error = databaseError.toString();
                }
               // Log.i("Firebase Debug", "The error is: " + databaseError.toString());
            });
            postText.setText("");
            Toast.makeText(getContext(), "Sending Feeds", Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        // Get existing layout params for the window
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog().getWindow()).getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
        // Call super onResume after sizing
        super.onResume();
    }
}