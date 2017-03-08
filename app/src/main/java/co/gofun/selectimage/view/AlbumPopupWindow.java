package co.gofun.selectimage.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.List;

import co.gofun.selectimage.R;
import co.gofun.selectimage.adapter.FolderAdapter;
import co.gofun.selectimage.bean.FolderInfo;


public class AlbumPopupWindow extends PopupWindow {



    ListView folderLv;
    private FolderAdapter folderAdapter;

    private View contentView;
    private Context mContext;
//    private FolderAdapter.OnFolderClickListener onFolderClickListener;

    public void setOnFolderClickListener(FolderAdapter.OnFolderClickListener onFolderClickListener) {
//        this.onFolderClickListener = onFolderClickListener;
        if (folderAdapter != null) {
            folderAdapter.setOnFolderClickListener(onFolderClickListener);
        }
    }

    public AlbumPopupWindow(Context context) {
        this.mContext = context;
        init();
//        initList();
        folderAdapter = new FolderAdapter(null, mContext);
    }


    private void init() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_file_list, null);
        setContentView(contentView);

        this.setHeight(1000);
//        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        this.setOutsideTouchable(true);

        // 设置弹出窗体显示时的动画，从底部向上弹出
//        this.setAnimationStyle(R.style.take_photo_anim);

    }

    private void initList() {
        folderLv = (ListView) contentView.findViewById(R.id.folder_Lv);
        folderLv.setAdapter(folderAdapter);
        folderAdapter.notifyDataSetChanged();
    }

    public void setData(List<FolderInfo> folders) {
        folderAdapter.setData(folders);
    }


    public void show(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        showAtLocation(v,Gravity.TOP,0,location[1] - getHeight());
        if (isShowing()) {
            initList();
        }

    }

}
