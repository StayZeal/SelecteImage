package co.gofun.selecteimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.gofun.selecteimage.bean.ImageInfo;
import co.gofun.selecteimage.util.LoadImageUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ImageListActivity extends AppCompatActivity {


    @Bind(R.id.image_Rv)
    RecyclerView imageRv;

    private SelectImagesAdapter selectImagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        ButterKnife.bind(this);


        init();

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
//        Bundle bundle = new Bundle("iamgeUrls", imageUrls);
        intent.putExtra("imageUrls", (Serializable) imageUrls);
        context.startActivity(intent);
    }


    private void getDatas() {
        Observable.create(new Observable.OnSubscribe<List<ImageInfo>>() {
            @Override
            public void call(Subscriber<? super List<ImageInfo>> subscriber) {

                subscriber.onNext(LoadImageUtil.getImages(ImageListActivity.this));
                subscriber.onCompleted();
                ;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ImageInfo>>() {
                    @Override
                    public void call(List<ImageInfo> imageInfos) {
                        selectImagesAdapter.setImages(imageInfos);
                    }
                });

    }


}
