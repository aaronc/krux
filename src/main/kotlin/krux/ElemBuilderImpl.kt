package krux

private class ElemBuilderImpl<T>(val element: T, val scheduler: UpdateScheduler) : ElementBuilder<T> {
    private val bindings: MutableSet<Disposable> = mutableSetOf()

    override fun <V> bind(setter: (T, V) -> Unit, f: ReactiveContext.() -> V) {
        bindings.add(Binding(Reaction(f), { v -> setter(element, v) }, scheduler))
    }

}

fun <T> HasUpdateScheduler.elemBuilder(element: T): ElementBuilder<T> {
    return ElemBuilderImpl(element, scheduler)
}