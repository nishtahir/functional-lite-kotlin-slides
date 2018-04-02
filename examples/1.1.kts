#!/usr/bin/env kotlinc -script

tailrec fun sum(map: (Int) -> Int,  lower: Int, upper: Int) : Int{
    return if(lower > upper) {
        0
    } else {
        map(lower) + sum(lower + 1, upper)
    }
}

fun square(num: Int): Int {
    return num * num
}

val sumInts = sum({ num -> num }, 1, 5)
val sumDouble = sum({num -> num * 2}, 6, 10) 
val sumSquares = sum(::square, 1, 5) 

println("Sum of Ints = $sumInts")
println("Sum of Squares = $sumSquares")
