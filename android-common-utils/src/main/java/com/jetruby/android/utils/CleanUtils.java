package com.jetruby.android.utils;

import android.os.Environment;

import java.io.File;


public final class CleanUtils {

    private CleanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static boolean cleanInternalCache() {
        return deleteFilesInDir(Utils.getApp().getCacheDir());
    }


    public static boolean cleanInternalFiles() {
        return deleteFilesInDir(Utils.getApp().getFilesDir());
    }


    public static boolean cleanInternalDbs() {
        return deleteFilesInDir(new File(Utils.getApp().getFilesDir().getParent(), "databases"));
    }


    public static boolean cleanInternalDbByName(final String dbName) {
        return Utils.getApp().deleteDatabase(dbName);
    }


    public static boolean cleanInternalSp() {
        return deleteFilesInDir(new File(Utils.getApp().getFilesDir().getParent(), "shared_prefs"));
    }


    public static boolean cleanExternalCache() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && deleteFilesInDir(Utils.getApp().getExternalCacheDir());
    }


    public static boolean cleanCustomCache(final String dirPath) {
        return deleteFilesInDir(dirPath);
    }


    public static boolean cleanCustomCache(final File dir) {
        return deleteFilesInDir(dir);
    }

    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    private static boolean deleteFilesInDir(final File dir) {
        if (dir == null) return false;
        // 目录不存在返回 true
        if (!dir.exists()) return true;
        // 不是目录返回 false
        if (!dir.isDirectory()) return false;
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // 目录不存在返回 true
        if (!dir.exists()) return true;
        // 不是目录返回 false
        if (!dir.isDirectory()) return false;
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
