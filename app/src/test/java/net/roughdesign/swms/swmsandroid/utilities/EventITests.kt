package net.roughdesign.swms.swmsandroid.utilities


import net.roughdesign.swms.swmsandroid.utilities.events.EventI
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EventITests {

	@Test
	fun isCalled() {
		val event = EventI<String>()
		var hasBeenCalled = false
		event += { hasBeenCalled = true }

		event.invoke("")

		assertTrue(hasBeenCalled)
	}

	@Test
	fun transmitsValue() {
		val event = EventI<String>()
		val expected = "blarb"
		var result = ""
		event += { result = it }

		event.invoke(expected)

		assertEquals(expected, result)
	}
}


