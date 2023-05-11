package com.morpheusdata.managedServiceTab

import com.morpheusdata.core.Plugin
import com.morpheusdata.web.PluginController
import com.morpheusdata.web.Route
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.model.Permission
import com.morpheusdata.views.ViewModel
import com.morpheusdata.views.JsonResponse
import groovy.sql.Sql
import java.sql.Connection
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

@Slf4j
class ManagedServiceTabPluginController implements PluginController {
    // we can set these via the constructor or directly instead of overriding getters

    Plugin plugin
	MorpheusContext morpheus
	String code = "managed-services-tab-controller"
	String name = "Managed Services Controller"

	ManagedServiceTabPluginController(Plugin plugin, MorpheusContext context){
	    this.plugin = plugin
	    this.morpheus = context
	}

    List<Route> getRoutes() {
        [
            Route.build("/managed-services/delete", "deleteManagedService", [Permission.build("managed-service-tab", "full")]),
        ]
    }

    def deleteManagedService(ViewModel<Map> model ) {
        def res = [:]
        Boolean valid = false
        res.status = "500"

        // get some request data
        List cookies = model.request.getCookies()
        HashMap queryVars = model.request.getParameterMap().collectEntries {
                [it.key, it.value.length > 1 ? it.value : it.value[0]]
        }

        // check the nonce
        for (cookie in cookies) {
            String cookieName = cookie.getName()
            String cookieValue = cookie.getValue()

            if (cookieName == "morpheus-managed-service-nonce") {
                if (cookieValue == queryVars["nonce"]) {
                    // valid request, proceed
                    println("we are here")
                    valid = true
                }
            }
        }

        if (valid) {
            // process the deletion

           /* try {
                  dbConnection = morpheus.report.getReadOnlyDatabaseConnection().blockingGet()
                  results = new Sql(dbConnection).rows("DELETE FROM custom_managed_services_plugin;")
            } finally {
                  morpheus.report.releaseDatabaseConnection(dbConnection)
            }*/

            res.status="200"
        }

        return JsonResponse.of(res)
    }
}