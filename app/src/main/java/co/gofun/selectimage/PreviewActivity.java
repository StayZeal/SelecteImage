package co.gofun.selectimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PreviewActivity extends AppCompatActivity {


    @Bind(R.id.preview_Vp)
    ViewPager previewVp;

    private MyAdapter mAdapter;
    private List<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);


        getDatas();
        mAdapter = new MyAdapter(getSupportFragmentManager());
        previewVp.setAdapter(mAdapter);

    }


    private void getDatas() {
        imageUrls = (List<String>) getIntent().getSerializableExtra("imageUrls");
//        mAdapter.notifyDataSetChanged();
    }


    public static void showActivity(Context context, List<String> imageUrls) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra("imageUrls", (Serializable) imageUrls);
        context.startActivity(intent);
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PreviewFragment.newInstance(imageUrls.get(position), "");
        }

        @Override
        public int getCount() {
            return imageUrls == null ? 0 : imageUrls.size();
        }
    }


}
