package casestudytwo.plantuml

object KotlinToPlantUml {
    private val accessMap: Map<Access, String> = mapOf(
        Access.public to "+",
        Access.private to "-",
        Access.protected to "#",
        Access.packagePrivate to "~"
    )

    private val nonAccessModifierMap: Map<NonAccessModifier, String> = mapOf(
        NonAccessModifier.static to " {static}",
        NonAccessModifier.abstract to " {abstract}",
        NonAccessModifier.none to ""
    )

    private val dependencyMap: Map<DependencyType, String> = mapOf(
        DependencyType.EXTENSION to "<|--",
        DependencyType.COMPOSITION to "*--",
        DependencyType.AGGREGATION to "o--",
    )

    fun buildPlantUml(plantUml: PlantUml): String {
        return buildString {
            append("@startuml\n")
            append(convertConcept(plantUml))
            append(convertDependencies(plantUml))
            append("@enduml")
        }
    }

    private fun convertConcept(plantUml: PlantUml): String {
        return buildString {
            append(plantUml.classes.joinToString("") { clazz ->
                mapClassName(clazz) +
                        mapFields(clazz.fields) +
                        mapConstructors(clazz.name, clazz.constructors) +
                        mapMethods(clazz.methods) + if (isBodyLess(clazz)) "" else "}\n"
            })

            append(plantUml.abstractClasses.joinToString("") { abstractClass ->
                mapAbstractClassName(abstractClass) +
                        mapFields(abstractClass.fields) +
                        mapConstructors(abstractClass.name, abstractClass.constructors) +
                        mapMethods(abstractClass.methods) + if (isBodyLess(abstractClass)) "" else "}\n"
            })

            append(plantUml.enums.joinToString("") { enum ->
                mapEnumName(enum) +
                        mapValues(enum.values) +
                        if (isBodyLess(enum)) "" else "\n}\n"
            })
            append(plantUml.interfaces.joinToString("") { interfaceEntry ->
                mapInterfaceName(interfaceEntry) +
                        mapFields(interfaceEntry.fields) +
                        mapMethods(interfaceEntry.methods) + if (isBodyLess(interfaceEntry)) "" else "}\n"
            })
        }
    }

    private fun mapValues(values: List<String>): String = values.joinToString("\n")

    private fun mapConstructors(className: String, constructors: List<Constructor>): String {
        return buildString {
            constructors.forEach { constructor ->
                val parameters = constructor.parameters.joinToString(", ") { parameter ->
                    "${parameter.name}: ${parameter.type}"
                }
                append("${accessMap[constructor.access]} $className($parameters) <<Constructor>>\n")
            }
        }
    }

    private fun mapMethods(methods: List<Method>): String {
        return buildString {
            methods.forEach { method ->
                val parameters = method.parameters.joinToString(", ") { parameter ->
                    "${parameter.name}: ${parameter.type}"
                }
                append("${accessMap[method.access]}${nonAccessModifierMap[method.nonAccessModifier]} ${method.name}($parameters): ${method.type}\n")
            }
        }
    }


    private fun mapFields(fields: List<Field>): String {
        return buildString {
            fields.forEach { field ->
                append(accessMap[field.access])
                append(nonAccessModifierMap[field.nonAccessModifier])
                append(" ${field.name}: ${field.type}\n")
            }
        }
    }


    private fun mapClassName(clazz: Class): String =
        "class ${clazz.name}" + if (isBodyLess(clazz)) "\n" else " {\n"

    private fun mapAbstractClassName(clazz: AbstractClass): String =
        "abstract class ${clazz.name}" + if (isBodyLess(clazz)) "\n" else " {\n"

    private fun mapEnumName(enum: Enum): String =
        "enum ${enum.name}" + if (isBodyLess(enum)) "\n" else " {\n"

    private fun mapInterfaceName(interfaceEntry: Interface): String =
        "interface ${interfaceEntry.name}" + if (isBodyLess(interfaceEntry)) "\n" else " {\n"

    private fun isBodyLess(interfaceEntry: Interface): Boolean {
        return interfaceEntry.methods.isEmpty() && interfaceEntry.fields.isEmpty()
    }

    private fun isBodyLess(clazz: Class): Boolean =
        clazz.methods.isEmpty() && clazz.fields.isEmpty() && clazz.constructors.isEmpty()

    private fun isBodyLess(abstractClass: AbstractClass): Boolean =
        abstractClass.methods.isEmpty() && abstractClass.fields.isEmpty() && abstractClass.constructors.isEmpty()

    private fun isBodyLess(enum: Enum): Boolean = enum.values.isEmpty()


    private fun convertDependencies(plantUml: PlantUml): String =
        buildString {
            append(plantUml.dependencies.joinToString("") { dependency ->
                "${dependency.from.name}${getFromCardinality(dependency.from.cardinality)} ${dependencyMap[dependency.dependencyType]} ${
                    getToCardinality(
                        dependency.to.cardinality
                    )
                }${dependency.to.name}${getLabel(dependency.label)}\n"
            })
        }

    private fun getLabel(label: String): String = if (label != "") " : $label" else ""

    private fun getFromCardinality(cardinality: String): String = if (cardinality != "") " \"$cardinality\"" else ""
    private fun getToCardinality(cardinality: String): String = if (cardinality != "") "\"$cardinality\" " else ""
}
