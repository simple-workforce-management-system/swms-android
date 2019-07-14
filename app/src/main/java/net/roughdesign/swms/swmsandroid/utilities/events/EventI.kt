package net.roughdesign.swms.swmsandroid.utilities.events

class EventI<T> {


	private val _observers = arrayListOf<(T) -> Unit>()
	private val _lock = Any()

	operator fun plusAssign(observer: (T) -> Unit) {
		synchronized(_lock) {
			_observers.add(observer)
		}
	}

	operator fun minusAssign(observer: (T) -> Unit) {
		synchronized(_lock) {
			_observers.remove(observer)
		}
	}


	fun invoke(value: T) {
		var observers: Iterable<(T) -> Unit>
		synchronized(_lock) {
			observers = _observers.toList()
		}
		for (observer in observers) {
			observer(value)
		}
	}
}



