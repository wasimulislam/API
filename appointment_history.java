package zaberp.zab;
import java.sql.*;
import java.io.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;


@SuppressWarnings("serial")
public class appointment_history extends HttpServlet{

final String thisName = "";
  JDBCpool pool = null;
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
    zabInfo zabinfo = new zabInfo();
    responseObj.setHeader("Cache-Control", "no-cache");
    PrintWriter writer = responseObj.getWriter();
    String xpatient = requestObj.getParameter("xpatient");
    String xfdate = requestObj.getParameter("xfdate");
    String xtdate = requestObj.getParameter("xtdate");
    Connection con = null;
    Statement stmt = null;
    String s ="";
    String sql="";
    String result="false";
   
    // checking user authentication
	
    sql = "select zid from zbusiness where zid=400010";
//    System.out.println(sql);
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
    JSONObject obj1 = new JSONObject();
    JSONArray list = new JSONArray();
    
    if(xfdate==null) {
    	 sql="select top 5 xcase,xpatient,isnull(xappslottype,'')as xappslottype ,isnull(xdeptname,'')as xdeptname,"
    	    		+ "xdoctor from mmappointment where xpatient='"+xpatient+"'";
    	    System.out.println(sql);
    }
    else {
    	sql="select xcase,xpatient,isnull(xappslottype,'')as xappslottype ,isnull(xdeptname,'')as xdeptname,"
        		+ "xdoctor from mmappointment where xpatient='"+xpatient+"' and xdate between '"+xfdate+"' and '"+xtdate+"'";
        System.out.println(sql);
    }
    
    
    
    
	rs = null;
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
            obj.put("Patient No", rs.getString("xpatient"));
            obj.put("Slot Type", rs.getString("xappslottype"));
            obj.put("Department", rs.getString("xdeptname"));
            obj.put("Doctor ID", rs.getString("xdoctor"));
            list.add(obj);
//            System.out.println(obj);
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
    
   // obj1.put("productlist", list);
    if(result.equals("true"))
    	writer.println(list.toJSONString());
    else
    	writer.println("false");
	writer.close();
    }
}