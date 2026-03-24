# Use official Postgres image
FROM postgres:16

# Set environment variables (optional defaults)
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=mydb

# Copy initialization scripts (optional)
# Any .sql or .sh files here will run on first startup
COPY ./init-scripts/ /docker-entrypoint-initdb.d/

# Expose default Postgres port
EXPOSE 5432