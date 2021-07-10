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
export FLYWAY_URL="jdbc:mysql://localhost:${MYSQL_PORT}"
export FLYWAY_LOCATIONS="filesystem:sql"
export FLYWAY_CONNECT_RETRIES=35
export MYSQL_CONNECTION_STRING="server=${MYSQL_HOST};port=3306;database=${MYSQL_DATABASE};user=${MYSQL_USER};password=${MYSQL_PASSWORD}"
