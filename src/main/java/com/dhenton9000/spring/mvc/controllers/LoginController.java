package com.dhenton9000.spring.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static String authorizationRequestBaseUri
            = "oauth2/authorization";
    //  Map<String, String> oauth2AuthenticationUrls
    //          = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/loginFailure")
    public String getLoginFailure(Model model) {

        return "tile.login.failure";
    }

    @GetMapping("/logoutdone")
    public String getLoginOut(Model model) {

        return "tiles.logout";
    }

    @GetMapping("/oauth_login")
    public String getLoginPage(Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE
                && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        ClientRegistration registration = clientRegistrations.iterator().next();
        //  clientRegistrations.forEach(registration
        //         -> oauth2AuthenticationUrls.put(registration.getClientName(),
        //               authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("url", authorizationRequestBaseUri + "/" + registration.getRegistrationId());

        return "tiles.login";
    }

}
