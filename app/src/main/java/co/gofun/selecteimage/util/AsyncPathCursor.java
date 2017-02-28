package co.gofun.selecteimage.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by yangfeng on 2017/2/28.
 */
public class AsyncPathCursor implements LoaderManager.LoaderCallbacks<Cursor> {

    String mCurFilter;
    private Context mContext;

    public AsyncPathCursor(SimpleCursorAdapter simpleCursorAdapter, Context mContext) {
        this.simpleCursorAdapter = simpleCursorAdapter;
        this.mContext = mContext;
    }

    private SimpleCursorAdapter simpleCursorAdapter;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri;
        if (mCurFilter != null) {
//            MediaStore.Images.ImageColumns
            baseUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    Uri.encode(mCurFilter));
        } else {
            baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

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

        simpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleCursorAdapter.swapCursor(null);
    }
}
