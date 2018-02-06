package com.example.gimhana.lecbuddy;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Gimhana on 11/5/2017.
 */

public class PicasoClient {
    public static void downloadImage(Context context, String url, ImageView imageView) {
        if (url != null && url.length() > 0) {
            Picasso.with(context).load(url).into(imageView);
        }
    }
}
