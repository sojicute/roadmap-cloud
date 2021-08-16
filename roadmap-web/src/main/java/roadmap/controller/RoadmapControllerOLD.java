package roadmap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import roadmap.Roadmap;
import roadmap.data.ElementRepository;
import roadmap.data.RoadmapRepository;

@Controller
@RequestMapping("/roadmap")
@SessionAttributes("create")
public class RoadmapControllerOLD {
	private RoadmapRepository roadmapRepo;
	private ElementRepository elementRepo;

	@Autowired
	public RoadmapControllerOLD(RoadmapRepository roadmapRepo, ElementRepository elementRepo) {
		this.roadmapRepo = roadmapRepo;
		this.elementRepo = elementRepo;
	}

	@ModelAttribute(name = "create")
	public Roadmap design() {
		return new Roadmap();
	}
	

	@GetMapping
	public String listRoadmap(Model model) {
		List<Roadmap> roadmaps = new ArrayList<>();
		roadmapRepo.findAll().forEach(i -> roadmaps.add(i));
		
		model.addAttribute("roadmaps", roadmaps);
		return "roadmap";
	}
	
	
	@GetMapping("/{id}")
	public String roadmapById(@PathVariable long id, Model model) {
		
		Roadmap roadmap = roadmapRepo.findById(id).get();
		model.addAttribute("roadmap", roadmap);
		return "roadmapDetail";
	}
	
	@PostMapping(consumes="aplication/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Roadmap postRoadmap(@RequestBody Roadmap roadmap) {
		return roadmapRepo.save(roadmap);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteRoadmap(@PathVariable("id") Long id) {
		try {
			roadmapRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {}
	}
}
