package casestudyone

object SemanticModelBuilder : PredefinedTerms() {
    private val errors: MutableList<String> = mutableListOf()
    private val predefinedTerms: MutableSet<String> = mutableSetOf(anything, something, letters, numbers)

    fun buildRegex(
        starts: RegexBuilder.StartsWithCommand,
        followed: RegexBuilder.FollowedWithCommand,
        ends: RegexBuilder.EndsWithCommand
    ): Regex {
        val pattern: String = buildString {
            append(if (starts.startList.isEmpty()) "" else starts.startList.buildStartsWithRegex())
            append(if (followed.list.isEmpty()) "" else followed.list.joinToString("") {
                buildFollowedWithRegex(it.followedList)
            })
            append(if (ends.endsList.isEmpty()) "" else ends.endsList.buildEndsWithRegex())
        }
        validateRegex()
        return try {
            Regex((pattern))
        } catch (e: Exception) {
            throw java.lang.RuntimeException("Regex pattern is invalid: ${e.message}")
        }
    }

    private fun MutableList<RegexExpression>.buildStartsWithRegex(): String = "^(${
        this.joinToString("|") { it.content.addQuantification(it.quantification) }
    })"

    fun buildFollowedWithRegex(followedList: MutableList<RegexExpression>): String {
        val joinToString = followedList.joinToString("|") { it.content.addQuantification(it.quantification) }
        return joinToString.let { "($it)" }
    }

    private fun MutableList<RegexExpression>.buildEndsWithRegex(): String =
        "(${this.joinToString("|") { it.content.addQuantification(it.quantification) }})$"

    fun String.createRegexExpression(): RegexExpression =
        if (this in predefinedTerms) RegexExpression(this, 1..1) else RegexExpression(this.replaceDot(), 1..1)

    private fun String.replaceDot(): String = this.replace(".", "\\.").replace("\\\\.", "\\.")

    fun String.addQuantification(quantification: IntRange): String {
        validateQuantification(quantification)
        if (isIndefinitely(quantification)) return "($this)*"
        if (isOnce(quantification)) return this
        if (isTheSame(quantification)) return "($this){${quantification.first}}"
        return "($this){${quantification.first},${quantification.last}}"
    }

    private fun validateQuantification(quantification: IntRange) {
        if (isIndefinitely(quantification)) return
        if (isNegative(quantification)) errors.add("Quantification must be positive for:" + " $quantification")
        if (isWrongOrder(quantification)) errors.add("First value must be smaller" + " than second value for: $quantification")
    }

    private fun isIndefinitely(quantification: IntRange): Boolean =
        quantification.first == indefinitely.first && quantification.last == indefinitely.last

    private fun isTheSame(quantification: IntRange): Boolean = quantification.first == quantification.last

    private fun isOnce(quantification: IntRange): Boolean = quantification.first == 1 && quantification.last == 1

    private fun isWrongOrder(quantification: IntRange) = quantification.first > quantification.last

    private fun isNegative(quantification: IntRange) = quantification.first < 0 || quantification.last < 0

    private fun validateRegex() {
        if (errors.isNotEmpty()) throw RuntimeException("The following Errors occurred:\n${errors.joinToString("\n")}".trimMargin()).also { errors.clear() }
    }
}