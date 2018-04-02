// functor : Applicative : Monad
sealed class Option<out T> {

    companion object {
        // applicative
        fun <T> pure(value: T?): Option<T> = if(value == null) None else Some(value)
    }

    // functor
    fun <R> map(transform: (T) -> R?): Option<R> = fold({ None }, { a -> from(transform(a))})

    // monad
    fun <R> flatMap(transform: (T) -> Option<R>): Option<R> = fold({ None }, { transform(it) })
    
    inline fun <R> fold(ifEmpty: () -> R, some: (T) -> R): R = when (this) {
        is None -> ifEmpty()
        is Some -> some(value)
    }

    fun asNullable(): T? {
        return when (this) {
            is Some -> value
            is None -> null
        }
    }

    data class Some<out T>(val value: T) : Option<T>()
    object None : Option<Nothing>()
}
