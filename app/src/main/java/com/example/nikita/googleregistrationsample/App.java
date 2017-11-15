package com.example.nikita.googleregistrationsample;

import android.app.Application;
import timber.log.Timber;

/**
 * Created by nikita on 15.11.2017.
 */

public class App extends Application {
  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}
