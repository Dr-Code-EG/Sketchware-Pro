package pro.sketchware.utility.locale;

import android.content.Context;
import android.os.LocaleList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

/**
 * Thin wrapper around {@link AppCompatDelegate} per-app language APIs introduced in
 * AppCompat 1.6+. Together with the {@code AppLocalesMetadataHolderService} declared
 * in the manifest with {@code autoStoreLocales=true}, the user's choice is persisted
 * across app launches without us managing SharedPreferences manually.
 */
public final class LocaleHelper {

    private LocaleHelper() {
    }

    /**
     * Sets the application locale. Pass {@code null} or an empty tag to reset to the
     * system default ("Use system language").
     */
    public static void setAppLocale(@Nullable String bcp47Tag) {
        if (bcp47Tag == null || bcp47Tag.isEmpty()) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList());
            return;
        }
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(bcp47Tag));
    }

    /**
     * Returns the BCP-47 tag of the currently selected app locale, or empty string
     * if the user follows the system default.
     */
    @NonNull
    public static String getCurrentTag() {
        LocaleListCompat locales = AppCompatDelegate.getApplicationLocales();
        if (locales.isEmpty()) {
            return "";
        }
        Locale first = locales.get(0);
        return first != null ? first.toLanguageTag() : "";
    }

    /**
     * Convenience: returns the {@link Locale} actually being applied to UI (falls
     * back to system default if no per-app override).
     */
    @NonNull
    public static Locale getEffectiveLocale(@NonNull Context context) {
        LocaleListCompat locales = AppCompatDelegate.getApplicationLocales();
        if (!locales.isEmpty() && locales.get(0) != null) {
            return locales.get(0);
        }
        LocaleList sys = context.getResources().getConfiguration().getLocales();
        return sys.isEmpty() ? Locale.getDefault() : sys.get(0);
    }
}
