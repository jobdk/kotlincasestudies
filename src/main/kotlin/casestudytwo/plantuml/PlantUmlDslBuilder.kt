package casestudytwo.plantuml

@DslMarker
annotation class PlantUmlDslMarker

typealias start = PlantUmlDslBuilder

@PlantUmlDslMarker
class PlantUmlDslBuilder {
    val add: PlantUmlDslBuilder = this
    private val classes: MutableList<Class> = mutableListOf()
    private val abstractClasses: MutableList<AbstractClass> = mutableListOf()
    private val enums: MutableList<Enum> = mutableListOf()
    private val interfaces: MutableList<Interface> = mutableListOf()
    private var dependencies: MutableList<Dependency> = mutableListOf()

    companion object {
        infix fun uml(umlBuilderBlock: PlantUmlDslBuilder.() -> Unit): String =
            PlantUmlDslBuilder().apply(umlBuilderBlock).buildPlantUml()    }

    infix fun Class(className: String): Class {
        val clazz = Class(className, mutableListOf(), mutableListOf(), mutableListOf())
        classes.add(clazz)
        return clazz    }

    infix fun AbstractClass(className: String): AbstractClass {
        val abstractClass = AbstractClass(className, mutableListOf(), mutableListOf(), mutableListOf())
        abstractClasses.add(abstractClass)
        return abstractClass    }

    infix fun Enum(enumName: String): Enum {
        val enum = Enum(enumName, mutableListOf())
        enums.add(enum)
        return enum    }

    infix fun Interface(interfaceName: String): Interface {
        val value = Interface(interfaceName, mutableListOf(), mutableListOf())
        interfaces.add(value)
        return value   }

    infix fun Class.with(classBuilderBlock: ClassBuilder.() -> Unit): Class =
        ClassBuilder().apply(classBuilderBlock).buildClass(this.name)
            .also { classes[classes.indexOf(this)] = it }

    infix fun AbstractClass.with(classBuilderBlock: ClassBuilder.() -> Unit): Class =
        ClassBuilder().apply(classBuilderBlock).buildClass(this.name)
            .also {
                abstractClasses[abstractClasses.indexOf(this)] =
                    AbstractClass(it.name, it.fields, it.methods, it.constructors)            }

    infix fun Enum.with(enumBuilderBlock: EnumBuilder.() -> Unit): Enum =
        EnumBuilder().apply(enumBuilderBlock).buildEnum(this.name)
            .also { enums[enums.indexOf(this)] = it }

    infix fun Interface.with(interfaceBuilderBlock: InterfaceBuilder.() -> Unit): Interface =
        InterfaceBuilder().apply(interfaceBuilderBlock).buildInterface(this.name)
            .also { interfaces[interfaces.indexOf(this)] = it }

    infix fun Dependencies(dependencyBuilderBlock: DependencyBuilder.() -> Unit): MutableList<Dependency> =
        DependencyBuilder().apply(dependencyBuilderBlock).buildDependency()
            .also { dependencies = it }

    fun buildPlantUml(): String {
        val plantUml = PlantUml(classes, abstractClasses, enums, interfaces, dependencies)
        return KotlinToPlantUml.buildPlantUml(plantUml)    }}

@PlantUmlDslMarker
class ClassBuilder {
    private val fields: MutableList<Field> = mutableListOf()
    private val methods: MutableList<Method> = mutableListOf()
    private val constructors: MutableList<Constructor> = mutableListOf()

    infix fun field(fieldBuilderBlock: FieldBuilder.() -> Unit) {
        FieldBuilder().apply(fieldBuilderBlock).buildField()
            .also { fields.add(it) }    }

    infix fun method(methodBuilderBlock: MethodBuilder.() -> Unit) {
        MethodBuilder().apply(methodBuilderBlock).buildMethod()
            .also { methods.add(it) }    }

    infix fun constructor(constructorBuilderBlock: ConstructorBuilder.() -> Unit) {
        ConstructorBuilder().apply(constructorBuilderBlock).buildConstructor()
            .also { constructors.add(it) }    }

    fun buildClass(name: String): Class = Class(name, fields, methods, constructors)}

@PlantUmlDslMarker
class EnumBuilder {
    val add: EnumBuilder = this
    private var values: MutableList<String> = mutableListOf()

    infix fun values(value: String): MutableList<String> = addValueToValues(value)

