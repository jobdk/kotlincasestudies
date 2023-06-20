package casestudytwo.expirements.body.field

import casestudytwo.plantuml.Access
import casestudytwo.plantuml.NonAccessModifier
import casestudytwo.plantuml.NonAccessModifier.static
import casestudytwo.plantuml.PlantUml
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Tries to implement the plantUml field syntax, which is not possible in Kotlin as methods are needed between
 * fieldName and type. Colon and other operators cannot be overloaded or used as existing shorthand.
 * */

class FieldSyntaxTest {
    @Test
    fun testFieldSyntax() {
        // Needs to have methods between {static} and fieldName, as well as type to work.
        val plantUml: PlantUml = start uml {
            add Class "ClassName" with {
                +{ static } field "fieldName" of "int"
                +"fieldName1" of "String"
                +"fieldName2" of "int"
            }
        }
        Assertions.assertEquals(plantUml.classes[0].name, "ClassName")
        Assertions.assertEquals(plantUml.classes[0].fields[0].name, "fieldName")
        Assertions.assertEquals(plantUml.classes[0].fields[0].type, "int")
        Assertions.assertEquals(plantUml.classes[0].fields[0].nonAccessModifier, static)
        Assertions.assertEquals(plantUml.classes[0].fields[0].access, Access.public)
        Assertions.assertEquals(plantUml.classes[0].fields[1].name, "fieldName1")
        Assertions.assertEquals(plantUml.classes[0].fields[1].type, "String")
        Assertions.assertEquals(plantUml.classes[0].fields[1].nonAccessModifier, NonAccessModifier.none)
        Assertions.assertEquals(plantUml.classes[0].fields[1].access, Access.public)


        // Using a shorthand like in groovy is not possible it is nod provided by Kotlin.
//    build uml {
//        add Class "ClassName" with {
//            add + { static } with "fieldName" : "String"
//        }
//    }

        // Directly chaining String to result of Lamda is not possible as method is needed
//    build uml {
//        add Class "ClassName" with {
//            add + { static } "fieldName" of "String"
//        }
//    }
    }
//        @Test
//        fun tester(){
//            val plantUml: PlantUml = start uml {
//                add Class "ClassName" with {
//                    public field "fieldName" of "int"
//                    public method "methodName" with {
//                        public parameter "parameterName" of "int"
//                        public parameter "parameterName1" of "String"
//                    } returns "String"
//                }
//            }
//        }


}
