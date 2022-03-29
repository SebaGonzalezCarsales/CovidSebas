package au.com.carsales.basemodule.extension

import java.util.*

fun MutableList<String>.push(value: String) {
    this.add(value)
}

fun MutableList<String>.lastElement(): String {
    return if (this.isNullOrEmpty()) {
        ""
    } else {
        this.last()
    }

}

fun MutableList<String>.pop() {
    this.remove(this.last())
}

//using this method to search by name for the value of an enum avoids exceptions
inline fun <reified E : Enum<E>> String.valueOfEnum(default: E?) =
        enumValues<E>().find { it.name.toUpperCase() == this.toUpperCase() }
                ?: default

fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null
