package co.gofun.selectimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.gofun.selectimage.adapter.SelectImagesAdapter;
import co.gofun.selectimage.bean.ImageInfo;
import co.gofun.selectimage.view.AlbumPopupWindow;

public class ImageListActivity extends AppCompatActivity {


    @Bind(R.id.image_Rv)
    RecyclerView imageRv;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.album_Btn)
    Button albumBtn;
    @Bind(R.id.preview_Btn)
    Button previewBtn;

    private SelectImagesAdapter selectImagesAdapter;
    private AlbumPopupWindow albumPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        ButterKnife.bind(this);


        init();
        initPop();

//        getDatas();

        selectImagesAdapter.setImages(getImages());
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        imageRv.setLayoutManager(gridLayoutManager);
        imageRv.setAdapter(selectImagesAdapter);


    }

    public static void showActivity(Context context, List<String> imageUrls) {
        Intent intent = new Intent(context, ImageListActivity.class);
        intent.putExtra("imageUrls", (Serializable) imageUrls);
        context.startActivity(intent);
    }


//    private void getDatas() {
//        Observable.create(new Observable.OnSubscribe<List<ImageInfo>>() {
//            @Override
//            public void call(Subscriber<? super List<ImageInfo>> subscriber) {
//
//                subscriber.onNext(LoadImageUtil.getImages(ImageListActivity.this));
//                subscriber.onCompleted();
//                ;
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<ImageInfo>>() {
//                    @Override
//                    public void call(List<ImageInfo> imageInfos) {
//                        selectImagesAdapter.setImages(imageInfos);
//                    }
//                });
//
//    }

    private void initPop() {
        albumPopupWindow = new AlbumPopupWindow(this);
    }

    private void showPop() {
        int[] location = new int[2];
//        View v = findViewById(R.id.content);
        View v = findViewById(R.id.menu_ll);
        v.getLocationOnScreen(location);
//        albumPopupWindow.showAsDropDown(v);
//        albumPopupWindow.showAsDropDown(v, 0, location[1]);
        albumPopupWindow.showAtLocation(v,
                Gravity.TOP,
//                Gravity.BOTTOM,
                0,
//                0);
                location[1] - albumPopupWindow.getHeight());
    }


    @OnClick({R.id.album_Btn, R.id.preview_Btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.album_Btn:
                showPop();
                break;
            case R.id.preview_Btn:
                break;
        }
    }
}
