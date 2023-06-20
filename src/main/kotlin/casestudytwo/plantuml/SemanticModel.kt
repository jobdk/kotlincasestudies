package casestudytwo.plantuml

data class PlantUml(
    val classes: List<Class>,
    val abstractClasses: List<AbstractClass>,
    val enums: List<Enum>,
    val interfaces: List<Interface>,
    val dependencies: List<Dependency>
)

data class Class(
    val name: String,
    val fields: List<Field>,
    val methods: List<Method>,
    val constructors: List<Constructor>
)

data class AbstractClass(
    val name: String,
    val fields: List<Field>,
    val methods: List<Method>,
    val constructors: List<Constructor>
)

data class Enum(val name: String, val values: List<String>)

data class Interface(val name: String, val fields: List<Field>, val methods: List<Method>)

data class Field(
    val nonAccessModifier: NonAccessModifier,
    val access: Access,
    val name: String,
    val type: String
)

data class Method(
    val nonAccessModifier: NonAccessModifier,
    val access: Access,
    val name: String,
    val type: String,
    val parameters: List<Parameter>
)

data class Constructor(val access: Access, val parameters: List<Parameter>)

data class Parameter(val name: String, val type: String)

enum class Access { public, private, protected, packagePrivate }

enum class NonAccessModifier { static, abstract, none }

data class DependencyConcept(val name: String, var cardinality: String)

data class Dependency(
    val from: DependencyConcept,
    val to: DependencyConcept,
    val dependencyType: DependencyType,
    val label: String
)

enum class DependencyType { EXTENSION, COMPOSITION, AGGREGATION }

