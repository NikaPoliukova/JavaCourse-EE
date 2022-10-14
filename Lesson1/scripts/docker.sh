docker run --rm  --name tomcat -p 8080:8080 --network my_network -v "C:\Users\nikan\Downloads\servlet\target\servlet-1.0-SNAPSHOT.war:/usr/local/tomcat/webapps/servlet.war"  tomcat:9.0.53-jdk11-openjdk

docker run --rm  --name custom-tomcat -p 8080:8080 --network my_network servlet:latest

docker run --network my_network --rm curlimages/curl:7.85.0 http://custom-tomcat:8080/servlet/information
