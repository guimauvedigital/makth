---
title: Math Lexer
parent: Numbers
nav_order: 13
---

# Math Lexer

To use the math lexer, you should define a context first:

```kotlin
val context = Context()
```

Then, call the lexer with this context:

```kotlin
val result = MathLexer("1 + 2").execute(context) // 3
```

Thanks to the context, you can pass variables:

```kotlin
val context = Context(
    mapOf(
        "x" to IntegerFactory.instantiate(2)
    )
)
val result = MathLexer("x + 3").execute(context) // 5
```
