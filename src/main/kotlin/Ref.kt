package krux

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

interface Property<A, B> {

}

interface ReadRef<A>: Observable<A> {
    operator fun <B> get(property: KProperty1<A, B>): ReadRef<B?>
}

interface Ref<A>: ReadRef<A> {
    operator fun <B> get(property: KMutableProperty1<A, B>): Ref<B?>
}

data class A(val x: String, var y: Int) {

}

fun test(c: Ref<A>) {
    val c2 = c[A::x]
    val c3 = c[A::y]()
}