package pro.sketchware.utility.theme;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;

public class ThemeManager {

    public static final int THEME_SYSTEM = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;
    private static final String THEME_PREF = "themedata";
    private static final String THEME_KEY = "idetheme";
    private static final String DYNAMIC_COLOR_KEY = "dynamic_color_enabled";

    public static void applyTheme(Context context, int type) {
        saveTheme(context, type);

        switch (type) {
            case THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    /**
     * Wires up Material You / dynamic color theming for the whole app.
     * Safe to call on any API level - the Material library no-ops when the
     * device does not support dynamic color (Android 11 and below, or OEMs
     * that haven't shipped the wallpaper-derived palette).
     */
    public static void applyDynamicColorsIfEnabled(Application application) {
        if (!isDynamicColorEnabled(application)) {
            return;
        }
        DynamicColorsOptions options = new DynamicColorsOptions.Builder()
                .setPrecondition((activity, theme) -> isDynamicColorEnabled(activity))
                .build();
        DynamicColors.applyToActivitiesIfAvailable(application, options);
    }

    public static boolean isDynamicColorEnabled(Context context) {
        return getPreferences(context).getBoolean(DYNAMIC_COLOR_KEY, true);
    }

    public static void setDynamicColorEnabled(Context context, boolean enabled) {
        getPreferences(context).edit().putBoolean(DYNAMIC_COLOR_KEY, enabled).apply();
    }

    public static int getCurrentTheme(Context context) {
        return getPreferences(context).getInt(THEME_KEY, THEME_SYSTEM);
    }

    public static boolean isSystemTheme(Context context) {
        return getCurrentTheme(context) == THEME_SYSTEM;
    }

    public static int getSystemAppliedTheme(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        return switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_NO -> THEME_LIGHT;
            case Configuration.UI_MODE_NIGHT_YES -> THEME_DARK;
            default -> THEME_SYSTEM;
        };
    }

    private static void saveTheme(Context context, int theme) {
        getPreferences(context).edit().putInt(THEME_KEY, theme).apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
    }
}