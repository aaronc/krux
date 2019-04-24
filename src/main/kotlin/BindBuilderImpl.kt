package krux

import kotlin.reflect.KMutableProperty1

interface Disposable {
    fun dispose()
}

class Binding<T>(private val reaction: Reaction<T>, private val setter: (T) -> Unit, private val updateContext: UpdateContext): Disposable {
    private var sub: Subscription? = null
    private var value = reaction.valueNonReactive

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
            val newValue = reaction.valueNonReactive
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

class Reaction<T>(private val f: ReactiveContext.() -> T): Observable<T> {
    override val ReactiveContext.value: T
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    private fun compute(): T {
        TODO()
    }

    override fun onChanged(f: (T) -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInvalidated(f: () -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private inner class ReactiveContextImpl : ReactiveContext {
        private val subs = mutableSetOf<Subscription>()

        private fun invalidated() {
            TODO()
        }

        override fun <T> dep(observable: Observable<T>) {
            val sub = observable.onInvalidated(this::invalidated)
            subs.add(sub)
        }
    }
}

open class BindBuilderImpl<T>(protected val value: T, protected val scheduler: UpdateContext) : BindBuilder<T> {
    private val bindings: MutableSet<Disposable> = mutableSetOf()

    override fun <V> bind(setter: (T, V) -> Unit, f: ReactiveContext.() -> V) {
        bindings.add(Binding(Reaction(f), { v -> setter(value, v) }, scheduler))
    }

    override fun <V> bind(p: KMutableProperty1<T, V>, f: ReactiveContext.() -> V) {
        bindings.add(Binding(Reaction(f), { v -> p.set(value, v) }, scheduler))
    }
}