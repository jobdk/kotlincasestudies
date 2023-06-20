package casestudyone
interface RegexOrCommand {
    infix fun or(next: String): RegexOrAndOccursCommands}