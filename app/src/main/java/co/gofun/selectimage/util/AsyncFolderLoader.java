package co.gofun.selectimage.util;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import co.gofun.selectimage.bean.FolderInfo;


public class AsyncFolderLoader implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String TAG = "AsyncFolderLoader";
    private static final int GET_VIDE0 = 1;
    private static final int GET_IMAGE = 2;

    private AppCompatActivity mContext;
    private String mCurFilter;
    private Map<String, List> folders = new HashMap<>();

    private OnDataLoadCompleteListener onDataLoadCompleteListener;
    private List<FolderInfo> folderInfos = new ArrayList<>();

    public AsyncFolderLoader(AppCompatActivity mContext, String mCurFilters) {
        this.mContext = mContext;
        this.mCurFilter = mCurFilter;
        mContext.getSupportLoaderManager().initLoader(1, null, this);
        mContext.getSupportLoaderManager().initLoader(2, null, this);
    }

    public void setOnDataLoadCompleteListener(OnDataLoadCompleteListener onDataLoadCompleteListener) {
        this.onDataLoadCompleteListener = onDataLoadCompleteListener;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri;

        switch (id) {
            case GET_VIDE0:
                baseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                break;
            default:
                GET_IMAGE:
                baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                break;
        }


        /**
         * video 和 image 的数据库字段名相同，所以这个可以用同一个
         */
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.BUCKET_ID
        };
        return new CursorLoader(mContext, baseUri, projection, null, null,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.i(TAG, "thread id :" + Thread.currentThread().getId());
        long startTime = System.currentTimeMillis();

        if (data.moveToFirst()) {
            while (data.moveToNext()) {

                Log.i(TAG, data.getString(1));
                Log.i(TAG, data.getString(2));
//                Log.i(TAG, data.getString(7));
                if (folders.containsKey(data.getString(2))) {
                    folders.get(data.getString(2)).add(data.getString(1));
                } else {
                    List<String> imagrUrls = new ArrayList<>();
                    imagrUrls.add(data.getString(1));
                    folders.put(data.getString(2), imagrUrls);
                }
            }
        }


        Iterator<Map.Entry<String, List>> iterator = folders.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List> entry = iterator.next();
            FolderInfo f = new FolderInfo();
            f.imageCount = entry.getValue().size();
            f.name = entry.getKey();
            f.firstImageUrl = (String) entry.getValue().get(0);
            f.imagrUrls = entry.getValue();
            folderInfos.add(f);

        }

        long endTime = System.currentTimeMillis();
        Log.i(TAG, "用时：" + (endTime - startTime) + "ms");
        if (onDataLoadCompleteListener != null) {
            onDataLoadCompleteListener.onComplete(folderInfos);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface OnDataLoadCompleteListener {
        void onComplete(List<FolderInfo> folders);
    }

}
