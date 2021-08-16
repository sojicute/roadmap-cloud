package roadmap.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import roadmap.Roadmap;

public interface RoadmapRepository extends PagingAndSortingRepository<Roadmap, Long> {

}
