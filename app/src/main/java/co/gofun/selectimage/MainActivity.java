package co.gofun.selectimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.gofun.selectimage.pop.SelectImageActivity;
import co.gofun.selectimage.view.AlbumPopupWindow;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.select_Btn)
    Button selectBtn;
    @Bind(R.id.show_folders_Btn)
    Button showFoldersBtn;
    @Bind(R.id.show_pop_Btn)
    Button showPopBtn;
    @Bind(R.id.show_diglog_Btn)
    Button showDiglogBtn;
    @Bind(R.id.show_spinner_Btn)
    Button showSpinnerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.select_Btn, R.id.show_folders_Btn, R.id.show_pop_Btn,
            R.id.show_diglog_Btn, R.id.show_spinner_Btn})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.select_Btn:
                intent = new Intent(this, ImageListActivity.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.show_folders_Btn:
                intent = new Intent(this, FileListActivity.class);
                startActivityForResult(intent, 2000);
                break;
            case R.id.show_pop_Btn:
//                showPop();
                intent = new Intent(this, SelectImageActivity.class);
                startActivity(intent);
                break;
            case R.id.show_diglog_Btn:
                intent = new Intent(this, co.gofun.selectimage.dialog.SelectImageActivity.class);
                startActivity(intent);

                break;
            case R.id.show_spinner_Btn:
                intent = new Intent(this, co.gofun.selectimage.spinner.SelectImageActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showPop() {

        AlbumPopupWindow albumPopupWindow = new AlbumPopupWindow(this);
        albumPopupWindow.showAtLocation(findViewById(android.R.id.content),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                0,
                0);
        Log.i(TAG, "showPop()");
    }


}
