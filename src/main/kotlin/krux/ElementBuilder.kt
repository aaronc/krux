package krux

import kotlin.reflect.KMutableProperty1

interface ElementBuilder<out T> {
    fun <V> bind(setter: (T, V) -> Unit, f: ReactiveContext.() -> V)
}

fun <T, V> ElementBuilder<T>.bind(p: KMutableProperty1<T, V>, f: ReactiveContext.() -> V) {
    bind({ e, v -> p.set(e, v) }, f)
}
