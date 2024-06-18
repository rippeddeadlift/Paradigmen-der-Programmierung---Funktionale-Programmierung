enum class OrderResult {
    Lower, Higher, Equal
}

class Person(val name: String, var age: Int, val height: Double) {
    override fun toString(): String {
        return "name = $name, age = $age \n"
    }
}

typealias Ordering<A> = (A, A) -> OrderResult


class Sorting {
    fun <T> sort(list: List<T>, ordering: Ordering<T>): List<T> {
        return list.sortedWith { a, b ->
            when (ordering(a, b)) {
                OrderResult.Lower -> -1
                OrderResult.Higher -> 1
                OrderResult.Equal -> 0
            }
        }
    }
}

fun main() {
    val intOrd: Ordering<Int> = { left, right ->

        if (left < right) OrderResult.Lower
        else if (left > right) OrderResult.Higher
        else OrderResult.Equal

    }

    val stringOrd: Ordering<String> = { left, right ->

        if (left < right) OrderResult.Lower
        else if (left > right) OrderResult.Higher
        else OrderResult.Equal

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

    fun <A> Ordering<A>.reversed(): Ordering<A> = { a1, a2 ->
        when (this(a1, a2)) {
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

    fun <A, B> Ordering<A>.contraMap(transform: (B) -> A): Ordering<B> = { b1, b2 ->
        this(transform(b1), transform(b2))
    }

    fun <A, B> Ordering<A>.zip(other: Ordering<B>): Ordering<Pair<A, B>> = { p1, p2 ->
        val result1 = this(p1.first, p2.first)
        if (result1 == OrderResult.Equal) {
            other(p1.second, p2.second)
        } else {
            result1
        }
    }/*fun <A, B> zip(orderA: Ordering<A>, orderB: Ordering<B>): Ordering<Pair<A, B>> {
        return { pair1: Pair<A, B>, pair2: Pair<A, B> ->
            val primaryResult = orderA(pair1.first, pair2.first)
            if (primaryResult != OrderResult.Equal) {
                primaryResult
            } else {
                orderB(pair1.second, pair2.second)
            }
        }
    }
    // Wir erzeugen ein neues String Ordering das auf Zeichenlänge vergleicht ...
    val stringLengthOrd: Ordering<String> = contraMap(ordering = intOrd, transform = { string -> string.length })
    // Ordering für den Vergleich von Personen nach Alter
    val intLengthOrd: Ordering<Int> = contraMap(intOrd, transform = { int -> int }) // { it }
    // Frage: wir können wir mit transform int ins Double umwandeln
    val ord: Ordering<Person> = contraMap(zip(stringOrd, intOrd)) { person ->
        Pair(person.name, person.age)
    }*/

    val sorting = Sorting()
    val people = listOf(
        Person(" Nathalie ", 25, 172.5),
        Person(" Alex ", 33, 186.0),
        Person("Zah ", 28, 158.3),
        Person(" Alexandra ", 18, 183.0),
        Person(" Jens ", 33, 168.5),
    )

    val personOrd: Ordering<Person> = stringOrd.zip(intOrd.reversed()).zip(doubleOrd).contraMap { person ->
        person.name to person.age to person.height // kürzere Schreibweise für Pair(Pair(person.name, person.age), person.height)
    }
    println(sorting.sort(people, personOrd))

/*val alice = Person("Alice", 30)
val bob = Person("Bob", 25)
val charlie = Person("Charlie", 25)

println(stringLengthOrd(alice.name, bob.name)) //  Higher
println(stringLengthOrd(bob.name, charlie.name)) //  Lower
println(stringLengthOrd(charlie.name, charlie.name)) //  equal
println("--------------")
println(intLengthOrd(alice.age, bob.age)) //  Higher
println(intLengthOrd(bob.age, charlie.age)) //  Equal
println(intLengthOrd(charlie.age, alice.age)) //  Lower*/
}






