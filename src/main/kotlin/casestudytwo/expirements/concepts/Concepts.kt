package casestudytwo.expirements.concepts

import casestudytwo.plantuml.*
import casestudytwo.plantuml.Class
import casestudytwo.plantuml.Enum

@DslMarker
annotation class PlantUmlDslMarker
/**
 * Experiment to reconstruct entity syntax
 * */
typealias start = PlantUmlDslBuilder

@PlantUmlDslMarker
class PlantUmlDslBuilder {
    val add = this
    private val classes: MutableList<Class> = mutableListOf()
    private val abstractClasses: MutableList<AbstractClass> = mutableListOf()
    private val interfaces: MutableList<Interface> = mutableListOf()
    private val enums: MutableList<Enum> = mutableListOf()

    companion object {
        infix fun uml(umlBuilderBlock: PlantUmlDslBuilder.() -> Unit): PlantUml =
            PlantUmlDslBuilder().apply(umlBuilderBlock).buildPlantUml()
    }

    private fun buildPlantUml(): PlantUml =
        PlantUml(
            classes,
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        )

    infix fun Class(className: String): Class {
        val clazz =
            Class(className, mutableListOf(), mutableListOf(), mutableListOf())
        classes.add(clazz)
        return clazz
    }

    infix fun Interface(interfaceName: String): Interface {
        val interface_ =
            Interface(interfaceName, mutableListOf(), mutableListOf())
        interfaces.add(interface_)
        return interface_
    }

    infix fun Enum(enumName: String): Enum {
        val enum = Enum(enumName, mutableListOf())
        enums.add(enum)
        return enum
    }

    infix fun AbstractClass(abstractClassName: String) {
        abstractClasses.add(
            AbstractClass(
                abstractClassName,
                mutableListOf(),
                mutableListOf(),
                mutableListOf()
            )
        )
    }

    infix fun Class.with(
        classBuilderBlock: ClassBuilder.() -> Unit
    ): Class = ClassBuilder().apply(classBuilderBlock).buildClass(this.name)
        .also { classes[classes.indexOf(this)] = it }
}

@PlantUmlDslMarker
class ClassBuilder {
    private var name: String = ""
    private val fields: MutableList<Field> = mutableListOf()
    private val methods: MutableList<Method> = mutableListOf()
    private val constructors: MutableList<Constructor> = mutableListOf()


    fun name(name: String) {
        this.name = name
    }

    infix fun field(fieldBuilderBlock: FieldBuilder.() -> Unit) {
        FieldBuilder().apply(fieldBuilderBlock).buildField()
            .also { fields.add(it) }
    }

    infix fun method(
        methodBuilderBlock: MethodBuilder.() -> Unit
    ) {
        MethodBuilder().apply(methodBuilderBlock).buildMethod()
            .also { methods.add(it) }
    }

    infix fun constructor(
        constructorBuilderBlock: ConstructorBuilder.() -> Unit
    ) {
        ConstructorBuilder().apply(constructorBuilderBlock).buildConstructor()
            .also { constructors.add(it) }
    }

    fun buildClass(name: String): Class =
        Class(name, fields, methods, constructors)

}
