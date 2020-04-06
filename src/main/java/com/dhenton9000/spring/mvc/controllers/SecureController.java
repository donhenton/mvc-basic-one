package com.dhenton9000.spring.mvc.controllers;

import com.dhenton9000.spring.mvc.config.UserHybrid;
import static com.dhenton9000.spring.mvc.config.WebSecurityConfig.CLIENT_PROPERTY_KEY;
import java.util.HashMap;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/secured/")
public class SecureController {

    private static Logger log = LoggerFactory.getLogger(SecureController.class);
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private ClientRegistrationRepository clientRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;

    @PreAuthorize("hasAuthority('SERVICE')")
    @RequestMapping("service")
    public String handleServicePage(Model model) {

        return "tiles.secured.service";
    }

    @PreAuthorize("hasAuthority('ADMINS')")
    @RequestMapping("admins")
    public String handleAdminsPage(Model model) {

        return "tiles.secured.admins";
    }

    @RequestMapping("tokens")
    public String handleGetAccessToken(Model model, OAuth2AuthenticationToken authentication) {

        model.addAttribute("regId", authentication.getAuthorizedClientRegistrationId());
        model.addAttribute("clientName", authentication.getName());
        String clientId = env.getProperty(CLIENT_PROPERTY_KEY + "okta.client-id");

        ClientRegistration registration = clientRepository
                .findByRegistrationId(authentication
                        .getAuthorizedClientRegistrationId());

        String authUri = registration.getProviderDetails().getAuthorizationUri();
        model.addAttribute("authUri", authUri);
        // https://developer.okta.com/docs/guides/implement-auth-code/overview/
        String renewURl = authUri;
        //https://dev-862436.okta.com/oauth2/default/v1/authorize
        //response_type=id_token
        //scope=openid offline_access
        HashMap<String, String> vars = new HashMap<>();
        vars.put("response_type", "code");
        vars.put("scope", "openid");
        vars.put("client_id", clientId);
        vars.put("state",UUID.randomUUID().toString());
        vars.put("nonce",UUID.randomUUID().toString());
        vars.put("redirect_uri","http://local.awsdhenton.com:9000/login/oauth2/code/okta");
        
        authUri += "?response_type={response_type}";
        authUri += "&scope={scope}";
        authUri += "&client_id={client_id}";
        authUri += "&redirect_uri={redirect_uri}";
        authUri += "&state={state}";
        authUri += "&nonce={nonce}";
        
        ResponseEntity<String> stuff = null;
        try {
            stuff = restTemplate.getForEntity(authUri, String.class, vars);
            model.addAttribute("tRequest",stuff);
        } catch (Exception err) {
            log.error(err.getClass()+" "+err.getMessage());
        }

        UserHybrid secObject = (UserHybrid) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        model.addAttribute("accessToken", secObject.getAccessToken().getTokenValue());
        /*
/api/v1/users?search=profile.login eq "ddigital9000@gmail.com" 
            if (!StringUtils.isEmpty(userInfoEndpointUri)) {

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                        .getTokenValue());

                HttpEntity<String> entity = new HttpEntity<>("", headers);

                ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
                Map userAttributes = response.getBody();
                model.addAttribute("name", userAttributes);
            }
           
        } else {
             model.addAttribute("name", "client is null");
        }
         */

        return "tiles.secured.userinfo";
    }

}
