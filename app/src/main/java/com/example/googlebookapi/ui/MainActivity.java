package com.example.googlebookapi.ui;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.googlebookapi.Book;
import com.example.googlebookapi.BookAdapter;
import com.example.googlebookapi.BookLoader;
import com.example.googlebookapi.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    public static final String SEARCH_MESSAGE = "com.example.Calculator3.MESSAGE";

    public static final String LOG_TAG = MainActivity.class.getName();

    private TextView mEmptyStateTextView;
    private SearchView mSimpleSearchView;
    private ImageView mGoogleIcon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSimpleSearchView = (SearchView) findViewById(R.id.search_view);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_text);
        mGoogleIcon = (ImageView) findViewById(R.id.google_icon);
        mGoogleIcon.setImageResource(R.drawable.ic_google);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mSimpleSearchView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setVisibility(View.GONE);
        } else {
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        mSimpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query==null || query.equals("")){
                    Toast.makeText(getApplicationContext(),R.string.no_search_item, Toast.LENGTH_SHORT).show();
                } else {
                    // Opens BookActivity
                    Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                    String message = mSimpleSearchView.getQuery().toString().replaceAll(" ", "");
                    intent.putExtra(SEARCH_MESSAGE, message);
                    startActivity(intent);

                }

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }





}