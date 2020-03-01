package com.dhenton9000.spring.mvc.controllers;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import javax.security.auth.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

@Controller
public class MainController {

    private static Logger log = LoggerFactory.getLogger(NotUsedHomePageController.class);

    /**
     *
     * https://developer.okta.com/blog/2018/02/13/secure-spring-microservices-with-oauth?_ga=2.101078273.1236934600.1583006660-154979438.1582567334
     * 
     * 
     * 
     * @param principal
     * @param token
     * @param model
     * @param details
     * @return
     */
   
 
    @RequestMapping("/")
    public ModelAndView handleIndexPage(Principal principal, 
            OAuth2AuthenticationToken token,  Model model) {
        //DefaultOidcUser
         OidcUser  userX = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Request for default / url processed "+userX.getClass().getName());
        // OAuth2AuthenticationToken t = (OAuth2AuthenticationToken) principal;
        // the principal can be cast to OAuth2AuthenticationToken
        
        ;
        
        OAuth2User item = token.getPrincipal();
        Map<String, Object> user = (Map<String, Object>) item.getAttributes();
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        /*   return "User Name: " + oauth2User.getName() + "<br/>"
                + "User Authorities: " + oauth2User.getAuthorities() + "<br/>"
                + "Client Name: " + authorizedClient.getClientRegistration().getClientName() + "<br/>"
                + this.prettyPrintAttributes(oauth2User.getAttributes());
         */
        if (principal != null) {
        model.addAttribute("user",principal.toString());
        }
        else {
             model.addAttribute("user"," user is null");
        }
        model.addAttribute("data", authorities);
         model.addAttribute("userx", userX.getName());
        
        

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
