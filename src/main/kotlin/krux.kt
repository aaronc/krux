package krux

import kotlin.reflect.KMutableProperty1

interface UpdateContext {
    fun scheduleUpdate(f: () -> Unit)
}

interface ReactiveContext {
    fun <T> dep(observable: Observable<T>): T
    val updateContext: UpdateContext
}

interface BindBuilder<T> {
    fun <V> bind(setter: (T,V) -> Unit, f: ReactiveContext.() -> V)
    fun <V> bind(p: KMutableProperty1<T, V>, f: ReactiveContext.() -> V)
}
