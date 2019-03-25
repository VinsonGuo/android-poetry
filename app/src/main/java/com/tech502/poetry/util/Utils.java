package com.tech502.poetry.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.LocaleList;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tech502.poetry.R;
import com.tech502.poetry.model.BaseResponse;
import com.tech502.poetry.ui.MainActivity;
import com.tech502.poetry.view.VerticalTextView;
import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zengp on 2017/12/2.
 */

public class Utils {

    private static final String LAN_CODE = "lan_code";
    public static final int SIMPLIFY_VALUE = 1;
    public static final int TRADITIONAL_VALUE = 2;

    /**
     * dp2px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 实现文本复制功能
     *
     * @param text
     */
    public static void copyText(Context context, String text) {
        // 得到剪贴板管理器
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager cmb = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(text.trim());
        } else {
            android.content.ClipboardManager cmb = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(text.trim());
        }
    }

    /**
     * 状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            statusBarHeight = 0;
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getLanCode(Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE);
        return sp.getInt(LAN_CODE, 0);
    }

    public static void toggleSimplify(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        int value;
        if (getLanCode(context) == 0) {  // 默認情況，要看系統的語言
            value = config.locale.equals(Locale.SIMPLIFIED_CHINESE) ? TRADITIONAL_VALUE : SIMPLIFY_VALUE;
        } else {
            value = getLanCode(context) == SIMPLIFY_VALUE ? TRADITIONAL_VALUE : SIMPLIFY_VALUE;
        }
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE);
        if (sp.edit().putInt(LAN_CODE, value).commit()) {

            // 应用用户选择语言
            Locale locale = value == SIMPLIFY_VALUE ? Locale.SIMPLIFIED_CHINESE : Locale.TRADITIONAL_CHINESE;
            config.setLocale(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocales(new LocaleList(locale));
            }
            resources.updateConfiguration(config, dm);
            showToast(context, R.string.change_language_msg);
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }

    public static void initLocation(Context context) {
        if (Utils.getLanCode(context) != 0) {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration config = resources.getConfiguration();
            Locale newLocal = (Utils.getLanCode(context) == Utils.SIMPLIFY_VALUE) ? Locale.SIMPLIFIED_CHINESE : Locale.TRADITIONAL_CHINESE;
            Locale.setDefault(newLocal);
            config.setLocale(newLocal);
            resources.updateConfiguration(config, dm);

        }
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, @StringRes int id) {
        showToast(context, context.getString(id));
    }


    public static void setText(TextView tv, @StringRes int id) {
        setText(tv, tv.getContext().getString(id));
    }


    public static void setText(TextView tv, String text) {
        setText(tv, text, true);
    }


    public static void setText(TextView tv, String text, boolean filterSymbol) {
        text = text.replaceAll("\\|", "\n");
        if (filterSymbol) {
            text = text.replaceAll("[，。？！、]", "\t");
        }
        if (tv instanceof VerticalTextView) {
            text = text.replace('“', '﹃')
                    .replace('”', '﹄')
                    .replace('‘', '﹁')
                    .replace('’', '﹂')
                    .replace('「', '﹁')
                    .replace('」', '﹂')
                    .replace('（', '︵')
                    .replace('）', '︶')
                    .replace('《', '︻')
                    .replace('》', '︼');
        }
        Configuration config = tv.getContext().getResources().getConfiguration();
        if (config.locale.equals(Locale.TRADITIONAL_CHINESE)) {
            tv.setText(ChineseConverter.convert(text, ConversionType.S2T, tv.getContext()));
//            tv.setText(text);
        } else {
            tv.setText(ChineseConverter.convert(text, ConversionType.T2S, tv.getContext()));
        }
    }

    public static void closeKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = context.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static File saveScreenshot(View view, int width, int height) throws Exception {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        File imagePath = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                        + "/share.jpg");
        FileOutputStream fos;
        fos = new FileOutputStream(imagePath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        return imagePath;
    }


    /**
     * io线程执行，主线程观察
     * .compose(RxUtils.<T>applySchedulers())
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T extends BaseResponse> ObservableTransformer<T, T> applyBizSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable
                        .map(new Function<T, T>() {
                            @Override
                            public T apply(T t) throws Exception {
                                if (t.getStatus() != 0) {
                                    throw new RuntimeException(t.getMsg());
                                }
                                return t;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}