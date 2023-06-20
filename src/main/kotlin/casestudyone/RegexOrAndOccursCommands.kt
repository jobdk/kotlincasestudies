package casestudyone

interface RegexOrAndOccursCommands {
    infix fun or(next: String): RegexOrAndOccursCommands
    infix fun occurs(intRange: IntRange): RegexOrCommand
}