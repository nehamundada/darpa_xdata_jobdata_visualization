package usc.edu;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("controller")
public class Controller {



	@GET
	@Path("/sample1")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJobsByCountry( @Context HttpHeaders headers) {

		return Response.status(200).entity(DBUtil.getSampleData().toString()).build();
	}

	  
    @GET
   	@Path("/sample2/{country_name}/{jobtype}")
   	@Produces(MediaType.TEXT_PLAIN)
       public Response getJobsByCityForCountry(
       		@Context HttpHeaders headers,       		
       		@PathParam("country_name") String country_name,
       		@PathParam("jobtype") String jobtype) {

    	return Response.status(200).entity(DBUtil.getdataforcompanies(country_name , jobtype).toString()).build();
       
       }
   

    @GET
   	@Path("/sample3/{company}")
   	@Produces(MediaType.TEXT_PLAIN)
       public Response plotBarGraph(
       		@Context HttpHeaders headers,       		
       		@PathParam("company") String company) {

    	return Response.status(200).entity(DBUtil.plotBarGraph(company).toString()).build();
       
       }
    
	@GET
	@Path("/sample2/{country_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJobsByCityForCountry(
			@Context HttpHeaders headers,       		
			@PathParam("country_name") String country_name) {

		return Response.status(200).entity(DBUtil.getSampleData2(country_name).toString()).build();

	}
	
	
	@GET
	@Path("/serachCompany")
	@Produces(MediaType.APPLICATION_JSON)
	public Response serachCompany(
			@Context HttpHeaders headers,       		
			@QueryParam("term") String term) {
		return Response.status(200).entity(DBUtil.serachCompanyNames(term).toString()).build();

	}
	
	@POST
	@Path("/getCompaniesForCountry")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompaniesForCountry(
			@Context HttpHeaders headers, 
			@FormParam("term") String term) {
		return Response.status(200).entity(DBUtil.getCompaniesForCountry(term).toString()).build();

	}
	@POST
	@Path("/getJobPointsForCountry")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJobPointsForCountry(
			@Context HttpHeaders headers, 
			@FormParam("term") String term) {
		return Response.status(200).entity(DBUtil.getJobPointsForCountry(term).toString()).build();

	}
	
	@POST
	@Path("/getCompanyGrowthOverTime")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanyGrowthOverTime(
			@Context HttpHeaders headers, 
			@FormParam("term") String term) {
		return Response.status(200).entity(DBUtil.getCompanyGrowthOverTime(term).toString()).build();

	}
	
	@POST
	@Path("/getCompanyGrowthOverTimePoints")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanyGrowthOverTimePoints(
			@Context HttpHeaders headers, 
			@FormParam("term") String term) {
		return Response.status(200).entity(DBUtil.getCompanyGrowthOverTimePoints(term).toString()).build();

	}
	
	@POST
	@Path("/getJobCategoryGrowth")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJobCategoryGrowth() {
		return Response.status(200).entity(DBUtil.getJobCategoryGrowth().toString()).build();
		
	}
	
}
