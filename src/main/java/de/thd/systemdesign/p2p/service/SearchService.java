package de.thd.systemdesign.p2p.service;

import de.thd.systemdesign.p2p.config.NodeConfig;
import de.thd.systemdesign.p2p.messages.ForwardedMessage;
import de.thd.systemdesign.p2p.messages.SearchMessage;
import de.thd.systemdesign.p2p.messages.SearchResponseMessage;
import de.thd.systemdesign.p2p.model.Search;
import de.thd.systemdesign.p2p.model.SearchResponse;
import de.thd.systemdesign.p2p.repository.SearchRepository;
import de.thd.systemdesign.p2p.repository.SearchResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SearchService {
    private static Logger log = LoggerFactory.getLogger(SearchService.class);

    private NodeConfig cfg;
    private ConnectionService connectionService;
    private Environment env;
    private SearchRepository searchRepository;
    private SearchResponseRepository searchResponseRepository;

    class MediaRepository {
        public boolean query(String query) {
            return (env.getProperty("media.content", "").equals(query));
        }
    }
    private MediaRepository mediaRepository = new MediaRepository();

    @Autowired
    SearchService(SearchResponseRepository searchResponseRepository, SearchRepository searchRepository, NodeConfig cfg, ConnectionService connectionService, Environment env) {
        this.cfg = cfg;
        this.env = env;
        this.connectionService = connectionService;
        this.searchRepository = searchRepository;
        this.searchResponseRepository = searchResponseRepository;
    }

    public Set<Search> searches() {
        return searchRepository.findAll();
    }

    public void search_for(String thing) {
        ForwardedMessage msg = new ForwardedMessage(cfg.getNode());
        SearchMessage search = new SearchMessage(cfg.getNode(), thing);
        msg.setMsg(search);
        searchRepository.save(new Search(search.getUid(), search.getQuery()));
        connectionService.sendMessage(msg);
    }

    public void search(ForwardedMessage msg) {
        SearchMessage s = (SearchMessage) msg.getMsg();
        boolean hit = mediaRepository.query(s.getQuery());
        if (hit) {
            log.debug("HIT! Now send a reply");
            SearchResponseMessage sr = new SearchResponseMessage(cfg.getNode(), s.getUid(), cfg.getNode() + "media/" + s.getQuery());
            connectionService.sendTo(s.getSource(), sr);
        }
        msg.addHop(cfg.getNode());
        connectionService.forwardMessage(msg);
    }

    public Set<SearchResponse> getResponses() {
        return searchResponseRepository.findAll();
    }

    public void found(String id, SearchResponseMessage searchreply) {
        log.info("Got a search reply for query " + id);
        SearchResponse sr = new SearchResponse(searchreply.getUid(), searchreply.getLocation());
        searchResponseRepository.save(sr);
    }

}