package krux

class Binding<T>(private val reaction: Reaction<T>, private val setter: (T) -> Unit, private val updateContext: UpdateScheduler): Disposable {
    private var sub: Subscription? = null
    private var value = reaction()

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
            val newValue = reaction()
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