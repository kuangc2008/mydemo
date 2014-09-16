package com.example.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.example.demo.R;

/**
 * Created by kuangcheng on 2014/9/16.
 */
public class Volley3 extends Activity{
    ImageView iv = null;
    private static String[] imageUrls = {
            "http://test.designer.c-launcher.com/resources/wallpaper/img/848/5397d1250cf267d0f0d15dd8/1402458405568/wallpaper_s.jpg",
            "http://test.designer.c-launcher.com/resources/wallpaper/img/391/5397eb860cf20c6436f7565f/1402465158841/wallpaper_s.jpg",
            "http://test.designer.c-launcher.com/resources/wallpaper/img/333/5397e5d50cf244d2003552cd/1402463701155/wallpaper_s.jpg",
            "http://test.designer.c-launcher.com/resources/wallpaper/img/246/5397d1310cf267d0f0d15dde/1402458417493/wallpaper_s.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/caef76094b36acafff0500fb7ed98d1000e99cd4.jpg",
            "http://img.xgo-img.com.cn/pics/1645/1644205.jpg",
            "http://img1b.xgo-img.com.cn/pics/1645/1644167.jpg",
            "http://img1d.xgo-img.com.cn/pics/1645/1644164.jpg",
            "http://www.mwfy.com/uploads/allimg/c130505/13CJc604DP-21I52.jpg",
            "http://www.mwfy.com/uploads/allimg/c130505/13CJc6156030-233638.jpg",
            "http://www.mwfy.com/uploads/allimg/c130505/13CJc61b5P-2424O.jpg",
            "http://www.mwfy.com/uploads/allimg/c130505/13CJc623V10-251154.jpg",
            "http://www.mwfy.com/uploads/allimg/c130505/13CJc62VL0-261552.jpg",
            "http://www.mwfy.com/uploads/allimg/c130505/13CJc632U40-2IJ6.jpg",
            "http://www.mwfy.com/uploads/allimg/c130505/13CJc63VC0-2W3I.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1204/27/c6/11407462_1335526666823_800x800.jpg",
            "http://image.tianjimedia.com/uploadImages/2013/304/5001PI40084L.jpg",
            "http://pic19.nipic.com/20120222/5378638_081628440187_2.jpg",
            "http://image.tianjimedia.com/uploadImages/2014/104/13/9L1F7PF3X827.jpg",
            "http://image.tianjimedia.com/uploadImages/2014/104/46/R260R78ZR0I2.jpg",
            "http://image.tianjimedia.com/uploadImages/2013/317/XA838Y914RDV.jpg",
            "http://image.tianjimedia.com/uploadImages/2013/304/138U9RIE58V1.jpg",
            "http://www.mwfy.com/uploads/allimg/c130505/13CJc604DP-21I52.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1204/27/c6/11407462_1335526666823_800x800.jpg",
            "http://image.tianjimedia.com/uploadImages/2013/326/X4826619EK7C.jpg",
            "http://p2.so.qhimg.com/t01560090d15627b107.jpg",
            "http://image.tianjimedia.com/uploadImages/2013/317/X5LQX60CD572.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1205/25/c2/11755122_1337938898582.jpg",
            "http://www.wed114.cn/jiehun/uploads/allimg/c130416/13B0ZA0920-45E03.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1405/06/c6/33940574_33940574_1399384704358_mthumb.jpg",
            "http://p2.so.qhimg.com/t0136e4f1abb24d3a2c.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1205/25/c2/11755122_1337938898581.jpg",
            "http://p1.so.qhimg.com/t0142f3bd36b940cbe6.jpg",
            "http://pic.7y7.com/201312/2013122434771641_600x0.jpg",
            "http://image.tianjimedia.com/uploadImages/2014/016/TL8TAQKJ7BDL.jpg",
            "http://img.hb.aicdn.com/861585bd4a79d66900ef384274ffc3da5f887ba737abc-cVU8p9_fw554",
            "http://bbs.wuhu.cc/data/attachment/forum/201209/05/165102d4625b6c20eiq2kk.jpg",
            "http://www.365zhuti.com/wall/UploadPic/2012-9/365zhuti_20120905093710_1.jpg",
            "http://img.hb.aicdn.com/d4a1bb24ea9afc7245da31e513daa843ea65b9ee4a1fd-G3VZvX_fw580",
            "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1204/27/c12/11408994_1335534973113_800x800.jpg",
            "http://image.tianjimedia.com/uploadImages/2013/305/7T69XZU3X52F.jpg",
            "http://img.hb.aicdn.com/554d72fd1e5e1339d1b0045d8efcada3572b78bda20a-XU0LS4_fw658",
            "http://pic.rmzt.com/2012/09/10-1/qxyqmv1-1.jpg",
            "http://news.xinhuanet.com/local/2012-04/12/122967910_41n.jpg",
            "http://img5.hao123.com/data/1_cd0fef2deb6fd31756c4caddaabc4741_510",
            "http://imga1.pic21.com/bizhi/131225/05698/s14.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iv = new ImageView(this);
        setContentView(iv);

        RequestManager.getInstance(this).loadImage(imageUrls[0], new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                iv.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                iv.setImageResource(R.drawable.ic_launcher);
            }
        });
    }
}
