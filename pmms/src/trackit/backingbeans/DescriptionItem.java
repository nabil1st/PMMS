package trackit.backingbeans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="descriptionitem")
public class DescriptionItem implements IDescriptionItem, IListItem {
	private Long id;
	private String description;

	public DescriptionItem(){}
	
	public DescriptionItem(Long id, String desc) {
		this.id = id;
		this.description = desc;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
