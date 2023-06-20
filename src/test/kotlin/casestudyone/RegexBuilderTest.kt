package casestudyone

import casestudyone.RegexBuilderTest.Constants.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RegexBuilderTest {

    enum class Constants(val pattern: String) {
        ANYTHING_PATTERN(".*"),
        SOMETHING_PATTERN(".+"),
        LETTERS_PATTERN("""[a-zA-ZäÄüÜöÖß\s]+"""),
        NUMBERS_PATTERN("[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?"),
    }

    // _________________ starts with _________________
    @Test
    fun `starts with anything`() {
        // GIVEN
        // WHEN
        val startsWith: Regex = build regex {
            starts with anything
        }
        // THEN
        assertEquals(
            "^(${ANYTHING_PATTERN.pattern})",
            startsWith.pattern
        )
        assertTrue(startsWith.matches("Should match."))
        assertTrue(startsWith.matches(""))
    }

    @Test
    fun `starts with something`() {
        // GIVEN
        // WHEN
        val startsWith: Regex =
            build regex { starts with something }

        // THEN
        assertEquals(
            "^(${SOMETHING_PATTERN.pattern})",
            startsWith.pattern
        )
        assertTrue(startsWith.matches("Should match."))
        assertFalse(startsWith.matches(""))
    }

    @Test
    fun `starts with letters`() {
        // GIVEN
        // WHEN
        val startsWith: Regex =
            build regex { starts with letters }

        // THEN
        assertEquals(
            "^(${LETTERS_PATTERN.pattern})",
            startsWith.pattern
        )
        assertTrue(startsWith.matches("Should match"))
        assertFalse(startsWith.matches("123"))
    }

    @Test
    fun `starts with numbers`() {
        // GIVEN
        // WHEN
        val startsWith: Regex =
            build regex { starts with numbers }

        // THEN
        assertEquals(
            "^(${NUMBERS_PATTERN.pattern})",
            startsWith.pattern
        )
        assertTrue(startsWith.matches("123"))
        assertFalse(startsWith.matches("Test"))
    }

    @Test
    fun `starts with Hello`() {
        // GIVEN
        // WHEN
        val startsWith: Regex =
            build regex { starts with "Hello" }

        // THEN
        assertEquals("^(Hello)", startsWith.pattern)
        assertTrue(startsWith.matches("Hello"))
        assertFalse(startsWith.matches("hello"))
    }

    @Test
    fun `starts with @`() {
        // GIVEN
        // WHEN
        val startsWith: Regex =
            build regex { starts with "@" }

        // THEN
        assertEquals("^(@)", startsWith.pattern)
        assertTrue(startsWith.matches("@"))
    }

    @Test
    fun `starts with first or second`() {
        // GIVEN
        // WHEN
        val startsWith: Regex =
            build regex { starts with "first" or "second" }

        // THEN
        assertEquals("^(first|second)", startsWith.pattern)
        assertTrue(startsWith.matches("first"))
        assertTrue(startsWith.matches("second"))
        assertFalse(startsWith.matches("third"))
        assertFalse(startsWith.matches("fourth"))
    }

    @Test
    fun `starts with whitespace between words`() {
        // GIVEN
        // WHEN
        val startsWith: Regex =
            build regex { starts with "Hello World!" or "Hello" }

        // THEN
        assertEquals(
            "^(Hello World!|Hello)",
            startsWith.pattern
        )
        assertTrue(startsWith.matches("Hello World!"))
        assertTrue(startsWith.matches("Hello"))
        assertFalse(startsWith.matches("Test"))
    }

    // _________________ followed with _________________
    @Test
    fun `followed with anything`() {
        // GIVEN
        // WHEN
        val followedWith: Regex =
            build regex { followed with anything }

        // THEN
        assertEquals(
            "(${ANYTHING_PATTERN.pattern})",
            followedWith.pattern
        )
        assertTrue(followedWith.matches("Should match."))
        assertTrue(followedWith.matches(""))
    }

    @Test
    fun `followed with something`() {
        val followedWith: Regex =
            build regex { followed with something }
        assertEquals(
            "(${SOMETHING_PATTERN.pattern})",
            followedWith.pattern
        )
        assertTrue(followedWith.matches("Should match."))
        assertFalse(followedWith.matches(""))
    }

    @Test
    fun `followed with letters`() {
        val followedWith: Regex =
            build regex { followed with letters }
        assertEquals(
            "(${LETTERS_PATTERN.pattern})",
            followedWith.pattern
        )
        assertTrue(followedWith.matches("Test"))
        assertFalse(followedWith.matches("123"))
    }

    @Test
    fun `followed with numbers`() {
        // GIVEN
        // WHEN
        val followedWith: Regex =
            build regex { followed with numbers }

        // THEN
        assertEquals(
            "(${NUMBERS_PATTERN.pattern})",
            followedWith.pattern
        )
        assertTrue(followedWith.matches("123"))
        assertFalse(followedWith.matches("Test"))
    }

    @Test
    fun `followed with @`() {
        val followedWith: Regex =
            build regex { followed with "@" }
        assertEquals("(@)", followedWith.pattern)
        assertTrue(followedWith.matches("@"))
        assertFalse(followedWith.matches("@@"))
    }

    @Test
    fun `followed with @ or email`() { // Scala, Groovy,
        val followedWith: Regex =
            build regex { followed with "@" or "email" }
        assertEquals("(@|email)", followedWith.pattern)
        assertTrue(followedWith.matches("@"))
        assertTrue(followedWith.matches("email"))
        assertFalse(followedWith.matches("@email"))
        assertFalse(followedWith.matches("emailtest"))
    }

    @Test
    fun `followed with multiple times in correct order`() { // Scala, Groovy
        // GIVEN
        // WHEN
        val followedWith: Regex = build regex {
            followed with "gmail" or "gmx"
            followed with "."
            followed with "com" or "de"
        }

        // THEN
        assertEquals(
            "(gmail|gmx)(\\.)(com|de)",
            followedWith.pattern
        )
        assertTrue(followedWith.matches("gmail.com"))
        assertTrue(followedWith.matches("gmail.de"))
        assertTrue(followedWith.matches("gmx.com"))
        assertTrue(followedWith.matches("gmx.de"))
        assertFalse(followedWith.matches("gmxde"))
        assertFalse(followedWith.matches("gmailgmx.de"))
        assertFalse(followedWith.matches("gmail."))
    }

    // _________________ ends with _________________
    @Test
    fun `ends with anything`() {
        // GIVEN
        // WHEN
        val endsWith: Regex =
            build regex { ends with anything }

        // THEN
        assertEquals(
            "(${ANYTHING_PATTERN.pattern})$",
            endsWith.pattern
        )
        assertTrue(endsWith.matches("Should match."))
        assertTrue(endsWith.matches(""))
    }

    @Test
    fun `ends with something`() {
        // GIVEN
        // WHEN
        val endsWith: Regex =
            build regex { ends with something }

        // THEN
        assertEquals(
            "(${SOMETHING_PATTERN.pattern})$",
            endsWith.pattern
        )
        assertTrue(endsWith.matches("Should match."))
        assertFalse(endsWith.matches(""))
    }

    @Test
    fun `ends with letters`() {
        // GIVEN
        // WHEN
        val endsWith: Regex =
            build regex { ends with letters }

        // THEN
        assertEquals(
            "(${LETTERS_PATTERN.pattern})$",
            endsWith.pattern
        )
        assertTrue(endsWith.matches("Should match"))
        assertFalse(endsWith.matches("123"))
    }

    @Test
    fun `ends with numbers`() {
        // GIVEN
        // WHEN
        val endsWith: Regex =
            build regex { ends with numbers }

        // THEN
        assertEquals(
            "(${NUMBERS_PATTERN.pattern})$",
            endsWith.pattern
        )
        assertTrue(endsWith.matches("123"))
        assertFalse(endsWith.matches("Test"))
    }

    @Test
    fun `ends with Hello`() {
        // GIVEN
        // WHEN
        val endsWith: Regex =
            build regex { ends with "Hello" }

        // THEN
        assertEquals("(Hello)$", endsWith.pattern)
        assertTrue(endsWith.matches("Hello"))
        assertFalse(endsWith.matches("Hello World"))
    }

    @Test
    fun `ends with first or second`() {
        // GIVEN
        // WHEN
        val endsWith: Regex =
            build regex { ends with "first" or "second" }

        // THEN
        assertEquals("(first|second)$", endsWith.pattern)
        assertTrue(endsWith.matches("first"))
        assertTrue(endsWith.matches("second"))
        assertFalse(endsWith.matches("third"))
        assertFalse(endsWith.matches("fourth"))
    }

    // _________________ Multiplicities  _________________
    @Test
    fun `starts quantification`() {
        // GIVEN
        // WHEN
        val regex: Regex = build regex {
            starts with "aa" occurs 1..2 or "b" occurs 3..3
        }

        // THEN
        assertEquals("^((aa){1,2}|(b){3})", regex.pattern)
        assertTrue(regex.matches("aa"))
        assertTrue(regex.matches("aaaa"))
        assertTrue(regex.matches("bbb"))
        assertFalse(regex.matches("aaa"))
        assertFalse(regex.matches("bb"))
        assertFalse(regex.matches("b"))
    }

    @Test
    fun `followed with repeated and quantifications`() {
        // GIVEN
        // WHEN
        val followedWith: Regex = build regex {
            followed with "gmail" occurs 1..2 or "gmx"
            followed with "." occurs 3..3
            followed with "com" or "de"
        }

        // THEN
        assertEquals(
            "((gmail){1,2}|gmx)((\\.){3})(com|de)",
            followedWith.pattern
        )
        assertTrue(followedWith.matches("gmailgmail...com"))
        assertTrue(followedWith.matches("gmail...de"))
        assertTrue(followedWith.matches("gmx...com"))
        assertTrue(followedWith.matches("gmx...de"))
        assertFalse(followedWith.matches("gmxde"))
        assertFalse(followedWith.matches("gmailgmx.de"))
        assertFalse(followedWith.matches("gmail."))
        assertFalse(followedWith.matches("gmail."))
    }

    @Test
    fun `ends quantification`() {
        // GIVEN
        // WHEN
        val endsWith: Regex = build regex {
            ends with "aa" occurs 1..2 or "b" occurs 3..3
        }

        // THEN
        assertEquals(
            "((aa){1,2}|(b){3})\$",
            endsWith.pattern
        )
        assertTrue(endsWith.matches("aa"))
        assertTrue(endsWith.matches("aaaa"))
        assertTrue(endsWith.matches("bbb"))
        assertFalse(endsWith.matches("aaa"))
        assertFalse(endsWith.matches("bb"))
        assertFalse(endsWith.matches("b"))
    }

    @Test
    fun `ends with quantification indefinitely`() {
        // GIVEN
        // WHEN
        val endsWith: Regex = build regex {
            ends with "aa" occurs indefinitely or "b" occurs 3..3
        }

        // THEN
        assertEquals("((aa)*|(b){3})\$", endsWith.pattern)
        assertTrue(endsWith.matches("aaaaaaaaaa"))
        assertTrue(endsWith.matches("aaaa"))
        assertTrue(endsWith.matches("bbb"))
        assertFalse(endsWith.matches("aaa"))
        assertFalse(endsWith.matches("bb"))
        assertFalse(endsWith.matches("b"))
    }

    // _________________ integration _________________

    @Test
    fun `simple regex`() {
        // GIVEN
        // WHEN
        val regex = build regex {
            starts with "T"
            followed with anything
            ends with "s."
        }

        // THEN
        assertEquals("^(T)(.*)(s\\.)\$", regex.pattern)
        assertTrue(regex.matches("This matches."))
        assertFalse(regex.matches("This does not match."))
    }

    @Test
    fun `simple regex 2`() {
        // GIVEN
        // WHEN
        val regex = build regex {
            starts with "This m"
            followed with "atche"
            ends with "s."
        }

        // THEN
        assertEquals(
            "^(This m)(atche)(s\\.)\$",
            regex.pattern
        )
        assertTrue(regex.matches("This matches."))
        assertFalse(regex.matches("Does not match."))
    }

    @Test
    fun `email regex`() {
        // GIVEN
        // WHEN
        val regex = build regex {
            starts with something
            followed with "@"
            followed with something
            followed with "."
            ends with "com" or "de" or "net"
        }

        // THEN
        assertEquals(
            "^(.+)(@)(.+)(\\.)(com|de|net)\$",
            regex.pattern
        )
        assertTrue(regex.matches("name@gmail.com"))
        assertTrue(regex.matches("name@gmx.de"))
        assertTrue(regex.matches("name@gmx.de"))
        assertTrue(regex.matches("name@gmx.net"))
        assertFalse(regex.matches("@gmx.de"))
        assertFalse(regex.matches("name@.de"))
        assertFalse(regex.matches("name@.ch"))
        assertFalse(regex.matches("name.de"))
    }

    // _________________ inner regex _________________
    @Test
    fun `Regex Email inner regex`() {
        // GIVEN
        // WHEN
        val regex = build regex {
            starts with innerRegex {
                followed with "John" or "Steve"
            } or "Hello"
            followed with "Doe" or "Mustermann"
            followed with "@"
            ends with "gmail.com"
        }

        // THEN
        assertEquals(
            "^((John|Steve)|Hello)(Doe|Mustermann)(@)(gmail\\.com)\$",
            regex.pattern
        )
        assertTrue(regex.matches("JohnDoe@gmail.com"))
        assertTrue(regex.matches("JohnMustermann@gmail.com"))
        assertTrue(regex.matches("SteveMustermann@gmail.com"))
        assertTrue(regex.matches("HelloMustermann@gmail.com"))
        assertFalse(regex.matches("JohnD@gmail.com"))
        assertFalse(regex.matches("Demian@gmail.com"))
    }


    @Test
    fun `Inner regex followed with with alternative`() {
        // GIVEN
        // WHEN
        val regex = build regex {
            starts with innerRegex {
                followed with "1" or "2"
            } or "4"
            followed with innerRegex {
                followed with "a"
                followed with "b" or "c"
            }
            ends with "end"
        }

        // THEN
        assertEquals(
            "^((1|2)|4)((a)(b|c))(end)$",
            regex.pattern
        )
        assertTrue(regex.matches("1abend"))
        assertTrue(regex.matches("2abend"))
        assertTrue(regex.matches("4acend"))
        assertFalse(regex.matches("4aend"))
    }

    // _________________ semantic validation _________________
    @Test
    fun `Quantification is negative`() {
        // GIVEN
        // WHEN
        val result =
            assertThrows(RuntimeException::class.java) {
                build regex {
                    starts with "first" occurs 1..-1
                    followed with "second" occurs -1..-3
                    ends with "third" occurs 1000..992
                }
            }

        // THEN
        assertEquals(
            """The following Errors occurred:
Quantification must be positive for: 1..-1
First value must be smaller than second value for: 1..-1
Quantification must be positive for: -1..-3
First value must be smaller than second value for: -1..-3
First value must be smaller than second value for: 1000..992""",
            result.message
        )
    }


    @Test
    fun testForLoc() {
        val result = build regex {
            starts with "domain "
            followed with innerRegex {
                followed with "specific "
                followed with innerRegex {
                    followed with "modeling" or "design"
                }
            } or "driven design"
            ends with anything
        }
        assertEquals("^(domain )(((specific )(modeling|design))|driven design)(.*)$", result.pattern)
    }
}
