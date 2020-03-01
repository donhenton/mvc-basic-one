package com.dhenton9000.spring.mvc.controllers;

import java.security.Principal;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private static Logger log = LoggerFactory.getLogger(NotUsedHomePageController.class);

    /**
     *
     * https://developer.okta.com/blog/2018/02/13/secure-spring-microservices-with-oauth?_ga=2.101078273.1236934600.1583006660-154979438.1582567334
     * @param principal
     * 
     * @param model
     * @param oauth2User
     *
     * @return
     */
    
    //org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
    @RequestMapping("/")
    public ModelAndView handleIndexPage( OAuth2AuthenticationToken principal,  Model model) {
        log.info("Request for default / url processed ");
        OAuth2User item = principal.getPrincipal();
        Map<String, Object> user = (Map<String, Object>) item.getAttributes();
        Collection<GrantedAuthority> authorities = principal.getAuthorities();
        /*   return "User Name: " + oauth2User.getName() + "<br/>"
                + "User Authorities: " + oauth2User.getAuthorities() + "<br/>"
                + "Client Name: " + authorizedClient.getClientRegistration().getClientName() + "<br/>"
                + this.prettyPrintAttributes(oauth2User.getAttributes());
         */
        model.addAttribute("data", prettyPrintAttributes(user));

        return new ModelAndView("tiles.index");
    }

    private String prettyPrintAttributes(Map<String, Object> attributes) {
        String acc = "User Attributes: ";
        for (String key : attributes.keySet()) {
            Object value = attributes.get(key);
            acc += key + " " + value.toString();
        }
        return acc;
    }
}
