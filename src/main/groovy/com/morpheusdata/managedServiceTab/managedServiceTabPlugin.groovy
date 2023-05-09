package com.morpheusdata.managedServiceTab

import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Permission
import com.morpheusdata.views.HandlebarsRenderer
import com.morpheusdata.views.ViewModel
import com.morpheusdata.web.Dispatcher
import com.morpheusdata.model.OptionType
import groovy.util.logging.Slf4j

@Slf4j
class ManagedServiceTabPlugin extends Plugin {

	@Override
	String getCode() {
		return 'managed-service-tab'
	}

	@Override
	void initialize() {
		this.setName("Managed Service Instance Tab")
		this.setDescription("Custom instance tab which displays managed service information")
		this.setAuthor("Ollie Phillips")

        ManagedServiceTabProvider managedServiceTabProvider = new ManagedServiceTabProvider(this, morpheus)
        this.pluginProviders.put(managedServiceTabProvider.code, managedServiceTabProvider)

		this.setPermissions([Permission.build('Managed Service Tab Plugin','managed-service-tab', [Permission.AccessType.none, Permission.AccessType.full])])

		// additional metadata setters here
		// https://developer.morpheusdata.com/api/com/morpheusdata/core/Plugin.html
	}

	@Override
	void onDestroy() {
	    // called when a plugin is being removed from the plugin manager (aka Uninstalled)
	}
}