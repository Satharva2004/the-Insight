FROM tomcat:9.0-jdk11

# Remove default apps and deploy your WAR file
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/summy.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
