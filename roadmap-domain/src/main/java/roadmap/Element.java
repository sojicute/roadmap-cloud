package roadmap;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ELEMENTS")
public class Element {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Type type;

	public static enum Type {
		MUSTHAVE, RECOMMENDED, NOTRELEVANT
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "roadmap_id")
	@JsonIgnore
	private Roadmap roadmap;

	@OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
	private List<Element> subElements = new ArrayList<>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "element_id")
	@JsonIgnore
	private Element element;

	@OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

}
