package info.firozansari.nested_recyclerview.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

import info.firozansari.nested_recyclerview.R;
import info.firozansari.nested_recyclerview.model.Backdrop;

/**
 * @author msahakyan
 */
public class BackdropPagerAdapter extends PagerAdapter {

    private LayoutInflater mLayoutInflater;
    private List<Backdrop> mBackdropList;

    public BackdropPagerAdapter(Context context, List<Backdrop> backdropList) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBackdropList = backdropList;
    }

    @Override
    public int getCount() {
        return mBackdropList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.backdrop_pager_item, container, false);

        ImageView backdropImageView = (ImageView) itemView.findViewById(R.id.backdrop_image);
        //ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        //backdropImageView.setImageUrl(Endpoint.IMAGE + "/w500/" + mBackdropList.get(position).getFilePath(), imageLoader);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
