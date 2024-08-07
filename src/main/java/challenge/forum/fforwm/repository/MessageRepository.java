package challenge.forum.fforwm.repository;

import challenge.forum.fforwm.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByContentContains(String str, Pageable page);
}
