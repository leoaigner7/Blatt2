package de.thd.systemdesign.p2p.repository;

import de.thd.systemdesign.p2p.dto.P2PClientDto;
import de.thd.systemdesign.p2p.model.P2PClient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<P2PClient, Long> {

    public List<P2PClient> findAll();

    @Query("FROM P2PClient c WHERE c.nodeid = :nodeId")
    Optional<P2PClient> findById(String nodeId);

    @Query("SELECT new de.thd.systemdesign.p2p.dto.P2PClientDto(c.nodeid, c.lastSeen) FROM P2PClient c WHERE c.nodeid = :nodeId")
    Optional<P2PClientDto> findByIdDto(String nodeId);

    @Query("SELECT new de.thd.systemdesign.p2p.dto.P2PClientDto(c.nodeid, c.lastSeen) FROM P2PClient c")
    List<P2PClientDto> findAllDto();

    @Transactional
    @Modifying
    @Query("DELETE FROM P2PClient c WHERE c.lastSeen < :thresh")
    void deleteAllByLastModificationDate(LocalDateTime thresh);
}