package net.roughdesign.swms.swmsandroid.utilities

class Event {

    private val _observers = arrayListOf<Runnable>()
    private val _lock = Any()


    operator fun plusAssign(observer: Runnable) {
        synchronized(_lock) {
            _observers.add(observer)
        }
    }

    operator fun minusAssign(observer: Runnable) {
        synchronized(_lock) {
            _observers.remove(observer)
        }
    }

    fun invoke() {
        val observers = _observers.toList()
        for (observer in observers) {
            observer.run()
        }
    }
}


