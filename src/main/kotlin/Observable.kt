package krux

interface Observable<T> {
  // TODO should this be renamed getDirect() so that it's clear invoke is only used directly in reactive contexts?
  operator fun invoke(): T
  operator fun ReactiveContext.invoke(): T
  fun onInvalidated(f: () -> Unit): Subscription
  fun onChanged(f: (T) -> Unit): Subscription
}