
fun <A, B, C> forwardCompose(func1: (A) -> B, func2: (B) -> C): (A) -> C {
    return { a -> func2(func1(a)) }
}

fun <A, B, C> compose(func1: (A) -> B, func2: (C) -> A) : (C) -> B {
    return {c -> func1(func2(c)) }
}


// Forward composing function with andThen
fun <A, B, C> ((A) -> B).andThen(transform: (B) -> C): (A) -> C {
    return { a -> transform(this(a)) }
}

val doubleAndAdd3 = { num: Int -> num * 2 }
    .andThen { num: Int -> num + 3 }
    .andThen { num: Int -> num - 7 }
