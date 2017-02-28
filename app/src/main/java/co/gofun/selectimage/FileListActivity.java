package co.gofun.selectimage;

import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.gofun.selectimage.bean.FolderInfo;
import co.gofun.selectimage.util.AsyncFolderLoader;
import co.gofun.selectimage.util.AsyncPathCursor;
import co.gofun.selectimage.view.SquareImageView;

public class FileListActivity extends AppCompatActivity {

    private static final String TAG = "FileListActivity";
    @Bind(R.id.filder_Lv)
    ListView filderLv;

    SimpleAdapter mAdapter;

    List<Map<String, String>> datas = new ArrayList<>();
    private SimpleCursorAdapter simpleCursorAdapeter;
    private List<FolderInfo> folders = new ArrayList<>();
    FoldersAdapter mFoldersAdapter = new FoldersAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        ButterKnife.bind(this);

        Log.i(TAG, "thread id :" + Thread.currentThread().getId());

        mAdapter = new SimpleAdapter(this, datas,
                android.R.layout.simple_list_item_1,
                new String[]{"id"},
                new int[]{android.R.id.text1});

        /*simpleCursorAdapeter = new FoldersAdapter(this,
                android.R.layout.simple_list_item_2, null,
                new String[]{MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);*/

        filderLv.setAdapter(mFoldersAdapter);

//        init();
//        initLoaderManager();
        initFolderLoader();
    }

    private void initFolderLoader() {
        AsyncFolderLoader asyncFolderLoader = new AsyncFolderLoader(this, "");
        asyncFolderLoader.setOnDataLoadCompleteListener(new AsyncFolderLoader.OnDataLoadCompleteListener() {
            @Override
            public void onComplete(List<FolderInfo> folders) {
                FileListActivity.this.folders = folders;
                mFoldersAdapter.notifyDataSetChanged();
            }
        });
//        getSupportLoaderManager().initLoader(1, null, asyncFolderLoader);
//        getSupportLoaderManager().initLoader(2, null, asyncFolderLoader);
    }


    private void initLoaderManager() {
        AsyncPathCursor asyncPathCursor = new AsyncPathCursor(simpleCursorAdapeter, this);

        Bundle bundle = new Bundle();
        getSupportLoaderManager().initLoader(0, bundle, asyncPathCursor);
    }


    class FoldersAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return folders.size();
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
                convertView = LayoutInflater.from(FileListActivity.this).inflate(R.layout.item_folder, parent, false);
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
            Glide.with(FileListActivity.this)
                    .load(folders.get(position).firstImageUrl)
                    .into(vHolder.image);

            final int pos = position;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageListActivity.showActivity(FileListActivity.this, folders.get(pos).imagrUrls);
                }
            });

            return convertView;

        }


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
