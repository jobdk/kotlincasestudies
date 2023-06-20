package casestudyone

import casestudyone.SemanticModelBuilder.createRegexExpression


typealias build = RegexBuilder

@RegexDsl
class RegexBuilder : PredefinedTerms() {
    val starts: StartsWithCommand = StartsWithCommand(mutableListOf())
    val followed: FollowedWithCommand = FollowedWithCommand(mutableListOf())
    val ends: EndsWithCommand = EndsWithCommand(mutableListOf())

    infix fun innerRegex(initialize: RegexBuilder.InnerRegex.() -> Unit): String = buildString {
        append(InnerRegex().apply(initialize).buildInnerRegex())
    }

    companion object {
        infix fun regex(regexBuilderBlock: RegexBuilder.() -> Unit): Regex =
            RegexBuilder().apply(regexBuilderBlock).buildRegex()
    }

    private fun buildRegex(): Regex = SemanticModelBuilder.buildRegex(starts, followed, ends)

    @RegexDsl
    inner class InnerRegex : PredefinedTerms() {
        fun buildInnerRegex(): Regex {
            val followedWith: String = if (this@RegexBuilder.followed.list.isNotEmpty()) {
                this@RegexBuilder.followed.list.joinToString("") { SemanticModelBuilder.buildFollowedWithRegex(it.followedList) }
            } else ""
            this@RegexBuilder.followed.list.clear()
            return Regex(followedWith)
        }

        infix fun innerRegex(initialize: RegexBuilder.InnerRegex.() -> Unit): String = buildString {
            append(this@RegexBuilder.InnerRegex().apply(initialize).buildInnerRegex())
        }

        val followed: FollowedWithCommand = this@RegexBuilder.FollowedWithCommand(mutableListOf())
    }

    inner class StartsWithCommand(val startList: MutableList<RegexExpression>) : RegexWithCommand {

        override infix fun with(next: String): StartsOrAndOccursCommands {
            startList.add(next.createRegexExpression())
            return StartsOrAndOccursCommands()
        }

        inner class StartsOrAndOccursCommands : RegexOrAndOccursCommands {
            override infix fun or(next: String): StartsOrAndOccursCommands {
                startList.add(next.createRegexExpression())
                return this
            }

            override infix fun occurs(intRange: IntRange): StartsOrCommand {
                startList[startList.lastIndex] = RegexExpression(startList.last().content, intRange)
                return StartsOrCommand()
            }

            inner class StartsOrCommand : RegexOrCommand {
                override infix fun or(next: String): StartsOrAndOccursCommands {
                    startList.add(next.createRegexExpression())
                    return StartsOrAndOccursCommands()
                }
            }
        }
    }

    inner class FollowedWithCommand(var list: MutableList<FollowedEntry>) : RegexWithCommand {
        inner class FollowedEntry {
            val followedList: MutableList<RegexExpression> = mutableListOf()

            fun addToList(next: String): Boolean = followedList.add(next.createRegexExpression())
        }

        override infix fun with(next: String): RegexOrAndOccursCommands {
            followed.list.add(FollowedEntry().apply { addToList(next) })
            return FollowedOrAndOccursCommands()
        }

        inner class FollowedOrAndOccursCommands : RegexOrAndOccursCommands {
            override infix fun or(next: String): RegexOrAndOccursCommands {
                followed.list.last().addToList(next)
                return this
            }

            override infix fun occurs(intRange: IntRange): RegexOrCommand {
                val currentFollowedListIndex = followed.list.last().followedList.lastIndex
                followed.list.last().followedList[currentFollowedListIndex] = RegexExpression(
                    followed.list.last().followedList.elementAt(currentFollowedListIndex).content, intRange
                )
                return FollowedOrCommand()
            }

            inner class FollowedOrCommand : RegexOrCommand {
                override infix fun or(next: String): RegexOrAndOccursCommands {
                    followed.list.last().addToList(next)
                    return FollowedOrAndOccursCommands()
                }
            }
        }
    }

    inner class EndsWithCommand(val endsList: MutableList<RegexExpression>) : RegexWithCommand {
        override infix fun with(next: String): RegexOrAndOccursCommands {
            endsList.add(next.createRegexExpression())
            return EndsOrAndOccursCommands()
        }

        inner class EndsOrAndOccursCommands : RegexOrAndOccursCommands {

            override infix fun or(next: String): RegexOrAndOccursCommands {
                endsList.add(next.createRegexExpression())
                return EndsOrAndOccursCommands()
            }

            override infix fun occurs(intRange: IntRange): RegexOrCommand {
                endsList[endsList.lastIndex] = RegexExpression(endsList.last().content, intRange)
                return EndsOrCommand()
            }

            inner class EndsOrCommand : RegexOrCommand {
                override infix fun or(next: String): RegexOrAndOccursCommands {
                    endsList.add(next.createRegexExpression())
                    return EndsOrAndOccursCommands()
                }
            }
        }
    }
}