package co.gofun.selectimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.gofun.selectimage.ImageListActivity;
import co.gofun.selectimage.R;
import co.gofun.selectimage.bean.FolderInfo;
import co.gofun.selectimage.view.SquareImageView;

public class FolderAdapter extends BaseAdapter {


    private List<FolderInfo> folders;
    private Context mContext;

    public FolderAdapter(List<FolderInfo> folders, Context mContext) {
        this.folders = folders;
        this.mContext = mContext;
    }

    public void setData(List<FolderInfo> folders) {
        this.folders = folders;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return folders == null ? 0 : folders.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_folder, parent, false);
            vHolder = new ViewHolder(convertView);

            convertView.setTag(vHolder);

        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
         /*   Cursor cursor = getCursor();
            if (cursor != null && cursor.move(position)) {
                vHolder.title.setText(cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)));
                long date = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));
                vHolder.content.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(date)));
//                vHolder.image.setImageURI(Uri.parse(cursor.getString(1)));
          *//*      Glide.with(FileListActivity.this)
                        .load(cursor.getString(1))
                        .into(vHolder.image);*//*
                Log.i(TAG, cursor.getString(5));
                Log.i(TAG, cursor.getString(6));
            }*/

        vHolder.title.setText(folders.get(position).name);
        vHolder.content.setText("共" + folders.get(position).imageCount + "张");
        Glide.with(mContext)
                .load(folders.get(position).firstImageUrl)
                .into(vHolder.image);

        final int pos = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageListActivity.showActivity(mContext, folders.get(pos).imagrUrls);
            }
        });

        return convertView;

    }


    static class ViewHolder {
        @Bind(R.id.image)
        SquareImageView image;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}