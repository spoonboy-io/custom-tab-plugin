.PHONY: build

build:
	## make is now a task, which builds plugin, moves it, then removes build artifacts
	./gradlew make

release:
	## TODO the release.yml workflow is not working correctly ATM
	## create a plugin release by pushing a tag in format vx.x.x
	@echo "Enter the plugin release version (format vx.x.x).."; \
	read VERSION; \
	git tag -a $$VERSION -m "Releasing "$$VERSION; \
	git push origin $$VERSION
