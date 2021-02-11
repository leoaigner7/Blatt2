package de.thd.systemdesign.p2p.repository;

import de.thd.systemdesign.p2p.model.Search;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SearchRepository extends CrudRepository<Search, Long> {

    Set<Search> findAll();

}