package net.roughdesign.swms.swmsandroid.utilities.dependencyinjection


object DI {

	private val swmsContext = SwmsContext()

	fun <T> resolve(klass: Class<T>): T {
		return swmsContext.resolve(klass)
	}
}