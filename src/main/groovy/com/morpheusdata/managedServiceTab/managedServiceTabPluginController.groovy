package com.morpheusdata.managedServiceTab

import com.morpheusdata.core.Plugin
import com.morpheusdata.web.PluginController
import com.morpheusdata.web.Route
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.model.Permission
import com.morpheusdata.views.ViewModel
import com.morpheusdata.views.JsonResponse
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

    def deleteManagedService(ViewModel<Map> model) {
        def res = [:]
        res.test="test"
        res.test2="test"
        return JsonResponse.of(res)
    }
}