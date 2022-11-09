
#Build and start service

mvn clean install

mvn spring-boot:run -f pom.xml


#Testing

1. run unit test cases in the test package
2. import post man collections from test/resources/postMan