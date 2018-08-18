package com.example.spdn.sampleservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if(Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
      String msg = "";

      // kitcat 이상 버젼에서 처리
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // 1. SmsMessage 배열을 함수로 꺼낸다.
        SmsMessage smss[] = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for(SmsMessage sms : smss) {
          msg += sms.getMessageBody();
        }
      } else {
        // 1. intent를 통해 넘어온 데이터를 꺼낸다.
        Bundle bundle = intent.getExtras();
        Object objects[] = (Object[]) bundle.get("pdus");
        // 2. 꺼낸 Object형의 데이터를 SmsMessage 객체로 변환
        SmsMessage smss[] = new SmsMessage[objects.length];
        // 3. SmsMessage를 반복문을 통해서 꺼낸다
        for(int i = 0; i < objects.length; ++i) {
          byte temp[] = (byte[]) objects[i];
          smss[i] = SmsMessage.createFromPdu(temp);
          msg += smss[i].getMessageBody();
        }
      }
      Log.d("SMS", "msg=" + msg);
      System.out.println("sms test : " + msg);
    }
  }
}
