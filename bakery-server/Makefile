# Build the application
all: build test

build:
	@echo "Building ..."

	@go build -o ./bin/main ./cmd/api/main.go

# Run the application
run:
	@go run cmd/api/main.go

run-prod: build
	./bin/main

# Create DB container
docker-run:
	@if docker compose up -d 2>/dev/null; then \
		: ; \
	else \
		echo "Falling back to Docker Compose V1"; \
		docker compose up -d; \
	fi

# Shutdown DB container
docker-down:
	@if docker compose down 2>/dev/null; then \
		: ; \
	else \
		echo "Falling back to Docker Compose V1"; \
		docker compose down; \
	fi

# Test the application
test:
	@echo "Testing..."
	@go test ./... -v

# Integrations Test for the application
itest:
	@echo "Running integration tests..."
	@go test ./internal/database -v

# Clean the binary
clean:
	@echo "Cleaning..."
	rm -f ./bin/main

# Live Reload
watch:
	@if command -v air > /dev/null; then \
            air; \
            echo "Watching...";\
        else \
            read -p "Go's 'air' is not installed on your machine. Do you want to install it? [Y/n] " choice; \
            if [ "$$choice" != "n" ] && [ "$$choice" != "N" ]; then \
                go install github.com/air-verse/air@latest; \
                air; \
                echo "Watching...";\
            else \
                echo "You chose not to install air. Exiting..."; \
                exit 1; \
            fi; \
        fi

sqlc:
	@sqlc generate

migrate-up:
	@GOOSE_DRIVER=sqlite3 GOOSE_MIGRATION_DIR=./sql/schema GOOSE_DBSTRING="./bakery.db" goose up  

migrate-down:
	@GOOSE_DRIVER=sqlite3 GOOSE_MIGRATION_DIR=./sql/schema GOOSE_DBSTRING="./bakery.db" goose down

.PHONY: all build run run-prod test clean watch docker-run docker-down sqlc templ migrate-up migrate-down itest
