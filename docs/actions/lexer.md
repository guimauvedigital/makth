---
title: Algorithm Lexer
parent: Actions
nav_order: 25
---

# Algorithm Lexer

## Basic Usage

A set of actions can be obtained using the algorithm lexer:

```kotlin
val raw = """
// This is an example algorithm
set(x, 2)
while (x < 10) {
    set(x, x + 1)
}
print("x = ", x)
"""
val actions = AlgorithmLexer(raw).execute()
val result = Context().execute(actions)
```

## Register custom actions

You can define a custom action by implementing the `Action` interface:

```kotlin
data class CustomAction(val value: Value) : Action {

    companion object {

        fun handler(args: List<Value>): Action {
            if (args.count() != 1) {
                throw IncorrectArgumentCountException("custom", args.count(), 1)
            }
            return CustomAction(args[0])
        }
    }

    override fun execute(context: Context): Context {
        return context
    }

    override val algorithmString: String
        get() {
            return "custom(" + value.algorithmString + ")"
        }

}
```

And then register it in your lexer instance to use it in your algorithm:

```kotlin
val actions = AlgorithmLexer("custom(2)")
    .registerKeyword("custom", CustomAction::handler)
    .execute()
```
