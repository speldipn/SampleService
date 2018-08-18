package com.example.spdn.sampleservice;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 1. SDK 버젼 체크
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkPermission();
    } else {
      init();
    }
  }

  private static int REQ_PERM = 55;

  @TargetApi(Build.VERSION_CODES.M)
  private void checkPermission() {
    if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
      || checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
      String perms[] = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS};
      requestPermissions(perms, REQ_PERM);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode == REQ_PERM) {
      int i = 0;
      while(i < grantResults.length) {
        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
          Toast.makeText(this, "권한이 없으면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show();
          finish();
        }
        ++i;
      }
      if(i == grantResults.length) {
        init();
      }
    }
  }


  private void init() {
    Log.d("SMS", "SMS Receiver : init() called.");
    System.out.println("sms init !!!!");
  }
}