package clustering;

public class Tweet {
	
	String createdAt;
	String id;
	String text;
	String source;
	String id_author;
	
	public Tweet(String createdAt, String id, String text, String source, String id_author) {
		super();
		this.createdAt = createdAt;
		this.id = id;
		this.text = text;
		this.source = source;
		this.id_author = id_author;
	}

	@Override
	public String toString() {
		return  "createdAt= " + createdAt 
				+ "\nid=" + id 
				+ "\ntext=" + text 
				+ "\nsource=" + source 
				+ "\nauthor=" + id_author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
