package pro.sketchware.utility

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

/**
 * Pure-JVM unit tests for [PropertiesUtil]. We only exercise the helpers that
 * don't reach into android.util.Pair / android.text.TextUtils — those are
 * covered by instrumentation tests because the Android SDK stubs in the
 * unit-test classpath throw "Stub!" RuntimeExceptions for them.
 */
class PropertiesUtilTest {

    @Test
    fun `parseReferName extracts value after separator`() {
        assertThat(PropertiesUtil.parseReferName("@string/app_name", "/")).isEqualTo("app_name")
    }

    @Test
    fun `parseReferName returns input when separator missing`() {
        assertThat(PropertiesUtil.parseReferName("just_text", "/")).isEqualTo("just_text")
    }

    @Test
    fun `parseReferName returns null for null input`() {
        assertThat(PropertiesUtil.parseReferName(null, "/")).isNull()
    }

    @Test
    fun `parseReferName returns input unchanged when separator is the last character`() {
        // Separator at the very end means there is no value after it.
        assertThat(PropertiesUtil.parseReferName("foo/", "/")).isEqualTo("foo/")
    }

    @Test
    fun `generateItems creates the requested number of labelled rows`() {
        val items = PropertiesUtil.generateItems("Item", 3)
        assertThat(items).containsExactly("Item 1", "Item 2", "Item 3").inOrder()
    }

    @Test
    fun `generateItems returns empty list when count is zero`() {
        assertThat(PropertiesUtil.generateItems("X", 0)).isEmpty()
    }

    @Test
    fun `generateItems supports a different prefix`() {
        assertThat(PropertiesUtil.generateItems("Tab", 2))
            .containsExactly("Tab 1", "Tab 2").inOrder()
    }

    @Test
    fun `regex constants are exposed and usable`() {
        // Defensive: we don't want a refactor to silently break the regex API.
        assertThat(PropertiesUtil.HEX_COLOR_PATTERN.pattern())
            .isEqualTo("^#([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
        assertThat(PropertiesUtil.UNIT_PATTERN.matcher("16dp").find()).isTrue()
        assertThat(PropertiesUtil.UNIT_PATTERN.matcher("12sp").find()).isTrue()
        assertThat(PropertiesUtil.UNIT_PATTERN.matcher("plain").find()).isFalse()
    }
}
