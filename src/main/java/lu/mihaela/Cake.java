package lu.mihaela;

public class Cake {
	
	static final long serialVersionUID = 12345L;
	
	private long id;

	private String imgName, name, ingredients, method;
	
	private double price;
	
	public void Cake(){};

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public double getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}

	public void setPrice(double price) {
		// TODO Auto-generated method stub
		this.price = price;
	}
}
