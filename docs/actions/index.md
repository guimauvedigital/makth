---
title: Actions
nav_order: 20
---

# Actions

You can execute actions to play with a context:

```kotlin
val result = Context().execute(
    listOf(
        SetAction("x", IntegerFactory.instantiate(2)),
        WhileAction(
            Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(10), Equality.Operator.LessThan),
            listOf(
                SetAction("x", Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(1)))
            )
        ),
        PrintAction(listOf(StringValue("x = "), VariableFactory.instantiate("x")))
    )
)
```

`result` will looks like this:

```kotlin
Context(
    mapOf(
        "x" to IntegerFactory.instantiate(10) // The value of x
    ),
    listOf(
        StringValue("x = "), IntegerFactory.instantiate(10), StringValue("\n") // What we printed
    )
)
```
