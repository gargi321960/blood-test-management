FROM tomcat:9.0

# Clean the webapps directory
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file to webapps
COPY RegisterLoginPage.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
