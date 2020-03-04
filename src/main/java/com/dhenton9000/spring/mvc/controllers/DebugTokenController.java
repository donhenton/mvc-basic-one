package com.dhenton9000.spring.mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.lowagie.text.pdf.PdfFileSpecification.url;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value = "/debug/")
@SessionAttributes("tokenRequest")
public class DebugTokenController {

    @Autowired
    private Environment env;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RestTemplate restTemplate;
    private static Logger LOG = LoggerFactory.getLogger(DebugTokenController.class);

    @RequestMapping(value = "/getToken")
    public String getDebugTokenPage(Model model) {
        TokenRequest token = new TokenRequest();

        String authorizeURI = env.getProperty("okta.oauth2.base") + "/oauth2/v1/authorize";
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

    @RequestMapping(value = "processRequest", method = RequestMethod.POST)
    public String addForm(@ModelAttribute("tokenRequest") TokenRequest form, BindingResult result,
            WebRequest webRequest, HttpSession session, Model model) {
        model.addAttribute("tokenRequest", form);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        String url = form.getAuthorizeURI();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("client_id", form.getClientId())
                .queryParam("response_type", form.getTypeString())
                .queryParam("response_mode", form.getResponseMode())
                .queryParam("scope", form.getScope())
                .queryParam("redirect_uri", form.getRedirectURI())
                .queryParam("nonce", form.getNonce())
                .queryParam("state", form.getState());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        LOG.debug(" xxx " + builder.toUriString());
        HttpEntity<HashMap> response = this.restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                HashMap.class);

        HashMap topMap = response.getBody();

        return "tiles.debug.process.page";
    }

}
