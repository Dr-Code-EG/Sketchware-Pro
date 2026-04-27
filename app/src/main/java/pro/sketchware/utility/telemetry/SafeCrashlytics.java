package pro.sketchware.utility.telemetry;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

/**
 * Null-safe wrapper around {@link FirebaseCrashlytics}.
 *
 * <p>Sketchware Pro is built without a {@code google-services.json}, so the
 * Firebase content provider that auto-initializes the default {@code FirebaseApp}
 * never runs. Calls to {@code FirebaseCrashlytics.getInstance()} therefore throw
 * {@link IllegalStateException} the first time they are evaluated. Several
 * activities held that instance in a {@code private final} field which is
 * initialized in the constructor &mdash; before {@code onCreate} &mdash; meaning
 * the activity could not even be instantiated, which produced
 * "Unable to instantiate activity" crashes (see Phase 1 r1 bug report).
 *
 * <p>This wrapper resolves the {@code FirebaseCrashlytics} instance lazily and
 * silently no-ops every call when Firebase is unavailable, so the host app stays
 * functional. When telemetry is wanted in the future, callers do not need to
 * change &mdash; the wrapper will start delegating as soon as a
 * {@code FirebaseApp} exists.
 */
public final class SafeCrashlytics {

    private static final String TAG = "SafeCrashlytics";
    private static final SafeCrashlytics INSTANCE = new SafeCrashlytics();

    @Nullable
    private final FirebaseCrashlytics delegate;

    private SafeCrashlytics() {
        this.delegate = resolveDelegate();
    }

    @Nullable
    private static FirebaseCrashlytics resolveDelegate() {
        try {
            // Touch the default FirebaseApp first to surface
            // IllegalStateException locally rather than from getInstance().
            FirebaseApp.getInstance();
            return FirebaseCrashlytics.getInstance();
        } catch (IllegalStateException notInitialized) {
            Log.i(TAG, "FirebaseApp not initialized; crashlytics disabled.");
            return null;
        } catch (Throwable other) {
            Log.w(TAG, "Failed to obtain FirebaseCrashlytics; crashlytics disabled.", other);
            return null;
        }
    }

    @NonNull
    public static SafeCrashlytics get() {
        return INSTANCE;
    }

    public boolean isEnabled() {
        return delegate != null;
    }

    public void log(@NonNull String message) {
        if (delegate != null) {
            try {
                delegate.log(message);
            } catch (Throwable ignored) {
                // Never let telemetry plumbing escalate into a user-facing crash.
            }
        }
    }

    public void recordException(@NonNull Throwable throwable) {
        if (delegate != null) {
            try {
                delegate.recordException(throwable);
            } catch (Throwable ignored) {
                // Same rationale as log(): keep telemetry best-effort.
            }
        }
    }
}
