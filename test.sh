cd db

. exec-start.sh

cd ..

docker run --network="docker-services_default" \
  -it --rm --name testing-purpose \
  -v "$(pwd)":/usr/src/mymaven \
  -w /usr/src/mymaven maven:3.8.1-jdk-11 mvn clean test
