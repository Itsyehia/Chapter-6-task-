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
@Path("/calc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class calculationService {

	

	@PersistenceContext(unitName="CalcPU")
    private  EntityManager entityManager;
//
//	
	
	
	
	@POST
	@Path("cal")
	
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
	    public List<calculation> getAllCalculations() {
	        try {
	        	
	            // Create a JPQL query to retrieve all calculations
	            TypedQuery<calculation> query = entityManager.createQuery("SELECT c FROM calculation c", calculation.class);

	            // Execute the query and return the list of calculations
	            List<calculation> calculations = query.getResultList();
	            return calculations;
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Return null or handle the error appropriately
	            return null;
	        }
	        }
	
}
