package com.jetruby.android.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;


public final class IntentUtils {

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static Intent getInstallAppIntent(final String filePath, final String authority) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath), authority);
    }


    public static Intent getInstallAppIntent(final File file, final String authority) {
        return getInstallAppIntent(file, authority, false);
    }


    public static Intent getInstallAppIntent(final File file,
                                             final String authority,
                                             final boolean isNewTask) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(Utils.getApp(), authority, file);
        }
        intent.setDataAndType(data, type);
        return getIntent(intent, isNewTask);
    }


    public static Intent getUninstallAppIntent(final String packageName) {
        return getUninstallAppIntent(packageName, false);
    }


    public static Intent getUninstallAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    public static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    public static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = Utils.getApp().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) return null;
        return getIntent(intent, isNewTask);
    }

    public static Intent getAppDetailsSettingsIntent(final String packageName) {
        return getAppDetailsSettingsIntent(packageName, false);
    }

    public static Intent getAppDetailsSettingsIntent(final String packageName,
                                                     final boolean isNewTask) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    public static Intent getShareTextIntent(final String content) {
        return getShareTextIntent(content, false);
    }

    public static Intent getShareTextIntent(final String content, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return getIntent(intent, isNewTask);
    }

    public static Intent getShareImageIntent(final String content, final String imagePath) {
        return getShareImageIntent(content, imagePath, false);
    }

    public static Intent getShareImageIntent(final String content,
                                             final String imagePath,
                                             final boolean isNewTask) {
        if (imagePath == null || imagePath.length() == 0) return null;
        return getShareImageIntent(content, new File(imagePath), isNewTask);
    }

    public static Intent getShareImageIntent(final String content, final File image) {
        return getShareImageIntent(content, image, false);
    }

    public static Intent getShareImageIntent(final String content,
                                             final File image,
                                             final boolean isNewTask) {
        if (image != null && image.isFile()) return null;
        return getShareImageIntent(content, Uri.fromFile(image), isNewTask);
    }

    public static Intent getShareImageIntent(final String content, final Uri uri) {
        return getShareImageIntent(content, uri, false);
    }

    public static Intent getShareImageIntent(final String content,
                                             final Uri uri,
                                             final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return getIntent(intent, isNewTask);
    }

    public static Intent getComponentIntent(final String packageName, final String className) {
        return getComponentIntent(packageName, className, null, false);
    }

    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final boolean isNewTask) {
        return getComponentIntent(packageName, className, null, isNewTask);
    }

    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle) {
        return getComponentIntent(packageName, className, bundle, false);
    }

    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle,
                                            final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return getIntent(intent, isNewTask);
    }

    public static Intent getShutdownIntent() {
        return getShutdownIntent(false);
    }

    public static Intent getShutdownIntent(final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
        return getIntent(intent, isNewTask);
    }

    public static Intent getDialIntent(final String phoneNumber) {
        return getDialIntent(phoneNumber, false);
    }

    public static Intent getDialIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    public static Intent getCallIntent(final String phoneNumber) {
        return getCallIntent(phoneNumber, false);
    }

    public static Intent getCallIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    public static Intent getSendSmsIntent(final String phoneNumber,
                                          final String content,
                                          final boolean isNewTask) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return getIntent(intent, isNewTask);
    }

    public static Intent getCaptureIntent(final Uri outUri) {
        return getCaptureIntent(outUri, false);
    }

    public static Intent getCaptureIntent(final Uri outUri, final boolean isNewTask) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return getIntent(intent, isNewTask);
    }

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }
}
