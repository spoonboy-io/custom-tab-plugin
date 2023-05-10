## Custom Tab Plugin

This is a prototype Morpheus plugin which demonstrates how to uses
the Morpheus Plugin framework's custom tab provider to query and 
display data from a custom table in Morpheus.

In a fictional scenario, Company XYZ wishes to show a list of installed
services associated with each Instance. Data is created and inserted via a 
workflow (not provided) but can be viewed (and deleted) from an
Instance's custom tab.

### Getting started

There are database migration files provided to create a custom table and seed that table with dummy data.
Follow these steps to load the data to your Morpheus appliance database.

Create database table Morpheus.

```
mysql -h hostname -u morpheus morpheus < /path/to/migrations/create_managed_service_table.sql
```
Seed the database table with dummy data.

```
mysql -h hostname -u morpheus morpheus < path/to/migrations/seed_managed_service_table.sql
```

**NOTE**: Dummy data is available for an instance with name `DummyTechService-01`. 
Instances with other names will show the tab, but report no managed services. 
Create an instance with the above name, to show the dummy data.

### Build the plugin

Build the plugin locally with the included gradle wrapper.

```
./gradlew make
```

The compiled plugin will be available in the `./plugin` directory. 

Upload the plugin to Morpheus (admin > integrations > plugins tab)

Log out/in to Morpheus to see the tab on existing instances.

### Create a release

!Note this is work-in-progess!

Releases can be automatically created on Github by pushing a version tag
in the format `vx.x.x`.

To this end there is a `Makefile` with a `release` command to assist with this.

### Versions

The plugin has been configured and tested with Morpheus v.6.1.0

Plugin builds are tested/performed with Java v16

### Todo/Ideas

* Mechanism to delete a service
* Provisioning Workflow to attach services to an instance