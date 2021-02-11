package de.thd.systemdesign.p2p.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	private static Logger log = LoggerFactory.getLogger(HomeController.class);
	
    /**
     * Custom handler for the welcome view.
     * Note that this handler relies on the RequestToViewNameTranslator to
     * determine the logical view name based on the request URL: "/welcome"
     * @return View name "home" selecting view "/view/home.jsp"
     */
	@RequestMapping({"/", "/p2p", "/welcome"})
    public ModelAndView welcome() {
    	log.info("Controller für welcome");

		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Herzlich Willkommen!");
		mv.addObject("message", "Sie können hier ihre P2P Node steuern.");
		mv.setViewName("p2p");
		return mv;
    }

}