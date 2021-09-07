package roadmap.data;

import org.springframework.data.repository.CrudRepository;

import roadmap.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	
}
