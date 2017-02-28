package co.gofun.selectimage.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.gofun.selectimage.bean.FolderInfo;
import co.gofun.selectimage.bean.ImageInfo;

/**
 * Created by yangfeng on 2017/1/17.
 */
public class LoadImageUtil {


    private static final String TAG = "LoadImageUtil";

    public static List<ImageInfo> getImages(Context context) {

        List<ImageInfo> images = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE
        };
        final Cursor cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

// Put it in the image view
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                ImageInfo image = new ImageInfo();
                image.url = cursor.getString(1);
                images.add(image);
            }

        }
        cursor.close();
        return images;
    }


    public static List<FolderInfo> getImageFolders(String rootUrl, List<FolderInfo> folders) {
//        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.i(TAG, "rootUrl:" + rootUrl);
        File file = new File(rootUrl);
        if (!file.exists() || file.listFiles() == null || file.listFiles().length == 0) {
            return folders;
        }

        int count = 0;
        File firstImageFile = null;
        boolean firstImage = true;
        for (File f : file.listFiles()) {
            if (file.isDirectory()) {
                folders = getImageFolders(f.getAbsolutePath(), folders);
            } else {
                if (isImage(f)) {
                    if (firstImage) {
                        firstImageFile = f;
                        firstImage = false;
                    }
                    count++;
                }
            }
        }

        if(firstImageFile!=null){
            FolderInfo folderInfo = new FolderInfo();
            folderInfo.firstImageUrl = firstImageFile.getAbsolutePath() + "/" + file.getName();
            folderInfo.name = firstImageFile.getParent();
            folderInfo.imageCount = count;
            folders.add(folderInfo);
        }

        return folders;
    }

    public static FolderInfo parseFolderInfo(File file) {
        FolderInfo folderInfo = new FolderInfo();
        folderInfo.name = file.getName();
        folderInfo.firstImageUrl = file.getAbsolutePath() + "/" + file.getName();


        return folderInfo;
    }


    public static boolean isImage(File file) {
        String name = file.getName();
        if (name.endsWith(".png") || name.endsWith(".jpg")
                || name.endsWith(".git") || name.endsWith(".jpeg")
                || name.endsWith(".bmp")) {
            return true;
        }
        return false;
    }

}
