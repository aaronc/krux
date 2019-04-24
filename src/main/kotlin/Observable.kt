package krux

interface Observable<T> {
  fun ReactiveContext.invoke(): T
  val ReactiveContext.value: T
  val valueNonReactive: T
  fun onInvalidated(f: () -> Unit): Subscription
  fun onChanged(f: (T) -> Unit): Subscription
}