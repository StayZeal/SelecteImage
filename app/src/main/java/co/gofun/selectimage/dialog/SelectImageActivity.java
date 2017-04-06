package co.gofun.selectimage.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.gofun.selectimage.R;
import co.gofun.selectimage.adapter.FolderAdapter;
import co.gofun.selectimage.adapter.SelectImagesAdapter;
import co.gofun.selectimage.bean.FolderInfo;
import co.gofun.selectimage.bean.ImageInfo;
import co.gofun.selectimage.util.AsyncFolderLoader;
import co.gofun.selectimage.view.AlbumPopupWindow;

public class SelectImageActivity extends AppCompatActivity {


    @Bind(R.id.image_Rv)
    RecyclerView imageRv;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.album_Btn)
    Button albumBtn;
    @Bind(R.id.preview_Btn)
    Button previewBtn;
    @Bind(R.id.complete_Btn)
    Button completeBtn;
    @Bind(R.id.menu_ll)
    LinearLayout menuLl;
    @Bind(R.id.shade_Fl)
    FrameLayout shadeFl;

    private SelectImagesAdapter selectImagesAdapter;
    private AlbumPopupWindow albumPopupWindow;

    CustomDialogFragment mCustomDialogFragment;
    FolderAdapter mFolderAdapter;


    private List<FolderInfo> folders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        ButterKnife.bind(this);


        init();
        initDialog();
        initFolderLoader();
     }

    private List<ImageInfo> getImages() {
        List<String> imageUrls = (List<String>) getIntent().getSerializableExtra("imageUrls");
        List<ImageInfo> imageInfos = new ArrayList<>();
        for (String s : imageUrls) {
            ImageInfo image = new ImageInfo();
            image.url = s;
            imageInfos.add(image);

        }
        return imageInfos;

    }

    private void init() {
        selectImagesAdapter = new SelectImagesAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        imageRv.setLayoutManager(gridLayoutManager);
        imageRv.setAdapter(selectImagesAdapter);
        selectImagesAdapter.setOnSelectImageListener(new SelectImagesAdapter.OnSelectImageListener() {
            @Override
            public void onSelect(List<String> selectImages) {
                completeBtn.setText("完成（" + selectImages.size() + ")");
            }
        });


    }

    public static void showActivity(Context context, List<String> imageUrls) {
        Intent intent = new Intent(context, SelectImageActivity.class);
        intent.putExtra("imageUrls", (Serializable) imageUrls);
        context.startActivity(intent);
    }

    private void initFolderLoader() {
        AsyncFolderLoader asyncFolderLoader = new AsyncFolderLoader(this, "");
        asyncFolderLoader.setOnDataLoadCompleteListener(new AsyncFolderLoader.OnDataLoadCompleteListener() {
            @Override
            public void onComplete(List<FolderInfo> folders, List<String> images) {
                SelectImageActivity.this.folders = folders;
                mFolderAdapter.setData(folders);
                selectImagesAdapter.setImages(parstImageInfo(images));
            }
        });
    }


    private void initDialog() {
        mFolderAdapter = new FolderAdapter(null,this);
        mCustomDialogFragment = new CustomDialogFragment();
        mCustomDialogFragment.setOnCompleteListener(new CustomDialogFragment.OnCompleteListener() {
            @Override
            public void complete(ListView listView) {
                listView.setAdapter(mFolderAdapter);
            }
        });


    }


    @OnClick({R.id.album_Btn, R.id.preview_Btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.album_Btn:
                mCustomDialogFragment.show(getSupportFragmentManager(),"");

                break;
            case R.id.preview_Btn:
                break;
        }
    }

    private List<ImageInfo> parstImageInfo(List<String> imageUrls) {
        List<ImageInfo> imageInfos = new ArrayList<>();
        for (String i : imageUrls) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.url = i;
            imageInfos.add(imageInfo);
        }
        return imageInfos;
    }





    static class CustomDialogFragment extends DialogFragment {

        public ListView listView;

        OnCompleteListener onCompleteListener;

        public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
            this.onCompleteListener = onCompleteListener;
        }


        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.pop_file_list, null);
            listView = (ListView) dialogView.findViewById(R.id.folder_Lv);

            final Dialog dialog = new Dialog (getActivity(), R.style.Theme_Light_Dialog);

            //获得dialog的window窗口
            Window window = dialog.getWindow();
            //设置dialog在屏幕底部
            window.setGravity(Gravity.BOTTOM);
            //设置dialog弹出时的动画效果，从屏幕底部向上弹出
            window.setWindowAnimations(R.style.dialogStyle);
            window.getDecorView().setPadding(0, 0, 0, 0);
            //获得window窗口的属性
            WindowManager.LayoutParams lp = window.getAttributes();
            //设置窗口宽度为充满全屏
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //设置窗口高度为包裹内容
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //将设置好的属性set回去
            window.setAttributes(lp);
            //将自定义布局加载到dialog上
            dialog.setContentView(dialogView);
            if(onCompleteListener!=null){
                onCompleteListener.complete(listView);
            }
            return  dialog;
        }



        public interface OnCompleteListener{
            void complete(ListView listView);
        }
    }
}
