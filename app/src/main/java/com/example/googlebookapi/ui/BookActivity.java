package com.example.googlebookapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.googlebookapi.core.Book;
import com.example.googlebookapi.core.BookAdapter;
import com.example.googlebookapi.R;
import com.example.googlebookapi.impl.GoogleApi;
import com.example.googlebookapi.model.BookVolumes;
import com.example.googlebookapi.model.ImageLinks;
import com.example.googlebookapi.model.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.googlebookapi.impl.GoogleApi.BASE_URL;

public class BookActivity extends AppCompatActivity {

    public static final String LOG_TAG = BookActivity.class.getName();

    /** RecyclerView */
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    /** Adapter for the list of Books */
    private BookAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private View mLoadingIndicator;
    private TextView mNoBooksTextView;

    /** Search String for the SearchView */
    private String mSearchString;

    /** New Book object for the adapter of recyclerView */
    private ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        mRecyclerView = findViewById(R.id.recycler_view);
        mNoBooksTextView = (TextView) findViewById(R.id.invisible_text_view);
        mLoadingIndicator = (View) findViewById(R.id.loading_spinner);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(new ArrayList<Book>());
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Get the Intent that started this activity and extract the string
        getIntentMethod();

        // Retrofit parses JSON and mAdapter adds all data to the Book object.
        retrofit(mSearchString);

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

    private List<Book> retrofit(String searchString) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GoogleApi api = retrofit.create(GoogleApi.class);

        Call<BookVolumes> call = api.create(searchString,"20");

        call.enqueue(new Callback<BookVolumes>() {
            @Override
            public void onResponse(Call<BookVolumes> call, Response<BookVolumes> response) {
                Log.d("MainActivity", " Response String: " + response.toString());
                Log.d("MainActivity", " Response Body String: " + response.body());

                try {
                    ArrayList<Item> itemList = response.body().getItems();

                    for (int i = 0; i < itemList.size(); i++) {

                        String title;
                        try {
                            title = itemList.get(i).getVolumeInfo().getTitle();
                        } catch (Exception e) {
                            title = "No title";
                        }

                        String authors = "";
                        try {
                            List<String> authorsArray = itemList.get(i).getVolumeInfo().getAuthors();

                            for (int j = 0; j < authorsArray.size(); j++) {
                                if (authorsArray.size() == 1) {
                                    authors = authorsArray.get(j);
                                } else {
                                    authors = authors + ", " + authorsArray.get(j);
                                }
                            }
                        } catch (Exception e) {
                            authors = "Unknown author";
                        }

                        String pagesCountStr;
                        try {
                            int pagesCount = itemList.get(i).getVolumeInfo().getPageCount();
                            pagesCountStr = "Pages: " + pagesCount;

                        } catch (Exception e) {
                            pagesCountStr = "Not specified";
                        }

                        String publishedDate;
                        try {
                            publishedDate = "P. Date: " + itemList.get(i).getVolumeInfo().getPublishedDate();
                        } catch (Exception e) {
                            publishedDate = "Not specified";
                        }

                        String averageRatingStr = "0.0";
                        try {
                            double averageRating = itemList.get(i).getVolumeInfo().getAverageRating();
                            averageRatingStr = String.valueOf(averageRating);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String smallThumbnail = null;
                        try {
                            ImageLinks imageLinks = itemList.get(i).getVolumeInfo().getImageLinks();
                            smallThumbnail = imageLinks.getSmallThumbnail();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(LOG_TAG, "Null smallThumbnail!");
                        }

                        String infoLink = "";
                        try {
                            infoLink = itemList.get(i).getVolumeInfo().getInfoLink();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(LOG_TAG, "Null infoLink!");
                        }

                        Book book = new Book(title, authors.replaceFirst(", ", ""), pagesCountStr, publishedDate, averageRatingStr, smallThumbnail, infoLink);
                        // Add the new {@link Book} to the list of books.
                        books.add(book);

                        Log.d(LOG_TAG,"Title: " + title + "\n" +
                                "Authors: " + authors + "\n" +
                                "PageCount: " + pagesCountStr + "\n" +
                                "PublishedDate: " + publishedDate + "\n" +
                                "AverageRating: " + averageRatingStr + "\n" +
                                "SmallTumbnail: " + smallThumbnail + "\n" +
                                "InfoLink: " + infoLink + "\n");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "Exception while parsing JSON!");
                }

                // Hide loading indicator because the data has been loaded
                mLoadingIndicator.setVisibility(View.GONE);

                // Set empty state text to display "No books found."
                mNoBooksTextView.setVisibility(View.VISIBLE);

                // Clear the adapter of previous book data
                mAdapter.clear();

                // If there is a valid list of {@link Book}s, then add them to the adapter's
                // data set. This will trigger the ListView to update.
                if (books != null && !books.isEmpty()) {
                    mAdapter.addAll(books);
                    mAdapter.notifyDataSetChanged();
                    mNoBooksTextView.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<BookVolumes> call, Throwable t) {
                Log.d(LOG_TAG, "Retrofit Failure Exception message:" + t.getMessage());
            }

        });

        return books;
    }

    private void getIntentMethod(){
        Intent intent = getIntent();
        mSearchString = intent.getStringExtra(MainActivity.SEARCH_MESSAGE);
        //message = message.contains(".") ? message : message.replaceAll("0*$", "").replaceAll("\\.$", "");
        Log.i(LOG_TAG, "SEARCH STRING: " + mSearchString);
    }
}
