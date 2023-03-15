package zaberp.zab;
import java.sql.*;
import java.util.Base64;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class presc_data extends HttpServlet{

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
    Statement stmt1 = null;
    Statement stmt2 = null;
    Statement stmt3 = null;
    Statement stmt4 = null;
    Statement stmt5 = null;
    Statement stmt6 = null;
    Statement stmt7 = null;
    Statement stmt8 = null;
    Statement stmt9 = null;
    
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
    String result="false";
    
    @SuppressWarnings("unused")
	java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
   // String AppointmentID = requestObj.getParameter("AppointmentID");
    int id = zabTools.getInt(requestObj.getParameter("id"));
    String patientID = requestObj.getParameter("patientID");
    JSONArray list = new JSONArray();
//    String authHeader = requestObj.getHeader("authorization");
//	String encodedAuth = authHeader.substring(authHeader.indexOf(' ')+1);
//	String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
//	String username = decodedAuth.substring(0, decodedAuth.indexOf(':'));
//	String password = decodedAuth.substring(decodedAuth.indexOf(':')+1);
    
    sql = "select zid from zbusiness where zid=400010";
	
	ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    ResultSet rs4 = null;
    ResultSet rs5 = null;
    ResultSet rs6 = null;
    ResultSet rs7 = null;
    ResultSet rs8 = null;
    ResultSet rs9 = null;
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
    
    sql1 = "select xtdate,ztime,zid,xcase,xgenericdesc,xinst,xdosage,xdose,xfrequency,xtakingtime,xdurationday,xqty,xroute,\r\n"
    		+ "         xduration1,xrem,xpatient,xname,xage,xsex,doctorname,designationname,departmentname,xregino,xprofdegree from mmprescriptionviewrpt\r\n"
    		+ "         where zid='" + id + "' and  xpatient ='" + patientID + "'";
    System.err.println(sql1);
    
    sql2 = "select zid,xdate,xcase,xdoctor,xpatient,xpastgh,xsurgicalhistory,xbreastfeeding,ximmunization,xfinaldiagnosis,xnote5,\r\n"
    		+ "         (select REPLACE(xchiefcomplain, ',', '<br>'))as xchiefcomplain,xfamilyhistory,xsocialhistory,(select REPLACE(xdrughistory, ',', '<br>'))as\r\n"
    		+ "			xdrughistory,xurinetwo,xurinethree,xmicrgrowth,xobhistory,xmeandobhistory,xcontraception,(select REPLACE(xcomorbidities, ',', '<br>')) as xcomorbidities,\r\n"
    		+ "			(select REPLACE(xdrugall, ',', '<br>')) as xdrugall,xfoodall,xphychostatus,xsuspecteddisease,xrisk,xpast,xoutlook,xsysenquiry,xedspecialist,xprovdiagnosis,xplanecare,\r\n"
    		+ "			xphysicianassist,xchest,xgeneral,xpae,xps,xpve,xlungs,xepinspection,xeppalpation,xeppercussion,xepauscultation,xfetalheart,xabdomen,xdiagnosis,xadvice,\r\n"
    		+ "			xfollowupadvice,xpatadvice,xadadvice,xfamilyother,xfeel,xmove,xinvestigationhistroy,xcompcheck,xmorbcheck,xsurgcheck,xfamcheck,xriskcheck,\r\n"
    		+ "			xoncology,xfollwadviceother,xgcs,xvision,xhearing,xspeach,xgate,xstance,xcerebellar,xsensation,xmusclepower,xsclera,xcorhea,xeyeac,xpupil,xiris,xlens,xiop,\r\n"
    		+ "			xfurdi,xrefdoctordesc,designationname,xprofdegree,departmentname,xreflexes,xplantar,xholtman,xexamperipheral,xpr,xcardiovascular,\r\n"
    		+ "			xhead,xface,xeyeball,xorbit,xeyelibs,xlacrimalsac,xlacrimalgland,xconjunctive,xmedicheck,xobscheck,xmenscheck,xcontcheck,xinvescheck,xallergycheck,xother,\r\n"
    		+ "			xdrughistoryothers from mmappassview where zid='" + id + "' and  xpatient ='" + patientID + "' ";
    System.err.println(sql2); 
//    
    sql3 = "select xdate,xbp,xrespiration,xtemperature,xspo2,xheightvit,xweightvit,xbmigyn,xbsa,xpulserate,xanemia,xavpu,xofc,xnote,xpalor,xodema,xjaundice from mmvitalphview where zid='" + id + "' and  xpatient='" + patientID + "'";
    System.err.println(sql3);
//    
    sql4 = "select xdate,xrow,xitem,xrem,xdesc from mmlabphview where zid='" + id + "' and  xpatient='" + patientID + "'";
    System.err.println(sql4);
//    
    sql5 = "select xdate,xrow,xlong,xcode,xnote from mmonexamview where zid='" + id + "' and  xpatient='" + patientID + "'";
    System.err.println(sql5);
//
    sql6 = "select xdate,xstatus,xstatuspharma,xapptype from mmappointment where zid='" + id + "' and xpatient='" + patientID + "'";
    System.err.println(sql6);
//    
    sql7 = "select xdate,xrow,xcase,xcag,xptcadt1,xptcadt2,xptcadt3,xcabgdt,xcabg,xsrgothers,xptca1,xptca2,xptca3 from mmcardsurghistoryview where zid='" + id + "' and xpatient='" + patientID + "'";
    System.err.println(sql7);
//
    sql8 = "select xdate,xrow,xlong,xduration1,xdurationday,xnote from mmcardriskhistoryview where zid='" + id + "' and xpatient='" + patientID + "'";
    System.err.println(sql8);
//
    sql9 = "select xdate,xchest,xchestprbcheck,xheartrate,xmorbcheck,xbloodpressure,xbpcheck,xpacemakerother,xpacemaker,\r\n"
    		+ "        	xheartprob,xheartvalve,xheartmedic,xrheumaticfever,xartificialvalve,xbloodproblem,xintestinalproblem,\r\n"
    		+ "        	xulcersprblm,xkidneyprblm,xcancerprblm,xartvlvcheck,xheartmedicheck,xbldprbcheck,\r\n"
    		+ "         xintestinalprbcheck,xulcerprbcheck,xkdnyprbcheck,xcancerprbcheck,xlocalanestheticcheck,xpencilincheck,\r\n"
    		+ "        	xsulfadrugcheck,xaspirincheck,xrubbercheck,xdiabetescheck,xsmokingcheck,xdrinkingcheck,xlivercheck,xneurocheck,\r\n"
    		+ "        	xdrugcheck,xantibioticcheck,xcoumadincheck,xhbpmcheck,xaspcheck,xsteroidcheck,xhormoncheck,xpregnantcheck,xnursingcheck,xrheumaticradio,\r\n"
    		+ "        	xheartmedicradio,xheartrateradio,xartificialradio,xdiabetic,xdrinkingalchol,xsmokingyes,xhepatitesjanliv,xneurological,xhistryofalcohol,xduedate,\r\n"
    		+ "        	xliver,xhepatitesjancheck from mmdentalview where zid='" + id + "' and xpatient='" + patientID + "'";
    System.err.println(sql9);
//    
    sql10 = "select xdate,xrow,xlprofiledt,xlprofiletc,xlprofileldl,xlprofilehdl,xlprofiletg,xresultval,xlprofileunit,xechola,xecholvidd,xecholvids,\r\n"
    		+ "        	xechoef,xecholvef,xechorwma,xechounit,xchodt,xechopasp,xcreatinine,xcreatininedt,xcreatinineunit,xett,xett1,xett2,xettdate,xlprofilealt,xast,\r\n"
    		+ "        	xtc,xhb,xplatelate,xhstroponin,xhstroponindtunit,xhstroponindt,xtroponin,xtroponindt,xtroponindtunit,xna,xk,xcl,xca,xmg,xhco3,xserumelecdt,\r\n"
    		+ "        	xunacr,xcxrpa,xecg,xcbc,xfbs,xhb1ac,xesr,xnt,xpbf,xother,xvitaminD3,xtsh,xlprofilech,xlprofilecpk from mmcardhistoryview where zid='" + id + "' and xpatient='" + patientID + "'";
    System.err.println(sql10);
//

    //System.err.println(sql12);

    try {
      con = pool.getConnection();
    } catch (Exception esql) {
  	    result = "false";
    }
 
    try {
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql1);
        
        while (rs.next()){
        	result = "true";
            JSONObject obj = new JSONObject();

            obj.put("xtdate", rs.getString("xtdate"));
            obj.put("ztime", rs.getString("ztime"));
            obj.put("zid", rs.getString("zid"));
            obj.put("xcase", rs.getString("xcase"));
            obj.put("xgenericdesc", rs.getString("xgenericdesc"));
            obj.put("xinst", rs.getString("xinst"));
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
        
        stmt1 = con.createStatement();
        rs1 = stmt1.executeQuery(sql2);
        
        while (rs1.next()){
        	result = "true"; 
            JSONObject obj = new JSONObject();
            obj.put("zid", rs1.getString("zid"));
            obj.put("zxdate", rs1.getString("xdate"));
            obj.put("xcase", rs1.getString("xcase"));
            obj.put("xdoctor", rs1.getString("xdoctor"));
            obj.put("xpatient", rs1.getString("xpatient"));
            obj.put("xpastgh", rs1.getString("xpastgh"));
            obj.put("xsurgicalhistory", rs1.getString("xsurgicalhistory"));
            obj.put("xbreastfeeding", rs1.getString("xbreastfeeding"));
            obj.put("ximmunization", rs1.getString("ximmunization"));
            obj.put("xfinaldiagnosis", rs1.getString("xfinaldiagnosis"));
            obj.put("xnote5", rs1.getString("xnote5"));
            obj.put("xchiefcomplain", rs1.getString("xchiefcomplain"));
            obj.put("xfamilyhistory", rs1.getString("xfamilyhistory"));
            obj.put("xsocialhistory", rs1.getString("xsocialhistory"));
            obj.put("xdrughistory", rs1.getString("xdrughistory"));
            obj.put("xurinetwo", rs1.getString("xurinetwo"));
            obj.put("xurinethree", rs1.getString("xurinethree"));
            obj.put("xmicrgrowth", rs1.getString("xmicrgrowth"));
            obj.put("xobhistory", rs1.getString("xobhistory"));
            obj.put("xmeandobhistory", rs1.getString("xmeandobhistory"));
            obj.put("xcontraception", rs1.getString("xcontraception"));
            obj.put("xcomorbidities", rs1.getString("xcomorbidities")); 
            obj.put("xdrugall", rs1.getString("xdrugall"));
            obj.put("xfoodall", rs1.getString("xfoodall"));
            obj.put("xphychostatus", rs1.getString("xphychostatus"));
            obj.put("xsuspecteddisease", rs1.getString("xsuspecteddisease"));
            obj.put("xrisk", rs1.getString("xrisk"));
            obj.put("xpast", rs1.getString("xpast"));
            obj.put("xoutlook", rs1.getString("xoutlook"));
            obj.put("xsysenquiry", rs1.getString("xsysenquiry"));
            obj.put("xedspecialist", rs1.getString("xedspecialist"));
            obj.put("xprovdiagnosis", rs1.getString("xprovdiagnosis"));
            obj.put("xplanecare", rs1.getString("xplanecare"));  
            obj.put("xphysicianassist", rs1.getString("xphysicianassist"));
            obj.put("xchest", rs1.getString("xchest"));
            obj.put("xgeneral", rs1.getString("xgeneral"));
            obj.put("xpae", rs1.getString("xpae"));
            obj.put("xps", rs1.getString("xps"));
            obj.put("xpve", rs1.getString("xpve"));
            obj.put("xlungs", rs1.getString("xlungs"));
            obj.put("xepinspection", rs1.getString("xepinspection"));
            obj.put("xeppalpation", rs1.getString("xeppalpation"));
            obj.put("xeppercussion", rs1.getString("xeppercussion"));
            obj.put("xepauscultation", rs1.getString("xepauscultation"));
            obj.put("xfetalheart", rs1.getString("xfetalheart"));
            obj.put("xabdomen", rs1.getString("xabdomen"));
            obj.put("xdiagnosis", rs1.getString("xdiagnosis")); 
            obj.put("xadvice", rs1.getString("xadvice"));
            obj.put("xfollowupadvice", rs1.getString("xfollowupadvice"));
            obj.put("xpatadvice", rs1.getString("xpatadvice"));
            obj.put("xadadvice", rs1.getString("xadadvice"));
            obj.put("xfamilyother", rs1.getString("xfamilyother"));
            obj.put("xfeel", rs1.getString("xfeel"));
            obj.put("xmove", rs1.getString("xmove"));
            obj.put("xinvestigationhistroy", rs1.getString("xinvestigationhistroy"));
            obj.put("xcompcheck", rs1.getString("xcompcheck"));
            obj.put("xmorbcheck", rs1.getString("xmorbcheck"));
            obj.put("xsurgcheck", rs1.getString("xsurgcheck"));
            obj.put("xfamcheck", rs1.getString("xfamcheck"));
            obj.put("xriskcheck", rs1.getString("xriskcheck")); 
            obj.put("xoncology", rs1.getString("xoncology"));
            obj.put("xfollwadviceother", rs1.getString("xfollwadviceother"));
            obj.put("xgcs", rs1.getString("xgcs"));
            obj.put("xvision", rs1.getString("xvision"));
            obj.put("xhearing", rs1.getString("xhearing"));
            obj.put("xspeach", rs1.getString("xspeach"));
            obj.put("xgate", rs1.getString("xgate"));
            obj.put("xstance", rs1.getString("xstance"));
            obj.put("xcerebellar", rs1.getString("xcerebellar"));
            obj.put("xsensation", rs1.getString("xsensation"));
            obj.put("xmusclepower", rs1.getString("xmusclepower"));
            obj.put("xsclera", rs1.getString("xsclera"));
            obj.put("xcorhea", rs1.getString("xcorhea"));
            obj.put("xeyeac", rs1.getString("xeyeac"));
            obj.put("xpupil", rs1.getString("xpupil"));
            obj.put("xiris", rs1.getString("xiris"));
            obj.put("xlens", rs1.getString("xlens"));
            obj.put("xiop", rs1.getString("xiop"));  
            obj.put("xfurdi", rs1.getString("xfurdi"));
            obj.put("xrefdoctordesc", rs1.getString("xrefdoctordesc"));
            obj.put("designationname", rs1.getString("designationname"));
            obj.put("xprofdegree", rs1.getString("xprofdegree"));
            obj.put("departmentname", rs1.getString("departmentname"));
            obj.put("xreflexes", rs1.getString("xreflexes"));
            obj.put("xplantar", rs1.getString("xplantar"));
            obj.put("xholtman", rs1.getString("xholtman"));
            obj.put("xexamperipheral", rs1.getString("xexamperipheral"));
            obj.put("xpr", rs1.getString("xpr"));
            obj.put("xcardiovascular", rs1.getString("xcardiovascular"));  
            obj.put("xhead", rs1.getString("xhead"));
            obj.put("xface", rs1.getString("xface"));
            obj.put("xeyeball", rs1.getString("xeyeball"));
            obj.put("xorbit", rs1.getString("xorbit"));
            obj.put("xeyelibs", rs1.getString("xeyelibs"));
            obj.put("xlacrimalsac", rs1.getString("xlacrimalsac"));
            obj.put("xlacrimalgland", rs1.getString("xlacrimalgland"));
            obj.put("xconjunctive", rs1.getString("xconjunctive"));
            obj.put("xmedicheck", rs1.getString("xmedicheck"));
            obj.put("xobscheck", rs1.getString("xobscheck"));
            obj.put("xmenscheck", rs1.getString("xmenscheck"));
            obj.put("xcontcheck", rs1.getString("xcontcheck"));
            obj.put("xinvescheck", rs1.getString("xinvescheck"));
            obj.put("xallergycheck", rs1.getString("xallergycheck"));
            obj.put("xother", rs1.getString("xother"));
            obj.put("Row", rs1.getString("xdrughistoryothers"));
            list.add(obj);
        }
        
        stmt2 = con.createStatement();
        rs2 = stmt2.executeQuery(sql3);
        
        while (rs2.next()){
        	result = "true";
            JSONObject obj = new JSONObject();
            obj.put("xdate", rs2.getString("xdate"));  
            obj.put("xbp", rs2.getString("xbp"));   
            obj.put("xrespiration", rs2.getString("xrespiration"));
            obj.put("xtemperature", rs2.getString("xtemperature"));
            obj.put("xspo2", rs2.getString("xspo2"));
            obj.put("xheightvit", rs2.getString("xheightvit"));
            obj.put("xweightvit", rs2.getString("xweightvit"));
            obj.put("xbmigyn", rs2.getString("xbmigyn"));
            obj.put("xbsa", rs2.getString("xbsa"));
            obj.put("xpulserate", rs2.getString("xpulserate"));
            obj.put("xanemia", rs2.getString("xanemia"));
            obj.put("xavpu", rs2.getString("xavpu"));
            obj.put("xofc", rs2.getString("xofc"));
            obj.put("xnote", rs2.getString("xnote"));
            obj.put("xpalor", rs2.getString("xpalor"));
            obj.put("xodema", rs2.getString("xodema"));
            obj.put("xjaundice", rs2.getString("xjaundice"));
            list.add(obj);
        }
        
        stmt3 = con.createStatement();
        rs3 = stmt3.executeQuery(sql4);
        
        while (rs3.next()){
        	result = "true";
            JSONObject obj = new JSONObject();
            obj.put("xdate", rs3.getString("xdate"));
            obj.put("xrow", rs3.getString("xrow"));
            obj.put("xitem", rs3.getString("xitem"));
            obj.put("xrem", rs3.getString("xrem"));
            obj.put("xdesc", rs3.getString("xdesc"));
            list.add(obj);
        }
        
        
        stmt4 = con.createStatement();
        rs4 = stmt4.executeQuery(sql5);
        
        while (rs4.next()){
        	result = "true";
            JSONObject obj = new JSONObject();
            obj.put("xdate", rs4.getString("xdate")); 
            obj.put("xrow", rs4.getString("xrow")); 
            obj.put("xlong", rs4.getString("xlong"));
            obj.put("xcode", rs4.getString("xcode"));
            obj.put("xnote", rs4.getString("xnote"));
            list.add(obj);
        }
        
        stmt5 = con.createStatement();
        rs5 = stmt5.executeQuery(sql6);
        
        while (rs5.next()){
        	result = "true";
            JSONObject obj = new JSONObject();
            obj.put("xdate", rs5.getString("xdate")); 
            obj.put("xstatus", rs5.getString("xstatus"));  
            obj.put("xstatuspharma", rs5.getString("xstatuspharma"));
            obj.put("xapptype", rs5.getString("xapptype"));
            list.add(obj);
        }
        
        stmt6 = con.createStatement();
        rs6 = stmt6.executeQuery(sql7); 
        
        while (rs6.next()){
        	result = "true";
            JSONObject obj = new JSONObject();
            obj.put("xdate", rs6.getString("xdate")); 
            obj.put("xrow", rs6.getString("xrow"));  
            obj.put("xcase", rs6.getString("xcase"));  
            obj.put("xcag", rs6.getString("xcag"));
            obj.put("xptcadt1", rs6.getString("xptcadt1"));  
            obj.put("xptcadt2", rs6.getString("xptcadt2"));  
            obj.put("xptcadt3", rs6.getString("xptcadt3"));
            obj.put("xcabgdt", rs6.getString("xcabgdt"));  
            obj.put("xcabg", rs6.getString("xcabg"));  
            obj.put("xsrgothers", rs6.getString("xsrgothers"));
            obj.put("xptca1", rs6.getString("xptca1"));  
            obj.put("xptca2", rs6.getString("xptca2"));  
            obj.put("xptca3", rs6.getString("xptca3"));
            list.add(obj);
        }
        
        stmt7 = con.createStatement();
        rs7 = stmt7.executeQuery(sql8); 
        
        while (rs7.next()){
        	result = "true";
            JSONObject obj = new JSONObject();  
            obj.put("xdate", rs7.getString("xdate")); 
            obj.put("xrow", rs7.getString("xrow"));  
            obj.put("xlong", rs7.getString("xlong"));  
            obj.put("xduration1", rs7.getString("xduration1"));
            obj.put("xdurationday", rs7.getString("xdurationday"));  
            obj.put("xnote", rs7.getString("xnote"));  
            list.add(obj);
        }
        
        
        stmt8 = con.createStatement();
        rs8 = stmt8.executeQuery(sql9); 
        
        while (rs8.next()){
        	result = "true";
            JSONObject obj = new JSONObject();   
            obj.put("xdate", rs8.getString("xdate")); 
            obj.put("xchest", rs8.getString("xchest"));  
            obj.put("xchestprbcheck", rs8.getString("xchestprbcheck"));  
            obj.put("xheartrate", rs8.getString("xheartrate"));
            obj.put("xmorbcheck", rs8.getString("xmorbcheck"));  
            obj.put("xbloodpressure", rs8.getString("xbloodpressure"));  
            obj.put("xbpcheck", rs8.getString("xbpcheck"));  
            obj.put("xpacemakerother", rs8.getString("xpacemakerother"));  
            obj.put("xpacemaker", rs8.getString("xpacemaker"));  
            obj.put("xheartprob", rs8.getString("xheartprob"));
            obj.put("xheartvalve", rs8.getString("xheartvalve"));  
            obj.put("xheartmedic", rs8.getString("xheartmedic"));  
            obj.put("xrheumaticfever", rs8.getString("xrheumaticfever"));
            obj.put("xartificialvalve", rs8.getString("xartificialvalve"));  
            obj.put("xbloodproblem", rs8.getString("xbloodproblem"));  
            obj.put("xintestinalproblem", rs8.getString("xintestinalproblem"));  
            obj.put("xulcersprblm", rs8.getString("xulcersprblm"));  
            obj.put("xkidneyprblm", rs8.getString("xkidneyprblm"));
            obj.put("xcancerprblm", rs8.getString("xcancerprblm"));
            obj.put("xartvlvcheck", rs8.getString("xartvlvcheck"));  
            obj.put("xheartmedicheck", rs8.getString("xheartmedicheck"));  
            obj.put("xbldprbcheck", rs8.getString("xbldprbcheck"));  
            obj.put("xintestinalprbcheck", rs8.getString("xintestinalprbcheck"));  
            obj.put("xulcerprbcheck", rs8.getString("xulcerprbcheck"));  
            obj.put("xkdnyprbcheck", rs8.getString("xkdnyprbcheck"));  
            obj.put("xcancerprbcheck", rs8.getString("xcancerprbcheck"));  
            obj.put("xlocalanestheticcheck", rs8.getString("xlocalanestheticcheck"));
            obj.put("xpencilincheck", rs8.getString("xpencilincheck")); 
            obj.put("xsulfadrugcheck", rs8.getString("xsulfadrugcheck"));  
            obj.put("xaspirincheck", rs8.getString("xaspirincheck"));  
            obj.put("xrubbercheck", rs8.getString("xrubbercheck"));
            obj.put("xdiabetescheck", rs8.getString("xdiabetescheck"));  
            obj.put("xsmokingcheck", rs8.getString("xsmokingcheck"));  
            obj.put("xdrinkingcheck", rs8.getString("xdrinkingcheck"));  
            obj.put("xlivercheck", rs8.getString("xlivercheck"));  
            obj.put("xneurocheck", rs8.getString("xneurocheck")); 
            obj.put("xdrugcheck", rs8.getString("xdrugcheck"));
            obj.put("xantibioticcheck", rs8.getString("xantibioticcheck"));  
            obj.put("xcoumadincheck", rs8.getString("xcoumadincheck"));  
            obj.put("xhbpmcheck", rs8.getString("xhbpmcheck"));
            obj.put("xaspcheck", rs8.getString("xaspcheck"));  
            obj.put("xsteroidcheck", rs8.getString("xsteroidcheck"));  
            obj.put("xhormoncheck", rs8.getString("xhormoncheck"));  
            obj.put("xpregnantcheck", rs8.getString("xpregnantcheck"));  
            obj.put("xnursingcheck", rs8.getString("xnursingcheck"));
            obj.put("xrheumaticradio", rs8.getString("xrheumaticradio"));  
            obj.put("xheartmedicradio", rs8.getString("xheartmedicradio"));  
            obj.put("xheartrateradio", rs8.getString("xheartrateradio"));  
            obj.put("xartificialradio", rs8.getString("xartificialradio"));
            obj.put("xdiabetic", rs8.getString("xdiabetic"));  
            obj.put("xdrinkingalchol", rs8.getString("xdrinkingalchol"));  
            obj.put("xsmokingyes", rs8.getString("xsmokingyes"));  
            obj.put("xhepatitesjanliv", rs8.getString("xhepatitesjanliv"));  
            obj.put("xneurological", rs8.getString("xneurological"));
            obj.put("xhistryofalcohol", rs8.getString("xhistryofalcohol"));
            obj.put("xduedate", rs8.getString("xduedate"));  
            obj.put("xliver", rs8.getString("xliver"));  
            obj.put("xhepatitesjancheck", rs8.getString("xhepatitesjancheck"));
            list.add(obj);
        }
        

        
        
        stmt9 = con.createStatement();
        rs9 = stmt9.executeQuery(sql10); 
        
        while (rs9.next()){
        	result = "true";
            JSONObject obj = new JSONObject(); 
            
            obj.put("xdate", rs9.getString("xdate")); 
            obj.put("xrow", rs9.getString("xrow"));  
            obj.put("xlprofiledt", rs9.getString("xlprofiledt"));  
            obj.put("xlprofiletc", rs9.getString("xlprofiletc"));
            obj.put("xlprofileldl", rs9.getString("xlprofileldl"));  
            obj.put("xlprofilehdl", rs9.getString("xlprofilehdl"));  
            obj.put("xlprofiletg", rs9.getString("xlprofiletg"));  
            obj.put("xresultval", rs9.getString("xresultval"));  
            obj.put("xlprofileunit", rs9.getString("xlprofileunit"));
            obj.put("xechola", rs9.getString("xechola"));  
            obj.put("xecholvidd", rs9.getString("xecholvidd"));  
            obj.put("xecholvids", rs9.getString("xecholvids"));  
            obj.put("xechoef", rs9.getString("xechoef"));  
            obj.put("xecholvef", rs9.getString("xecholvef"));
            obj.put("xechorwma", rs9.getString("xechorwma"));  
            obj.put("xechounit", rs9.getString("xechounit"));  
            obj.put("xchodt", rs9.getString("xchodt"));  
            obj.put("xechopasp", rs9.getString("xechopasp"));  
            obj.put("xcreatinine", rs9.getString("xcreatinine"));
            obj.put("xcreatininedt", rs9.getString("xcreatininedt"));  
            obj.put("xcreatinineunit", rs9.getString("xcreatinineunit"));  
            obj.put("xett", rs9.getString("xett"));  
            obj.put("xett1", rs9.getString("xett1"));
            obj.put("xett2", rs9.getString("xett2"));  
            obj.put("xettdate", rs9.getString("xettdate"));  
            obj.put("xlprofilealt", rs9.getString("xlprofilealt"));  
            obj.put("xast", rs9.getString("xast"));  
            obj.put("xtc", rs9.getString("xtc"));
            obj.put("xhb", rs9.getString("xhb"));  
            obj.put("xrow", rs9.getString("xrow")); 
            obj.put("xplatelate", rs9.getString("xplatelate"));
            obj.put("xhstroponin", rs9.getString("xhstroponin"));  
            obj.put("xhstroponindtunit", rs9.getString("xhstroponindtunit")); 
            obj.put("xhstroponindt", rs9.getString("xhstroponindt"));
            obj.put("xtroponin", rs9.getString("xtroponin"));  
            obj.put("xtroponindt", rs9.getString("xtroponindt")); 
            obj.put("xtroponindtunit", rs9.getString("xtroponindtunit"));
            obj.put("xna", rs9.getString("xna"));  
            obj.put("xk", rs9.getString("xk")); 
            obj.put("xcl", rs9.getString("xcl"));
            obj.put("xca", rs9.getString("xca"));  
            obj.put("xmg", rs9.getString("xmg")); 
            obj.put("xhco3", rs9.getString("xhco3"));  
            obj.put("xserumelecdt", rs9.getString("xserumelecdt"));  
            obj.put("xunacr", rs9.getString("xunacr"));  
            obj.put("xcxrpa", rs9.getString("xcxrpa")); 
            obj.put("xecg", rs9.getString("xecg"));  
            obj.put("xcbc", rs9.getString("xcbc")); 
            obj.put("xfbs", rs9.getString("xfbs"));  
            obj.put("xhb1ac", rs9.getString("xhb1ac")); 
            obj.put("xesr", rs9.getString("xesr"));  
            obj.put("xnt", rs9.getString("xnt")); 
            obj.put("xpbf", rs9.getString("xpbf"));
            obj.put("xother", rs9.getString("xother"));  
            obj.put("xvitaminD3", rs9.getString("xvitaminD3")); 
            obj.put("xtsh", rs9.getString("xtsh"));  
            obj.put("xlprofilech", rs9.getString("xlprofilech")); 
            obj.put("xlprofilecpk", rs9.getString("xlprofilecpk"));       
            list.add(obj);
        }
        
        
//        int k = stmt.executeUpdate(sql1);

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