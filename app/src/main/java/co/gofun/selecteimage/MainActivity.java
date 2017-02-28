package co.gofun.selecteimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.select_Btn)
    Button selectBtn;
    @Bind(R.id.show_folders_Btn)
    Button showFoldersBtn;

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

    @OnClick({R.id.select_Btn, R.id.show_folders_Btn})
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
        }
    }
}
