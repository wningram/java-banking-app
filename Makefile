.PHONY: build run clean

export SHELL := /usr/bin/bash
export BASH_ENV := bash_init.sh

build:
	@bash -c "shopt -s globstar; javac -sourcepath src -d build src/**/*.java"
	jar -cfe out/BankingApp.jar com.wningram.practice.BankingApp -C build .

run: build
	java -jar out/BankingApp.jar

clean:
	rm -rf build/*
	rm -rf out/*