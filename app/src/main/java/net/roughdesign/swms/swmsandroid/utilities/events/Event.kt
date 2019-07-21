package net.roughdesign.swms.swmsandroid.utilities.events

class Event {

	private val _observers = arrayListOf<() -> Unit>()
	private val _lock = Any()


	operator fun plusAssign(observer: () -> Unit) {
		synchronized(_lock) {
			_observers.add(observer)
		}
	}


	fun invoke() {
		var observers: Iterable<() -> Unit>
		synchronized(_lock) {
			observers = _observers.toList()
		}
		for (observer in observers) {
			observer.invoke()
		}
	}
}


