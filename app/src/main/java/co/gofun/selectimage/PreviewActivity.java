package co.gofun.selectimage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PreviewActivity extends AppCompatActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.next_Btn)
    Button nextBtn;
    @Bind(R.id.preview_Vp)
    ViewPager previewVp;

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);


        mAdapter = new MyAdapter(getSupportFragmentManager());
        previewVp.setAdapter(mAdapter);
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PreviewFragment.newInstance("", "");
        }

        @Override
        public int getCount() {
            return 0;
        }
    }


}
