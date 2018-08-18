# SampleService
Broadcast Receiver example code - sms receiver

### Manifest 설정
* SMS Message를 가져오기 위해서는 RECEIVE_SMS와 READ_SMS 권한이 필요하며, Manifest에서 권한이 추가되어야하고 Main Activity에서 Permission 체크가 필요하다.

#### SMS메세지를 Catch하기 위한 권한 설정
* 오레오 버젼부터는 하기 2개의 권한이 통합되어서 앱실행시 한번만 물어보지만 그 전에는 2개다 allow가 필요
````xml
<uses-permission android:name="android.permission.RECEIVE_SMS" />
<uses-permission android:name="android.permission.READ_SMS" />
````

#### recevier 등록
````xml
<receiver
  android:name=".SmsReceiver"
  android:enabled="true"
  android:exported="true">
  <!-- 들어오는 메세지의 종류를 필터링하기 위해서 사용 -->
  <intent-filter>
      <action android:name="android.provider.Telephony.SMS_RECEIVED" />
  </intent-filter>
</receiver>
````
