package casestudytwo.expirements.body.field

import casestudytwo.plantuml.*
import casestudytwo.plantuml.NonAccessModifier.none

/**
 * Experiment to reconstruct field syntax
 * */
typealias start = PlantUmlDslBuilder

class PlantUmlDslBuilder {
    val add: PlantUmlDslBuilder = this
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

    infix fun Class.with(classBuilderBlock: ClassBuilder.() -> Unit): Class =
        ClassBuilder().apply(classBuilderBlock).buildClass(this.name)
            .also { classes[classes.indexOf(this)] = it }
}

class ClassBuilder {
    private val fields: MutableList<Field> = mutableListOf()

    fun buildClass(name: String): Class = Class(name, fields, mutableListOf(), mutableListOf())

    operator fun <R> (() -> R).unaryPlus(): Field = Field(this() as NonAccessModifier, Access.public, "", "")
        .also { fields.add(it) }

    operator fun String.unaryPlus(): Field = Field(none, Access.public, this, "").also { fields.add(it) }

    infix fun Field.field(name: String): Field {
        val updatedField: Field = this.copy(name = name)
        fields[fields.lastIndex] = updatedField
        return updatedField
    }

    infix fun Field.of(type: String): Field {
        val updatedField: Field = this.copy(type = type)
        fields[fields.lastIndex] = updatedField
        return updatedField
    }
}