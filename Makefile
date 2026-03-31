.PHONY: build run clean build-db run-db clean-db

# Load and export variables from .env.local if present
ifneq (,$(wildcard .env.local))

include .env.local

endif

# Build the project using the included gradle wrapper
build: ./gradlew
	echo "Building the project with Fixer API key: $(FIXER_API_KEY)"
	FIXER_API_KEY=$(FIXER_API_KEY) ./gradlew clean build

# Run the built jar (depends on build)
run: build
	@echo "Running the application..."
	FIXER_API_KEY=$(FIXER_API_KEY) java -jar build/libs/banking-app-0.0.1-SNAPSHOT.jar

# Clean target
clean: ./gradlew
	@echo "Cleaning project..."
	@bash ./gradlew clean

# Build the database docker image (fix directory spelling)
build-db:
	@echo "Building database Docker image..."
	cd ./banking-app-infrastructure/database && docker build -t banking-app-db:latest .
	
# Run the database container (depends on build-image)
run-db: build-db
	@echo "Running database container..."
	docker run -d --name banking-app-db -p 5432:5432 banking-app-db:latest
	
clean-db:
	@echo "Removing database container..."
	-docker ps -a -f 'label=app=java-banking-app' --format '{{.ID}}' | xargs docker rm

	@echo "Removing database image..."
	-docker image ls -f 'label=app=java-banking-app' --format '{{.ID}}' | tr '\n' ' ' | xargs docker image rm
