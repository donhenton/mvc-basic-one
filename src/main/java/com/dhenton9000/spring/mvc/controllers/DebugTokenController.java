package com.dhenton9000.spring.mvc.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping(value = "/debug/")
@SessionAttributes("tokenRequest")
public class DebugTokenController {

    @Autowired
    private Environment env;

    @RequestMapping(value = "/getToken")
    public String getDebugTokenPage(Model model) {
        TokenRequest token = new TokenRequest();

        String authorizeURI = env.getProperty("okta.oauth2.issuer") + "/oauth2/v1";
        String clientId = env.getProperty("spring.security.oauth2.client.registration.okta.client-id");
        token.setAuthorizeURI(authorizeURI);
        token.setClientId(clientId);
        model.addAttribute("tokenRequest", token);
        return "tiles.debug.page";
    }

    @RequestMapping(value = "/redirect/target")
    public String showDebugTokenPage() {

        return "tiles.debug.redirect.target";
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("tokenRequest") TokenRequest request,
            BindingResult result) {

        return "tiles.debug.page";
    }

}
