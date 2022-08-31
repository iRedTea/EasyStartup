package site.easystartup.web.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import site.easystartup.web.chat.domain.model.ChatMessage;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {
}
