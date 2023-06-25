./mvnw clean install -DskipTests=true && 
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/Urban_India-0.0.1-SNAPSHOT.jar
