package krux

interface Observable<T> {
  val value: T
  fun onInvalidated(f: () -> Unit): Subscription
  fun onChanged(f: (T) -> Unit): Subscription
}