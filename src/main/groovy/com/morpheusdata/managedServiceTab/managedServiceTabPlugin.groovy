package com.morpheusdata.managedServiceTab

import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Permission
import com.morpheusdata.views.HandlebarsRenderer
import com.morpheusdata.model.OptionType
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

	    // configuration options for additional database connection
        this.settings << new OptionType(
            name: "Database Hostname",
            code: "mst-database-host",
            fieldName: "databaseHostname",
            displayOrder: 0,
            fieldLabel: "Database Hostname",
            helpText: 'Enter the hostname for the database',
            required: true,
            inputType: OptionType.InputType.TEXT
        )

        this.settings << new OptionType(
            name: "Database Port",
            code: "mst-database-port",
            fieldName: "databasePort",
            displayOrder: 1,
            fieldLabel: "Database Port",
            helpText: 'Enter the port for the database connection',
            required: true,
            inputType: OptionType.InputType.TEXT
        )

        this.settings << new OptionType(
            name: "Database Name",
            code: "mst-database-name",
            fieldName: "databaseName",
            displayOrder: 2,
            fieldLabel: "Database Name",
            helpText: 'Enter the database name',
            required: true,
            inputType: OptionType.InputType.TEXT
        )

        this.settings << new OptionType(
            name: "Database User",
            code: "mst-database-user",
            fieldName: "databaseUser",
            displayOrder: 3,
            fieldLabel: "Database User",
            helpText: 'Enter the database user',
            required: true,
            inputType: OptionType.InputType.TEXT
        )

        this.settings << new OptionType(
            name: "Database Password",
            code: "mst-database-password",
            fieldName: "databasePassword",
            displayOrder: 4,
            fieldLabel: "Database Password",
            helpText: 'Enter the database password',
            required: true,
            inputType: OptionType.InputType.PASSWORD
        )
	}

	@Override
	void onDestroy() {}
}