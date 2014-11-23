package usc.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class DBUtil {

	private static Connection connection = null;
	private static final String dbPath = "/Users/shri/devel/cs572/code/Content-extraction-and-search-using-Apache-Tika/python_scripts/jobs.sqlite";

	static {

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONArray getSampleData() {
		JSONArray res = new JSONArray();
		try {
			String sql = "select count(*) as total, a2.country from jobs a1 join address_info a2 on a2.lat = a1.latitude and a2.long = a1.longitude group by a2.country";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				JSONObject obj = new JSONObject();
				obj.put("name", rs.getString("country"));
				obj.put("value", rs.getInt("total"));
				res.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static JSONArray getSampleData2(String country) {
		JSONArray res = new JSONArray();

		try {
			String sql = "select count(*) as total, a2.city from jobs a1 join address_info a2 on a2.lat = a1.latitude and a2.long = a1.longitude where a2.country = '"+country+"' group by a2.city order by count(*) desc;";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				JSONObject obj = new JSONObject();
				obj.put("name", rs.getString("city"));
				obj.put("value", rs.getInt("total"));
				res.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	
}
