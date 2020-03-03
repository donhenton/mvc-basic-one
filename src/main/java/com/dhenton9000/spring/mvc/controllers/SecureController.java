package com.dhenton9000.spring.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/secured/")
public class SecureController {

    private static Logger log = LoggerFactory.getLogger(SecureController.class);

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

}