    infix fun MutableList<String>.and(other: String): MutableList<String> = addValueToValues(other)

    private fun addValueToValues(other: String): MutableList<String> = values.also { values.add(other) }

    fun buildEnum(name: String): Enum = Enum(name, values)}

@PlantUmlDslMarker
class InterfaceBuilder {
    private val fields: MutableList<Field> = mutableListOf()
    private val methods: MutableList<Method> = mutableListOf()

    infix fun field(fieldBuilderBlock: FieldBuilder.() -> Unit) {
        FieldBuilder().apply(fieldBuilderBlock).buildField()
            .also { fields.add(it) }    }

    infix fun method(methodBuilderBlock: MethodBuilder.() -> Unit) {
        MethodBuilder().apply(methodBuilderBlock).buildMethod()
            .also { methods.add(it) }    }

    fun buildInterface(name: String): Interface = Interface(name, fields, methods)}

@PlantUmlDslMarker
class FieldBuilder {
    var nonAccessModifier: NonAccessModifier = NonAccessModifier.none
    var access: Access = Access.public
    var name: String = ""
    var type: String = ""

    fun buildField(): Field = Field(nonAccessModifier, access, name, type)
}

@PlantUmlDslMarker
class MethodBuilder {
    var nonAccessModifier: NonAccessModifier = NonAccessModifier.none
    var access: Access = Access.public
    var name: String = ""
    var type: String = ""
    private var parameters: MutableList<Parameter> = mutableListOf()

    infix fun parameter(parameterBuilderBlock: ParameterBuilder.() -> Unit) {
        ParameterBuilder().apply(parameterBuilderBlock).buildParameter()
            .also { parameters.add(it) }    }

    fun buildMethod(): Method = Method(nonAccessModifier, access, name, type, parameters)}

@PlantUmlDslMarker
class ParameterBuilder {
    var name: String = ""
    var type: String = ""

    fun buildParameter(): Parameter = Parameter(name, type)}

class ConstructorBuilder {
    var access: Access = Access.public
    private val parameters: MutableList<Parameter> = mutableListOf()

    infix fun parameter(parameterBuilderBlock: ParameterBuilder.() -> Unit) {
        ParameterBuilder().apply(parameterBuilderBlock).buildParameter().also { parameters.add(it) }    }

    fun buildConstructor(): Constructor = Constructor(access, parameters)}

@PlantUmlDslMarker
class DependencyBuilder {
    private val dependencies: MutableList<Dependency> = mutableListOf()

    infix fun String.extends(nextClass: String): Int = addDependency(
        DependencyConcept(this, ""),
        DependencyConcept(nextClass, ""),
        DependencyType.EXTENSION    )

    infix fun String.composes(nextClass: String): Int = addDependency(
        DependencyConcept(this, ""),
        DependencyConcept(nextClass, ""),
        DependencyType.COMPOSITION    )

    infix fun String.aggregates(nextClass: String): Int = addDependency(
        DependencyConcept(this, ""),
        DependencyConcept(nextClass, ""),
        DependencyType.AGGREGATION    )

    infix fun DependencyConcept.extends(nextClass: String): Int =
        addDependency(this, DependencyConcept(nextClass, ""), DependencyType.EXTENSION)

    infix fun DependencyConcept.composes(nextClass: String): Int =
        addDependency(this, DependencyConcept(nextClass, ""), DependencyType.COMPOSITION)

    infix fun DependencyConcept.aggregates(nextClass: String): Int =
        addDependency(this, DependencyConcept(nextClass, ""), DependencyType.AGGREGATION)

    infix fun String.of(cardinality: String): DependencyConcept = DependencyConcept(this, cardinality)

    infix fun Int.of(cardinality: String): Dependency {
        val last: Dependency = dependencies.last()
        dependencies[this] = last.copy(to = DependencyConcept(last.to.name, cardinality))
        return dependencies.last()    }

    infix fun Dependency.labeled(label: String) {
        dependencies[dependencies.indexOf(this)] = this.copy(label = label)    }

    infix fun Int.labeled(label: String) {
        dependencies[this] = dependencies[this].copy(label = label)    }

    private fun addDependency(from: DependencyConcept, to: DependencyConcept, type: DependencyType): Int {
        dependencies.add(Dependency(from, to, type, ""))
        return dependencies.size - 1    }

    fun buildDependency(): MutableList<Dependency> = dependencies}