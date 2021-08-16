package roadmap.controller.api;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import roadmap.Element;
import roadmap.Roadmap;

public class RoadmapResource extends RepresentationModel<RoadmapResource> {
	
	@Getter
	private final String name;
	
	@Getter
	private final Date createdAt;
	
	@Getter
	private final List<Element> elements;
	
	public RoadmapResource(Roadmap roadmap) {
		this.name = roadmap.getName();
		this.createdAt = roadmap.getCreatedAt();
		this.elements = roadmap.getElements();
	}
}
