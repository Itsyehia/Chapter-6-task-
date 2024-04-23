package App;

 

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import ejbs.calculation;

@Stateless
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class calculationService {

	

	@PersistenceContext(unitName="CalcPU")
    private  EntityManager entityManager;
	
	@POST
	@Path("calc")
	
	public String addCalculation(calculation  c) {
		
		 int number1 = c.getNumber1();
	        int number2 = c.getNumber2();
	        String operation = c.getOperation();
	        double res = c.calculateResult(number1, number2, operation);
	        c.setResult(res);

	        // Persist the calculation entity
	        try {
	            entityManager.persist(c); // This will save the calculation entity to the database
	            return "Result: " + res;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Status: 500";
	        }  
	}
	
	
//	 @GET
//	 @Path("/test")
//	 public String test() {
//		return "test";
//	}
	 
	@GET
    @Path("calculations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCalculations() {
        try {
            TypedQuery<Calculation> query = entityManager.createQuery("SELECT c FROM Calculation c", Calculation.class);
            List<Calculation> calculations = query.getResultList();
            return Response.status(Response.Status.OK).entity(calculations).build();
        } catch (Exception e) {
            e.printStackTrace(); 
            // Return error response with status 500 and error message
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
	
}
