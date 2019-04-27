package krux

class Reaction<T>(private val f: ReactiveContext.() -> T): Observable<T> {
    override fun invoke(): T {
        if (!dirty) {
            return value!!
        }
        return compute()
    }

    override fun ReactiveContext.invoke(): T {
        this.dep(this@Reaction)
        return invoke()
    }

    private fun compute(): T {
        val ctx = ReactiveContextImpl()
        var v = ctx.f()
        value = v
        dirty = false
        return v
    }

    override fun onChanged(f: (T) -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInvalidated(f: () -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var dirty = true
    var value: T? = null

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