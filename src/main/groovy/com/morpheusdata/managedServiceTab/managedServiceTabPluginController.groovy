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

    // this a temp solution, we will provide these from the plugin config
    // we can't use the Morpheus plugin api's connection currently because it
    // only supports read queries
    // with this approach we can choose to use the morpheus db are a separate
    // database for custom tables, which might be a better idea!
    // if we use the morpheus db, let's at least create a separate user with
    // delete only privs on the table `custom_managed_services_plugin
	String dbUser = "deleter"
	String dbPassword = "aPassword"
	String dbHostPort = "localhost:3306"
	String dbName = "morpheus"

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

        // check the nonce, we have it passed in the req and we check the session cookie
        for (cookie in cookies) {
            String cookieName = cookie.getName()
            String cookieValue = cookie.getValue()

            if (cookieName == "morpheus-managed-service-nonce") {
                if (cookieValue == queryVars["nonce"]) {
                    // valid request, proceed
                    valid = true
                }
            }
        }

        if (valid) {
           // process the deletion
           Sql conn
           String sql

           // create a string of the id list to delete
           String ids = ""
           queryVars.each{
                if (it.key != "nonce") {
                    ids += it.value + ","
                }
           }

           ids = ids.substring(0, ids.length() - 1); //remove trailing ,

           try {
                conn = Sql.newInstance("jdbc:mysql://$dbHostPort/$dbName", dbUser, dbPassword, 'com.mysql.jdbc.Driver')
                sql = "DELETE FROM custom_managed_services_plugin where id IN (" + ids + ");"
                conn.execute sql
           } finally {
                conn.close()
           }

           res.status="200"
        }

        return JsonResponse.of(res)
    }
}