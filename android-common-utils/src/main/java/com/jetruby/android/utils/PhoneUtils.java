package com.jetruby.android.utils;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;


public final class PhoneUtils {

    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static boolean isPhone() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }


    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }


    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMSI() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }


    public static int getPhoneType() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }


    public static boolean isSimCardReady() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }


    public static String getSimOperatorName() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimOperatorName() : null;
    }


    public static String getSimOperatorByMnc() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) return null;
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "中国移动";
            case "46001":
                return "中国联通";
            case "46003":
                return "中国电信";
            default:
                return operator;
        }
    }


    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getPhoneStatus() {
        TelephonyManager tm =
                (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) return "";
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "PhoneType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }


    public static void dial(final String phoneNumber) {
        Utils.getApp().startActivity(IntentUtils.getDialIntent(phoneNumber, true));
    }


    public static void call(final String phoneNumber) {
        Utils.getApp().startActivity(IntentUtils.getCallIntent(phoneNumber, true));
    }


    public static void sendSms(final String phoneNumber, final String content) {
        Utils.getApp().startActivity(IntentUtils.getSendSmsIntent(phoneNumber, content, true));
    }


    public static void sendSmsSilent(final String phoneNumber, final String content) {
        if (StringUtils.isEmpty(content)) return;
        PendingIntent sentIntent = PendingIntent.getBroadcast(Utils.getApp(), 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }


    public static void getAllContactInfo() {
        Log.i("PhoneUtils", "Please refer to the following code.");

    }


    public static void getContactNum() {
        Log.i("PhoneUtils", "Please refer to the following code.");

    }


    public static void getAllSMS() {
        Log.i("PhoneUtils", "Please refer to the following code.");

    }
}
