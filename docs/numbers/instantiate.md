---
title: Instantiate numbers
parent: Numbers
nav_order: 11
---

# Instantiate numbers

```kotlin
val integer = IntegerFactory.instantiate(2) // 2
val rational = RationalFactory.instantiate(3, 2) // 3/2
val real = RealFactory.instantiate(1.23456789) // 1.23456789
```

## Predefined constants

```kotlin
val pi = RealFactory.pi
```
