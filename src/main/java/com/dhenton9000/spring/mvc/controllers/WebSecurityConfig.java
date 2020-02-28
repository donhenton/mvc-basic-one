package com.dhenton9000.spring.mvc.controllers;

/*

WebSecurityConfig
This class extends the WebSecurityConfigurerAdapter. This is a convenience class
that allows customization to both WebSecurity and HttpSecurity.


 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
@PropertySource(value = "classpath:config.properties")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);
    private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";

    @Autowired
    private Environment env;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        LOG.info("called configure");
 
        http.authorizeRequests()
                .antMatchers("/oauth_login", "/loginFailure", "/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .loginPage("/oauth_login")
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize-client")
                .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                .tokenEndpoint()
                .accessTokenResponseClient(accessTokenResponseClient())
                .and()
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure");
 

  

    }

    //https://www.baeldung.com/spring-security-5-oauth2-login
    //https://github.com/eugenp/tutorials/blob/master/spring-5-security-oauth/src/main/java/com/baeldung/oauth2/SecurityConfig.java
    //https://developer.okta.com/docs/reference/api/oidc/
    private static List<String> clients = Arrays.asList("okta");

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = new ArrayList<ClientRegistration>();
        String clientId = env.getProperty(CLIENT_PROPERTY_KEY + "okta.client-id");
        String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + "okta.client-id");
        String issuer = env.getProperty(CLIENT_PROPERTY_KEY + "okta.issuer");
        String authorizationUri = issuer + "/v1/authorize";
        String tokenUri = issuer + "/v1/token";

        ClientRegistration reg = CommonOAuth2Provider.OKTA.getBuilder("okta")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .build();
        registrations.add(reg);
        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        return accessTokenResponseClient;
    }

}
