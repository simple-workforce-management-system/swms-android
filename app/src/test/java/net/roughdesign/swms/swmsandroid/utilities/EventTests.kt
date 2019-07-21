package net.roughdesign.swms.swmsandroid.utilities

import net.roughdesign.swms.swmsandroid.utilities.events.Event
import org.junit.Assert
import org.junit.Test

class EventTests {

	@Test
	fun isCalled(){
		val event = Event()

		var hasBeenCalled = false
		event += {hasBeenCalled = true}

		event.invoke()
		Assert.assertTrue(hasBeenCalled)
	}
}