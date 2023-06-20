package casestudytwo.expirements.concepts

import org.junit.jupiter.api.Test

class FieldSyntaxTest {
    /**
     * Summary:
     *
     *
     * The PlantUml-Syntax cannot be implemented as it uses reserved words.
     *
     * The closest possible implementation is using wrapping the "this" keyword in an object called add and capitalizing
     * the first letter of the concept name.
     *
     *
     * */

    @Test
    fun testFieldSyntax() {
        // Does not work as words are already in use.
        /*start uml {
            class "ClassName"
            abstract class "AbstractClassName"
            interface "InterfaceName"
            enum "EnumName"
        }*/

        start uml {
            add Class "ClassName" with { }
            add AbstractClass "AbstractClassName"
            add Interface "InterfaceName"
            add Enum "EnumName"
        }
    }
}