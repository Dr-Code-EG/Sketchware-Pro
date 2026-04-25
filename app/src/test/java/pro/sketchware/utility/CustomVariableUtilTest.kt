package pro.sketchware.utility

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

/**
 * Unit tests for [CustomVariableUtil]. The class powers the "custom variable"
 * declaration parser used by Sketchware's Java exporter, so the regex needs
 * to handle the most common Java field declaration shapes.
 */
class CustomVariableUtilTest {

    @Test
    fun `parses simple typed declaration with initializer`() {
        val input = "private String userName = \"alice\""
        assertThat(CustomVariableUtil.getVariableModifier(input)).isEqualTo("private")
        assertThat(CustomVariableUtil.getVariableType(input)).isEqualTo("String")
        assertThat(CustomVariableUtil.getVariableName(input)).isEqualTo("userName")
        assertThat(CustomVariableUtil.getVariableInitializer(input)?.trim())
            .isEqualTo("\"alice\"")
    }

    @Test
    fun `parses generic type declaration without initializer`() {
        val input = "public List<String> tags"
        assertThat(CustomVariableUtil.getVariableModifier(input)).isEqualTo("public")
        assertThat(CustomVariableUtil.getVariableType(input)).isEqualTo("List<String>")
        assertThat(CustomVariableUtil.getVariableName(input)).isEqualTo("tags")
        assertThat(CustomVariableUtil.getVariableInitializer(input)).isNull()
    }

    @Test
    fun `parses array declaration`() {
        val input = "static int[] codes"
        assertThat(CustomVariableUtil.getVariableType(input)).isEqualTo("int[]")
        assertThat(CustomVariableUtil.getVariableName(input)).isEqualTo("codes")
    }

    @Test
    fun `parses package-private declaration with no modifier`() {
        val input = "double total = 0.0"
        assertThat(CustomVariableUtil.getVariableModifier(input)).isEmpty()
        assertThat(CustomVariableUtil.getVariableType(input)).isEqualTo("double")
        assertThat(CustomVariableUtil.getVariableName(input)).isEqualTo("total")
        assertThat(CustomVariableUtil.getVariableInitializer(input)?.trim())
            .isEqualTo("0.0")
    }
}
