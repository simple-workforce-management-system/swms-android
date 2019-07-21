package net.roughdesign.swms.swmsandroid.utilities

import net.roughdesign.swms.swmsandroid.utilities.events.EventII
import org.junit.Assert
import org.junit.Test

class EventIITests {

	@Test
	fun isCalled() {
		val event = EventII<String, Int>()
		var hasBeenCalled = false
		event += { _: String, _: Int -> hasBeenCalled = true }

		event.invoke("", 2)

		Assert.assertTrue(hasBeenCalled)
	}

	@Test
	fun transmitsValues() {
		val event = EventII<String, Int>()
		val expected1 = "blarb"
		val expected2 = 8
		var result1 = ""
		var result2 = 0
		event += { s: String, i: Int -> result1 = s; result2= i }

		event.invoke(expected1, expected2)

		Assert.assertEquals(expected1, result1)
		Assert.assertEquals(expected2, result2)
	}
}