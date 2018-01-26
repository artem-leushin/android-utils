package com.jetruby.android.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;


public final class KeyboardUtils {

    private static int sContentViewInvisibleHeightPre;

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }




    public static void showSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    public static void showSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    public static void hideSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    public static boolean isSoftInputVisible(final Activity activity) {
        return getContentViewInvisibleHeight(activity) >= 200;
    }


    public static boolean isSoftInputVisible(final Activity activity,
                                             final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
    }

    private static int getContentViewInvisibleHeight(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        Rect r = new Rect();
        contentView.getWindowVisibleDisplayFrame(r);
        return contentView.getBottom() - r.bottom;
    }


    public static void registerSoftInputChangedListener(final Activity activity,
                                                        final OnSoftInputChangedListener listener) {
        final View contentView = activity.findViewById(android.R.id.content);
        sContentViewInvisibleHeightPre = getContentViewInvisibleHeight(activity);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener != null) {
                    int height = getContentViewInvisibleHeight(activity);
                    if (sContentViewInvisibleHeightPre != height) {
                        listener.onSoftInputChanged(height);
                        sContentViewInvisibleHeightPre = height;
                    }
                }
            }
        });
    }


    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) return;
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < 3; i++) {
            try {
                Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                if (declaredField == null) continue;
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(imm);
                if (obj == null || !(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(imm, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }


    public static void clickBlankArea2HideSoftInput() {
        Log.i("KeyboardUtils", "Please refer to the following code.");

    }

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int height);
    }
}
