package co.gofun.selectimage.bean;

import java.util.List;

/**
 * Created by yangfeng on 2017/2/10.
 */
public class FolderInfo {
    public List<String> imagrUrls;
    public String firstImageUrl = imagrUrls == null ? null : imagrUrls.get(0);
    public String name;
    public String url;
    public int imageCount = imagrUrls == null ? 0 : imagrUrls.size();
}
