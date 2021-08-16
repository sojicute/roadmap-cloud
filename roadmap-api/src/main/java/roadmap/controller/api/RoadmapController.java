package roadmap.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roadmap.Roadmap;
import roadmap.data.RoadmapRepository;

@RestController
@RequestMapping(path = "/roadmap", produces = { "application/json", "text/xml" })
@CrossOrigin(origins = "*")
public class RoadmapController {
	private RoadmapRepository roadmapRepo;

	@Autowired
	EntityLinks entityLinks;

	public RoadmapController(RoadmapRepository roadmapRepo) {
		this.roadmapRepo = roadmapRepo;
	}

	@GetMapping("/recent")
	public CollectionModel<RoadmapResource> recentRoadmaps() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		
		List<Roadmap> roadmaps = roadmapRepo.findAll(page).getContent();
		
		CollectionModel<RoadmapResource> recentResources = new RoadmapResourceAssembler().toCollectionModel(roadmaps);
				
		recentResources.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoadmapController.class).recentRoadmaps()).withRel("recent"));
		
		return recentResources;
	}

	@GetMapping("/api/{id}")
	public Roadmap roadmapById(@PathVariable("id") Long id) {
		Optional<Roadmap> optRoadmap = roadmapRepo.findById(id);
		if (optRoadmap.isPresent()) {
			return optRoadmap.get();
		}
		return null;
	}

}
