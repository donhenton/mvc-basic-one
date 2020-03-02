package com.dhenton9000.spring.mvc.config;

/*

WebSecurityConfig
This class extends the WebSecurityConfigurerAdapter. This is a convenience class
that allows customization to both WebSecurity and HttpSecurity.


 */
import java.security.Principal;
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
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

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
                .antMatchers("/oauth_login", "/login**", "/logout**", "/resources/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint(userInfoEndpoint ->
                        userInfoEndpoint.oidcUserService(this.oidcUserService())    
                    )
                .authorizedClientService(authorizedClientService())
                .loginPage("/oauth_login")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logoutdone").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true) ;
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorize-client")
//                .authorizationRequestRepository(authorizationRequestRepository())
//                .and()
//                .tokenEndpoint()
//                .accessTokenResponseClient(accessTokenResponseClient())
//                .and()
//                .defaultSuccessUrl("/loginSuccess")
//                .failureUrl("/loginFailure");

    }
    private static List<String> clients = Arrays.asList("okta");

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = new ArrayList<ClientRegistration>();
        String clientId = env.getProperty(CLIENT_PROPERTY_KEY + "okta.client-id");
        String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + "okta.client-secret");
        String issuer = env.getProperty(CLIENT_PROPERTY_KEY + "okta.issuer");
        String authorizationUri = issuer + "/v1/authorize";
        String tokenUri = issuer + "/v1/token";
        String jwkUri = issuer + "/v1/keys";

        ClientRegistration reg = CommonOAuth2Provider.OKTA.getBuilder("okta")
                .clientId(clientId)
                .jwkSetUri(jwkUri)
                .clientSecret(clientSecret)
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .build();
        registrations.add(reg);
        return new InMemoryClientRegistrationRepository(registrations);
    }

//    @Bean
//    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
//        return new HttpSessionOAuth2AuthorizationRequestRepository();
//    }
//
//    @Bean
//    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
//        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
//        return accessTokenResponseClient;
//    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {

        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    //https://stackoverflow.com/questions/55894402/principalextractor-and-authoritiesextractor-are-not-getting-called
   //https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#oauth2login-advanced-map-authorities-oauth2userservice
    //https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#oauth2login-advanced-map-authorities-grantedauthoritiesmapper
    
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OidcUser oidcUser = delegate.loadUser(userRequest);
            LOG.debug("hit user service "+oidcUser.getClass().getName());

//            OAuth2AccessToken accessToken = userRequest.getAccessToken();
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            // TODO
            // 1) Fetch the authority information from the protected resource using accessToken
            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
           // oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
          
           
           
           OAuth2AuthenticationToken ccc;
            
            return oidcUser;
        };
    }
}
