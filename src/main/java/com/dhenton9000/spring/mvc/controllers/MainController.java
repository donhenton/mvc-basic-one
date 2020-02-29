package com.dhenton9000.spring.mvc.controllers;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private static Logger log = LoggerFactory.getLogger(NotUsedHomePageController.class);

    /**
     *
     *
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping("/")
    public ModelAndView handleIndexPage(Model model, Locale locale) {
        log.info("Request for default / url processed ");
        return new ModelAndView("tiles.index");
    }

}
