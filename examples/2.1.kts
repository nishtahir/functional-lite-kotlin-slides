import java.io.IOException
import java.io.File

sealed class Result<out T, out E> {
    companion object {
        fun <V> ok(value: V): Result.Ok<V> = Result.Ok(value)
        fun <E> error(error: E): Result.Error<E> = Result.Error(error)
    }
    
    data class Ok<out T> internal constructor(val value: T) : Result<T, Nothing>()
    data class Error<out E> internal constructor(val error: E) : Result<Nothing, E>()
}

fun readFileFromDisk(file: File): Result<String, IOException> = try {
    Result.ok(file.readText())
} catch (e: IOException) {
    Result.error(e)
}

val result = readFileFromDisk(File("2.1.kts"))
when (result) {
    is Result.Ok -> {
        println(result.value)
    }
    is Result.Error -> {
        println("Something went wrong. That's all I know.")
    }
}