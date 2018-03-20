package com.madonasyombua.growwithgoogleteamproject.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.util.BitmapHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFeedFragment extends DialogFragment {


    public static int RESULT_LOAD_IMAGE = 1;
    public static int RESULT_CAMERA     = 2;

    private OnFragmentInteractionListener mListener;

    private EditText postText;
    private TextView header, postingAs, username, name, attachedImageName;
    private ImageView closeButton, sendButton, imageButton, cameraButton, attachedImage,
            attachmentCloseButton;
    private View view;
    private RelativeLayout attachment;
    private ProgressBar progressBar;

    //private RequestQueue requestQueue;

    private Uri fileUri;
    private Bitmap imageToUpload;
    private BitmapHandler bitmapHandler;

    private String stringCameraImage, stringSomethingWentWrong;

    public PostFeedFragment() {
        // Empty constructor required for DialogFragment
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Title.
     * @param postingAs Label.
     * @param username The posting user's username.
     * @param name The posting user's name.
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

        stringCameraImage = getResources().getString(R.string.camera_image);
        stringSomethingWentWrong = getResources().getString(R.string.something_went_wrong);

        bitmapHandler = new BitmapHandler(new BitmapHandler.OnPostExecuteListener() {
            @Override
            public void onPostExecute(String encodedImage) {
                uploadImageToServer(encodedImage);
            }
        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_feed, container, false);

        header = (TextView) view.findViewById(R.id.header);
        postText = (EditText) view.findViewById(R.id.post);
        postingAs = (TextView) view.findViewById(R.id.postingAs);
        // username = (TextView) view.findViewById(R.id.username);
        name = (TextView) view.findViewById(R.id.name);

        header.setText(getArguments().getString("title"));
        postingAs.setText(getArguments().getString("postingAs"));
//        username.setText(getArguments().getString("username"));
        name.setText(getArguments().getString("name"));

        closeButton = (ImageView) view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );
//get image from gallery
        imageButton = (ImageView) view.findViewById(R.id.imageButton);
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
//take photo
        cameraButton = (ImageView) view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //fileUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +
                           //     File.separator + "_tmp.jpg"));
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(cameraIntent, RESULT_CAMERA);
                    }
                }
        );

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        sendButton = (ImageView) view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setEnabled(false);
                        System.out.println("imageToUpload: " + imageToUpload);
                        if (imageToUpload != null) {
                            setEnabled(false);
                            bitmapHandler.process(imageToUpload);
                        }
                        else {
                            mListener.onDialogSubmit(PostFeedFragment.this, postText.getText().toString(), null);
                        }
                    }
                }
        );

        attachment = (RelativeLayout) view.findViewById(R.id.attachment);
        attachment.setVisibility(View.INVISIBLE);
        attachedImage = (ImageView) view.findViewById(R.id.attachedImage);
        attachedImageName = (TextView) view.findViewById(R.id.attachedImageName);

        attachmentCloseButton = (ImageView) view.findViewById(R.id.attachmentCloseButton);
        attachmentCloseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
       //work on results.
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
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

    public void uploadImageToServer(final String encodedImage) {
        //FIXME: upload image to server get user etc
    }
}
