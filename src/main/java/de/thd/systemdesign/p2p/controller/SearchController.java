package de.thd.systemdesign.p2p.controller;

import de.thd.systemdesign.p2p.messages.ForwardedMessage;
import de.thd.systemdesign.p2p.messages.SearchMessage;
import de.thd.systemdesign.p2p.messages.SearchResponseMessage;
import de.thd.systemdesign.p2p.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
	private static Logger log = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private SearchService searchService;

	/**
	 * Starts a search for @Parameter thing.
	 */
	@GetMapping(value = "/suchen/")
	public ModelAndView list() {
		log.info("list searches");

		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Herzlich Willkommen!");
		mv.addObject("message", "");
		mv.addObject("ergebnisse", searchService.getResponses());
		mv.setViewName("list-suchen");
		return mv;
	}

	/**
	 * Starts a search for @Parameter thing.
     */
    @GetMapping(value = "/search/{thing}")
    public ModelAndView searchFor(@PathVariable("thing") String thing) {
    	log.info("search for " + thing);
		searchService.search_for(thing);
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Herzlich Willkommen!");
		mv.addObject("message", "Suche nach " + thing);
		mv.setViewName("p2p");
		return mv;
    }

	/**
	 * Starts a search for @Parameter thing.
	 */
	@PostMapping("/search/{id}/")
	@ResponseBody
	public String search_reply(@PathVariable("id") String id, @RequestBody SearchResponseMessage searchreply) {
		log.info("search result for " + id);
		assert(id.equals(searchreply.getUid()));
		searchService.found(id, searchreply);

		return "1";
	}

	/**
	 * Receives a search query, checks and forwards it.
	 */
	@PostMapping("/search/")
	@ResponseBody
	public String search(@RequestBody ForwardedMessage msg) {
		log.info("search for " + ((SearchMessage)msg.getMsg()).getQuery());
		searchService.search(msg);
		return "1";
	}
}