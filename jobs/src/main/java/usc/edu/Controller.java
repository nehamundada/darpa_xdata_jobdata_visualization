package usc.edu;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
   	@Path("/sample2/{country_name}")
   	@Produces(MediaType.TEXT_PLAIN)
       public Response getJobsByCityForCountry(
       		@Context HttpHeaders headers,       		
       		@PathParam("country_name") String country_name) {

    	return Response.status(200).entity(DBUtil.getSampleData2(country_name).toString()).build();
       
       }
}
