package clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class PostProcessing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateDatasets(loadData("/Users/lukaskrabbe/Desktop/TwitterData/"), "/Users/lukaskrabbe/Desktop/Users/",
				"/Users/lukaskrabbe/Desktop/Tweets/");
	}

	private static void CreateDatasets(List<List> loadData, String Path_Userfile, String PathTweetfile) {
		Data_File data_file_tmp;
		List<Tweet> Tweets = loadData.get(0);
		List<User> Users = loadData.get(1);
		String[] foundDatasetsUsers = new File(Path_Userfile).list();
		String[] foundDatasetsTweets = new File(PathTweetfile).list();

		for (User user : Users) {
			boolean found = false;
			for (int i = 0; i < foundDatasetsUsers.length; i++) {
				if (foundDatasetsUsers[i].contains(user.getId()))
					found = true;
			}
			if (!found) {
				data_file_tmp = new Data_File(Path_Userfile, user.getId());
				data_file_tmp.addVal(user.toString());
				data_file_tmp.toFile();
			} 
		}

		for (Tweet tweet : Tweets) {
			boolean found = false;
			for (int i = 0; i < foundDatasetsTweets.length; i++) {
				if (foundDatasetsTweets[i].contains(tweet.getId()))
					found = true;
			}
			if (!found) {
				data_file_tmp = new Data_File(PathTweetfile, tweet.getId());
				data_file_tmp.addVal(tweet.toString());
				data_file_tmp.toFile();
			}
		}

	}

	public static List<List> loadData(String Path) {
		List<List> l = new ArrayList<List>();
		List<Tweet> Tweets = new ArrayList<Tweet>();
		List<User> Users = new ArrayList<User>();
		String[] foundDatasets = new File(Path).list();

		FileReader filRe;
		BufferedReader bufRe;
		String tmp;

		for (int i = 1; i < foundDatasets.length; i++) {
			try {

				filRe = new FileReader(Path + foundDatasets[i]);
				bufRe = new BufferedReader(filRe);

				while ((tmp = bufRe.readLine()) != null) {
					if (tmp.length() > 2) { // check if it isn't an empty row
						if (tmp.contains("{\"delete\":")) {
							// Deleted Tweet's
						} else {

							// Tweets :
							String created_at = tmp.substring(tmp.indexOf("{\"created_at\":\"") + 15,
									tmp.indexOf("\",\"id\":"));
							String id_Tw = tmp.substring(tmp.indexOf("\",\"id\":") + 7, tmp.indexOf(",\"id_str"));
							String text = tmp.substring(tmp.indexOf("\"text\":\"") + 8, tmp.indexOf(",\"source\":\""));
							text = text.contains("display_text_range")
									? text.substring(0, text.indexOf("\",\"display_text_range\"")) : text;
							String source = tmp.substring(tmp.indexOf(",\"source\":\"") + 11,
									tmp.indexOf(",\"truncated\":"));
							String id_author = tmp.substring(tmp.indexOf("user\":{\"id\":") + 12,
									tmp.indexOf(",\"id_str\":\"", tmp.indexOf("user\":{\"id\":")));
							Tweets.add(new Tweet(created_at, id_Tw, text, source, id_author));

							// Users:
							String id_Usr = tmp.substring(tmp.indexOf("user\":{\"id\":") + 12,
									tmp.indexOf(",\"id_str\":\"", tmp.indexOf("user\":{\"id\":")));

							String name = tmp.substring(tmp.indexOf("\",\"name\":\"") + 10,
									tmp.indexOf("\",\"screen_name\":\""));

							String screenname = tmp.substring(tmp.indexOf("\",\"screen_name\":\"") + 17,
									tmp.indexOf("\",\"location\"", tmp.indexOf("\",\"screen_name\":\"")));

							String description = tmp.substring(
									tmp.indexOf(",\"description\":", tmp.indexOf("user\":{\"id\":")) + 15,
									tmp.indexOf(",\"protected\":", tmp.indexOf("user\":{\"id\":")));
							description = (description.charAt(0) == '\"')
									? description.substring(1, description.length() - 1) : description;

							String url = tmp.substring(tmp.indexOf(",\"url\":", tmp.indexOf("user\":{\"id\":")) + 7,
									tmp.indexOf(",\"description\":", tmp.indexOf("user\":{\"id\":")));
							url = (url.charAt(0) == '\"') ? url.substring(1, url.length() - 1) : url;

							String protectdS = tmp.substring(
									tmp.indexOf(",\"protected\":", tmp.indexOf("user\":{\"id\":")) + 13,
									tmp.indexOf(",\"verified\":", tmp.indexOf("user\":{\"id\":")));
							boolean protectd = protectdS.contains("false") ? false : true;

							String veriefiedS = tmp.substring(
									tmp.indexOf(",\"verified\":", tmp.indexOf("user\":{\"id\":")) + 12,
									tmp.indexOf(",\"followers_count\":", tmp.indexOf("user\":{\"id\":")));
							boolean veriefied = veriefiedS.contains("false") ? false : true;

							String followersS = tmp.substring(
									tmp.indexOf(",\"followers_count\":", tmp.indexOf("user\":{\"id\":")) + 19,
									tmp.indexOf(",\"friends_count\":", tmp.indexOf("user\":{\"id\":")));
							int followers = Integer.parseInt(followersS);

							String friendsS = tmp.substring(
									tmp.indexOf(",\"friends_count\":", tmp.indexOf("user\":{\"id\":")) + 17,
									tmp.indexOf(",\"listed_count\":", tmp.indexOf("user\":{\"id\":")));
							int friends = Integer.parseInt(friendsS);

							String favouritesS = tmp.substring(
									tmp.indexOf(",\"friends_count\":", tmp.indexOf("user\":{\"id\":")) + 17,
									tmp.indexOf(",\"listed_count\":", tmp.indexOf("user\":{\"id\":")));
							int favourites = Integer.parseInt(favouritesS);

							String numstatusesS = tmp.substring(
									tmp.indexOf(",\"statuses_count\":", tmp.indexOf("user\":{\"id\":")) + 18,
									tmp.indexOf(",\"created_at\":", tmp.indexOf("user\":{\"id\":")));
							int num_statuses = Integer.parseInt(numstatusesS);

							String created = tmp.substring(
									tmp.indexOf(",\"created_at\":", tmp.indexOf("user\":{\"id\":")) + 15,
									tmp.indexOf("\",\"utc_offset\":", tmp.indexOf("user\":{\"id\":")));

							String time_zone = tmp.substring(
									tmp.indexOf(",\"time_zone\":", tmp.indexOf("user\":{\"id\":")) + 13,
									tmp.indexOf(",\"geo_enabled\":", tmp.indexOf("user\":{\"id\":")));
							time_zone = (time_zone.charAt(0) == '\"') ? time_zone.substring(1, time_zone.length() - 1)
									: time_zone;

							String geo_enabledS = tmp.substring(
									tmp.indexOf(",\"geo_enabled\":", tmp.indexOf("user\":{\"id\":")) + 15,
									tmp.indexOf(",\"lang\":", tmp.indexOf("user\":{\"id\":")));
							boolean geo_enabled = geo_enabledS.contains("false") ? false : true;

							String lang = tmp.substring(tmp.indexOf(",\"lang\":", tmp.indexOf("user\":{\"id\":")) + 8,
									tmp.indexOf(",\"contributors_enabled\":", tmp.indexOf("user\":{\"id\":")));
							lang = (lang.charAt(0) == '\"') ? lang.substring(1, lang.length() - 1) : lang;

							String hasProfile_pictureS = tmp.substring(
									tmp.indexOf(",\"default_profile_image\":", tmp.indexOf("user\":{\"id\":")) + 25,
									tmp.indexOf(",\"following\":", tmp.indexOf("user\":{\"id\":")));
							boolean hasProfile_picture = hasProfile_pictureS.contains("false") ? false : true;

							String hasBackground_imageS = tmp.substring(
									tmp.indexOf("\"profile_background_image_url_https\":",
											tmp.indexOf("user\":{\"id\":")) + 37,
									tmp.indexOf(",\"profile_background_tile\":", tmp.indexOf("user\":{\"id\":")));
							boolean hasBackground_image = hasBackground_imageS.length() > 2 ? true : false;

							Users.add(new User(id_Usr, name, screenname, url, description, protectd, veriefied,
									followers, friends, favourites, num_statuses, created, time_zone, geo_enabled, lang,
									hasProfile_picture, hasBackground_image));

						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		l.add(Tweets);
		l.add(Users);
		return l;
	}

}
