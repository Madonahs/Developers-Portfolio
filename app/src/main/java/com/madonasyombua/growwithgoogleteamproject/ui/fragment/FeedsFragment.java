package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.madonasyombua.growwithgoogleteamproject.AddFeeds;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.adapter.FeedsAdapter;
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener;
import com.madonasyombua.growwithgoogleteamproject.models.Feeds;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FeedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final String TAG = "FeedsFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private OnFragmentInteractionListener mListener;
    private List<Feeds> feedsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeedsAdapter adapter;

    // FIXME: 3/5/2018 This is the data being received from AddFeeds but at the first run it is null.
//    String feedtitle = getArguments().getString("FEEDS_TITLE");
//    String feedDescription = getArguments().getString("FEEDS_DESCRIPTION");
//
/// /    String feedtitle = "ahahkfdh";
//    String feedDescription = "adkjlfajdlfaj";

    String feedtitle;
    String feedDescription;

    public FeedsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedsFragment newInstance(String param1, String param2) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_feeds, container, false);
//        //Home ToolBar
//
//       /* Toolbar toolbar =  view.findViewById(R.id.feedsToolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home);*/

        recyclerView = view.findViewById(R.id.feeds_recyclerview);
        adapter = new FeedsAdapter(feedsList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//       dataFromActivity();
//       testFeeds();

        //starting the float button
        FloatingActionButton addFeeds = view.findViewById(R.id.add_feeds);
        if (addFeeds != null)
            addFeeds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFeedsActivity(view);
                }
            });
        getLoaderManager().initLoader(0, null, this);


        if (feedsList == null) {
            return null;
        } else {
            try{
                feedtitle = this.getArguments().getString("FEEDS_TITLE");
                feedDescription = this.getArguments().getString("FEEDS_DESCRIPTION");
            } catch (NullPointerException e){
                Log.e(TAG, "dataFromActivity: " + e);
            }
        }

        feedsList.add(new Feeds(feedtitle, feedDescription));
        adapter.setFeedsList(feedsList);
        adapter.notifyDataSetChanged();
        return view;
    }

    private void dataFromActivity() {

//        this.getArguments().getString("FEEDS_TITLE");
//        this.getArguments().getString("FEEDS_DESCRIPTION");
//
        try {
            feedtitle = this.getArguments().getString("FEEDS_TITLE");
            feedDescription = this.getArguments().getString("FEEDS_DESCRIPTION");
            feedsList.add(new Feeds(feedtitle, feedDescription));
            adapter.setFeedsList(feedsList);
            adapter.notifyDataSetChanged();

        } catch (NullPointerException e) {
            Log.e(TAG, "dataFromActivity: " + e);
        }
        Toast.makeText(getContext(), "ignore", Toast.LENGTH_SHORT).show();


//         else {
//            feedsList.add(new Feeds(feedtitle, feedDescription));
//            adapter.setFeedsList(feedsList);
//            adapter.notifyDataSetChanged();
//        }
    }


    private void testFeeds() {
        feedsList.add(new Feeds("Stuff", R.drawable.logo));
        feedsList.add(new Feeds("Stuffs", R.drawable.logo));
        feedsList.add(new Feeds("Stuffss", R.drawable.logo));
        feedsList.add(new Feeds("Stuffssss", R.drawable.logo));
        feedsList.add(new Feeds("Stuffsssss", R.drawable.logo));
        feedsList.add(new Feeds("Stuffssssss", R.drawable.logo));

        adapter.notifyDataSetChanged();
    }

    //my method to open the FeedsActivity
    private void openFeedsActivity(@SuppressWarnings("unused") View view) {
        Intent intent = new Intent(this.getActivity(), AddFeeds.class);
        startActivity(intent);
        Toast.makeText(getContext(), "Adding Feed", Toast.LENGTH_SHORT).show();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
