package com.morpheusdata.managedServiceTab

import com.morpheusdata.core.AbstractInstanceTabProvider
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Account
import com.morpheusdata.model.Instance
import com.morpheusdata.model.TaskConfig
import com.morpheusdata.model.ContentSecurityPolicy
import com.morpheusdata.model.User
import com.morpheusdata.model.Permission
import com.morpheusdata.views.HTMLResponse
import com.morpheusdata.views.ViewModel
import java.text.DecimalFormat
import groovy.sql.GroovyRowResult
import groovy.transform.ToString
import groovy.sql.Sql
import java.sql.Connection
import groovy.util.logging.Slf4j

@Slf4j
class ManagedServiceTabProvider extends AbstractInstanceTabProvider {
	Plugin plugin
	MorpheusContext morpheus
	Account accountInfo
	User userInfo

    String code = "managed-services-tab"
	String name = "Managed Services"

	ManagedServiceTabProvider(Plugin plugin, MorpheusContext context) {
		this.plugin = plugin
		this.morpheus = context
	}

	@Override
	HTMLResponse renderTemplate(Instance instance) {
		ViewModel<Instance> model = new ViewModel<>()
		TaskConfig config = morpheus.buildInstanceConfig(instance, [:], null, [], [:]).blockingGet()

        // get the data
        Connection dbConnection
        Account account = new Account()
        List<GroovyRowResult> results = []
        Integer serviceCount = 0
        Float total = 0.00
        Float avg = 0.00
        Float max = 0.00

        def viewData = [:]

        // add the tenant currency symbol
        switch (accountInfo.getCurrency()){
            case "EUR":
                viewData['currencySymbol'] = '€'
            break;
            case "GBP":
                viewData['currencySymbol'] = '£'
            break;
            default:
                viewData['currencySymbol'] = '$'
            break;
        }

		try {
        	dbConnection = morpheus.report.getReadOnlyDatabaseConnection().blockingGet()
        	results = new Sql(dbConnection).rows("SELECT * FROM custom_managed_services where instance_name = ${instance.name};")
        } finally {
        	morpheus.report.releaseDatabaseConnection(dbConnection)
        }

        // handle results (or lack of)
        if (results.size == 0) {
            // handle empty dataset
            model.object = viewData
            getRenderer().renderTemplate("hbs/noManagedServices", model)
        } else {
            // iterate the dataset and do the calcs
            for(result in results) {
                serviceCount += 1
                total += result.service_cost
                if (result.service_cost > max) {
                    max = result.service_cost
                }
            }
            avg = total / serviceCount

            // collect view data & format appropriately
            def df2 = new DecimalFormat("#0.00") // round(2) was inconsistent
            viewData['results'] = results
            viewData['instance'] = instance
            viewData['total'] = df2.format(total)
            viewData['count'] = serviceCount
            viewData['max'] = df2.format(max)
            viewData['avg'] = df2.format(avg)

            model.object = viewData
        	getRenderer().renderTemplate("hbs/managedServices", model)
        }
	}

	@Override
	Boolean show(Instance instance, User user, Account account) {
		def show = true

		// store info to properties for use with view
		this.accountInfo = account

		plugin.permissions.each { Permission permission ->
		    if(user.permissions[permission.code] != permission.availableAccessTypes.last().toString()){
		 		 show = false
		 	}
		}

		return show
	}

	@Override
	ContentSecurityPolicy getContentSecurityPolicy() {
	    // an alternative to setting this policy to include a nonce
	    // on any script, style or iframe tags used in the views
	    // that's the approach we will take
		def csp = new ContentSecurityPolicy()
		return csp
	}
}