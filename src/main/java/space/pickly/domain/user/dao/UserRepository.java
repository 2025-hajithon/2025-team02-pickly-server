package space.pickly.domain.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import space.pickly.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {}
