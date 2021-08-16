package roadmap.controller.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import roadmap.Roadmap;

public class RoadmapResourceAssembler extends RepresentationModelAssemblerSupport<Roadmap, RoadmapResource> {

	public RoadmapResourceAssembler() {
		super(RoadmapResourceAssembler.class, RoadmapResource.class);
	}

	@Override
	public RoadmapResource toModel(Roadmap roadmap) {
		return new RoadmapResource(roadmap);
	}

}
