package casestudyone

@RegexDsl
open class PredefinedTerms {
    val anything: String = ".*"
    val something: String = ".+"
    val letters: String = "[a-zA-ZäÄüÜöÖß\\s]+"
    val numbers: String = "[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?"
    val indefinitely: IntRange = 0..-Int.MAX_VALUE
}