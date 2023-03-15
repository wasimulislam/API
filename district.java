package zaberp.zab;
import java.sql.*;
import java.io.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;


@SuppressWarnings("serial")
public class district extends HttpServlet{

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

    responseObj.setHeader("Cache-Control", "no-cache");
    PrintWriter writer = responseObj.getWriter();
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
    sql="select xcode from xcodes where xtype='DISTRICT'"; 
    
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
            obj.put("xcode", rs.getString("xcode"));
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