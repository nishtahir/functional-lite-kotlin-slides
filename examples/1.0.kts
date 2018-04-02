#!/usr/bin/env kotlinc -script

fun sumInts(lower: Int, upper: Int) : Int {
    return if(lower > upper) {
        0
    } else {
        lower + sumInts(lower + 1, upper)
    }
}

fun sumSquares(lower: Int, upper: Int) : Int {
    return if(lower > upper) {
        0
    } else {
        (lower * lower) + sumSquares(lower + 1, upper)
    }
}

println("Sum of Ints = ${sumInts(1, 5)}")
println("Sum of Squares = ${sumSquares(1, 5)}")
