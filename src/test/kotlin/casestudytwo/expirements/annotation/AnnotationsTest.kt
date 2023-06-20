package casestudytwo.expirements.annotation

import casestudytwo.plantuml.PlantUml
import org.junit.jupiter.api.Test

/** The following code is commented to not infer with other test executives.
 * Uncomment to test.
 */
class AnnotationSyntaxTest {
    @Test
    fun firstExperiment() {
        /* Experiment 1**/
        @startuml1
        start uml {
            add Class "ClassName"
        }
        /*@enduml1 // Errormessage: Expecting an expression -> // needs a target, therefore code below it.*/
    }

    @Test
    fun secondExperiment() {
        /** Experiment 2*/
        /*@startuml2( // Only possible to give a class as a parameter, not an object. Needs to have code below it.
            PlantUml::class
        )*/

        // Not possible to provide an object as a parameter unless it is a compile-time constant.
        /*@startuml2( // Errormessage: An annotation argument must be a compile-time constant
            start uml {
                add Class "ClassName"
            }
        )*/

        // Result: Annotations can not be used to initialize the creation of objects.
    }

    //    Solution: Using a Lambda.
    @Test
    fun solution() {
        val plantUml: PlantUml = start uml {
            add Class "ClassName"
        }
    }
}
