package com.example.googlebookapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.googlebookapi.Book;
import com.example.googlebookapi.BookAdapter;
import com.example.googlebookapi.BookLoader;
import com.example.googlebookapi.R;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = BookActivity.class.getName();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    /** Adapter for the list of Books */
    private BookAdapter mAdapter;

    /**
     * Sample JSON response for a Google Books API query
     */
    private static String mBookRequestUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int BOOK_LOADER_ID = 1;

    /** TextView that is displayed when the list is empty */
    private View mLoadingIndicator;
    private TextView mNoBooksTextView;

    /** Search String*/
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.SEARCH_MESSAGE);
        message = message.indexOf(".") < 0 ? message : message.replaceAll("0*$", "").replaceAll("\\.$", "");
        searchString = message;

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(new ArrayList<Book>());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mNoBooksTextView = (TextView) findViewById(R.id.invisible_text_view);
        mLoadingIndicator = (View) findViewById(R.id.loading_spinner);


        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        mAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Book currentBook = mAdapter.getPosition(position);

                Uri bookUri = Uri.parse(currentBook.getInfoLink());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW,bookUri);
                startActivity(websiteIntent);

            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        // Create a new loader for the given URL
        return new BookLoader(this, mBookRequestUrl + searchString);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        mLoadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mNoBooksTextView.setVisibility(View.VISIBLE);
        // Clear the adapter of previous book data
        //mAdapter.clear();
        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
            mAdapter.notifyDataSetChanged();
            mNoBooksTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}