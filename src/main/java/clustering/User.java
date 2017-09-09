package clustering;

public class User {
	
	String id;
	String name;
	String screenname;
	String url;
	String description;
	boolean protectd;
	boolean veriefied;
	int followers;
	int friends;
	int favourites;
	int num_statuses;
	String created;
	String time_zone;
	boolean geo_enabled;
	String lang;
	boolean hasProfile_picture;
	boolean hasBackground_image;
	
	
	public User(String id, String name, String screenname, String url, String description,
			boolean protectd, boolean veriefied, int followers, int friends, int favourites, int num_statuses,
			String created, String time_zone, boolean geo_enabled, String lang, boolean hasProfile_picture,
			boolean hasBackground_image) {
		super();
		this.id = id;
		this.name = name;
		this.screenname = screenname;
		this.url = url;
		this.description = description;
		this.protectd = protectd;
		this.veriefied = veriefied;
		this.followers = followers;
		this.friends = friends;
		this.favourites = favourites;
		this.num_statuses = num_statuses;
		this.created = created;
		this.time_zone = time_zone;
		this.geo_enabled = geo_enabled;
		this.lang = lang;
		this.hasProfile_picture = hasProfile_picture;
		this.hasBackground_image = hasBackground_image;
	}


	@Override
	public String toString() {
		return "id= " + id 
				+ "\nname= " + name 
				+ "\nscreenname= " + screenname 
				+ "\nurl= " + url 
				+ "\ndescription= " + description 
				+ "\nprotectd= " + protectd 
				+ "\nveriefied= " + veriefied 
				+ "\nfollowers= " + followers
				+ "\nfriends= " + friends 
				+ "\nfavourites= " + favourites 
				+ "\nnum_statuses= " + num_statuses
				+ "\ncreated= " + created 
				+ "\ntime_zone= " + time_zone 
				+ "\ngeo_enabled= " + geo_enabled 
				+ "\nlang= " + lang 
				+ "\nhasProfile_picture= " + hasProfile_picture 
				+ "\nhasBackground_image= " + hasBackground_image;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
