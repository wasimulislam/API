package zaberp.zab;
import java.sql.*;
import java.util.Base64;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class appointmentinfo extends HttpServlet{ 

final String thisName = "";
  JDBCpool pool = null;
  String result=null;
  Statement stmtt = null;
  Connection cont = null;
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
//    zabInfo.LoadGlobal();
    pool = zabInfo.GetPool(this, thisName);
  }
  
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    doPost(req, res);
	  }

    protected void doPost(HttpServletRequest requestObj, HttpServletResponse responseObj)
                   throws IOException
    {
        //set the content type
    responseObj.setContentType("text/html");
    responseObj.setHeader("Cache-Control", "no-cache");
    
    //get the PrintWriter object to write the html page
    PrintWriter writer = responseObj.getWriter();
    Connection con = null;
    Statement stmt = null;
    String sql="";
    String sql1="";
    String result="false";
    @SuppressWarnings("unused")
	java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
   // String AppointmentID = requestObj.getParameter("AppointmentID");
    //String PatientID = requestObj.getParameter("PID");
    //String Paymentmode = requestObj.getParameter("Paymentmode");
    //String TransectionID = requestObj.getParameter("TransectionID");
    JSONArray list = new JSONArray();
//    String authHeader = requestObj.getHeader("authorization");
//	String encodedAuth = authHeader.substring(authHeader.indexOf(' ')+1);
//	String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
//	String username = decodedAuth.substring(0, decodedAuth.indexOf(':'));
//	String password = decodedAuth.substring(decodedAuth.indexOf(':')+1);
	sql = "select zid from zbusiness";
	ResultSet rs = null;
    try {
      con = pool.getConnection();
    } catch (Exception esql) {
  	    result = "false";
    }
    try {
        stmt = con.createStatement();
        
        rs = stmt.executeQuery(sql);
        while (rs.next()){
        	result = "true";
        }
    }catch(SQLException esql){
    	result = "false";
    }finally{

    	try {
            if (stmt != null) stmt.close();
        }catch(SQLException esql){
        	result = "false";
        }
    }

    if(result.equals("false")){
    	writer.println("Authentication Failed");
    	return ;
    }
    result = "false";
    
    sql = "select xcase,xrow,xdate,xapptime,xpatient,xname,xdoctor,xorg,xyearperdate,xmobile,xapptype,xstatus,xtype,xage,xdeptname,departmentname,xposition,xappslottype from mmappointmentview  where  xstatus in ('Reported','Attended') and xdate=cast(GETDATE() as Date)";
//    System.out.println(sql);

    try {
      con = pool.getConnection();
    } catch (Exception esql) {
  	    result = "false";
    }
 
    try {
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        
        while (rs.next()){
        	result = "true";
            JSONObject obj = new JSONObject();
            obj.put("Case No", rs.getString("xcase"));
            obj.put("Row", rs.getString("xrow"));
            obj.put("Date", rs.getString("xdate"));
            obj.put("Appointment Time", rs.getString("xapptime"));
            obj.put("Patient ID", rs.getString("xpatient"));
            obj.put("Patient Name", rs.getString("xname"));
            obj.put("Doctor ID", rs.getString("xdoctor"));
            obj.put("Dooctor Name", rs.getString("xorg"));
            obj.put("Mobile", rs.getString("xmobile"));
            obj.put("Yearperdate", rs.getString("xyearperdate"));
            obj.put("Appointment Type", rs.getString("xapptype"));
            obj.put("Appointment Status", rs.getString("xstatus"));
            obj.put("Patient Type", rs.getString("xtype"));
            obj.put("Patient Age", rs.getString("xage"));
            obj.put("Department ID", rs.getString("xdeptname"));
            obj.put("Department Name", rs.getString("departmentname"));
            obj.put("Position", rs.getString("xposition"));
            obj.put("Slot Type", rs.getString("xappslottype"));
            //obj.put("Status Pharmacy", rs.getString("xstatuspharma")); 
            
            
           
            
            list.add(obj);
        }
        int k = stmt.executeUpdate(sql1);

    }catch(SQLException esql){
  	    result = "false";
    }finally{

    	try {
          if (stmt != null) stmt.close();
        }catch(SQLException esql){
      	    result = "false";
        }
    }
//	System.out.println(result);
	if(result.equals("true")){
		writer.println(list.toJSONString());
    }else{
    	responseObj.sendError(404, "404 Not found" );
    }
	
    pool.releaseConnection(con);
	writer.close();
    }
}