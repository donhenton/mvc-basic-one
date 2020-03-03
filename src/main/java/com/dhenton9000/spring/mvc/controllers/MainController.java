package com.dhenton9000.spring.mvc.controllers;

import com.dhenton9000.spring.mvc.config.UserHybrid;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private static Logger log = LoggerFactory.getLogger(MainController.class);

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
            OAuth2AuthenticationToken token, Model model) {
        //DefaultOidcUser
        Object secObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //  log.info("Request for default / url processed "+userX.getClass().getName());
        // OAuth2AuthenticationToken t = (OAuth2AuthenticationToken) principal;
        // the principal can be cast to OAuth2AuthenticationToken

        UserHybrid user = (UserHybrid) secObject;

  
        Map<String, Object> attribs = (Map<String, Object>) user.getAttributes();
        Collection<GrantedAuthority> authorities = token.getAuthorities();

        if (principal != null) {
            model.addAttribute("principal", principal.getClass().getName());
        } else {
            model.addAttribute("principal", "principal is null");
        }

        model.addAttribute("secObject", secObject.getClass().getName());
        model.addAttribute("token", token.getCredentials());
        model.addAttribute("authorities", authorities);
        model.addAttribute("attribs", attribs);

        return new ModelAndView("tiles.index");
    }

}
