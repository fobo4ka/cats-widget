package com.github.fobo4ka.cats;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.InputStream;

class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    private final Context context;

    public ImageDownloader(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("ImageDownloader", "Download failed", e);
        }
        return mIcon;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setImageViewBitmap(R.id.widget_image, result);

        appWidgetManager.updateAppWidget(new ComponentName(context, CatsImageWidgetProvider.class), views);
    }
}
