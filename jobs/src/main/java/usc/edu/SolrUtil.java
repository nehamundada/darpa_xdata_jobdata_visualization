package usc.edu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class SolrUtil {
 
	public static JSONObject plotBarGraph(String company) {

		JSONObject object = new JSONObject();
		JSONArray months = new JSONArray();
		JSONArray counts = new JSONArray();

		URL obj;
		try {

			String url = "http://localhost:8983/solr/select?wt=json&indent=true&q="+company+"&" +
					"fl=firstSeenDate&facet=true&facet.field=firstSeenDate&" +
					"facet.pivot=firstSeenDate&facet.limit=10";
			obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			JSONObject jsonObj = new JSONObject(response.toString());

			JSONObject facetCounts  = (JSONObject) jsonObj.get("facet_counts");
			JSONObject facetPivot = (JSONObject) facetCounts.get("facet_pivot");

			JSONArray array = facetPivot.getJSONArray("firstSeenDate");

			for (int i = 0; i < array.length(); i++) {

				JSONObject object2 = array.getJSONObject(i);
				months.put(object2.getString("firstSeenDate"));
				counts.put(object2.getInt("total"));
			}
			object.put("regions", months);
			object.put("institutions", counts);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return object;
	}

	public static JSONArray getdataforcompanies(String company , String jobtype) {
		String url = "http://localhost:8983/solr/select?wt=json&indent=true&q="+company+"&" +
				"fl=latitude,longitude,countryCode,address&facet=true&facet.field=city&" +
				"facet.pivot=latitude,longitude,countryCode,address&facet.limit=10";
		URL obj;
		JSONArray returnarray = new JSONArray();

		try {
			obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			JSONObject jsonObj = new JSONObject(response.toString());

			JSONObject facetCounts  = (JSONObject) jsonObj.get("facet_counts");
			JSONObject facetPivot = (JSONObject) facetCounts.get("facet_pivot");

			JSONArray array = facetPivot.getJSONArray("latitude,longitude,countryCode,address");

			for (int i = 0; i < array.length(); i++) {

				JSONObject object = array.getJSONObject(i);
				JSONObject tempobject = array.getJSONObject(i);
				tempobject.put("latitude", object.getString("latitude"));
				tempobject.put("longitude", object.getString("longitude"));
				tempobject.put("countryCode", object.getString("countryCode"));
				tempobject.put("address", object.getString("address"));

				returnarray.put(tempobject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnarray;
	}


	public static JSONArray serachCompanyNames(String term) {

		String url = "http://localhost:8983/solr/select?wt=json&indent=true&q="+term+"&" +
				"fl=company&facet=true&facet.field=company&" +
				"facet.pivot=company&facet.limit=10";
		URL obj;
		JSONArray returnarray = new JSONArray();

		try {
			obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			JSONObject jsonObj = new JSONObject(response.toString());

			JSONObject facetCounts  = (JSONObject) jsonObj.get("facet_counts");
			JSONObject facetPivot = (JSONObject) facetCounts.get("facet_pivot");

			JSONArray array = facetPivot.getJSONArray("company");

			for (int i = 0; i < array.length(); i++) {

				JSONObject object = array.getJSONObject(i);
				array.put(object.getString("country"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnarray;

	}
	public static JSONArray getCompaniesForCountry(String term) {
		System.out.println("getCompaniesForCountry : " + term);
		JSONArray returnarray = new JSONArray();
		try {
			String url = "http://localhost:8983/solr/select?wt=json&indent=true&q="+term+"&" +
					"fl=company,lat,long&facet=true&facet.field=company,lat,long&" +
					"facet.pivot=company,lat,long&facet.limit=10";
			URL obj;
  
				obj = new URL(url);

				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				JSONObject jsonObj = new JSONObject(response.toString());

				JSONObject facetCounts  = (JSONObject) jsonObj.get("facet_counts");
				JSONObject facetPivot = (JSONObject) facetCounts.get("facet_pivot");

				JSONArray array = facetPivot.getJSONArray("company");

				for (int i = 0; i < array.length(); i++) {
					JSONObject temp = new JSONObject();
					JSONObject arr = (JSONObject)array.get(i);

					temp.put("company", arr.getString("company"));
					temp.put("lat", arr.getString("lat"));
					temp.put("long", arr.getString("long"));

					returnarray.put(temp);
				}
		 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnarray;
	}


	public static JSONArray getJobPointsForCountry(String term) {
		System.out.println("getCompaniesForCountry : " + term);
		JSONArray returnarray = new JSONArray();
		try {
			String url = "http://localhost:8983/solr/select?wt=json&indent=true&q="+term+"&" +
					"fl=company,lat,long,country&facet=true&facet.field=company,lat,long,country&" +
					"facet.pivot=company,lat,long,country&facet.limit=10";
			URL obj;
  
				obj = new URL(url);

				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				JSONObject jsonObj = new JSONObject(response.toString());

				JSONObject facetCounts  = (JSONObject) jsonObj.get("facet_counts");
				JSONObject facetPivot = (JSONObject) facetCounts.get("facet_pivot");

				JSONArray array = facetPivot.getJSONArray("country");

				for (int i = 0; i < array.length(); i++) {
					JSONObject temp = new JSONObject();
					JSONObject arr = (JSONObject)array.get(i);

					temp.put("country", arr.getString("country"));
					temp.put("company", arr.getString("company"));
					temp.put("lat", arr.getString("lat"));
					temp.put("long", arr.getString("long"));

					returnarray.put(temp);
				}
		 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnarray;
	}

	public static Object getCompanyGrowthOverTime_old(String term) {
		System.out.println("getCompaniesForCountry : " + term);
		JSONArray returnarray = new JSONArray();
		try {
			String url = "http://localhost:8983/solr/select?wt=json&indent=true&q="+term+"2012&" +
					"fl=postedDate,country,company&facet=true&facet.field=postedDate,country,company&" +
					"facet.pivot=postedDate,country,company&facet.limit=10";
				URL obj;
  
				obj = new URL(url);

				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				JSONObject jsonObj = new JSONObject(response.toString());

				JSONObject facetCounts  = (JSONObject) jsonObj.get("facet_counts");
				JSONObject facetPivot = (JSONObject) facetCounts.get("facet_pivot");

				JSONArray array = facetPivot.getJSONArray("postedDate,country,company");

				for (int i = 0; i < array.length(); i++) {
					JSONObject temp = new JSONObject();
					JSONObject arr = (JSONObject)array.get(i);

					temp.put("country", arr.getString("postedDate"));
					temp.put("company", arr.getString("country"));
					temp.put("lat", arr.getString("company"));
 
					returnarray.put(temp);
				}
		 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnarray;
	}
 
 }

