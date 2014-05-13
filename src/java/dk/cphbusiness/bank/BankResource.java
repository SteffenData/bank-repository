package dk.cphbusiness.bank;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@Path("bank")
public class BankResource {
  
  
  @EJB
  private BankManager manager;
  
  @Context
  private UriInfo context;

  public BankResource() {
    }

  @GET
  @Produces({"application/json", "application/xml"})
  public Collection<Bank> list() {
    return manager.list();
    }

  @GET
  @Path("{reg}")
  @Produces({"application/json", "application/xml"})
  public Bank find(@PathParam("reg") String reg) {
    return manager.find(reg);
    }

  @POST
  @Consumes({"application/json", "application/xml"})
  public void save(Bank bank) {
    manager.save(bank);
    }
  
  @DELETE
  @Path("{reg}")
  public void drop(@PathParam("reg") String reg) {
    manager.drop(reg);
    }

  }
