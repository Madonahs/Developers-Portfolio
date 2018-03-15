package com.madonasyombua.growwithgoogleteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class SearchViewTest extends AppCompatActivity {

    // SearchView
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_test);

        // Toolbar with Search Icon
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_help);
        setSupportActionBar(toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        searchView.setSuggestions(getResources().getStringArray(R.array.search_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                // Do something something
            }

            @Override
            public void onSearchViewClosed() {
                // Do something something
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWord = matches.get(0);
                if (!TextUtils.isEmpty(searchWord)) {
                    searchView.setQuery(searchWord, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
