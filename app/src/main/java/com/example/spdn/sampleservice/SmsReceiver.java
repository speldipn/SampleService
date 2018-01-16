package com.example.spdn.sampleservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
  public static final String TAG = "SmsReceiver";
  public SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss ");

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i(TAG, "OnReceive() 메소드 호출됨.");

    Bundle bundle = intent.getExtras();
    SmsMessage[] messages = parseSmsMessage(bundle);

    if (messages != null && messages.length > 0) {
      String sender = messages[0].getOriginatingAddress();
      String contents = messages[0].getMessageBody().toString();
      Date receivedData = new Date(messages[0].getTimestampMillis());
      sendToActivity(context, sender, contents , receivedData);

    }
  }

  private void sendToActivity(Context context, String sender, String contents, Date receivedData) {
    Intent intent = new Intent(context, SmsActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("sender", sender);
    intent.putExtra("contents", contents);
    intent.putExtra("receivedDate", format.format(receivedData));

    context.startActivity(intent);
  }

  private SmsMessage[] parseSmsMessage(Bundle bundle) {
    Object[] objs = (Object[]) bundle.get("pdus");
    SmsMessage[] messages = new SmsMessage[objs.length];

    int smsCount = objs.length;
    for (int i = 0; i < smsCount; i++) {
      // PDU 포맷으로 되어있는 메세지를 복원합니다.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        String format = bundle.getString("format");
        messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
      } else {
        messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
      }
    }

    return messages;
  }
}
