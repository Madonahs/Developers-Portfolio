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



import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.models.Post;
import com.madonasyombua.growwithgoogleteamproject.util.BitmapHandler;


import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFeedFragment extends DialogFragment {

    public static int RESULT_LOAD_IMAGE = 1;
    public static int RESULT_CAMERA = 2;

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
    private Bitmap imageToUpload;
    private BitmapHandler bitmapHandler;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;


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
        mAuth = FirebaseAuth.getInstance();
//        currentUserId = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("feeds");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("feeds_photos");


        /*bitmapHandler = new BitmapHandler(new BitmapHandler.OnPostExecuteListener() {
            @Override
            public void onPostExecute(String encodedImage) {
                uploadImageToServer();
            }
        });*/
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_feed, container, false);
        ButterKnife.bind(this, view);
        header.setText(getArguments().getString("title"));
        postingAs.setText(getArguments().getString("postingAs"));
        name.setText(getArguments().getString("name"));

        closeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

        imageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    }
                }
        );

        cameraButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //fileUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +
                       //  File.separator + ""));
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(cameraIntent, RESULT_CAMERA);
                    }
                }
        );

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToServer();


            }
        });

        attachment.setVisibility(View.INVISIBLE);
        attachmentCloseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                }
        );

        postText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        setEnabled(true);

        return view;
    }

    public void setEnabled(boolean enabled) {
        progressBar.setVisibility(enabled ? View.GONE : View.VISIBLE);
        sendButton.setVisibility(enabled ? View.VISIBLE : View.GONE);
        imageButton.setEnabled(enabled);
        cameraButton.setEnabled(enabled);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            System.out.println("resultCode: " + resultCode);
            if (resultCode == getActivity().RESULT_OK && data != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    TransitionManager.endTransitions(attachment);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(attachment);
                }

                attachment.setVisibility(View.VISIBLE);
                if (requestCode == RESULT_LOAD_IMAGE) {
                    // Get the Image from data

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
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

        Post post = new Post(postText.getText().toString(), "Madonahs", null);
            reference.push().setValue(post, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference dataReference) {
                    //String error = databaseError.toString();
                    if(databaseError != null) {
                        String error = databaseError.toString();
                    }
                   // Log.i("Firebase Debug", "The error is: " + databaseError.toString());
                }
            });
            postText.setText("");
            Toast.makeText(getContext(), "Sending Feeds", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }
}