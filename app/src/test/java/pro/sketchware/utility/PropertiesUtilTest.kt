package pro.sketchware.utility

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

/**
 * Unit tests for [PropertiesUtil]. Validates the regex/parsing helpers that
 * the View Editor relies on to interpret user-entered values like "16dp",
 * "@string/foo", "#FF112233".
 *
 * NOTE: tests covering the [PropertiesUtil.parseColor] / [PropertiesUtil.isHexColor]
 * branches are deferred to instrumentation tests because they call into
 * `android.graphics.Color` and `android.text.TextUtils`, which are stubbed out
 * in plain JVM unit tests.
 */
class PropertiesUtilTest {

    @Test
    fun `getUnitOrPrefix recognises dp suffix`() {
        val pair = PropertiesUtil.getUnitOrPrefix("16dp")
        assertThat(pair).isNotNull()
        assertThat(pair.first).isEqualTo("dp")
        assertThat(pair.second).isEqualTo("16")
    }

    @Test
    fun `getUnitOrPrefix recognises sp suffix with negative value`() {
        val pair = PropertiesUtil.getUnitOrPrefix("-12sp")
        assertThat(pair).isNotNull()
        assertThat(pair.first).isEqualTo("sp")
        assertThat(pair.second).isEqualTo("-12")
    }

    @Test
    fun `getUnitOrPrefix returns null for plain numbers without unit`() {
        assertThat(PropertiesUtil.getUnitOrPrefix("123")).isNull()
    }

    @Test
    fun `getUnitOrPrefix recognises string reference`() {
        val pair = PropertiesUtil.getUnitOrPrefix("@string/title")
        assertThat(pair).isNotNull()
        assertThat(pair.first).isEqualTo("@string/")
        assertThat(pair.second).isEqualTo("title")
    }

    @Test
    fun `getUnitOrPrefix recognises drawable reference with question mark`() {
        val pair = PropertiesUtil.getUnitOrPrefix("?attr/colorPrimary")
        assertThat(pair).isNotNull()
        assertThat(pair.first).isEqualTo("?attr/")
        assertThat(pair.second).isEqualTo("colorPrimary")
    }

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
    fun `generateItems creates the requested number of labelled rows`() {
        val items = PropertiesUtil.generateItems("Item", 3)
        assertThat(items).containsExactly("Item 1", "Item 2", "Item 3").inOrder()
    }

    @Test
    fun `generateItems returns empty list when count is zero`() {
        assertThat(PropertiesUtil.generateItems("X", 0)).isEmpty()
    }

    @Test
    fun `resolveSize falls back to default when value is null`() {
        assertThat(PropertiesUtil.resolveSize(null, 42)).isEqualTo(42)
    }
}
