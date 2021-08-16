package roadmap.data;

import org.springframework.data.repository.CrudRepository;

import roadmap.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
