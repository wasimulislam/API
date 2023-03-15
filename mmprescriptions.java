package zaberp.zab;
import java.sql.*;
import java.util.Base64;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class mmprescriptions extends HttpServlet{

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
    
    String sql = "";
    String sql1 = "";
    String sql2 = "";
    String sql3 = "";
    String sql4 = "";
    String sql5 = "";
    String sql6 = "";
    String sql7 = "";
    String sql8 = ""; 
    String sql9 = "";
    String sql10 = "";
    String sql11 = "";
    String sql12 = "";
    String result="false";
    
    @SuppressWarnings("unused")
	java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
   // String AppointmentID = requestObj.getParameter("AppointmentID");
    int id = zabTools.getInt(requestObj.getParameter("id"));
    String patientID = requestObj.getParameter("patientID");
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
    
    sql = "select ztime,zutime,zauserid,zuuserid,zid,xcase,xgenericdesc,xinst,xtdate,xdosage,xdose,xfrequency,xtakingtime,xdurationday,xqty,xroute,\r\n"
    		+ "            xduration1,xrem,xpatient,xname,xage,xsex,doctorname,designationname,departmentname,xregino,xprofdegree from mmprescriptionviewrpt\r\n"
    		+ "            where zid='" + id + "' and xpatient ='" + patientID + "'";
    //System.err.println(sql);
    
    sql1 = "select zid,xcase,xdoctor,xpatient,xpastgh,xsurgicalhistory,xbreastfeeding,ximmunization,xfinaldiagnosis,xnote5,\r\n"
    		+ "         (select REPLACE(xchiefcomplain, ',', '<br>'))as xchiefcomplain,xfamilyhistory,xsocialhistory,(select REPLACE(xdrughistory, ',', '<br>'))as\r\n"
    		+ "			xdrughistory,xurinetwo,xurinethree,xmicrgrowth,xobhistory,xmeandobhistory,xcontraception,(select REPLACE(xcomorbidities, ',', '<br>')) as xcomorbidities,\r\n"
    		+ "			(select REPLACE(xdrugall, ',', '<br>')) as xdrugall,xfoodall,xphychostatus,xsuspecteddisease,xrisk,xpast,xoutlook,xsysenquiry,xedspecialist,xprovdiagnosis,xplanecare,\r\n"
    		+ "			xphysicianassist,xchest,xgeneral,xpae,xps,xpve,xlungs,xepinspection,xeppalpation,xeppercussion,xepauscultation,xfetalheart,xabdomen,xdiagnosis,xadvice,\r\n"
    		+ "			xfollowupadvice,xpatadvice,xadadvice,xfamilyother,xfeel,xmove,xinvestigationhistroy,xcompcheck,xmorbcheck,xsurgcheck,xfamcheck,xriskcheck,\r\n"
    		+ "			xoncology,xfollwadviceother,xgcs,xvision,xhearing,xspeach,xgate,xstance,xcerebellar,xsensation,xmusclepower,xsclera,xcorhea,xeyeac,xpupil,xiris,xlens,xiop,\r\n"
    		+ "			xfurdi,xrefdoctordesc,designationname,xprofdegree,departmentname,xreflexes,xplantar,xholtman,xexamperipheral,xpr,xcardiovascular,\r\n"
    		+ "			xhead,xface,xeyeball,xorbit,xeyelibs,xlacrimalsac,xlacrimalgland,xconjunctive,xmedicheck,xobscheck,xmenscheck,xcontcheck,xinvescheck,xallergycheck,xother,\r\n"
    		+ "			xdrughistoryothers from mmappassview where zid='" + id + "' and  xpatient ='" + patientID + "'";
    //System.err.println(sql1);
    
    sql2 = "select xbp,xrespiration,xtemperature,xspo2,xheightvit,xweightvit,xbmigyn,xbsa,xpulserate,xanemia,xavpu,xofc,xnote,xpalor,xodema,xjaundice from mmvitalphview where zid='" + id + "' and  xpatient='" + patientID + "'";
    //System.err.println(sql2);
    
    sql3 = "select xrow,xitem,xrem,xdesc from mmlabphview where zid='" + id + "' and  xpatient='" + patientID + "'";
    //System.err.println(sql3);
    
    sql4 = "select xrow,xlong,xcode,xnote from mmonexam where zid='" + id + "' and  xpatient='" + patientID + "'";
    //System.err.println(sql4);

    sql5 = "select xstatus,xstatuspharma,xapptype from mmappointment where zid='" + id + "' and xpatient='" + patientID + "'";
    //System.err.println(sql5);
    
    sql6 = "select xrow,xcase,xcag,xptcadt1,xptcadt2,xptcadt3,xcabgdt,xcabg,xsrgothers,xptca1,xptca2,xptca3 from mmcardsurghistoryview where zid='" + id + "' and xpatient='" + patientID + "'";
    //System.err.println(sql6);

    sql7 = "select xrow,xlong,xduration1,xdurationday,xnote from mmcardriskhistoryview where zid='" + id + "' and xpatient='" + patientID + "'";
    //System.err.println(sql7);

    sql8 = "select xchest,xchestprbcheck,xheartrate,xmorbcheck,xbloodpressure,xbpcheck,xpacemakerother,xpacemaker,\r\n"
    		+ "        					xheartprob,xheartvalve,xheartmedic,xrheumaticfever,xartificialvalve,xbloodproblem,xintestinalproblem,\r\n"
    		+ "        					xulcersprblm,xkidneyprblm,xcancerprblm,xartvlvcheck,xheartmedicheck,xbldprbcheck,\r\n"
    		+ "        					xintestinalprbcheck,xulcerprbcheck,xkdnyprbcheck,xcancerprbcheck,xlocalanestheticcheck,xpencilincheck,\r\n"
    		+ "        					xsulfadrugcheck,xaspirincheck,xrubbercheck,xdiabetescheck,xsmokingcheck,xdrinkingcheck,xlivercheck,xneurocheck,\r\n"
    		+ "        					xdrugcheck,xantibioticcheck,xcoumadincheck,xhbpmcheck,xaspcheck,xsteroidcheck,xhormoncheck,xpregnantcheck,xnursingcheck,xrheumaticradio,\r\n"
    		+ "        					xheartmedicradio,xheartrateradio,xartificialradio,xdiabetic,xdrinkingalchol,xsmokingyes,xhepatitesjanliv,xneurological,xhistryofalcohol,xduedate,\r\n"
    		+ "        					xliver,xhepatitesjancheck from mmdentalview where zid='" + id + "' and xpatient='" + patientID + "'";
    //System.err.println(sql8);
    
    sql9 = "select xrow,xlprofiledt,xlprofiletc,xlprofileldl,xlprofilehdl,xlprofiletg,xresultval,xlprofileunit,xechola,xecholvidd,xecholvids,\r\n"
    		+ "        		         xechoef,xecholvef,xechorwma,xechounit,xchodt,xechopasp,xcreatinine,xcreatininedt,xcreatinineunit,xett,xett1,xett2,xettdate,xlprofilealt,xast,\r\n"
    		+ "        		         xtc,xhb,xplatelate,xhstroponin,xhstroponindtunit,xhstroponindt,xtroponin,xtroponindt,xtroponindtunit,xna,xk,xcl,xca,xmg,xhco3,xserumelecdt,\r\n"
    		+ "        		         xunacr,xcxrpa,xecg,xcbc,xfbs,xhb1ac,xesr,xnt,xpbf,xdate,xother,xvitaminD3,xtsh,xlprofilech,xlprofilecpk from mmcardhistoryview where zid='" + id + "' and xpatient='" + patientID + "'";
    //System.err.println(sql9);


    //System.err.println(sql12);

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
            obj.put("ztime", rs.getString("ztime"));
            obj.put("zutime", rs.getString("zutime"));
            obj.put("zauserid", rs.getString("zauserid"));
            obj.put("zuuserid", rs.getString("zuuserid"));
            obj.put("zid", rs.getString("zid"));
            obj.put("xcase", rs.getString("xcase"));
            obj.put("xgenericdesc", rs.getString("xgenericdesc"));
            obj.put("xinst", rs.getString("xinst"));
            obj.put("xtdate", rs.getString("xtdate"));
            obj.put("xdosage", rs.getString("xdosage"));
            obj.put("xdose", rs.getString("xdose"));
            obj.put("xfrequency", rs.getString("xfrequency"));
            obj.put("xtakingtime", rs.getString("xtakingtime"));
            obj.put("xdurationday", rs.getString("xdurationday"));
            obj.put("xqty", rs.getString("xqty"));
            obj.put("xroute", rs.getString("xroute"));
            obj.put("xduration1", rs.getString("xduration1"));
            obj.put("xrem", rs.getString("xrem"));
            obj.put("xpatient", rs.getString("xpatient"));
            obj.put("xname", rs.getString("xname"));
            obj.put("xage", rs.getString("xage"));
            obj.put("xsex", rs.getString("xsex"));
            obj.put("doctorname", rs.getString("doctorname"));
            obj.put("designationname", rs.getString("designationname"));
            obj.put("departmentname", rs.getString("departmentname"));
            obj.put("xregino", rs.getString("xregino"));
            obj.put("xprofdegree", rs.getString("xprofdegree"));
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