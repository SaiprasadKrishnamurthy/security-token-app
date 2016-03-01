## Security Gateway APP
### A simple security gateway server that performs Authentication, Authorization and Service Level Agreement (SLA) checks.

### Basically, the idea is simple: 

We felt a common need for Authentication & Authorisation & Service Level Agreement Checks for a lot of resources accessed via HTTP.  The idea is to simply provide these capabilities as a service that is based on industry standards, stateless, lightweight and easy to operate. 

## Authentication:
Authenticates the user against a user store and generates a token (valid for a configurable period of time). This token is generated using the industry standard Json Web Token (https://jwt.io/).

## Authorisation:
Every request then is passed into the Authorisation API to check if the user has got the right permissions to access the protected resource and hasn’t exceeded the quota of number of requests.
These access rules are defined by the administrator using the Rules Resource API. These govern the rules that define the access control policies of your system. The authorisation checks are purely based on the permission tokens associated with the user. 


**Here is how it works**
* The Administrators create access rules using the Rule Resource API. These rules govern which URI needs what permission tokens to consume using a specific HTTP verb. Also, it can additionally define how many requests (transactions) are allowed within a specific time window for users with specific permission tokens. This is basically the service level agreement.
* Once the rules are defined in the system, the user is authenticated using the Authentication Resource endpoint. There are a lot of canned users provided as part of the APP. These users can be listed using the Users Resource API. The password for all the users are pass
* Upon successful authentication, the users will get a signed Jason Web Token (JWT).
* The users will then need to pass this token in the http Authorization header (using the Bearer schema) to the Authorization Resource API to get the authorization and SLA checks performed.
* This set up uses an embedded LDAP for user store and a in-memory H2 database as a DB. However, they can be easily switched to a real LDAP and DB as a matter of configuration.

It's built using Java 8 + Spring Boot (as the micro framework).
* Health and Metrics Endpoints: Just the basic health and metrics are exposed via /health and /metrics via GET respectively.

**HATEOAS support is provided for Rule Resource only**

A few canned access rules are already created in the system for reference and can be accessed via the rules resource: /rules
## Licence: Apache V 2.0
## Prerequisites
* Java 8
* Maven 3+
* Internet connection (to download the maven dependencies for the first time)

## Setup steps
```
Clone the project (git clone ...)
mvn clean install
cd target
java -jar security-token-app.jar
```
### API index page can be viewed at http://localhost:9090/swagger-ui.html

## LDAP Details
The app is bundled with an in-memory/embedded LDAP instance. The user tree is loaded from ../src/main/resources/ldif/test-ldap-tree.ldif
Feel free to change this file to customize the tree. But a rebuild/restart of the app is required cause this is loaded during the startup.

## DB details
The app is bundled with an in-memory/embedded H2 database. The schema and a few canned data samples are defined in schema-hsqldb.sql and data-hsqldb.sql.

## To point to real LDAP and DB
It's quite easy. Just change the specific properties in application.properties

## Dockerizing
Build also supports building docker images via a maven plugin.
Dockerfile present in src/main/docker
To run this docker image:
```
docker run -p 9090:9090 -t saikris/security-token-app
```
The above will run the app in a docker container.




