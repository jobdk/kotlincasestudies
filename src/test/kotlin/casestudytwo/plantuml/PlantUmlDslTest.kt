package casestudytwo.plantuml

import casestudytwo.plantuml.Access.*
import casestudytwo.plantuml.NonAccessModifier.abstract
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class PlantUmlDslTest {
    @Test
    fun testClassWithFieldsMethodsAndConstructor() {
// Given
// When
        val plantUml: String = start uml {
            add Class "MyClass" with {
                field {
                    nonAccessModifier = NonAccessModifier.static
                    access = private
                    name = "privateField"
                    type = "String"
                }
                field {
                    access = protected
                    name = "protectedField"
                    type = "int"
                }
                field {
                    access = public
                    name = "publicField"
                    type = "double"
                }
                field {
                    access = packagePrivate
                    name = "packageField"
                    type = "boolean"
                }
                constructor {
                    access = public
                    parameter {
                        name = "parameter1"
                        type = "String"
                    }
                }

                method {
                    nonAccessModifier = NonAccessModifier.static
                    access = public
                    name = "publicMethod"
                    type = "void"
                    parameter {
                        name = "parameter1"
                        type = "String"
                    }
                    parameter {
                        name = "parameter2"
                        type = "String"
                    }
                }
                method {
                    access = protected
                    name = "protectedMethod"
                    type = "int"
                }
                method {
                    access = private
                    name = "privateMethod"
                    type = "String"
                }
                method {
                    access = packagePrivate
                    name = "packageMethod"
                    type = "boolean"
                }
            }
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "class MyClass {\n" +
                    "- {static} privateField: String\n" +
                    "# protectedField: int\n" +
                    "+ publicField: double\n" +
                    "~ packageField: boolean\n" +
                    "+ MyClass(parameter1: String) <<Constructor>>\n" +
                    "+ {static} publicMethod(parameter1: String, parameter2: String): void\n" +
                    "# protectedMethod(): int\n" +
                    "- privateMethod(): String\n" +
                    "~ packageMethod(): boolean\n" +
                    "}\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testClassWithoutBody() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Class "MyClass"
        }


        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "class MyClass\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testAbstractClassWithFields() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add AbstractClass "MyClass" with {
                field {
                    access = private
                    name = "privateField"
                    type = "String"
                }
            }
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "abstract class MyClass {\n" +
                    "- privateField: String\n" +
                    "}\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testAbstractClassWithoutBody() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add AbstractClass "MyClass"
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "abstract class MyClass\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testEnumWithBody() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Enum "TimeUnit" with {
                add values "DAYS" and "HOURS" and "MINUTES"
            }
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "enum TimeUnit {\n" +
                    "DAYS\n" +
                    "HOURS\n" +
                    "MINUTES\n" +
                    "}\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testEnumWithoutBody() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Enum "TimeUnit"
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "enum TimeUnit\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testInterfaceWithBody() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Interface "InterfaceName" with {
                field {
                    access = private
                    name = "privateField"
                    type = "String"
                }
                method {
                    access = public
                    name = "publicMethod"
                    type = "void"
                }
            }
            add Interface "InterfaceName2" with {
                field {
                    access = private
                    name = "privateField2"
                    type = "String"
                }
                method {
                    access = public
                    name = "publicMethod2"
                    type = "void"
                }
            }
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "interface InterfaceName {\n" +
                    "- privateField: String\n" +
                    "+ publicMethod(): void\n" +
                    "}\n" +
                    "interface InterfaceName2 {\n" +
                    "- privateField2: String\n" +
                    "+ publicMethod2(): void\n" +
                    "}\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testInterfaceWithoutBody() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Interface "InterfaceName"
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "interface InterfaceName\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testDependencyBetweenConcepts() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Class "Class"
            add Class "Class2"
            add Enum "Enum"
            add AbstractClass "AbstractClass"
            add Interface "Interface"
            add Dependencies {
                "AbstractClass" extends "Class"
                "Class" extends "Class2"
                "Class2" composes "Enum"
                "Class2" composes "Interface"
            }
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "class Class\n" +
                    "class Class2\n" +
                    "abstract class AbstractClass\n" +
                    "enum Enum\n" +
                    "interface Interface\n" +
                    "AbstractClass <|-- Class\n" +
                    "Class <|-- Class2\n" +
                    "Class2 *-- Enum\n" +
                    "Class2 *-- Interface\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testDependencyBetweenConceptsWithCardinalityAndLabel() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Class "Class"
            add Class "Class2"
            add Enum "Enum"
            add AbstractClass "AbstractClass"
            add Interface "Interface"
            add Dependencies {
                "AbstractClass" extends "Class"
                "Class" of "1" extends "Class2" labeled "test label"
                "Class2" composes "Enum" of "many"
                "Class2" of "2" composes "Interface" of "*" labeled "label"
            }
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            """@startuml
class Class
class Class2
abstract class AbstractClass
enum Enum
interface Interface
AbstractClass <|-- Class
Class "1" <|-- Class2 : test label
Class2 *-- "many" Enum
Class2 "2" *-- "*" Interface : label
@enduml""", plantUml
        )
    }

    @Test
    fun testWithAllConceptsMultipleTimesWithoutBody() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Class "Class"
            add Class "Class2"
            add Enum "Enum"
            add AbstractClass "AbstractClass"
            add Interface "Interface"
            add AbstractClass "AbstractClass2"
            add Enum "Enum2"
            add Interface "Interface2"
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "class Class\n" +
                    "class Class2\n" +
                    "abstract class AbstractClass\n" +
                    "abstract class AbstractClass2\n" +
                    "enum Enum\n" +
                    "enum Enum2\n" +
                    "interface Interface\n" +
                    "interface Interface2\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun test_with_all_concepts_multiple_times_with_body() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Class "Class" with {
                field {
                    access = private
                    name = "privateField"
                    type = "String"
                }
                method {
                    access = private
                    name = "privateMethod"
                    type = "String"
                }
                constructor {
                    access = public
                }
            }
            add Enum "Enum" with {
                add values "DAYS" and "HOURS" and "MINUTES"
            }
            add AbstractClass "AbstractClass" with {
                field {
                    access = private
                    name = "privateField"
                    type = "String"
                }
                method {
                    access = private
                    name = "privateMethod"
                    type = "String"
                }
                constructor {
                    access = public
                }
            }
            add Interface "InterfaceName" with {
                field {
                    access = private
                    name = "privateField"
                    type = "String"
                }
                method {
                    access = public
                    name = "publicMethod"
                    type = "void"
                }
            }
            add Dependencies {
                "AbstractClass" extends "Class"
                "Class" extends "Class2"
                "Class2" composes "Enum"
                "Class2" composes "InterfaceName"
            }
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "class Class {\n" +
                    "- privateField: String\n" +
                    "+ Class() <<Constructor>>\n" +
                    "- privateMethod(): String\n" +
                    "}\n" +
                    "abstract class AbstractClass {\n" +
                    "- privateField: String\n" +
                    "+ AbstractClass() <<Constructor>>\n" +
                    "- privateMethod(): String\n" +
                    "}\n" +
                    "enum Enum {\n" +
                    "DAYS\n" +
                    "HOURS\n" +
                    "MINUTES\n" +
                    "}\n" +
                    "interface InterfaceName {\n" +
                    "- privateField: String\n" +
                    "+ publicMethod(): void\n" +
                    "}\n" +
                    "AbstractClass <|-- Class\n" +
                    "Class <|-- Class2\n" +
                    "Class2 *-- Enum\n" +
                    "Class2 *-- InterfaceName\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testNonAccessModifier() {
        // GIVEN
        // WHEN
        val plantUml: String = casestudytwo.plantuml.start uml {
            add Class "Class" with {
                field {
                    nonAccessModifier = NonAccessModifier.static
                    access = private
                    name = "privateField"
                    type = "String"
                }
                method {
                    nonAccessModifier = abstract
                    access = private
                    name = "privateMethod"
                    type = "String"
                }
            }
        }

        // THEN
        println(plantUml)
        Assertions.assertEquals(
            "@startuml\n" +
                    "class Class {\n" +
                    "- {static} privateField: String\n" +
                    "- {abstract} privateMethod(): String\n" +
                    "}\n" +
                    "@enduml", plantUml
        )
    }

    @Test
    fun testForLoc() {
        // GIVEN
        // WHEN
        val plantUml: String = start uml {
            add Class "ClassName" with {
                field {
                    nonAccessModifier = NonAccessModifier.static
                    access = Access.private
                    name = "privateField"
                    type = "String"
                }
                constructor {
                    access = Access.public
                    parameter {
                        name = "privateField"
                        type = "String"
                    }
                }
                method {
                    nonAccessModifier = NonAccessModifier.static
                    access = Access.public
                    name = "publicMethod"
                    type = "void"
                    parameter {
                        name = "parameter"
                        type = "String"
                    }
                }
            }
            add AbstractClass "AbstractClassName" with {
                field {
                    nonAccessModifier = NonAccessModifier.abstract
                    access = Access.protected
                    name = "protectedField"
                    type = "int"
                }
                constructor {
                    access = Access.public
                    parameter {
                        name = "protectedField"
                        type = "int"
                    }
                }
                method {
                    nonAccessModifier = NonAccessModifier.static
                    access = Access.private
                    name = "privateMethod"
                    type = "void"
                    parameter {
                        name = "parameter"
                        type = "String"
                    }
                }
            }
            add Interface "InterfaceName" with {
                field {
                    access = Access.public
                    name = "publicField"
                    type = "String"
                }
                method {
                    access = Access.public
                    name = "publicMethod"
                    type = "Map"
                    parameter {
                        name = "parameter"
                        type = "String"
                    }
                }
            }
            add Enum "EnumName" with {
                add values "VALUE1" and "VALUE2" and "VALUE3"
            }
            add Dependencies {
                "ClassName" extends "EnumName"
                "ClassName" of "1" composes "AbstractClassName" of "many" labeled "test label"
                "AbstractClassName" aggregates "ClassName" of "*"
                "ClassName" aggregates "AbstractClassName" labeled "test"
            }
        }

        // THEN
        Assertions.assertEquals(
            """@startuml
class ClassName {
- {static} privateField: String
+ ClassName(privateField: String) <<Constructor>>
+ {static} publicMethod(parameter: String): void
}
abstract class AbstractClassName {
# {abstract} protectedField: int
+ AbstractClassName(protectedField: int) <<Constructor>>
- {static} privateMethod(parameter: String): void
}
enum EnumName {
VALUE1
VALUE2
VALUE3
}
interface InterfaceName {
+ publicField: String
+ publicMethod(parameter: String): Map
}
ClassName <|-- EnumName
ClassName "1" *-- "many" AbstractClassName : test label
AbstractClassName o-- "*" ClassName
ClassName o-- AbstractClassName : test
@enduml""", plantUml
        )

    }
}

