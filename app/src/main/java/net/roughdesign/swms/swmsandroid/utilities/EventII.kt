package net.roughdesign.swms.swmsandroid.utilities

class EventII<T1, T2> {

	private val _observers = arrayListOf<(T1, T2) -> Unit>()
	private val _lock = Any()


	operator fun plusAssign(observer: (T1, T2) -> Unit) {
		synchronized(_lock) {
			_observers.add(observer)
		}
	}

	operator fun minusAssign(observer: (T1, T2) -> Unit) {
		synchronized(_lock) {
			_observers.remove(observer)
		}
	}


	fun invoke(value1: T1, value2: T2) {
		var observers: Iterable<(T1, T2) -> Unit>
		synchronized(_lock) {
			observers = _observers.toList()
		}
		for (observer in observers) {
			observer(value1, value2)
		}
	}
}