package casestudytwo.expirements.body.enumeration

import casestudytwo.plantuml.PlantUml
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** Only researches ways to implement the PlantUml-Syntax for the Enum-Concept-Body, as the other
concepts are already checked in the FieldSyntaxTest.kt.*/
class BodiesTest {
    @Test
    fun testEnumBody() {
        /**
         * Full syntax is not achievable since the values cannot be directly separated by a new line within the enum-body.
         * Instead, they must be assigned to a variable of some sort and separated using a different delimiter.
         * As a result, an alternative approach is presented, using infix functions called "and" and assigning it to values.
         */
        /*Needed syntax:
          enum enumName {
           value1,
           value2,
           value3,
           value4
          }*/

        // Option 1: Using infix functions and the String.invoke extension function to provide the lambda context.
        val plantUmlInfixAndInvoke: PlantUml = PlantUmlDslBuilder uml {
            add Enum "enumName" {
                values = "value1" and "value2" and "value3" and "value4"
            }
        }

        // Invoke works with every String, so this is not a solid solution.
        start uml {
            add Enum "enumName" {
                // Should not be possible
                "value4" {
                }
            }
        }

        // Assertions
        val enum1 = plantUmlInfixAndInvoke.enums[0]
        assertEquals("enumName", enum1.name)
        assertEquals(mutableListOf("value1", "value2", "value3", "value4"), enum1.values)

        //Solution: Using infix functions and the with function to provide the lambda context.
        val plantUmlInfixAndWith: PlantUml = PlantUmlDslBuilder uml {
            add Enum "enumName" with {
                add values "value1" and "value2" and "value3" and "value4"
            }
        }

        // Assertions
        val enum2 = plantUmlInfixAndWith.enums[0]
        assertEquals("enumName", enum2.name)
        assertEquals(mutableListOf("value1", "value2", "value3", "value4"), enum2.values)
    }
}


