package lu.mihaela;

import java.sql.Blob;

public class Image {
	
	static final long serialVersionUID = 12345L;
	
	private int id;
	
	private String imgName;
	
	private Blob image;

	public Image() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}
	

}
