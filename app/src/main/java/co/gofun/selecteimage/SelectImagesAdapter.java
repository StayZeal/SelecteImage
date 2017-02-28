package co.gofun.selecteimage;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.gofun.selecteimage.bean.ImageInfo;
import co.gofun.selecteimage.view.SquareImageView;

public class SelectImagesAdapter extends RecyclerView.Adapter {

    static final int SELECTI_MAX_SIZE = 9;

    private List<ImageInfo> images;
    private Context mContext;
    private ArrayList<String> checkImages = new ArrayList<>();

    public SelectImagesAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.countCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = myViewHolder.getAdapterPosition();
                if (isChecked) {
                    checkImages.add(images.get(pos).url);
//                    buttonView.setText(checkImages.size() + 1 + "");
                } else {
                    checkImages.remove(images.get(pos).url);
                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Glide.with(mContext)
                .load(images.get(position).url)
                .override(300,300)
                .into(myViewHolder.contentIv);
        if (images.get(position).checked) {
//            myViewHolder.countCb.setText();
        } else {
            myViewHolder.countCb.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    private void updateCount(int pos) {

    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.content_Iv)
        SquareImageView contentIv;
        @Bind(R.id.count_Cb)
        CheckBox countCb;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
