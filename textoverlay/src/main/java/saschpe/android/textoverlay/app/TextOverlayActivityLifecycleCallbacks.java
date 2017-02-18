/*
 * Copyright 2016 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package saschpe.android.textoverlay.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import saschpe.android.textoverlay.service.TextOverlayService;

/**
 * Activity lifecycle callbacks that handle {@link TextOverlayService} interactions.
 *
 * Handles start / stop of the service as well as asking for the required permissions on
 * API level 23 or later.
 *
 * @see <a href="https://developer.android.com/reference/android/Manifest.permission.html#SYSTEM_ALERT_WINDOW">SYSTEM_ALERT_WINDOW</a>
 * @see <a href="https://developer.android.com/reference/android/provider/Settings.html#ACTION_MANAGE_OVERLAY_PERMISSION">ACTION_MANAGE_OVERLAY_PERMISSION</a>
 */
public final class TextOverlayActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = TextOverlayActivityLifecycleCallbacks.class.getSimpleName();
    private static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed: " + activity.getClass().getSimpleName());
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(activity)) {
                Log.d(TAG, "onActivityResumed: API level 23 and can draw overlays");
                activity.startService(new Intent(activity, TextOverlayService.class));
            }
        } else {
            Log.d(TAG, "onActivityResumed: Can draw overlays");
            activity.startService(new Intent(activity, TextOverlayService.class));
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused: " + activity.getClass().getSimpleName());
        activity.stopService(new Intent(activity, TextOverlayService.class));
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "onActivityStopped: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
