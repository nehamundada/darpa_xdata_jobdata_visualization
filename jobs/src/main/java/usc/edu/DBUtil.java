package usc.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

	public static Object getCompanyGrowthOverTime_old(String term) {
		System.out.println("getCompanyGrowthOverTime : " + term);
		JSONArray res = new JSONArray();
		try {
//			String sql = "select distinct latitude, longitude, country from jobs join address_info on lat=latitude and long=longitude where latitude is not null and latitude != 0 and latitude != '' and company = '"+term+"'";
			String sql = "select total, countr as country   from companyGrowth where company = '"+term+"' and postedYear = 2012";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				JSONObject obj = new JSONObject();
				obj.put("value", rs.getString("total"));
				obj.put("label", rs.getString("country"));
				res.put(obj);
			}
			System.out.println("---END" );
			
			sql = "select total, countr as country from companyGrowth where company = '"+term+"' and postedYear = 2013";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			JSONArray res1 = new JSONArray();
			while(rs.next())
			{
				JSONObject obj = new JSONObject();
				obj.put("value", rs.getString("total"));
				obj.put("label", rs.getString("country"));
				res1.put(obj);
			}
			System.out.println("---END" );
			JSONArray full_arr = new JSONArray();
			JSONObject year1 = new JSONObject();
			year1.put("key", "2012");
			year1.put("color", "#d67777");
			year1.put("values", res);
			full_arr.put(year1);
			
			year1 = new JSONObject();
			year1.put("key", "2013");
			year1.put("color", "#4f99b4");
			year1.put("values", res1);
			full_arr.put(year1);
			
			return full_arr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	public static Object getCompanyGrowthOverTime(String term) {
		System.out.println("getCompanyGrowthOverTime : " + term);
		try {
			String sql = "select total, countr as country   from companyGrowth where company = '"+term+"' and postedYear = 2012";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			HashMap<String, Integer> res1 = new HashMap<String, Integer>();
			while(rs.next()) {
				res1.put(rs.getString("country"), rs.getInt("total"));
			}
			
			sql = "select total, countr as country from companyGrowth where company = '"+term+"' and postedYear = 2013";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			HashMap<String, Integer> res2 = new HashMap<String, Integer>();
			while(rs.next()) {
				res2.put(rs.getString("country"), rs.getInt("total"));
			}
			
			Iterator<String> itr = res1.keySet().iterator();
			while(itr.hasNext()) {
				String k = itr.next();
				if(!res2.keySet().contains(k)) {
					res2.put(k, 0);
				}
			}
			
			itr = res2.keySet().iterator();
			while(itr.hasNext()) {
				String k = itr.next();
				if(!res1.keySet().contains(k)) {
					res1.put(k, 0);
				}
			}

			ArrayList<String> labels = new ArrayList<String>();
			ArrayList<Integer> d1 = new ArrayList<Integer>();
			ArrayList<Integer> d2 = new ArrayList<Integer>();
			itr = res2.keySet().iterator();
			while(itr.hasNext()) {
				String k = itr.next();
				labels.add(k);
				d1.add(res1.get(k));
				d2.add(res2.get(k));
			}
			res1 = null;
			res2 = null;
			
			JSONObject finalData = new JSONObject();
			JSONArray dataArr = new JSONArray();
			JSONObject o1 = new JSONObject();
			o1.put("data", d1);
			o1.put("label", "Year:2012");
			o1.put("fillColor", "#d67777");
			dataArr.put(o1);
			
			o1 = new JSONObject();
			o1.put("data", d2);
			o1.put("label", "Year:2013");
			o1.put("fillColor", "#4f99b4");
			dataArr.put(o1);
			
			
			finalData.put("labels", labels);
			finalData.put("datasets", dataArr);
			return finalData;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Object getCompanyGrowthOverTimePoints(String term) {
		System.out.println("getCompanyGrowthOverTimePoints : " + term);
		try {
			String sql = "select lat, long, countr as country  from companyGrowthPoints where company = '"+term+"' and postedYear = 2012";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			JSONArray res1 = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("latitude", rs.getString("lat"));
				obj.put("longitude", rs.getString("long"));
				obj.put("country", rs.getString("country"));
				res1.put(obj);
			}
			
			sql = "select lat, long, countr as country  from companyGrowthPoints where company = '"+term+"' and postedYear = 2013";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			JSONArray res2 = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("latitude", rs.getString("lat"));
				obj.put("longitude", rs.getString("long"));
				obj.put("country", rs.getString("country"));
				res2.put(obj);
			}
			JSONObject finalData = new JSONObject();
			finalData.put("2012", res1);
			finalData.put("2013", res2);
			return finalData;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
