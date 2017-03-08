package co.gofun.selectimage.spinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.Spinner;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.gofun.selectimage.PreviewActivity;
import co.gofun.selectimage.R;
import co.gofun.selectimage.adapter.FolderSpinnerAdapter;
import co.gofun.selectimage.adapter.SelectImagesAdapter;
import co.gofun.selectimage.bean.FolderInfo;
import co.gofun.selectimage.bean.ImageInfo;
import co.gofun.selectimage.util.AsyncFolderLoader;

public class SelectImageActivity extends AppCompatActivity {


    @Bind(R.id.image_Rv)
    RecyclerView imageRv;
    @Bind(R.id.content)
    FrameLayout content;

    @Bind(R.id.preview_Btn)
    Button previewBtn;
    @Bind(R.id.complete_Btn)
    Button completeBtn;
    @Bind(R.id.menu_ll)
    LinearLayout menuLl;
    @Bind(R.id.shade_Fl)
    FrameLayout shadeFl;
    @Bind(R.id.folder_spinner)
    Spinner folderSpinner;

    private ListPopupWindow listPopupWindow;
    //    private FolderAdapter mSpinnerAdapter;
    private FolderSpinnerAdapter mSpinnerAdapter;
//    private ArrayAdapter<CharSequence> mSpinnerAdapter;


    private SelectImagesAdapter selectImagesAdapter;


    private List<FolderInfo> folders = new ArrayList<>();
    private List<String> selectImages;
    private int[] mentLocation;

    AlertDialog s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image_spinner);
        ButterKnife.bind(this);


        init();
        initFolderLoader();
        initSpinner();

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
                SelectImageActivity.this.selectImages = selectImages;
                if (selectImages.size() > 0) {
                    previewBtn.setEnabled(true);
                } else {
                    previewBtn.setEnabled(false);

                }
            }
        });


    }

    private void initSpinner() {
//        mSpinnerAdapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        mSpinnerAdapter = new FolderAdapter(null, this);
        mSpinnerAdapter = new FolderSpinnerAdapter(null, this);
        folderSpinner.setAdapter(mSpinnerAdapter);
//        folderSpinner.setDropDownVerticalOffset(mentLocation[1]);

        folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectImagesAdapter.setImages(parstImageInfo(folders.get(position).imagrUrls));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(folderSpinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(5 * getResources().getDimensionPixelOffset(R.dimen.__picker_item_directory_height));
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
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
                FolderInfo folderInfo = new FolderInfo();
                folderInfo.imagrUrls = images;
                folderInfo.name = "全部照片";
                folderInfo.imageCount = images.size();
                folders.add(0, folderInfo);
                SelectImageActivity.this.folders = folders;

//                albumPopupWindow.setData(folders);
                mSpinnerAdapter.setData(folders);
                selectImagesAdapter.setImages(parstImageInfo(images));
            }
        });
    }


    @OnClick({R.id.preview_Btn})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.preview_Btn:
                PreviewActivity.showActivity(this, selectImages);
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

    private void getMenuLoation() {

        menuLl.getLocationOnScreen(mentLocation);
    }

}
