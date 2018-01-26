package com.jetruby.android.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


public final class ClipboardUtils {

    private ClipboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static void copyText(final CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("text", text));
    }


    public static CharSequence getText() {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(Utils.getApp());
        }
        return null;
    }


    public static void copyUri(final Uri uri) {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newUri(Utils.getApp().getContentResolver(), "uri", uri));
    }


    public static Uri getUri() {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }


    public static void copyIntent(final Intent intent) {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newIntent("intent", intent));
    }


    public static Intent getIntent() {
        ClipboardManager clipboard = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }
}
