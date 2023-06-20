package casestudytwo.expirements.annotation

import casestudytwo.plantuml.Class
import casestudytwo.plantuml.PlantUml
import kotlin.reflect.KClass

/** The following code is commented to not infer with other test executives.
 * Uncomment to test.
 */

/**Experiment 1: Using the annotation on top and below of building the uml in order to see if a context can be provided.
 *
 * Result:
 *
 * - The annotation is optional and, therefore, not a good practice and it can also be associated with different objects.
 *
 * - Annotation needs to have a target below it, therefore @enduml cannot be realised.*/
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.EXPRESSION)
annotation class startuml1

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.EXPRESSION) // needs a target, therefore code below it.
annotation class enduml1

/**Experiment 2: Creating the object inside the annotation
 *
 * Result:
 *
 * - Object cannot be created inside the annotation-parameters as it must be a compile-time constant.
 *
 * - The annotation still needs to be above a target even if the uml can be created inside the parameters.
 **/
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.EXPRESSION, AnnotationTarget.FILE, AnnotationTarget.LOCAL_VARIABLE)
annotation class startuml2(val plantUml: KClass<PlantUml>) // Only possible to expect a custom type with KClass

typealias start = PlantUmlDslBuilder

class PlantUmlDslBuilder {
    val add = this
    private val classes: MutableList<Class> = mutableListOf()

    companion object {
        infix fun uml(umlBuilderBlock: PlantUmlDslBuilder.() -> Unit): PlantUml =
            PlantUmlDslBuilder().apply(umlBuilderBlock).buildPlantUml()
    }

    private fun buildPlantUml(): PlantUml =
        PlantUml(classes, mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())

    infix fun Class(className: String): Class {
        val clazz = Class(className, mutableListOf(), mutableListOf(), mutableListOf())
        classes.add(clazz)
        return clazz
    }
}