package com.dhenton9000.spring.mvc.config;

/*

WebSecurityConfig
This class extends the WebSecurityConfigurerAdapter. This is a convenience class
that allows customization to both WebSecurity and HttpSecurity.


 */
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
import org.springframework.security.oauth2.core.OAuth2AccessToken;

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
                .userInfoEndpoint(userInfoEndpoint
                        -> userInfoEndpoint.oidcUserService(this.oidcUserService())
                )
                .authorizedClientService(authorizedClientService())
                .loginPage("/oauth_login")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logoutdone").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
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
        String userInfoUri = issuer + "/v1/userinfo";

        ClientRegistration reg = CommonOAuth2Provider.OKTA.getBuilder("okta")
                .clientId(clientId)
                .jwkSetUri(jwkUri)
                .clientSecret(clientSecret)
               // .scope("groups", "openid", "email", "profile")
              //  .userInfoUri(userInfoUri)
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .build();
        registrations.add(reg);
        return new InMemoryClientRegistrationRepository(registrations);
    }

 
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {

        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {

            OidcUser oidcUser = delegate.loadUser(userRequest);
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            List<GrantedAuthority> mappedAuthorities = this.decodeGroups(accessToken.getTokenValue());
            UserHybrid hybrid = new UserHybrid(mappedAuthorities,
                    oidcUser.getIdToken(), oidcUser.getUserInfo());

            return hybrid;
        };
    }

    /**
     * create a list of granted authorities. This is a list of roles that exist
     * in the accessToken. To get those entries into the token the server needs
     * to be configured to include them
     *
     * See the token folder in the docs folder for more information. Roles are
     * just Okta groups
     *
     *
     * @param jwtToken
     * @return
     */
    private List<GrantedAuthority> decodeGroups(String jwtToken) {
        String[] split_string = jwtToken.split("\\.");
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String base64EncodedBody = split_string[1];

        String t = new String(decoder.decode(base64EncodedBody));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Map<String, Object> map = mapper.readValue(t, Map.class);
            ArrayList<String> items = (ArrayList<String>) map.get("groups");
            items.replaceAll(String::toUpperCase);
            return AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", items));

        } catch (IOException ex) {
            LOG.error("Io jackson error: " + ex.getMessage());
        }
        return null;

    }

}
