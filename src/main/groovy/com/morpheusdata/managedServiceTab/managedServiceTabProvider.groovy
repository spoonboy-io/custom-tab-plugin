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
import groovy.util.logging.Slf4j

@Slf4j
class ManagedServiceTabProvider extends AbstractInstanceTabProvider {
	Plugin plugin
	MorpheusContext morpheus

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
		model.object = instance
		getRenderer().renderTemplate("hbs/managedServices", model)
	}

	@Override
	Boolean show(Instance instance, User user, Account account) {
		def show = true
		//println "user has permissions: ${user.permissions}"
		  /*plugin.permissions.each { Permission permission ->
		 	  if(user.permissions[permission.code] != permission.availableAccessTypes.last().toString()){
		 		  show = false
		 	  }
		  }*/
		/*catch(Exception ex) {
           log.info("Template Plugin:  ${ex}")
        }*/
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