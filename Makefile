.PHONY: build run clean env

# Load and export variables from .env.local if present
ifneq (,$(wildcard .env.local))

include .env.local

endif


build: ./gradlew
	FIXER_API_KEY=$(FIXER_API_KEY) ./gradlew clean build

run: build
	java -jar out/BankingApp.jar