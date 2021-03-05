package ts.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="ArrayOfString")
public class ArrayOfString {
	private List<String> list;

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
}
