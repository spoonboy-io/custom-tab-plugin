package com.morpheusdata.managedServiceTab

import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Permission
import com.morpheusdata.views.HandlebarsRenderer
import groovy.util.logging.Slf4j

@Slf4j
class ManagedServiceTabPlugin extends Plugin {

    String code = "managed-service-tab"  // we can do this in preference to override getCode(), but should we?

	@Override
	void initialize() {
	    // set additional metadata
		this.setName("Managed Service Instance Tab")
		this.setDescription("Custom instance tab which displays managed service information")
		this.setAuthor("Ollie Phillips")

        // call and register the tab provider
        ManagedServiceTabProvider managedServiceTabProvider = new ManagedServiceTabProvider(this, morpheus)
        this.pluginProviders.put(managedServiceTabProvider.code, managedServiceTabProvider)

        // register the controller for handling delete
        this.setRenderer(new HandlebarsRenderer(this.classLoader))
        this.controllers.add(new ManagedServiceTabPluginController(this, morpheus))

        // create a permission
		this.setPermissions([Permission.build('Managed Service Tab Plugin','managed-service-tab', [Permission.AccessType.none, Permission.AccessType.full])])
	}

	@Override
	void onDestroy() {}
}