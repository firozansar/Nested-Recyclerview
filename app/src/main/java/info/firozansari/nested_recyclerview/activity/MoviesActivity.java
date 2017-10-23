package info.firozansari.nested_recyclerview.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.firozansari.nested_recyclerview.R;
import info.firozansari.nested_recyclerview.adapter.MovieAdapter;
import info.firozansari.nested_recyclerview.helper.BundleKey;
import info.firozansari.nested_recyclerview.helper.Config;
import info.firozansari.nested_recyclerview.helper.Loader;
import info.firozansari.nested_recyclerview.helper.SearchSuggestionsProvider;
import info.firozansari.nested_recyclerview.helper.Endpoint;
import info.firozansari.nested_recyclerview.helper.TopMovieLoader;
import info.firozansari.nested_recyclerview.model.Movie;
import info.firozansari.nested_recyclerview.model.RecyclerItem;

/**
 * Created by topcashback on 20/10/2017.
 */

public class MoviesActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private List<RecyclerItem> mItems;
    private GridLayoutManager mLayoutManager;
    private Map<String, String> mUrlParams;
    private String mEndpoint;
    private int mCurrentPage;
    private int mTotalPageSize;
    private SearchView mSearchView;
    private long mMovieId;
    private boolean mShouldLoadSimilarItems;

    @BindView(R.id.movie_recycler_view)
    protected RecyclerView mRecyclerView;

    private MovieAdapter mAdapter;

    private boolean mLoading = false;

    public static void start(Context context) {
        Intent intent = new Intent(context, MoviesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);
        handleIntent(getIntent());

        mCurrentPage = 1;
        mTotalPageSize = 1;
        mItems = new ArrayList<>();

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mItems.get(position) instanceof Movie ? 1 : 2;
            }
        });

        mRecyclerView = findViewById(R.id.movie_recycler_view);

        // Set layout manager to recyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    //check for scroll down
                    loadMoreItems();
                }
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(BundleKey.EXTRA_MOVIE_ID)) {
            mTotalPageSize = 1;
            mShouldLoadSimilarItems = true;
            initSimilarMoviesEndpointAndUrlParams(mMovieId = intent.getLongExtra(BundleKey.EXTRA_MOVIE_ID, -1), mCurrentPage = 1);
        } else {
            initEndpointAndUrlParams(mCurrentPage);
        }

        mAdapter = new MovieAdapter(MoviesActivity.this, mItems);
        mRecyclerView.setAdapter(mAdapter);
        //loadMovieList(false);

        getSupportLoaderManager().initLoader(Loader.TOP_MOVIE_LOADER_ID, Bundle.EMPTY, topMovieLoaderCallbacks);
    }

    private void initEndpointAndUrlParams(int page) {
        mEndpoint = Endpoint.DISCOVER;
        mUrlParams = new HashMap<>();
        mUrlParams.put("api_key", Config.API_KEY);
        mUrlParams.put("page", String.valueOf(page));
    }

    private void initSearchEndpointAndUrlParams(String query) {
        mEndpoint = Endpoint.SEARCH;
        mUrlParams = new HashMap<>();
        mUrlParams.put("query", query.trim());
        mUrlParams.put("api_key", Config.API_KEY);
    }

    private void initSimilarMoviesEndpointAndUrlParams(long movieId, int page) {
        mEndpoint = "https://api.themoviedb.org/3/movie/" + movieId + "/similar";
        mUrlParams = new HashMap<>();
        mUrlParams.put("page", String.valueOf(page));
        mUrlParams.put("api_key", Config.API_KEY);
    }

    private void loadMoreItems() {
        if (mTotalPageSize > mCurrentPage) {
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

            if (!mLoading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    mLoading = true;
                    Log.v(TAG, "Reached last Item!");

                    // Fetching new data...
                    if (mShouldLoadSimilarItems) {
                        initSimilarMoviesEndpointAndUrlParams(mMovieId, ++mCurrentPage);
                    } else {
                        initEndpointAndUrlParams(++mCurrentPage);
                    }

                    //loadMovieList(true);
                }
            }
        }
    }

    private void loadMovieList(final boolean shouldInsertResults) {
        /*new NetworkUtilsImpl().executeJsonRequest(Request.Method.GET, new StringBuilder(mEndpoint),
                mUrlParams, new NetworkRequestListener() {
                    @Override
                    public void onSuccess(JSONObject jsonResponse) {
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        final int startPosition = mItems.size();
                        MovieListParser movieListParser = new Gson().fromJson(jsonResponse.toString(), MovieListParser.class);
                        mTotalPageSize = movieListParser.getTotalPages();
                        List<Movie> movieList = movieListParser.getResults();
                        if (shouldInsertResults) {
                            mItems.addAll(movieList);
                            mAdapter.notifyItemRangeInserted(startPosition, movieList.size());
                        } else {
                            mItems.clear();
                            mItems.addAll(movieList);
                            mAdapter.setRelatedItemsPosition(RecyclerView.NO_POSITION);
                            mAdapter.notifyDataSetChanged();
                        }

                        mLoading = false;
                    }

                    @Override
                    public void onError(VolleyError error) {
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    private void loadSearchResults() {
        /*new NetworkUtilsImpl().executeJsonRequest(Request.Method.GET, new StringBuilder(mEndpoint),
                mUrlParams, new NetworkRequestListener() {
                    @Override
                    public void onSuccess(JSONObject jsonResponse) {
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        List<Movie> movieList = new Gson().fromJson(jsonResponse.toString(), MovieListParser.class).getResults();
                        mItems.clear();
                        mCurrentPage = 1;
                        mTotalPageSize = 1;
                        for (Movie movie : movieList) {
                            mItems.add(movie);
                        }
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setRelatedItemsPosition(RecyclerView.NO_POSITION);
                        mLoading = false;
                    }

                    @Override
                    public void onError(VolleyError error) {
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Set layout manager to recyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //  Saving the query during of on handle intent
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionsProvider.AUTHORITY, SearchSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            try {
                query = URLEncoder.encode(query, "utf-8");
            } catch (UnsupportedEncodingException e) {
                Log.w(TAG, "Can't encode search query", e);
            }
            initSearchEndpointAndUrlParams(query);
            loadSearchResults();
            mSearchView.clearFocus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) menuItem.getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

       /* MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Show list of movies again
                initEndpointAndUrlParams(mCurrentPage = 1);
                loadMovieList(false);

                return true;
            }
        });
*/
        return true;
    }

    private LoaderManager.LoaderCallbacks<List<Movie>> topMovieLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Movie>>() {
        @Override
        public android.support.v4.content.Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
            return new TopMovieLoader(MoviesActivity.this);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<List<Movie>> loader, List<Movie> movieList) {
            if (movieList != null && movieList.size() > 0) {
                mItems.clear();
                mItems.addAll(movieList);
                mAdapter.setRelatedItemsPosition(RecyclerView.NO_POSITION);
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<List<Movie>> loader) {
            // do nothing, preferring stale data over no data
        }

    };
}
