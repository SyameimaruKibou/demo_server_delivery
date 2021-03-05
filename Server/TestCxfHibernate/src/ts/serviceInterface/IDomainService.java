package ts.serviceInterface;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import ts.model.ArrayOfString;
import ts.model.ExpressHistory;
import ts.model.ExpressSheet;
import ts.model.TemporaryExpress;
import ts.model.TransPackage;

@Path("/Domain")	
public interface IDomainService {
	
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressList/{Property}/{Restrictions}/{Value}") 
	public List<ExpressSheet> getExpressList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressListInPackage/PackageId/{PackageId}") 
	public List<ExpressSheet> getExpressListInPackage(@PathParam("PackageId")String packageId);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressSheet/{id}") 
	public Response getExpressSheet(@PathParam("id")String id);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressSheetHistory/{id}") 
	public List<ExpressHistory> getExpressHistory(@PathParam("id")String eps_id);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newTempExpress")
    public Response newTempExpress(TemporaryExpress teps);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deleteTempExpress/{SN}")
    public Response deleteTempExpress(@PathParam("SN")int SN);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getUnhandleEpsList/{tel}")
    public List<TemporaryExpress> getUnhandleEpsList(@PathParam("tel")String tel);
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newExpress/nodeid/{nodeid}/userid/{userid}")
    public Response newExpress(ExpressSheet obj,@PathParam("nodeid")String nodeid,@PathParam("userid")int userid);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/pack/sourcenode/{sourcenode}/targetnode/{targetnode}/userid/{userid}")
    public Response pack(ArrayOfString array,@PathParam("sourcenode")String nodeid,@PathParam("targetnode")String targetnode,@PathParam("userid")int userid);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/unpack/packageid/{packageid}/nodeid/{nodeid}/userid/{userid}")
    public Response unpack(@PathParam("packageid")String packageid,@PathParam("nodeid")String nodeid,@PathParam("userid")int userid);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/loadTranspack/packageid/{packageid}/userid/{userid}")
    public Response loadTranspack(@PathParam("packageid")String packageid,@PathParam("userid")int userid);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/unloadTranspack/packageid/{packageid}/userid/{userid}")
    public Response unloadTranspack(@PathParam("packageid")String packageid,@PathParam("userid")int userid);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deliver/expressid/{expressid}/nodeid/{nodeid}/userid/{userid}")
    public Response deliver(@PathParam("expressid")String expressid,@PathParam("nodeid")String nodeid,@PathParam("userid")int userid);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/assign/expressid/{expressid}/userid/{userid}")
    public Response assign(@PathParam("expressid")String expressid,@PathParam("userid")int userid);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackageList/{Property}/{Restrictions}/{Value}") 
	public List<TransPackage> getTransPackageList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackage/{id}") 
	public Response getTransPackage(@PathParam("id")String id);
    
}
