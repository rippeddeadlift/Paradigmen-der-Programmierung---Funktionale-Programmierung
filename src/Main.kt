import kotlin.random.Random

enum class OrderResult {
    Lower, Higher, Equal
}

class Person(val name: String, var age: Int) {
    override fun toString(): String {
        return "name = $name, age = $age \n"
    }
}

class Person2(val name: String, var age: Int, val height: Double) {
    override fun toString(): String {
        return "name = $name, age = $age, height = $height, \n"
    }
}

typealias Ordering<A> = (A, A) -> OrderResult


class Sorting {
    fun <A> sort(list: List<A>, ordering: Ordering<A>): List<A> {
        if (list.isEmpty()) return emptyList()
        val copiedList = list.toMutableList()
        var sorted = false
        var tmp: A?
        while (!sorted) {
            sorted = true
            for (i in 0 until copiedList.lastIndex) {
                val left = copiedList[i]
                val right = copiedList[i + 1]
                val result = ordering(left, right)
                if (result == OrderResult.Higher) {
                    tmp = left
                    copiedList[i] = right
                    copiedList[i + 1] = tmp
                    sorted = false
                }
            }
        }
        return copiedList
    }
}

sealed interface Either<out A, out B>
data class Left<A>(val value: A) : Either<A, Nothing>
data class Right<B>(val value: B) : Either<Nothing, B>

fun <A, B> makeEither(a: A, b: B): Either<A, B> {
    return if (Random.nextBoolean()) {
        Left(a)
    } else {
        Right(b)
    }
}


fun main() {
    val either1: Either<Int, String> = makeEither(42, "Hello")
    val either2: Either<Double, String> = makeEither(3.14, "World")

    // Ausgabe der Werte aus den Left-Instanzen
    when (either1) {
        is Left -> println("Left value: ${either1.value}") // Output: Left value: 42
        is Right -> println("Right value: ${either1.value}")
    }

    when (either2) {
        is Left -> println("Left value: ${either2.value}") // Output: Left value: 3.14
        is Right -> println("Right value: ${either2.value}")
    }





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

    fun <A> reversed(ord: Ordering<A>): Ordering<A> = { a, b ->
        when (ord(a, b)) {
            OrderResult.Lower -> OrderResult.Higher
            OrderResult.Higher -> OrderResult.Lower
            else -> OrderResult.Equal
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

    fun <A, B> contraMap(ord: Ordering<A>, transform: (B) -> A): Ordering<B> = { left, right ->
        ord(transform(left), transform(right))
    }

    fun <A, B> zip(ord1: Ordering<A>, ord2: Ordering<B>): Ordering<Pair<A, B>> = { pair1, pair2 ->
        val result1 = ord1(pair1.first, pair2.first)
        if (result1 != OrderResult.Equal) result1 else ord2(pair1.second, pair2.second)
    }
    val personOrd: Ordering<Person> =
        contraMap(zip(stringOrd, intOrd), transform = { person -> Pair(person.name, person.age) })
    val sorting = Sorting()
    val people = listOf(
        Person("Nathalie", 25), Person("Alex", 33), Person("Zah", 28), Person("Alex", 18), Person("Jens", 33)
    )
    println(sorting.sort(people, personOrd))
    fun <A> Ordering<A>.reversed(): Ordering<A> = { a1, a2 ->
        when (this(a1, a2)) {
            OrderResult.Lower -> OrderResult.Higher
            OrderResult.Higher -> OrderResult.Lower
            OrderResult.Equal -> OrderResult.Equal
        }
    }


    /*fun <A, B> zip(orderA: Ordering<A>, orderB: Ordering<B>): Ordering<Pair<A, B>> {
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

    fun <A, B> Ordering<A>.contraMap(transform: (B) -> A): Ordering<B> = { left, right ->
        this(transform(left), transform(right))
    }

    fun <A, B> Ordering<A>.zip2(ord: Ordering<B>): Ordering<Pair<A, B>> = { pair1, pair2 ->
        val result1 = this(pair1.first, pair2.first)
        if (result1 != OrderResult.Equal) {
            result1
        } else ord(pair1.second, pair2.second)
    }

    val people2 = listOf(
        Person2(" Alex ", 25, 172.5),
        Person2(" Alex ", 25, 186.0),
        Person2("Zah ", 28, 158.3),
        Person2(" Breya ", 18, 183.0),
        Person2(" Jens ", 33, 168.5),
    )

    val personOrd2: Ordering<Person2> = stringOrd.zip2(intOrd.reversed()).zip2(doubleOrd).contraMap { person ->
        person.name to person.age to person.height // kürzere Schreibweise für Pair(Pair(person.name, person.age), person.height)
    }
    println(sorting.sort(people2, personOrd2))

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