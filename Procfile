//web: java $JAVA_OPTS -jar build/libs/operation_board-0.0.1-SNAPSHOT.jar --server.port=$PORT --spring.profiles.active=heroku
web: target/start -Dhttp.port=${PORT} -DapplyEvolutions.default=true -Ddb.default.url=${@ec2-23-21-167-249.compute-1.amazonaws.com:5432/d3q7dn} -Ddb.default.driver=org.postgresql.Driver --spring.profiles.active=heroku