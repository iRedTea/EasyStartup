package site.easystartup.web.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import site.easystartup.web.chat.domain.model.ChatRoom;

public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long> {
}
