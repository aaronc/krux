package krux

interface HasUpdateScheduler {
    val scheduler: UpdateScheduler
}

interface UpdateScheduler {
    fun scheduleUpdate(f: () -> Unit)
}

interface ReactiveContext: HasUpdateScheduler {
    fun <T> dep(observable: Observable<T>)
}

