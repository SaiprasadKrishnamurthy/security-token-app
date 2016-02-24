FROM java:8
VOLUME /tmp
ADD security-token-app.jar app.jar
ADD  test-ldap-tree.ldif test-ldap-tree.ldif
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]