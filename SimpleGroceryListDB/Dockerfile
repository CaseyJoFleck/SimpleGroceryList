FROM postgres:14.1

ENV POSTGRES_PASSWORD="${POSTGRES_PASSWORD}" \
    POSTGRES_USER="${POSTGRES_USER}" \
    POSTGRES_DB="${POSTGRES_DB}" \
    PGDATA="${PGDATA}"

COPY dump.sql /docker-entrypoint-initdb.d/