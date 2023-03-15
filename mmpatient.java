package zaberp.zab;
import java.sql.*;
import java.io.*; 
import javax.servlet.http.*; 
import javax.servlet.*;
 
 
@SuppressWarnings("serial")
public class mmpatient extends HttpServlet{
  final String thisName = ""; 
  JDBCpool pool = null;
  public void init(ServletConfig config) throws ServletException {
    super.init(config); 
//    zabInfo.LoadGlobal();
    pool = zabInfo.GetPool(this, thisName);
  }
     protected void doGet(HttpServletRequest requestObj, HttpServletResponse responseObj)
                   throws IOException 
    {
    	 responseObj.setContentType("text/html");
    	 responseObj.setHeader("Cache-Control", "no-cache");
    	 zabInfo zabinfo = new zabInfo();
    	 PrintWriter writer = responseObj.getWriter();
    	 Connection con = null;
    	 Statement stmt = null;
    	 Statement stmt1 = null;
    	 String s ="";
    	 String sql="";
    	 String sql1="";
		/**** GETTING PARAMETER FROM URL/SCREEN ********************/
		sql = "select cast(ztime as date)as date\r\n"
				+ "FROM mmpatienttemp  \r\n"
				+ "WHERE cast(ztime as date) >= DATEADD(day,-7, GETDATE())\r\n"
				+ "group by cast(ztime as date)";
		System.out.println(sql);
//		System.err.println(sql);
		sql1 = "select count(xpatient) as xpatient \r\n"
				+ "FROM mmpatienttemp  \r\n"
				+ "WHERE cast(ztime as date) >= DATEADD(day,-7, GETDATE())\r\n"
				+ "group by cast(ztime as date)";
		System.out.println(sql1);
		
		ResultSet rs = null;
		ResultSet rs1 = null;
    try {
        con = pool.getConnection();
      } catch (Exception esql) {
      	zabinfo.log("zErr0002msg",esql.getMessage(),"Return Code = 0");
      }

    try { 

        
        s += "<script src=\"chart.js\"></script>";
        s+="<div>"
        		+ "<canvas id=\"Patient\"></canvas>"
        + "</div>";
        s+="<script>"
		+ "  new Chart(document.getElementById(\"Patient\"), {\r\n"
		+ "      type: 'bar',\r\n"
		+ "      data: {\r\n"
		+ "        labels: [";
        
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()){
        	String date = rs.getString("date");
	    	s+="\""+date+"\",";
		}
		s+= "],\r\n"
		+ "        datasets: [\r\n"
		+ "          {\r\n"
		+ "            label: \"Patient\",\r\n"
		+ "            backgroundColor: ['rgb(34, 87, 122)',\r\n"
		+ "              'rgb(56, 163, 165)',\r\n"
		+ "              'rgb(87, 204, 153)',\r\n"
		+ "              'rgb(128, 237, 153)'],\r\n"
		+ "            borderColor: \"#ffff\",\r\n"
		+ "            data: [";
		stmt1 = con.createStatement();
        rs1 = stmt1.executeQuery(sql1);
        while (rs1.next()){
	    	String Patient = rs1.getString("xpatient");
	    	
	    	s+=""+Patient+",";
	    	
		}
		s+= "]\r\n"
		+ "          }]\r\n"
		+ "      },\r\n"
		+ "    });";
        s+="</script>";
        
        
}catch(SQLException esql){
      	zabinfo.log("zErr0002msg",esql.getMessage(),"Return Code = 0");
      }finally{
        try {
          if (stmt != null) stmt.close();
        }catch(SQLException esql){
        } 
  //     System.err.println(s);
        writer.println(s);
      }
    pool.releaseConnection(con);
      writer.close();
  }
}