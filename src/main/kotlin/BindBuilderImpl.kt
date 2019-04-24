package krux

import kotlin.reflect.KMutableProperty1

interface Disposable {
    fun dispose()
}

data class Binding<T>(val reaction: Reaction<T>, val setter: (T) -> Unit, val updateContext: UpdateContext): Disposable {
    private var sub: Subscription? = null
    private var value = reaction.value

    init {
        setter(value)
        subscribe()
    }

    private fun subscribe() {
        reaction.onInvalidated(this::onInvalidated)
    }

    private fun onInvalidated() {
        sub?.unsubscribe()
        updateContext.scheduleUpdate {
            subscribe()
            val newValue = reaction.value
            if (value !== newValue) {
                value = newValue
                setter(value)
            }
        }
    }

    override fun dispose() {
        sub?.unsubscribe()
    }

}

data class Reaction<T>(val f: ReactiveContext.() -> T): Observable<T> {
    override val value: T
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onChanged(onChange: (T) -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInvalidated(f: () -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

data class BindBuilderImpl<T>(val value: T, val scheduler: UpdateContext) : BindBuilder<T> {
    private val bindings: MutableSet<Disposable> = mutableSetOf()

    override fun <V> bind(setter: (T, V) -> Unit, f: ReactiveContext.() -> V) {
        bindings.add(Binding(Reaction(f), { v -> setter(value, v) }, scheduler))
    }

    override fun <V> bind(p: KMutableProperty1<T, V>, f: ReactiveContext.() -> V) {
        bindings.add(Binding(Reaction(f), { v -> p.set(value, v) }, scheduler))
    }
}