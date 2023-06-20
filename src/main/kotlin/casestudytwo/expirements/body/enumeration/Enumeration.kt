package casestudytwo.expirements.body.enumeration

import casestudytwo.plantuml.Enum
import casestudytwo.plantuml.PlantUml

/**
 * Experiment to reconstruct enum syntax
 * */
typealias start = PlantUmlDslBuilder

class PlantUmlDslBuilder {
    val add: PlantUmlDslBuilder = this
    private val enums: MutableList<Enum> = mutableListOf()


    companion object {
        infix fun uml(umlBuilderBlock: PlantUmlDslBuilder.() -> Unit): PlantUml =
            PlantUmlDslBuilder().apply(umlBuilderBlock).buildPlantUml()
    }

    private fun buildPlantUml(): PlantUml =
        PlantUml(mutableListOf(), mutableListOf(), enums, mutableListOf(), mutableListOf())

    @Suppress("FunctionName", "Starts with capital in order to be consistent with Class and Interface")
    infix fun Enum(enum: Enum): Unit = Unit

    operator fun String.invoke(enumBuilderBlock: EnumBuilder.() -> Unit): Enum {
        val enumBuilder: EnumBuilder = EnumBuilder().apply(enumBuilderBlock)
        val enum = Enum(this, enumBuilder.values)
        enums.add(enum)
        return enum
    }

    // Using Enum.with extension funciton in order to know which lambda context to use.
    infix fun Enum(enumName: String): Enum = Enum(enumName, mutableListOf())

    infix fun Enum.with(enumBuilderBlock: EnumBuilder.() -> Unit): Enum {
        val enum: Enum = EnumBuilder().apply(enumBuilderBlock).buildEnum(this.name)
        enums.add(enum)
        return enum
    }

}

class EnumBuilder {
    var values: MutableList<String> = mutableListOf()

    infix fun String.and(other: String): MutableList<String> {
        addValueToValues(this)
        return addValueToValues(other)
    }

    // Infix functions with add
    val add = this
    infix fun values(value: String): MutableList<String> {
        return addValueToValues(value)
    }

    infix fun MutableList<String>.and(other: String): MutableList<String> = addValueToValues(other)

    private fun addValueToValues(other: String): MutableList<String> {
        values.add(other)
        return values
    }

    fun buildEnum(name: String): Enum = Enum(name, values)

}