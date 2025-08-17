package com.server.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.model.CodeMessage;
import java.util.Optional;

@Repository
public interface CodeMessageRepository extends JpaRepository<CodeMessage, Long> {
    Optional<CodeMessage> findTopByRoomIdOrderByLastUpdatedDesc(String roomId);
}
