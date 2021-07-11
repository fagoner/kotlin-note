echo "Loading env vars"
export TZ="America/Guatemala"
export MYSQL_HOST="db"
export MYSQL_ROOT_PASSWORD="R00tP45w0rd"
export MYSQL_DATABASE="note_app"
export MYSQL_USER="root"
export MYSQL_PASSWORD="R00tP45w0rd"
export MYSQL_PORT="3306"
export FLYWAY_USER=root
export FLYWAY_PASSWORD=$MYSQL_ROOT_PASSWORD
export FLYWAY_SCHEMAS="${MYSQL_DATABASE}"
export FLYWAY_URL="jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}"
export FLYWAY_LOCATIONS="filesystem:sql"
export FLYWAY_CONNECT_RETRIES=35
