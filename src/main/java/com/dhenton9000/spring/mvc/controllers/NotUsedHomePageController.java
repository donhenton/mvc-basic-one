package com.dhenton9000.spring.mvc.controllers;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The controller for the homepage
 *
 * @author Don
 *
 */
//@Controller
public class NotUsedHomePageController {

    private static Logger log = LoggerFactory.getLogger(NotUsedHomePageController.class);

    /**
     * To handle the regular request to the application context. e.g.
     * http://localhost:8080/learn-websockets-1
     *
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping("/")
    public ModelAndView handleIndexPage(Model model, Locale locale) {
        log.info("Request for default / url processed ");
        return new ModelAndView("tiles.login");
    }

    /**
     * Method is executed when there is a call to the <code>/logoutPage</code>
     * url.
     *
     * @return
     */
    @RequestMapping(value = "/logoutPage", method = RequestMethod.GET)
    public ModelAndView logoutPage() {
        log.info("Request for /logoutPage url processed");
        return new ModelAndView("tiles.logout");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/loginPage?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    /**
     * Method is executed when there is a call to the <code>/loginPage</code>
     * url. On successful login, the user is re-directed to the
     * <code>/secured/myPage</code> url.
     *
     * @return
     */
    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String loginPage() {
        log.info("Request for /loginPage url processed");
        return "tiles.login";
    }

    @RequestMapping(value = "/secured/securedSample", method = RequestMethod.GET)
    public String securedHomePage() {
        log.info("Request for secure page home url processed");
        return "tiles.securedSample";
    }

}
