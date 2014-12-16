package com.github.fobo4ka.cats;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class CatsImageWidgetProvider extends AppWidgetProvider {
    private static final String URL = "http://thecatapi.com/api/images/get?format=src&type=gif";
    private static final String ACTION = "update";
    private static final String SHARE = "share";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

            Intent intent = new Intent(context, CatsImageWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setAction(ACTION);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);

/*
            intent = new Intent(context, CatsImageWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setAction(SHARE);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.share_button, pendingIntent);
*/

            new ImageDownloader(context).execute(URL);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (ACTION.equals(intent.getAction())) {
            new ImageDownloader(context).execute(URL);
        }
/*
        else if (SHARE.equals(intent.getAction())) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            shareIntent.setType("text/plain");
            context.startActivity(Intent.createChooser(intent, "How do you want to share?"));
        }
*/
    }
}
