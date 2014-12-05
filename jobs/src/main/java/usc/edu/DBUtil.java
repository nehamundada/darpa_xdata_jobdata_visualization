package usc.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class DBUtil {

	private static Connection connection = null;
	private static final String dbPath = "F:/study/sem3/CS572/HW/HW3/Db/jobs.sqlite";

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
	
	
	
	public static JSONArray serachCompanyNames(String term) {
		JSONArray res = new JSONArray();
		try {
			String sql = "select distinct company from corporate_presence where company like '%"+term+"%'";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				res.put(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static JSONArray getCompaniesForCountry(String term) {
		System.out.println("getCompaniesForCountry : " + term);
		JSONArray res = new JSONArray();
		try {
			String sql = "select sum(total) as count, country from corporate_presence  where company = '"+term+"' group by country;";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				JSONObject obj = new JSONObject();
				obj.put("name", rs.getString("country"));
				obj.put("value", rs.getInt("count"));
				res.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	
	public static JSONArray getJobPointsForCountry(String term) {
		System.out.println("getJobPointsForCountry : " + term);
		JSONArray res = new JSONArray();
		try {
//			String sql = "select distinct latitude, longitude, country from jobs join address_info on lat=latitude and long=longitude where latitude is not null and latitude != 0 and latitude != '' and company = '"+term+"'";
			String sql = "select lat as latitude, long as longitude, country from country_points where company = '"+term+"'";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				JSONObject obj = new JSONObject();
				obj.put("latitude", rs.getString("latitude"));
				obj.put("longitude", rs.getString("longitude"));
				obj.put("country", rs.getString("country"));
				res.put(obj);
			}
			System.out.println("---END" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	public static Object getCompanyGrowthOverTime(String term) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
