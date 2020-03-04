# MVC-BASIC-ONE

Demonstration of using Okta for authentication and authorization for a non 
Spring Boot application.

## Items to Note
* Even though is an 'older' Spring application, security is configured via
java in WebSecurityConfig
* upgraded to Spring 5
* The original Principal is intercepted and replace with UserHybrid see 
WebSecurityConfig

 

* https://www.baeldung.com/spring-security-5-oauth2-login
* https://github.com/OhadR/oAuth2-sample/blob/master/oauth2-client/src/main/webapp/WEB-INF/spring-servlet.xml
* https://stackoverflow.com/questions/48253435/spring-oauth2-xml-configuration-for-client-and-resource-server
* https://github.com/OhadR/oAuth2-sample/blob/master/oauth2-client/src/main/webapp/WEB-INF/spring-servlet.xml
* https://developer.okta.com/blog/2017/12/18/spring-security-5-oidc
* https://docs.spring.io/spring-security/site/docs/current/reference/html5/
* https://stackoverflow.com/questions/55894402/principalextractor-and-authoritiesextractor-are-not-getting-called
* https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#oauth2login-advanced-map-authorities-oauth2userservice
* https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#oauth2login-advanced-map-authorities-grantedauthoritiesmapper
* https://www.baeldung.com/spring-security-5-oauth2-login
* https://github.com/eugenp/tutorials/blob/master/spring-5-security-oauth/src/main/java/com/baeldung/oauth2/SecurityConfig.java
* https://developer.okta.com/docs/reference/api/oidc/
* https://www.baeldung.com/spring-security-taglibs
* https://www.devglan.com/spring-security/spring-boot-security-google-oauth
* https://developer.okta.com/blog/2017/10/13/okta-groups-spring-security
* https://developer.okta.com/blog/2017/07/25/oidc-primer-part-1
* https://docs.spring.io/spring-security/site/docs/5.2.2.RELEASE/reference/html/oauth2.html#oauth2login-javaconfig-wo-boot