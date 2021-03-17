### Appium Test Helper Android  
Get the most of Appium with this installed next to your tested app.  
Test mode  
The value set here is returned to other apps through a content provider. It can be used to disable analytics during automated testing.  
content://com.sengami.appium_test_helper_android.provider/  
  
Toasts  
Will show a toast with text "This \is \a \sample \text" when the command below is used through ADB.  
adb shell am broadcast -a com.sengami.appium_test_helper_android.toast --es message \"This is a sample text\" -n com.sengami.appium_test_helper_android/.ToastBroadcastReceiver  
  
Vibrations  
Will vibrate the device for 2 seconds when the command below is used through ADB.  
adb shell am broadcast -a com.sengami.appium_test_helper_android.vibration --el duration 2000 -n com.sengami.appium_test_helper_android/.VibrationBroadcastReceiver  
  
[Download now on Google Play Store](https://play.google.com/store/apps/details?id=com.iamsteve.android)