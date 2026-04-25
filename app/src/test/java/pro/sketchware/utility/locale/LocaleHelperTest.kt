package pro.sketchware.utility.locale

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Unit tests for [LocaleHelper]. We mock {@link AppCompatDelegate} (a static API
 * surface) using MockK so the helper logic can be exercised in plain JVM tests.
 */
class LocaleHelperTest {

    @BeforeEach
    fun setUp() {
        mockkStatic(AppCompatDelegate::class)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getCurrentTag returns empty string when no override is active`() {
        every { AppCompatDelegate.getApplicationLocales() } returns LocaleListCompat.getEmptyLocaleList()
        assertThat(LocaleHelper.getCurrentTag()).isEmpty()
    }

    @Test
    fun `getCurrentTag returns the active BCP47 tag`() {
        every { AppCompatDelegate.getApplicationLocales() } returns LocaleListCompat.forLanguageTags("ar")
        assertThat(LocaleHelper.getCurrentTag()).isEqualTo("ar")
    }
}
