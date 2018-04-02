# Functional Lite Kotlin

Pracitical functional programming techniques for Kotlin, without the jargon

Nish Tahir, 2018

<br>
<img width="200px" src="https://cdn-images-1.medium.com/max/810/1*gZ9XF80M8yOasLiFUzL07g.png">

# Agenda

1. **Functional Lite™ programming?** - The heck is this?

2. Kotlin **functions**: Notation, terminology, etc.

3. Higher order functions: **Functions** in **functions** on **functions** that take **functions**

4. Monad like types: TL;DR - How to do cool stuff with **when**


<br>

Feel free to ask questions at any time

# Functional Lite™

Incorporate some Functional Programming concepts into your everyday OOP (or otherwise) code.

* Nothing new. More or less stolen from other languages.

* Make the most of tools that your language provides. 

* Make better and more reusable abstractions.

* (Hopefully) result in less code to manage


# Let's take a look at Kotlin functions

Kotlin ships with standard _First order functions_

```kotlin
fun foo(): Unit { println("Hello World!") }

fun bar(hello: String) = println(hello)

fun baz() {
    fun bar(): String = "Woo!"
    val baz = bar()
}
```

<br>

There is no order without the first order

<img src= "https://lumiere-a.akamaihd.net/v1/images/first-order_8ae2955f.jpeg?region=0%2C0%2C1560%2C878&width=768" width= "150px"/>


# Functions, Side Effects and You

* Accidentally (or otherwise) **mutating or consuming state** outside of your function. 
* This makes the function non deterministic in nature.
* **Determinism** = For every input, you compute the same output

```kotlin
var y: Int = 10

fun foo(x: Int): Int = y * 2 + x

foo(3) // 23

...

y = 100

...

foo(3) // 203 WTF!?
```

```kotlin
fun foo(x: Int, y: Int): Int = y * 2 + x
val y = foo(3, 10) // 23
val z = foo(3, 100) // 203
```

# Purifying side effects (sort of...)

* Wrap and encapsulate! While this doesn't always fix the problem, it makes it somewhat more manageable

* **Be wary of functions that return `Unit`**

```kotlin
val data = mutableEmptyList<String>()
val userId = 1

fun fetchUserData(user: Int) {
    fetch(userId) { newData ->
        data.addAll(newData)
    }
}
```

```kotlin
fun updateData(user: Int, 
    data: List<String>): List<String> {

    val newData = mutableEmptyList<String>()
    newData.addAll(data)

    fun fetchUserData(user: Int) {
        fetch(userId) {
            newData.addAll(it)
        }
    }

    fetchUserData(user)
    return newData
}

```

# Value immutability

* Reducing side effects goes hand in hand with immutable data 
* Treat pretty much everything as thought it was immutable
* Avoid `var` whenever possible (✅ `val`, ❌ `var`)

```kotlin
// 💯
data class Type (val thing: String, val otherThing: Int) 
val immutableCollection = listOf()

// Avoid ...
data class Type (var thing: String, var otherThing: Int) 
val immutableCollection = mutableListOf()

```

# Functions can be higher order

Functions can take other functions as parameters and return functions as the result of operations. 

These are _Higher order functions_.

```kotlin
fun foo(number: Int, transform: (Int) -> String): String = transform(number)

fun bar() : (String) -> Int {
    return fun(string: String): Int = Integer.valueOf(string)
}

// or

fun bar(): String -> Int = { string ->
    Integer.valueOf(string)
}
```

# First class functions

Functions in kotlin are just values.

```kotlin
val myFunction = { num: Int, string: String ->
    "$string ${num - 1}!"
}

// or 
val myFunction = fun(num: Int, string: String): String {
    return "$string ${num - 1}!"
}

// ...
myFunction(9001, "It's Over")

```

# Higher Order functions

Goal: Factor out operations by behavior. 
Compose behavior together to make more complex behavior

```kotlin
fun sumInts(lower: Int, upper: Int) : Int{
    return if(lower > upper) {
        0
    } else {
        lower + sumInts(lower + 1, upper)
    }
}

fun sumSquares(lower: Int, upper: Int) : Int{
    return if(lower > upper) {
        0
    } else {
        (lower * lower) + sumSquares(lower + 1, upper)
    }
}

println("Sum of Ints = ${sumInts(1, 5)}")
println("Sum of Squares = ${sumSquares(1, 5)}")
```

# Higher Order Functions

Composable functions are small, reusable, and easier to test!

```kotlin
fun sum(map: (Int) -> Int): (Int, Int) -> Int {
    fun compute(lower: Int, upper: Int): Int {
        return if(lower > upper) { 0 } 
            else {
                map(lower) + compute(lower + 1, upper)
            }
        }
    return ::compute
}

val sumInts = sum({ num -> num }) 
val sumSquares = sum({ num -> num * num }) 

println("Sum of Ints = ${sumInts(1, 5)}")
println("Sum of Squares = ${sumSquares(1, 5)}")
```

# Composing functions

* Fancy word for passing functions as parameters for other functions
* Grand scheme -> Add an Extra parameter to a larger function

```kotlin
fun <A, B, C> compose(func1: (A) -> B, func2: (C) -> A) : (C) -> B {
    return {c -> func1(func2(c)) }
}

fun <A, B, C> forwardCompose(func1: (A) -> B, func2: (B) -> C): (A) -> C {
    return { a -> func2(func1(a)) }
}
```

# Collection operators

Easy collections manipulation

```kotlin
(0 .. 10_000_000L)
    .map { it * it }
    .filter { it % 2 == 0L }
    .reduce { sum, it -> sum + it }


list.filter { it.average > 4 }
    .withIndex() // 1
    .sortedBy { (index, student) -> student.name }
    .take(10)
    .sortedBy { (index, student) -> student.average }
```

# Sealed classes 🤐

Represents a restricted class heirarchy.

```kotlin
sealed class Intention {
  class Refresh : Intention() 
  class LoadMore : Intention()
}

val intention: Intention = Intention.LoadMore()
val output = when (intention) {
    is Intention.Refresh -> "refresh"
    is Intention.LoadMore -> "load more"
}
```

# Helpful types

* `Option<T>`

* `Result<T, E>`

* `Try`

* `Either<T, U>`

#  Thanks for listening
