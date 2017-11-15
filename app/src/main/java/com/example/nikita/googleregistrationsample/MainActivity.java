package com.example.nikita.googleregistrationsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Intent intent = new Intent(this, GoogleLoginHiddenActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //https://console.cloud.google.com/apis/credentials?project=googolregistrationsample
    // need to paste WEB- Client key from that link
    intent.putExtra(GoogleLoginHiddenActivity.EXTRA_CLIENT_ID, "577365149636-ct63t5ndd1f2dcg0jucm4usfcrmjjbts.apps.googleusercontent.com");
    startActivity(intent);
  }
}
