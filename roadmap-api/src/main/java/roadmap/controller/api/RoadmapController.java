package roadmap.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roadmap.Roadmap;
import roadmap.data.RoadmapRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/v1/", produces = { "application/json", "text/xml" })
public class RoadmapController {
	
	@Autowired
	private RoadmapRepository roadmapRepo;

	@Autowired
	EntityLinks entityLinks;

	public RoadmapController(RoadmapRepository roadmapRepo) {
		this.roadmapRepo = roadmapRepo;
	}
	
	
	@GetMapping("/roadmaps")
	public Iterable<Roadmap> getAllRoadmaps() {
		return roadmapRepo.findAll();
	}
	
	@PostMapping("/roadmaps")
	public Roadmap createRoadmap(@RequestBody Roadmap roadmap) {
		return roadmapRepo.save(roadmap);
	}
	
	@GetMapping("/roadmaps/{id}")
	public ResponseEntity<Roadmap> getRoadmapById(@PathVariable Long id) {
		Roadmap roadmap = roadmapRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		return ResponseEntity.ok(roadmap);
	}
	
	@PutMapping("/roadmaps/{id}")
	public ResponseEntity<Roadmap> updateRoadmap(@PathVariable Long id, @RequestBody Roadmap roadmapDetails) {
		Roadmap roadmap = roadmapRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		roadmap.setName(roadmap.getName());
		roadmap.setElements(roadmap.getElements());
		
		Roadmap updatedRoadmap = roadmapRepo.save(roadmap);
		
		return ResponseEntity.ok(updatedRoadmap);
	}
	
	public ResponseEntity<Map<String, Boolean>> deleteRoadmap(@PathVariable Long id) {
		Roadmap roadmap = roadmapRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		roadmapRepo.delete(roadmap);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
//	Hateoas
	@GetMapping("/recent")
	public CollectionModel<RoadmapResource> recentRoadmaps() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		
		List<Roadmap> roadmaps = roadmapRepo.findAll(page).getContent();
		
		CollectionModel<RoadmapResource> recentResources = new RoadmapResourceAssembler().toCollectionModel(roadmaps);
				
		recentResources.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoadmapController.class).recentRoadmaps()).withRel("recent"));
		
		return recentResources;
	}
}
