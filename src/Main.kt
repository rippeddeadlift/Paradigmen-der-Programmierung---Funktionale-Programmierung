enum class OrderResult {
    Lower,
    Higher,
    Equal
}
typealias Ordering<A> = (A, A) -> OrderResult
fun main() {
    val intOrd: Ordering<Int> = { left, right ->
        when {
            left < right -> OrderResult.Lower
            left > right -> OrderResult.Higher
            else -> OrderResult.Equal
        }
    }

    val stringOrd: Ordering<String> = { left, right ->
        when {
            left < right -> OrderResult.Lower
            left > right -> OrderResult.Higher
            else -> OrderResult.Equal
        }
    }

    val doubleOrd: Ordering<Double> = { left, right ->
        when {
            left < right -> OrderResult.Lower
            left > right -> OrderResult.Higher
            else -> OrderResult.Equal
        }
    }

    val booleanOrd: Ordering<Boolean> = { left, right ->
        when {
            left == right -> OrderResult.Equal
            left -> OrderResult.Higher
            else -> OrderResult.Lower
        }
    }

    fun <A> reversed(ordering: Ordering<A>): Ordering<A> = { left, right ->
        when (ordering(left, right)) {
            OrderResult.Lower -> OrderResult.Higher
            OrderResult.Higher -> OrderResult.Lower
            OrderResult.Equal -> OrderResult.Equal
        }
    }
    fun <A> debug(ordering: Ordering<A>): Ordering<A> = { left, right ->
        val result = ordering(left, right)
        println("Comparing $left and $right: $result")
        result
    }
    fun <A> none(ordering: Ordering<A>): Ordering<A> = { _, _ ->
        OrderResult.Equal
    }


    val intDesc = reversed(intOrd)
    val stringDebug = debug(stringOrd)
    val doubleDesc = debug(reversed(doubleOrd))
    val noOrd = none(stringOrd)

    // Testing the functions
    println(intDesc(3, 4))
    println(stringDebug("apple", "banana"))
    println(doubleDesc(1.0, 2.0))
    println(noOrd("apple", "banana"))




}

