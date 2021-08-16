package roadmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="ROADMAPS")
public class Roadmap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Date createdAt;

	
    @OneToMany(mappedBy="roadmap", cascade = CascadeType.ALL)
	private List<Element> elements = new ArrayList<>();
	
	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

}
