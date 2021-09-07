package roadmap;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import roadmap.data.CommentRepository;
import roadmap.data.ElementRepository;
import roadmap.data.RoadmapRepository;
import roadmap.data.UserRepository;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

	@Bean
	public CommandLineRunner dataLoader(RoadmapRepository roadmapRepo, UserRepository userRepo,
			ElementRepository elementRepo, CommentRepository commentRepo, PasswordEncoder encoder) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				Comment comment = new Comment();
				comment.setText("Awesome!");
				
//				comment.setElement(element1);
//				element1.getComments().add(comment);
				
				Element element1 = new Element();
				Element element2 = new Element();

				Element childElement1 = new Element();
				Element childElement2 = new Element();
				Element childElement3 = new Element();

				element1.setName("Spring");
				element1.getComments().add(comment);
				
				element2.setName("Hibernate");
				
				comment.setElement(element1);
				
				childElement1.setName("JPA");
				childElement2.setName("Hibernate Detail");
				childElement3.setName("WTF");
				
				Roadmap roadmap = new Roadmap();
				element1.setRoadmap(roadmap);
				element2.setRoadmap(roadmap);

				childElement1.setElement(element1);
				childElement2.setElement(element2);
				childElement3.setElement(element1);

				element1.getSubElements().add(childElement1);
				element1.getSubElements().add(childElement3);
				element2.getSubElements().add(childElement2);

				roadmap.setName("Java developer");

				List<Element> elementList = new ArrayList<>();

				elementList.add(element1);
				elementList.add(element2);

				roadmap.setElements(elementList);

				elementRepo.save(element1);
				elementRepo.save(element2);
				elementRepo.save(childElement1);
				elementRepo.save(childElement2);
				elementRepo.save(childElement3);

				roadmapRepo.save(roadmap);

				userRepo.save(new User("kinetsu", encoder.encode("password"), "Craig Walls", "123 North Street",
						"Cross Roads", "TX", "76227", "123-123-1234"));

				
				Comment comment2 = new Comment();
				comment2.setText("Cool! My second comment!");
				
				element1.getComments().add(comment2);
				comment2.setElement(element1);
				
				commentRepo.save(comment2);
				
//				List<Comment> commentList = new ArrayList<>();
//
//				commentList.add(comment);

//				element1.setComments(commentList);
			}
		};
	}
}