package net.roughdesign.swms.swmsandroid.utilities.dependencyinjection

import net.roughdesign.ajiwa.InjectContext
import net.roughdesign.ajiwa.contexts.Container
import net.roughdesign.swms.swmsandroid.clients.web.ClientRepositoryFactory
import net.roughdesign.swms.swmsandroid.clients.web.ClientJsonConverterFactory

class SwmsContext : InjectContext() {
	override fun addDependencies(container: Container) {

		container.bindSingleton(ClientJsonConverterFactory::class.java, ClientJsonConverterFactory::class.java)
		container.bindSingleton(ClientRepositoryFactory::class.java, ClientRepositoryFactory::class.java)
	}
}