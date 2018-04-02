#!/usr/bin/env kotlinc -script

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
