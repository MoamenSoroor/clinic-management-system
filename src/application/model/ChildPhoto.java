package application.model;

import java.io.File;

public class ChildPhoto implements MyModel {
	
	private int childId;
	private File photo;
	
	public ChildPhoto()
	{
		childId = 0;
		photo = new File("src/application/sub/boy.png");
	}
	public ChildPhoto(int id , File photo2)
	{
		childId = id;
		photo = photo2;
	}
	
	public int getChildId() {
		return childId;
	}
	public void setChildId(int childId) {
		this.childId = childId;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}

	
}
