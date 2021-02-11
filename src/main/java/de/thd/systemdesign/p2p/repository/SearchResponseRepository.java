package de.thd.systemdesign.p2p.repository;

import de.thd.systemdesign.p2p.model.SearchResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SearchResponseRepository extends CrudRepository<SearchResponse, Long> {

    Set<SearchResponse> findAll();

}