package com.dieam.reactnativepushnotification.modules;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;

import java.util.List;

 public class RNPushNotificationActionHandlerReceiver extends BroadcastReceiver {

   @Override
  public void onReceive(Context context, Intent intent) {
    if (intent == null || intent.getExtras() == null) {
      return;
    }

    Intent i = new Intent();
    Bundle notif = (Bundle) intent.getExtras().get("notification");
    String id = (String) intent.getExtras().get("id");
    i.putExtra("notification", notif);
    i.putExtra("id", id);
    i.setAction("com.fable.mediman" + "." + id);
    context.sendBroadcast(i);

    Intent serviceIntent = new Intent(context, RNPushNotificationActionService.class);
    serviceIntent.putExtras(intent.getExtras());
    context.startService(serviceIntent);

     // Dismiss the notification popup.
    Bundle bundle = intent.getBundleExtra("notification");
    NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    int notificationID = Integer.parseInt(bundle.getString("id"));
    manager.cancel(notificationID);


    HeadlessJsTaskService.acquireWakeLockNow(context);
    
  }
 }
//    private boolean isAppOnForeground(Context context) {
//     /**
//      We need to check if app is in foreground otherwise the app will crash.
//      http://stackoverflow.com/questions/8489993/check-android-application-is-in-foreground-or-not
//      **/
//     ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//     List<ActivityManager.RunningAppProcessInfo> appProcesses =
//         activityManager.getRunningAppProcesses();
//     if (appProcesses == null) {
//       return false;
//     }
//     final String packageName = context.getPackageName();
//     for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//       if (appProcess.importance ==
//           ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
//           appProcess.processName.equals(packageName)) {
//         return true;
//       }
//     }
//     return false;
//   }
// }