package info.firozansari.nested_recyclerview.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.firozansari.nested_recyclerview.R;
import info.firozansari.nested_recyclerview.adapter.ItemDetailPagerAdapter;
import info.firozansari.nested_recyclerview.helper.BundleKey;
import info.firozansari.nested_recyclerview.helper.ZoomOutPageTransformer;
import info.firozansari.nested_recyclerview.model.RecyclerItem;
import info.firozansari.nested_recyclerview.model.RecyclerListItem;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @BindView(R.id.vpPager)
    ViewPager mViewPager;

    private ItemDetailPagerAdapter mAdapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Initialization ButterKnife
        ButterKnife.bind(this);

        List<RecyclerItem> movieList = getMoviesFromIntent();

        mAdapterViewPager = new ItemDetailPagerAdapter(getSupportFragmentManager(), movieList);
        mViewPager.setAdapter(mAdapterViewPager);

        // Attach the page change listener inside the activity
        mViewPager.addOnPageChangeListener(mPageChangeListener);

        // Attach page transformer
        mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            // Code goes here
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Code goes here
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // Code goes here
        }
    };

    private List<RecyclerItem> getMoviesFromIntent() {
        return ((RecyclerListItem) getIntent().getSerializableExtra(BundleKey.EXTRA_MOVIE_LIST)).getItems();
    }
}
