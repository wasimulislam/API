package zaberp.zab;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.Date;

@SuppressWarnings("serial")
public class mmprescription extends HttpServlet {
    final String thisName = "";
    JDBCpool pool = null;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //    zabInfo.LoadGlobal(); 
        pool = zabInfo.GetPool(this, thisName);
    }
    @SuppressWarnings("null")
	protected void doGet(HttpServletRequest requestObj, HttpServletResponse responseObj)
    throws IOException {
        //set the content type
        responseObj.setContentType("text/html");
        responseObj.setHeader("Cache-Control", "no-cache");
        //get the PrintWriter object to write the html page
        zabInfo zabinfo = new zabInfo();
        PrintWriter writer = responseObj.getWriter();
        Connection con = null;
        Statement stmt = null;
        Statement stmt5 = null; 
        String s = "";
        String sql = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";
        String sql6 = "";
        String sql7 = "";
        String sql8 = "";
        String sql9 = "";
        String sql10 = "";
        String sql12 = "";
        String sql14 = "";
        String sql15 = "";
        String sql16 = "";
        String sql17 = "";
        String sql18 = "";
        String sql19 = "";
        String sql20 = "";
        String sql21 = "";
        String sql22 = "";
        String sql23 = "";
        String sql24 = "";
        String sql25 = "";
        String sqlupd="";
        String sqlupd1="";
       

        /**** GETTING PARAMETER FROM URL/SCREEN ********************/
        int id = zabTools.getInt(requestObj.getParameter("id"));
        String caseno = requestObj.getParameter("caseno");
        String screen = requestObj.getParameter("screen");
        String xcase = requestObj.getParameter("xcase");
        String update = requestObj.getParameter("xupdate");
        	//System.err.println(update);  
      	

       
        sql = "select ztime,zutime,zauserid,zuuserid,zid,xcase,xgenericdesc,xinst,xtdate,xdosage,xdose,xfrequency,xtakingtime,xdurationday,xqty,xroute," +
            "xduration1,xrem,xpatient,xname,xage,xsex,doctorname,designationname,departmentname,xregino,xprofdegree from mmprescriptionviewrpt " +
            "where zid='" + id + "' and xcase='" + caseno + "'";
        //System.err.println(sql);
        sql2 = "select zid,xcase,xdoctor,xpatient,xpastgh,xsurgicalhistory,xbreastfeeding,ximmunization,xfinaldiagnosis,xnote5,\r\n" +
            "  (select REPLACE(xchiefcomplain, ',', '<br>'))as xchiefcomplain,xfamilyhistory,xsocialhistory,(select REPLACE(xdrughistory, ',', '<br>'))as xdrughistory,xurinetwo,xurinethree,xmicrgrowth,xobhistory,xmeandobhistory,xcontraception,(select REPLACE(xcomorbidities, ',', '<br>')) as xcomorbidities,(select REPLACE(xdrugall, ',', '<br>')) as xdrugall,\r\n" +
            "  xfoodall,xphychostatus,xsuspecteddisease,xrisk,xpast,xoutlook,xsysenquiry,xedspecialist,xprovdiagnosis,xplanecare,xphysicianassist,xchest,xgeneral,xpae,xps,xpve,\r\n" +
            "  xlungs,xepinspection,xeppalpation,xeppercussion,xepauscultation,xfetalheart,xabdomen,xdiagnosis,xadvice,xfollowupadvice,xpatadvice,xadadvice,xfamilyother,xfeel,xmove,\r\n" +
            "  xinvestigationhistroy,xcompcheck,xmorbcheck,xsurgcheck,xfamcheck,xriskcheck,xmedicheck,xobscheck,xmenscheck,xcontcheck,xinvescheck,xallergycheck,xother,xdrughistoryothers from mmappassview where zid='" + id + "' and  xcase='" + caseno + "'";
        //System.err.println(sql2);
        
        sql3 = "select xbp,xrespiration,xtemperature,xspo2,xheightvit,xweightvit,xbmigyn,xbsa,xpulserate,xanemia,xavpu,xofc,xnote,xpalor,xodema,xjaundice from mmvitalphview where zid='" + id + "' and  xcase='" + caseno + "'";
        //System.err.println(sql3);
        sql4 = "select xrow,xitem,xrem,xdesc from mmlabphview where zid='" + id + "' and  xcase='" + caseno + "'";
        //System.err.println(sql4);

        sql5 = "select xrow,xlong,xcode,xnote from mmonexam where zid='" + id + "' and  xcase='" + caseno + "'";
        //System.err.println(sql5);
        sql6 = "select xgeneral ,xchest ,xoutlook ,xfeel ,xmove ,xpae ,xphysicianassist ,xfetalheart ,xlungs ,xabdomen ,xepinspection ," +
            "xeppalpation ,xeppercussion ,xepauscultation ,xps ,xpve ,xsysenquiry ,xprovdiagnosis  ,(select REPLACE(xedspecialist, ',', '<br>')) as xedspecialist ,(select REPLACE(xfollowupadvice, ',', '<br>')) as xfollowupadvice," +
            "xadvice ,xplanecare ,xpatadvice ,xadadvice,xoncology,xfollwadviceother,xgcs,xvision,xhearing,xspeach,xgate,xstance,xcerebellar,xsensation,xmusclepower,"+
            "xreflexes,xplantar,xholtman,xexamperipheral,xpr,xcardiovascular,xhead,xface,xeyeball,xorbit,xeyelibs,xlacrimalsac,xlacrimalgland,xconjunctive,xsclera,xcorhea,xeyeac,xpupil,xiris,xlens,xiop,xfurdi,xrefdoctordesc,designationname,xprofdegree,departmentname"+
            " from mmappassview where zid='" + id + "' and  xcase='" + caseno + "'";
        //System.err.println(sql6);

        sql7 = "select xstatus,xstatuspharma,xapptype from mmappointment where zid='" + id + "' and xcase='" + caseno + "'";
        //System.err.println(sql7);

        sql9 = "select xrow,xcase,xcag,xptcadt1,xptcadt2,xptcadt3,xcabgdt,xcabg,xsrgothers,xptca1,xptca2,xptca3 from mmcardsurghistoryview where zid='" + id + "' and xcase='" + caseno + "'";
        //System.err.println(sql9);
        
        sql10 = "select xrow,xlong,xduration1,xdurationday,xnote from mmcardriskhistoryview where zid='" + id + "' and xcase='" + caseno + "'";
        //System.err.println(sql10);
        
        sql19="select xchest,xchestprbcheck,xheartrate,xmorbcheck,xbloodpressure,xbpcheck,xpacemakerother,xpacemaker,\r\n"
        		+ "			xheartprob,xheartvalve,xheartmedic,xrheumaticfever,xartificialvalve,xbloodproblem,xintestinalproblem,\r\n"
        		+ "			xulcersprblm,xkidneyprblm,xcancerprblm,xartvlvcheck,xheartmedicheck,xbldprbcheck,\r\n"
        		+ "			xintestinalprbcheck,xulcerprbcheck,xkdnyprbcheck,xcancerprbcheck,xlocalanestheticcheck,xpencilincheck,\r\n"
        		+ "			xsulfadrugcheck,xaspirincheck,xrubbercheck,xdiabetescheck,xsmokingcheck,xdrinkingcheck,xlivercheck,xneurocheck,\r\n"
        		+ "			xdrugcheck,xantibioticcheck,xcoumadincheck,xhbpmcheck,xaspcheck,xsteroidcheck,xhormoncheck,xpregnantcheck,xnursingcheck,xrheumaticradio,\r\n"
        		+ "			xheartmedicradio,xheartrateradio,xartificialradio,xdiabetic,xdrinkingalchol,xsmokingyes,xhepatitesjanliv,xneurological,xhistryofalcohol,xduedate,\r\n"
        		+ "			xliver,xhepatitesjancheck from mmdentalview where zid='" + id + "' and xcase='" + caseno + "'";
        //System.err.println(sql19);

        sql8 = "select xrow,xlprofiledt,xlprofiletc,xlprofileldl,xlprofilehdl,xlprofiletg,xresultval,xlprofileunit from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql8);
        
        sql12 = "select xrow,xechola,xecholvidd,xecholvids,xechoef,xecholvef,xechorwma,xechounit,xchodt,xechopasp from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql12);
        
        sql14 = "select xrow,xcreatinine,xcreatininedt,xcreatinineunit from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql14);
        
        
        sql15 = "select xrow,xlprofiledt,xlprofiletc,xlprofileldl,xlprofilehdl,xlprofiletg,xlprofilealt,xlprofilech,xresultval,\r\n"
        		+ "xlprofilecpk,xechola,xecholvidd,xecholvids,xechoef,xecholvef,xechorwma,xechounit,xchodt,xcreatinine,xna,xk,xcl,xmg,xca,xhco3,\r\n"
        		+ "xcreatininedt,xtroponindt,xlprofileunit,xechopasp,xhstroponin,\r\n"
        		+ "xhstroponindtunit,xhstroponindt,xserumelecdt,xnt,xtc,xhb,xplatelate,xast from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql15);
        
        sql16="select xunacr,xcxrpa,xecg,xcbc,xfbs,xhb1ac,xesr,xnt,xpbf,xdate,xother,xvitaminD3,xtsh from mmcardhistoryview where zid='" + id +"' and xcase='" + caseno +"'" ;
        //System.err.println(sql16);
        
        sql17 = "select xrow,xna,xk,xcl,xca,xmg,xhco3,xserumelecdt from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql17);
        
        sql18 = "select xrow,xtroponin,xtroponindt,xtroponindtunit from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql18);
        
        sql20 ="select xdeptname from mmappointmentview where zid='" + id + "' and xcase='" + caseno + "'";
        //System.err.println(sql20);
        sql21 = "select xrow,xhstroponin,xhstroponindtunit,xhstroponindt from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql21);
        sql22 = "select xrow,xtc,xhb,xplatelate from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql22);
        sql23 = "select xrow,xlprofilealt,xast from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql23);
        sql24 = "select xrow,xett,xett1,xett2,xettdate from mmcardhistoryview where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql24);
        sql25 = "update mmappointment set xstatus='Completed' and xstatuspharma='In Process'  where zid='" + id + "' and xcase='" + caseno + "'" ;
        //System.err.println(sql25);
        sqlupd="update mmappointment set xstatus='Completed',xstatuspharma='In Process' where zid='400010' and xcase='" + xcase + "'";
        //Displaying current date and time in 12 hour format with AM/PM
        //System.out.println(sqlupd);
    	sqlupd1="update mminitappointment set xstatus='Completed',xstatuspharma='In Process' where zid='400010' and xcase='" + xcase + "'";
    	//System.out.println(sqlupd1);
        
    	DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
        String dateString2 = dateFormat2.format(new Date()).toString();
        //System.out.println("Current date and time in AM/PM: "+dateString2);
        int records = 0;
        int records1 = 0;
        int records2 = 0;
        int records3 = 0;
        int records8 = 0;
        int records7 = 0;
        int records9 = 0;
        int records14 = 0;
        int records16 = 0;
        int records17 = 0;
        int records19 = 0;
        int records21 = 0;
        int records22 = 0;
        int records23 = 0;
        int records24 = 0;
        int records25 = 0;
        int records26 = 0;
        int records27 = 0;
        
        String result = "";
        String genericdesc = "";
        String inst = "";
        String dose = "";
        String takingtime = "";
        String xmedcadvice = "";
        String doctorname = "";
        String designationname = "";
        String departmentname = "";
        String xregino = "";
        String xname = "";
        String xage = "";
        String xsex = "";
        String xtdate = "";
        String xpatient = "";
        String xinvestigationhistroy = "";
        String xfamilyother = "";
        String xchiefcomplain = "";
        String xurinetwo = "";
        String xurinethree = "";
        String xbreastfeeding = "";
        String xpastgh = "";
        String ximmunization = "";
        String xmicrgrowth = "";
        String xphychostatus = "";
        String xsurgicalhistory = "";
        String xsuspecteddisease = "";
        String xobhistory = "";
        String xmeandobhistory = "";
        String xcontraception = "";
        String xcomorbidities = "";
        String xfamilyhistory = "";
        String xsocialhistory = "";
        String xdrugall = "";
        String xfoodall = "";
        String xdrughistory = "";
        String xrisk = "";
        String xdiagnosis = "";
        String xfinaldiagnosis = "";
        String xnote5 = "";
        String xbp = "";
        String xrespiration = "";
        String xtemperature = "";
        String xspo2 = "";
        String xheightvit = "";
        String xweightvit = "";
        String xbmigyn = "";
        String xbsa = "";
        String xrow = "";
        String xrem = "";
        String xdesc = "";
        String xobscheck = "";
        String xmenscheck = "";
        String xcontcheck = "";
        String xsurgcheck = "";
        String xcompcheck = "";
        String xmorbcheck = "";
        String xinvescheck = "";
        String xfamcheck = "";
        String xmedicheck = "";
        String xprofdegree = "";
        String xpulserate = "";
        String xallergycheck = "";
        String xother = "";
        String xdrughistoryothers = "";

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
        ResultSet rs10 = null;
        ResultSet rs12 = null;
        ResultSet rs14 = null;
        ResultSet rs15 = null;
        ResultSet rs16 = null;
        ResultSet rs17 = null;
        ResultSet rs18 = null;
        ResultSet rs19 = null;
        ResultSet rs20 = null;
        ResultSet rs21 = null;
        ResultSet rs22 = null;
        ResultSet rs23 = null;
        ResultSet rs24 = null;
        ResultSet rs25 = null;
        ResultSet rs26 = null;
        ResultSet rs27 = null;
        
        try {
            con = pool.getConnection();
        } catch (Exception esql) {
            zabinfo.log("zErr0002msg", esql.getMessage(), "Return Code = 0");
        }

        try {	
        	s += "<style id=\"table_style\" type=\"text/css\">" +
                    "table {\r\n" +
                    "    font-family: Calibri;\r\n" +
                    "}\r\n" +
                    "\r\n" +
                    ".table {\r\n" +
                    "   font-family: Calibri;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    " .med_list p {\r\n" +
                    "                font-size: 15px;\r\n" +
                    "                font-weight: normal;\r\n" +
                    "                margin: 0px;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .med_list {\r\n" +
                    "                margin-left: 10px;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .prescription {\r\n" +
                    "                width: 100%;\r\n" +
                    "                border: none;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .d-header {\r\n" +
                    "                font-weight: bold;\r\n" +
                    "                margin: 0px;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .symptoms p {\r\n" +
                    "                margin: 3px 15px 3px 20px;\r\n" +
                    "                font-size: 13px;\r\n" +
                    "                word-break: break-word;\r\n" +
                    "            }\r\n" +
                    "            .symptoms b {\r\n" +
                    "              font-size: 14px;" +
                    "            }\r\n" +
                    "            .symptoms span {\r\n" +
                    "                font-size: 13px;\r\n" +
                    "                word-break: break-word;\r\n" +
                    "            }\r\n" +
                    "\r\n" 
                    + "             .credentials-opth{\r\n"
                    + "                page-break-after: always;\r\n"
                    + "               }\r\n"+
                    "            .zero-margin p {\r\n" +
                    "                margin: 0px;\r\n" +
                    "                font-weight: normal;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .header-tr {\r\n" +
                    "                border-bottom: 1.2px solid black;\r\n" +
                    "                border-top: 0px;\r\n" +
                    "                border-left: 0px;\r\n" +
                    "                border-right: 0px;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .border-right {\r\n" +
                    "                border-bottom: 0px;\r\n" +
                    "                border-top: 0px;\r\n" +
                    "                border-right: 0px !important;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
//                    "            .credentials {\r\n" +
//                    "                background: #ffff;\r\n" +
//                    "                position: relative;\r\n" +
//                    "					font-size: 20px;" +
//                    "            }\r\n" +
					"            .credentials-new {\r\n" +
					"				margin-top: -35px;"+
					"         }\r\n" +
                    "\r\n" +
                    "            .footer {\r\n" +
                    "                height: 85px;\r\n" +
                    "                background: #ffff;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .footer>div {\r\n" +
                    "                position: absolute;\r\n" +
                    "                bottom: 0;\r\n" +
                    "                right: -1%;\r\n" +
                    "                width: 100%;\r\n" +
                    "				background: #ffff;" +
                    "            }\r\n" +
                    "\r\n" +
                    "			.td-gap{\r\n"+
                    "				text-align: center;\r\n"+
                    "            }\r\n" +
                    "\r\n" +
                    "            .symptoms {\r\n" +
                    "                page-break-inside: avoid;\r\n" +
                    "            }\r\n" +
                    "            .vl {\r\n" +
                    "                height: 700px;\r\n" +
                    "                 margin-left: 15px;"+	
                    "            }\r\n" +
                    "            td {\r\n" +
                    "                vertical-align: top;\r\n" +
                    "            }" +
                    "hr {"+
                    "border: 0.1px solid black;\r\n"
                    + "    margin: 0px;"+
                    "}"+
                    " .td-gap {"
                    + "			font-size: 11px;"
                    + "}"+
                    "\r\n"
                    + "table td{\r\n"
                    + "	padding: 2px;\r\n"
                    + "}\r\n"
                    + "table th{\r\n"
                    + "	padding: 2px;\r\n"
                    + "	font-size: 13px;\r\n"
                    + "}\r\n"
                    + ".head {   \r\n"
                    +"font-size: 13px;\r\n"
                    + "  text-align: center;\r\n"
                    + "  margin-bottom: 0rem;"
                    + "}\r\n"
                    + ".grid-container {\r\n"
                    + "  display: flex;\r\n"
                    + "  padding: 10px;\r\n"
                    + "\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + "\r\n"
                    + ".row {\r\n"
                    + "  float: left;\r\n"
                    + "  margin:10px;\r\n"
                    +" width:100%;\r\n"
                    + "   \r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".column1 {\r\n"
                    +"font-size: 13px;\r\n"
                    + " float: left;\r\n"
                    + "  width: 50%;\r\n"
                    + "\r\n"
                    + "}\r\n"
                    + ".column2 {\r\n" 
                    +"font-size: 13px;\r\n"
                    + " float: right;\r\n"
                    + "  width: 50%;\r\n"
                    + "  \r\n"
                    + "}\r\n"
                    + ".check1a {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check2b {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check3 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check1 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check4 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check5 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check6 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check7 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check8 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check9 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check10 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check49 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check48 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check11 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check12 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check13 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check14 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check15 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check16 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check17 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check18 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check19 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check20 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check21 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".check23 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check24 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +" margin-top: -6px;"
                    + "}\r\n"
                    + ".check25 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 9px;"
                    + "}\r\n"
                    + ".check26 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "  margin-top: 2px;"
                    + "}\r\n"
                    + ".check27 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 9px;"
                    + "}\r\n"
                    + ".check28 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +"    margin-top: 5px;"
                    + "}\r\n"
                    + ".check29 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 10px;"
                    + "}\r\n"
                    + ".check30 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +"    margin-top: 8px;"
                    + "}\r\n"
                    + ".check31 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 10px;"
                    + "}\r\n"
                    + ".check32 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +"    margin-top: 8px;"
                    + "}\r\n"
                    + ".check33 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 10px;"
                    + "}\r\n"
                    + ".check34 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +"    margin-top: 8px;"
                    + "}\r\n"
                    + ".check35 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "  margin-left:20px;\r\n"
                    + "}\r\n"
                    + ".check36 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check37 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check38 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "  margin-left:20px;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".rightdiv1 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv2 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv3 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv4 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv5 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv6 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv7 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".rightdiv8 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv9 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv10 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv11 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv12 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv13 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv14 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv15 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv16 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv17 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv18 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "}\r\n"
                    + ".rightdiv19 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv20 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv21 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv22 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv23 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "  margin-left:20px;\r\n"
                    + "}\r\n"
                    + ".rightdiv24 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv25 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}" +
                    "        @page {\r\n" +
                    "            margin: 35mm 10mm 10mm 15mm;\r\n" +
                    "        }\r\n" +
                    "\r\n" +
                    "@media print {\r\n" +
                    "            table {\r\n" +
                    "                font-family: Calibri;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .symptoms span {\r\n" +

                    "                font-size: 13px;\r\n" +
                    "                word-break: break-word;\r\n" +
                    "            }\r\n" 
                    + "             .credentials-opth{\r\n"
                    + "                page-break-after: always;\r\n"
                    + "               }\r\n"+
                    " .td-gap {"
                    + "			font-size: 11px;"
                    + "}"+
                    "            .table {\r\n" +
                    "                font-family: Calibri;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .med_list p {\r\n" +
                    "                font-size: 13px !important;\r\n" +
                    "                font-weight: normal;\r\n" +
                    "                margin: 0px;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .med_list {\r\n" +
                    "                margin-left: 13px;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .prescription {\r\n" +
                    "                width: 100%;\r\n" +
                    "                border: none;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .symptoms b {\r\n" +
                    "              font-size: 13px;" +
                    "            }\r\n" +
                    "            .d-header {\r\n" +
                    "                font-weight: bold;\r\n" + 
                    "                margin: 0px;\r\n"
                    + "				font-size: 13px !important;" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .symptoms p{\r\n" +
                    "                margin: 3px 15px 3px 20px;\r\n" +
                    "                font-size: 13px !important;\r\n" +
                    "                word-break: break-word;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .zero-margin p {\r\n" +
                    "                margin: 0px;\r\n" +
                    "                font-weight: normal;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .header-tr {\r\n" +
                    "                border-bottom: 1.2px solid black;\r\n" +
                    "                border-top: 0px;\r\n" +
                    "                border-left: 0px;\r\n" +
                    "                border-right: 0px;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .border-right {\r\n" +
                    "                border-bottom: 0px;\r\n" +
                    "                border-top: 0px;\r\n" +
                    "                border-right: 0px !important;\r\n" +
                    "            }\r\n" +
                    "\r\n" +  
                    "            .credentials-new {\r\n" +
                    "				margin-top: -35px;"+
                    "            }\r\n" +
                    "\r\n" +
//                    "            .credentials-com {\r\n" +
//                    "				margin-top: 2px;"+
//                    "            }\r\n" +
//                    "\r\n" +
                    "            .credentials-com {\r\n" +
                    "                background: #ffff;\r\n" +
                    "                position: relative;\r\n" +
                    "                overflow: hidden;\r\n"+
                    
//                  "					font-size: 20px;" +
					"            }\r\n" +
                    "			.td-gap{\r\n"+
                    "				text-align: center;\r\n"+
                    "            }\r\n" +
                    "\r\n" +
                    "            .footer {\r\n" +
                    "                height: 85px;\r\n" +
                    "                background: #ffff;\r\n" +
                    "            }\r\n" +
                    "\r\n" +
                    "            .footer>div {\r\n" +
                    "                position: fixed;\r\n" +
                    "                bottom: 0;\r\n" +
                    "                right: 0;\r\n" +
                    "                width: 100%;\r\n" +
                    "				background: #ffff;" +
                    "            }\r\n" +
                    "\r\n" +
                    "hr {"+
                    "	border: 0.1px solid black;\r\n"
                    + "    margin: 0px;"+
                    "}"+
                    "            .symptoms {\r\n" +
                    "                page-break-inside: avoid;\r\n" +
                    "            }\r\n" +
                    "            .vl {\r\n" +
                    "                height: 700px;\r\n" +
                    "                 margin-left: 10px;"+		
                    "            }\r\n" +
                    "            td {\r\n" +
                    "                vertical-align: top;\r\n" +
                    "            }" 
                    
                    + "table td{\r\n"
                    + "	padding: 1px;\r\n"
                    + "}\r\n"
                    + "table th{\r\n"
                    + "	padding: 1px;\r\n"
                    + "	font-size: 12px;\r\n"
                    + "}\r\n"
                    + "" +
                    "        }\r\n"
                    + ".head {   \r\n"
                    +"font-size: 12px;\r\n"
                    + "  text-align: center;\r\n"
                    + "}\r\n"
                    + ".grid-container {\r\n"
                    + "  display: flex;\r\n"
                    + "  padding: 10px;\r\n"
                    + "\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + "\r\n"
                    + ".row {\r\n"
                    + "  float: left;\r\n"
                    + "  margin:10px;\r\n"
                    + "  width:100%;\r\n"
                    + "   \r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".column1 {\r\n"
                    +"font-size: 14px;\r\n"
                    + " float: left;\r\n"
                    + "  width: 50%;\r\n"
                    + " margin-top: -4px;"
                    + "\r\n"
                    + "}\r\n"
                    + ".column2{\r\n"
                    + " float: right;\r\n"
                    + "font-size: 14px;\r\n"
                    + "  width: 50%;\r\n"
                    + " margin-top: -4px;"
                    + "  \r\n"
                    + "}\r\n"
                    + ".check1a {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check2b {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check3 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check1 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check4 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check5 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check6 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check7 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check8 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check9 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check10 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check49 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check48 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check11 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check12 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check13 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check14 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check15 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check16 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check17 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check18 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check19 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check20 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "}\r\n"
                    + ".check21 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".check23 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + "}\r\n"
                    + ".check24 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +" margin-top: -6px;"
                    + "}\r\n"
                    + ".check25 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 9px;"
                    + "}\r\n"
                    + ".check26 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + "  margin-top: 2px;"
                    + "}\r\n"
                    + ".check27 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 9px;"
                    + "}\r\n"
                    + ".check28 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +"    margin-top: 5px;"
                    + "}\r\n"
                    + ".check29 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 10px;"
                    + "}\r\n"
                    + ".check30 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +"    margin-top: 8px;"
                    + "}\r\n"
                    + ".check31 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 10px;"
                    + "}\r\n"
                    + ".check32 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +"    margin-top: 8px;"
                    + "}\r\n"
                    + ".check33 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    +"    margin-top: 10px;"
                    + "}\r\n"
                    + ".check34 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    +"    margin-top: 8px;"
                    + "}\r\n"
                    + ".check35 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "  margin-left:20px;\r\n"
                    + "margin-top: -4px;"
                    + "}\r\n"
                    + ".check36 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 70%;\r\n"
                    + " margin-top: -4px;"
                    + "}\r\n"
                    + ".check37 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 30%;\r\n"
                    + " margin-top: -4px;"
                    + "}\r\n"
                    + ".check38 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "  margin-left:20px;\r\n"
                    + " margin-top: -10px;"
                    + "}\r\n"
                    + "\r\n"
                    + ".rightdiv1 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv2 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv3 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv4 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv5 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv6 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv7 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "}\r\n"
                    + "\r\n"
                    + ".rightdiv8 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv9 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv10 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv11 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv12 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv13 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv14 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv15 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv16 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv17 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv18 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "}\r\n"
                    + ".rightdiv19 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv20 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}\r\n"
                    + ".rightdiv21 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    +"    margin-top: 4px;"
                    + "}\r\n"
                    + ".rightdiv22 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    +"    margin-top: 6px;"
                    + "}\r\n"
                    + ".rightdiv23 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 100%;\r\n"
                    + "  margin-left:20px;\r\n"
                    +"    margin-top: 3px;"
                    + "}\r\n"
                    + ".rightdiv24 {\r\n"
                    + " float: left;\r\n"
                    + "  width: 60%;\r\n"
                    + "}\r\n"
                    + ".rightdiv25 {\r\n"
                    + " float: right;\r\n"
                    + "  width: 40%;\r\n"
                    + "}" +
                    
                    "    </style>";

        	stmt = con.createStatement();
        	
        	if(update==null) {
        		s+="";
        	}
        	else {
				stmt.executeUpdate(sqlupd);
				stmt.executeUpdate(sqlupd1);
        	}
        	
            rs7 = stmt.executeQuery(sql7);
            while (rs7.next()) {
            	String status=rs7.getString("xstatus");
            	 s += "<a class=\"btn btn-primary\" type=\"button\" href=\"login?screen="+screen+"&command=Show&xcase=" + caseno + "\" " +
                         "style=\"text-decoration: none;position: fixed;left: 50px;top: 100px;\">Back</a>";
            	String xapptype = "";
            	if (status.equals("Completed")) {
				
            	s+="";
            	 
            	}
            	else {
            		 s += "<a class=\"btn btn-primary\" type=\"button\" onclick=\"statusDiv('"+caseno+"')\"  href=\"login?screen="+screen+"&command=Show&xcase=" + caseno + "&xapptype="+xapptype+"\" " +
                             "style=\"text-decoration: none;position: fixed;left: 200px;top: 100px;\">Completed</a>";
            	}
             }
            s += "<div class=\"container\">";
            s += "<div class=\"container-fluid\" style=\"margin-top: 10%;\">";
            s += "<input class=\"btn btn-primary\" type=\"button\" value=\"Print\" onclick=\"printDiv()\" style=\"position: fixed;right: 100px;top: 100px;\"> ";
            s += "</div>";
            s += "</div>";
            
            
            s += "<div id=\"GFG\" class=\"container\">";
            s += "<div class=\"container\">";
            s += "<div class=\"container-fluid\">";
            
            rs20 = stmt.executeQuery(sql20);
            while (rs20.next()) {
            	String department=rs20.getString("xdeptname");
            	//s+=""+department+"";
           
                                s += "<table class=\"prescription\">" +
                                    "<thead>" +
                                    "	<tr>" +
                                    "		<th>" +
                                    "                <tr>\r\n" +
                                    "                    <td>" +
                                    "            <tr height=\"15%\">\r\n" +
                                    "                <td class=\"header-tr\" colspan=\"3\">\r\n" +
                                    "                    <div class=\"header\">\r\n" +
                                    "                        <div class=\"logo\">\r\n" +
                                    "                        </div>\r\n" +
                                    "                        <div class=\"credentials-com\">\r\n";

                                //stmt = con.createStatement();
                                rs1 = stmt.executeQuery(sql);
                                while (rs1.next()) {
                                    records17++;
                                    if (records17 == 1) {
                                        doctorname = rs1.getString("doctorname");
                                        designationname = rs1.getString("designationname");
                                        departmentname = rs1.getString("departmentname");
                                        xregino = rs1.getString("xregino");
                                        xtdate = rs1.getString("xtdate");
                                        xname = rs1.getString("xname");
                                        xage = rs1.getString("xage");
                                        xsex = rs1.getString("xsex");
                                        xpatient = rs1.getString("xpatient");
                                        xprofdegree = rs1.getString("xprofdegree");

                                        s += "           <span style=\"font-size: 15px;font-weight: bold;\">" + doctorname + "</span> <small> " + xprofdegree + "</small>\r\n" +
                                            "             <br />\r\n" +
                                            "             <small>" + designationname + "</small>\r\n" +
                                            "             <br />\r\n" +
                                            "             <small>" + departmentname + "</small>\r\n" +
                                            "             <br />" +
                                            "				<div style=\"display: inline-flex;width: 100%;\">" +
                                            "					<div style=\"text-align: start;width: 40%;\">" +
                                            "             		<small>BMDC Reg. no: " + xregino + "</small>\r\n" +
                                            "					</div>" +
                                            "					<div style=\"text-align: end;width: 60%;\">" +
                                            "						<small>Visit Date / Prescription No. : " + xtdate + " / " + caseno + "</small>" +
                                            "					</div>" +
                                            "				</div>" +
                                            "				<hr style=\"border: 0.1px solid black;margin: 0px;\"/>" +
                                            "				<div style=\"display: inline-flex;width: 100%;\">" +
                                            "					<div style=\"width: 80%;\">" +
                                            "						<small>Name: " + xname + "</small>" +
                                            "            			 <br />\r\n" +
                                            "						<small>Age: " + xage + "</small>" +
                                            "					</div>" +
                                            "					<div>" +
                                            "						<small>UHID: " + xpatient + "</small>" +
                                            "            			 <br />\r\n" +
                                            "						<small>Gender: " + xsex + "</small>" +
                                            "					</div>" +
                                            "				</div>";
                                    }

                                }
                                s += "                       </div>\r\n" +
                                    "                    </div>\r\n" +
                                    "                </td>\r\n" +
                                    "            </tr>\r\n" +
                                    "		</th>" +
                                    "	</tr>" +
                                    "</thead>" +
                                    "        <tbody id=\"prescription-height\">\r\n" +
                                    "                <tr>\r\n" +
                                    "                    <td>" +
                                    "            <tr height=\"15%\">\r\n" +
                                    "                <td colspan=\"3\">\r\n" +
                                    "                    <div class=\"header\">\r\n" +
                                    "                        <div class=\"logo\">\r\n" +
                                    "                        </div>\r\n" +
                                    "                        <div class=\"credentials\">\r\n"

                                    +
                                    "            <tr>\r\n" +
                                    "                <td width=\"37%\">\r\n" +
                                    "                    <div class=\"desease_details\">\r\n";
                    				
                                rs2 = stmt.executeQuery(sql2);
                                while (rs2.next()) {
                                    xchiefcomplain = rs2.getString("xchiefcomplain");
                                    xurinetwo = rs2.getString("xurinetwo");
                                    xurinethree = rs2.getString("xurinethree");
                                    xbreastfeeding = rs2.getString("xbreastfeeding");
                                    xpastgh = rs2.getString("xpastgh");
                                    ximmunization = rs2.getString("ximmunization");
                                    xmicrgrowth = rs2.getString("xmicrgrowth");
                                    xphychostatus = rs2.getString("xphychostatus");
                                    xsurgicalhistory = rs2.getString("xsurgicalhistory");
                                    xsuspecteddisease = rs2.getString("xsuspecteddisease");
                                    xobhistory = rs2.getString("xobhistory");
                                    xmeandobhistory = rs2.getString("xmeandobhistory");
                                    xcontraception = rs2.getString("xcontraception");
                                    xcomorbidities = rs2.getString("xcomorbidities");
                                    xfamilyhistory = rs2.getString("xfamilyhistory");
                                    xsocialhistory = rs2.getString("xsocialhistory");
                                    xdrugall = rs2.getString("xdrugall");
                                    xfoodall = rs2.getString("xfoodall");
                                    xdrughistory = rs2.getString("xdrughistory");
                                    xrisk = rs2.getString("xrisk");
                                    xdiagnosis = rs2.getString("xdiagnosis");
                                    xfinaldiagnosis = rs2.getString("xfinaldiagnosis");
                                    xnote5 = rs2.getString("xnote5");
                                    xfamilyother = rs2.getString("xfamilyother");
                                    xinvestigationhistroy = rs2.getString("xinvestigationhistroy");
                                    xobscheck = rs2.getString("xobscheck");
                                    xmenscheck = rs2.getString("xmenscheck");
                                    xcontcheck = rs2.getString("xcontcheck");
                                    xsurgcheck = rs2.getString("xsurgcheck");
                                    xcompcheck = rs2.getString("xcompcheck");
                                    xmorbcheck = rs2.getString("xmorbcheck");
                                    xinvescheck = rs2.getString("xinvescheck");
                                    xfamcheck = rs2.getString("xfamcheck");
                                    xmedicheck = rs2.getString("xmedicheck");
                                    xallergycheck = rs2.getString("xallergycheck");
                                    xother = rs2.getString("xother");
                                    xdrughistoryothers = rs2.getString("xdrughistoryothers");
                                    
                                    if (xdiagnosis.equals("") && xfinaldiagnosis.equals("")) {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Diagnosis</h6>\r\n" +
                                            "								<p>" + xdiagnosis + "<p/>" +
                                            "								<p>" + xfinaldiagnosis + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xnote5.equals("")) {
                                        s += "                      <div>\r\n" +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                      <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Note</h6>\r\n" +
                                            "								<p>" + xnote5 + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xcompcheck.equals("0")) {
                                        if (xchiefcomplain.equals("") && xfamilyother.equals("")) {
                                            s += "                      <div>\r\n" +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                      <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Presenting Symptoms</h6>\r\n" +
                                                "								<p>" + xchiefcomplain + "<p/>" +
                                                "								<p>" + xfamilyother + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                      <div>\r\n" +
                                            "                        </div>\r\n";
                                    }

                                    if (xurinetwo.equals("") && xurinethree.equals("")) {
                                        s += "                      <div>\r\n" +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                      <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Bowel-Bladder Condition</h6>\r\n" +
                                            "								<p><p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xurinetwo.equals("")) {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Passing Urine</h6>\r\n" +
                                            "								<p>" + xurinetwo + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xurinethree.equals("")) {
                                        s += "                        <div>\r\n" +
                                            "                        		</div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Passing Stool:</h6>\r\n" +
                                            "								<p>" + xurinethree + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xbreastfeeding.equals("")) {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Feeding History</h6>\r\n" +
                                            "								<p>" + xbreastfeeding + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xpastgh.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Antenatal, Natal and Post Natal History</h6>\r\n" +
                                            "								<p>" + xpastgh + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (ximmunization.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Immunization History</h6>\r\n" +
                                            "								<p>"+ximmunization+"<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xmicrgrowth.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Growth & Development History</h6>\r\n" +
                                            "								<p>" + xmicrgrowth + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xphychostatus.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Psycological Assessment</h6>\r\n" +
                                            "								<p>" + xphychostatus + "<p/>" +
                                            "                        </div>\r\n";
                                    }


                                    if (xsurgcheck.equals("0")) {
                                        if (xsurgicalhistory.equals("")) {
                                            s += "                        <div>\r\n"

                                                +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Past Medical / Surgical History</h6>\r\n" +
                                                "								<p>" + xsurgicalhistory + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    }

                                    if (xsuspecteddisease.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Disease Past History</h6>\r\n" +
                                            "								<p>" + xsuspecteddisease + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xobscheck.equals("0")) {
                                        if (xobhistory.equals("")) {
                                            s += "                        <div>\r\n"

                                                +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Obstretical History</h6>\r\n" +
                                                "								<p>" + xobhistory + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    }

                                    if (xmenscheck.equals("0")) {
                                        if (xmeandobhistory.equals("")) {
                                            s += "                        <div>\r\n"

                                                +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Menstrual History</h6>\r\n" +
                                                "								<p>" + xmeandobhistory + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    }

                                    if (xcontcheck.equals("0")) {
                                        if (xcontraception.equals("")) {
                                            s += "                        <div>\r\n"

                                                +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Contraceptive History </h6>\r\n" +
                                                "								<p>" + xcontraception + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    }

                                    if (xmorbcheck.equals("0")) {
                                        if (xcomorbidities.equals("")) {
                                            s += "                        <div>\r\n" +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Co-Morbidities</h6>\r\n" +
                                                "								<p>" + xcomorbidities + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    }


                                    if (xfamcheck.equals("0")) {
                                        if (xfamilyhistory.equals("")) {
                                            s += "                        <div>\r\n"

                                                +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Family History</h6>\r\n" +
                                                "								<p>" + xfamilyhistory + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    }

                                    if (xinvescheck.equals("0")) {
                                        if (xinvestigationhistroy.equals("")) {
                                            s += "                        <div>\r\n" +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Investigation History</h6>\r\n" +
                                                "								<p>" + xinvestigationhistroy + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    }

                                    if (xsocialhistory.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Personal/Social History</h6>\r\n" +
                                            "								<p>" + xsocialhistory + "<p/>" +
                                            "								<p>" + xother + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xallergycheck.equals("0")) {
                                        if (xdrugall.equals("") || xfoodall.equals("")) {
                                            s += "                  <div>\r\n" +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">H/O Allergy</h6>\r\n" +
                                                "								<p>Drug : " + xdrugall + "<p/>" +
                                                "								<p>Food : " + xfoodall + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "";
                                    }

                                    if (xrisk.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Risk Factor</h6>\r\n" +
                                            "								<p>" + xrisk + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                }
                                
                                rs10 = stmt.executeQuery(sql10);
                                //System.out.println(rs10);
                                rs10.setFetchSize(10000);
                                while (rs10.next()) {
                                    records7++;
                                    xrow = rs10.getString("xrow");
                                    String xlong = rs10.getString("xlong");
                                    String xduration1 = rs10.getString("xduration1");
                                    String xdurationday = rs10.getString("xdurationday");
                                    String xnote = rs10.getString("xnote");
                                    if (xrow.equals("0")) {
                                        s += "";
                                    } else {
                                        if (records7 == 1) {
                                            s += "<h6 class=\"d-header\">Risk Factor</h6>\r\n";
                                        }
                                        s += "<div class=\"symptoms\">\r\n" +
                                            "<span style=\"\r\n"
                                            + "    margin-left: 20px;\r\n"
                                            + "\">" + xlong +"   "+xduration1+" "+xdurationday+"<br></span>";
                                        			 
                                		 if(xnote.equals("")) {
                                			 s +="";
                                		 }
                                		 else {
                                			 s +="<b>Note :"+xnote+"";
                                		 }
                                		s+= "</b> </div>\r\n";
                                    }
                                }

                                s += " <div class=\"symptoms\">\r\n";
                                stmt5 = con.createStatement();
                                rs15 = stmt5.executeQuery(sql15);
                                //System.out.println(rs15);
                                while (rs15.next()) {
                                	records14++;
                                		String xlprofiledt = rs15.getString("xlprofiledt");
                                        String xlprofiletc = rs15.getString("xlprofiletc");
                                        String xlprofileldl = rs15.getString("xlprofileldl");
                                        String xlprofilehdl = rs15.getString("xlprofilehdl");
                                        String xlprofiletg = rs15.getString("xlprofiletg");
                                        String xlprofilech = rs15.getString("xlprofilech");
                                        String xresultval = rs15.getString("xresultval");
                                        String xlprofilecpk = rs15.getString("xlprofilecpk"); 
                                        String xechola = rs15.getString("xechola");
                                        String xecholvidd = rs15.getString("xecholvidd");
                                        String xecholvids = rs15.getString("xecholvids");
                                        String xechoef = rs15.getString("xechoef");
                                        String xecholvef = rs15.getString("xecholvef");
                                        String xechorwma = rs15.getString("xechorwma");
                                        String xchodt = rs15.getString("xchodt");
                                        String xcreatinine = rs15.getString("xcreatinine");
                                        String xna = rs15.getString("xna");
                                        String xk = rs15.getString("xk");
                                        String xcl = rs15.getString("xcl");
                                        String xca = rs15.getString("xca");
                                        String xmg = rs15.getString("xmg");
                                        String xhco3 = rs15.getString("xhco3");
                                        String xtdate2 = rs15.getString("xcreatininedt");
                                        String xtroponindt = rs15.getString("xtroponindt");
                                        String xlprofileunit = rs15.getString("xlprofileunit");
                                        String xechopasp = rs15.getString("xechopasp");
                                        String xhstroponin = rs15.getString("xhstroponin");
                                        String xhstroponindtunit = rs15.getString("xhstroponindtunit");
                                        String xhstroponindt = rs15.getString("xhstroponindt");
                                        String xserumelecdt = rs15.getString("xserumelecdt");
                                        String xnt = rs15.getString("xnt");
                                        String xtc = rs15.getString("xtc");
                                        String xhb = rs15.getString("xhb");
                                        String xplatelate = rs15.getString("xplatelate");
                                        String xast = rs15.getString("xast");
                                        
                                        																																									
                                        if (records14==1) {
                                        	//System.out.println("WOrld");
                                        	if (xlprofiledt.equals("") && xlprofiletc.equals("") && xlprofileldl.equals("") && xlprofilehdl.equals("")&& xlprofiletg.equals("") && xlprofilech.equals("") && xlprofilech.equals("") && xresultval.equals("")&& xechola.equals("") && xecholvidd.equals("") && xecholvids.equals("") && xechoef.equals("")&&xecholvef.equals("") && xechorwma.equals("")&& xchodt.equals("") && xcreatinine.equals("") && xna.equals("") && xk.equals("") && xcl.equals("")&& xmg.equals("") && xca.equals("")&& xhco3.equals("")&& xtdate2.equals("") && xtroponindt.equals("") && xlprofileunit.equals("")&& xechopasp.equals("")&& xhstroponin.equals("")&& xhstroponindtunit.equals("")&& xhstroponindt.equals("")&& xserumelecdt.equals("")&& xnt.equals("") && xtc.equals("") && xhb.equals("") && xplatelate.equals("") && xast.equals("")) 
                                              {
                                        		s += "";  
                                              }
                                              else {
                                            	//  System.out.println("World");
                                            	  s += "<h6 class=\"d-header\">Investigation History</h6>\r\n";
                                              }
                                        }
                                        s += "<p><p/>";
                                        }
                                   s += " </div>\r\n";
                                   
                                   rs8 = stmt.executeQuery(sql8);
                                   rs8.setFetchSize(10000);
                                   s+="<table style=\" margin-left: 20px;\">";
                                   while (rs8.next()) {
                                   	records8++;
                                   		String xlprofiledt = rs8.getString("xlprofiledt");
                                   		String xlprofiletc = rs8.getString("xlprofiletc");
                                   		String xlprofileldl = rs8.getString("xlprofileldl");
                                   		String xlprofilehdl = rs8.getString("xlprofilehdl");
                                   		String xlprofiletg = rs8.getString("xlprofiletg");
                                   		String xlprofileunit = rs8.getString("xlprofileunit");
                                   		String xresultval = rs8.getString("xresultval"); 
                                      
                                     if (records8==1) {
                                       	  if (xlprofiletc.equals("0.") &&xlprofileldl.equals("") && xlprofilehdl.equals("0.")&&xlprofiletg.equals("") && xresultval.equals("0.00") &&xlprofileunit.equals("")&&xlprofiledt!="") 
                                             {
                                       		 s+="<table style=\" margin-left: 20px; style=\"display: none;\"\">";
                                             }
                                             else {
                                            	 
                             					s += "<h6 class=\"d-header\" style=\"margin-left: 10px;\">Lipid Profile ("+xlprofileunit+") </h6>\r\n";
                             				  }
	                                       s+="<tr>";
	                                       if (xlprofiletc.equals("0.")) {
	                                      		s += "<th></th>";
	                                      	}
	                                      	else {
	                                      		s+="<th>TC</th>";
	                                      	}
	                                      	
	                                      	if (xlprofilehdl.equals("0.")) {
	                                      		s += "<th></th>";
	                                      	}
	                                      	else {
	                                      		s+="<th>HDL</th>";
	                                      	}
	                                      	if (xresultval.equals("0.00")) {
	                                      		s += "<th></th>";
	                                      	}
	                                      	else {
	                                      		s+="<th>TC/HDL Ratio</th>";
	                                      	}
	                                   	if (xlprofileldl.equals("")) {
	                                   		s += "<th></th>";
	                                   	}
	                                   	else {
	                                   		s+="<th>LDL</th>";
	                                   	}
	                                   	if (xlprofiletg.equals("")) {
	                                   		s += "<th></th>";
	                                   	}
	                                   	else {
	                                   		s+="<th>TG</th>";
	                                   	}	      
                                   
                                   	if (xlprofiledt.equals("1899-12-30") || xlprofiledt.equals("2998-12-28") || xlprofiledt.equals("")) {
                                    		s += "<th></th>";
                                    	}
                                   	else if (xlprofiletc.equals("0.") &&xlprofileldl.equals("") && xresultval.equals("0.00") && xlprofilehdl.equals("0.")&&xlprofiletg.equals("")&&xlprofileunit.equals("") && xlprofiledt!="" ) 
                                       {
                                   		s += "<th></th>";
                                    	}
                                       
                                    	else {
                                    		s+="<th>Date</th>";
                                    	}

                                        s+="</tr>";
                                     	}
                                       s+="<tr>";
                                       	if (xlprofiletc.equals("0.")) {
                                       		s += "<td></td>";
                                       	}
                                       	else {
                                       		s+="<td class=\"td-gap\">"+xlprofiletc+"</td>";
                                       	}
                                       	
                                       	if (xlprofilehdl.equals("0.")) {
                                       		s += "<td></td>";
                                       	}
                                       	else {
                                       		s+="<td class=\"td-gap\">"+xlprofilehdl+"</td>";
                                       	}
                                       	if (xresultval.equals("0.00")) {
                                       		s += "<td></td>";
                                       	}
                                       	else {
                                       		s+="<td class=\"td-gap\">"+xresultval+"</td>";
                                       	}
                                       	if (xlprofileldl.equals("")) {
                                       		s += "<td></td>";
                                       	}
                                       	else {
                                       		s+="<td class=\"td-gap\">"+xlprofileldl+"</td>";
                                       	}
                                       	if (xlprofiletg.equals("")) {
                                       		s += "<td></td>";
                                       	}
                                       	else {
                                       		s+="<td class=\"td-gap\">"+xlprofiletg+"</td>";
                                       	}	      
                       	            	
                       	            	if (xlprofiledt.equals("1899-12-30") || xlprofiledt.equals("2998-12-28") || xlprofiledt.equals("")) {
                       	            	
                                        		s += "<td></td>";
                                        	}
                       	            	else if (xlprofiletc.equals("0.") &&xlprofileldl.equals("") && xresultval.equals("0.00") && xlprofilehdl.equals("0.")&&xlprofiletg.equals("")&&xlprofileunit.equals("") && xlprofiledt!="" ) 
                                           {
                                       		s += "<td></td>";
                                        	}
                                        	else {
                                        		s+="<td class=\"td-gap\">"+xlprofiledt+"</td>";
                                        	}
                       	            s+="</tr>";
                                   }
                                s+="</table>";
                                
                                rs12 = stmt.executeQuery(sql12);
                                rs12.setFetchSize(10000);
                                s+="<table style=\"margin-left: 20px;\">";
                                while (rs12.next()) {
                                	records9++;
                                	
                                	String xechola = rs12.getString("xechola");
                                    String xecholvidd = rs12.getString("xecholvidd");
                                    String xecholvids = rs12.getString("xecholvids");
                                    String xechoef = rs12.getString("xechoef");
                                    String xecholvef = rs12.getString("xecholvef");
                                    String xechorwma = rs12.getString("xechorwma");
                                    String xchodt = rs12.getString("xchodt");
                                    String xechopasp =rs12.getString("xechopasp");
                                    String xechounit = rs12.getString("xechounit");
                                    
                                   if (records9==1) {
                                    
                                    	  if (xechola.equals("") && xechopasp.equals("") &&xecholvidd.equals("") &&xecholvids.equals("") && xechoef.equals("")&&xecholvef.equals("") && xechorwma.equals("") && xechounit.equals("")&& xchodt!="") 
                                          {
                                    		  s+="<table style=\" margin-left: 20px; style=\"display: none;\"\">";
                                          }
                                          else {
                                        	  
                                        	  s += "<h6 class=\"d-header\" style=\"margin-left: 10px;\">Echo ("+xechounit+")</h6>\r\n";
                          				
                          				  }
                                    s+="<tr>";
                                    if (xechola.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<th>LA</th>";
                                	}
                                	if (xecholvidd.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<th>LVIDd</th>";
                                	}
                                	if (xecholvids.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<th>LVIDs</th>";
                                	}
                                	if (xechopasp.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<th>PASP</th>";
                                	}	      
                                	if (xecholvef.equals("")) {
                                 		s += "<th></th>";
                                 	}
                                 	else {
                                 		s+="<th>LVEF(%)</th>";
                                 	}
                                	if (xechorwma.equals("")) {
                                 		s += "<th></th>";
                                 	}
                                 	else {
                                 		s+="<th>RWMA</th>";
                                 	}
                                	
                                	if (xchodt.equals("1899-12-30") || xchodt.equals("2998-12-28") || xchodt.equals("")) {
                                 		s += "<th></th>";
                                 	}
                                	else if (xechorwma.equals("") && xecholvef.equals("")&& xechopasp.equals("") &&  xecholvids.equals("") && xecholvidd.equals("") && xechola.equals("") ) {
                                		s += "<th></th>";
                                 	}
                                 	else {
                                 		s+="<th>Date</th>";
                                 	}
                                     s+="</tr>";
                                     
                                   }
                                    s+="<tr>";
                                    if (xechola.equals("")) {
                                    	s += "<td></td>";
                                	}
                                	else {
                                		s+="<td class=\"td-gap\">"+xechola+"</td>";
                                	}
                                	if (xecholvidd.equals("")) {
                                		s += "<td></td>";
                                	}
                                	else {
                                		s+="<td class=\"td-gap\">"+xecholvidd+"</td>";
                                	}
                                	if (xecholvids.equals("")) {
                                		s += "<td></td>";
                                	}
                                	else {
                                		s+="<td class=\"td-gap\">"+xecholvids+"</td>";
                                	}
                                	if (xechopasp.equals("")) {
                                		s += "<td></td>";
                                	}
                                	else {
                                		s+="<td class=\"td-gap\">"+xechopasp+"</td>";
                                	}	      
                                	if (xecholvef.equals("")) {
                                 		s += "<td></td>";
                                 	}
                                 	else {
                                 		s+="<td class=\"td-gap\">"+xecholvef+"</td>";
                                 	}
                                	if (xechorwma.equals("")) {
                                 		s += "<td></td>";
                                 	}
                                 	else {
                                 		s+="<td class=\"td-gap\">"+xechorwma+"</td>";
                                 	}
                                	
                                	if (xchodt.equals("1899-12-30") || xchodt.equals("2998-12-28") || xchodt.equals("") ) {
                                 		s += "<td></td>";
                                 	}
                                	else if (xechorwma.equals("") && xecholvef.equals("")&& xechopasp.equals("") &&  xecholvids.equals("") && xecholvidd.equals("") && xechola.equals("")) {
                                		s += "<td></td>";
                                 	}
                                	
                                 	else {
                                 		s+="<td class=\"td-gap\">"+xchodt+"</td>";
                                 	}
                                	s+="</tr>";   
                                }
                                s+="</table>";

                                rs17 = stmt.executeQuery(sql17);
                                rs17.setFetchSize(10000);

                                s+="<table style=\"margin-left: 20px;\">";
                                while (rs17.next()) {
                                	records16++;
                                	
                                	
                                	String xhco3 = rs17.getString("xhco3");
                                	String xca = rs17.getString("xca");
                                    String xna = rs17.getString("xna");
                                    String xk = rs17.getString("xk");
                                    String xcl = rs17.getString("xcl");
                                    String xmg = rs17.getString("xmg");
                                    String xserumelecdt = rs17.getString("xserumelecdt");
                                    
                                    if (records16==1) {
                                    	if (xhco3.equals("") && xna.equals("") &&xk.equals("") && xcl.equals("")&&xmg.equals("") &&xca.equals("")&&xserumelecdt!="") 
                                        {
                                    		s+="<table style=\"margin-left: 20px;display: none;\">";
                                        	
                                        }
                                        else {
                                        	
                        					s += "<h6 class=\"d-header\" style=\"margin-left: 10px;\">Serum Electrolytes (mmol/L)</h6>\r\n";
                        				  }
                                    s+="<tr>";
                                    //System.out.println("Hoooo");
                                	if (xna.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<th>Na</th>";
                                	}
                                	if (xk.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<th> K</th>";
                                	}
                                	if (xcl.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<th>Cl</th>";
                                	}
                                    if (xca.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<th>Ca</th>";
                                	}
                                	if (xmg.equals("")) {
                                 		s += "<th></th>";
                                 	}
                                 	else {
                                 		s+="<th>Mg</th>";
                                 	}
                                	if (xhco3.equals("")) {
                                 		s += "<th></th>";
                                 	}
                                 	else {
                                 		s+="<th>HCO3</th>";
                                 	}
                                	if (xserumelecdt.equals("1899-12-30") || xserumelecdt.equals("2998-12-28") || xserumelecdt.equals("2999-01-01")|| xserumelecdt.equals("")) {
                                 		s += "<th></th>";
                                 	}
                                	else if (xna.equals("") && xhco3.equals("")&& xmg.equals("") &&  xca.equals("") && xcl.equals("") && xk.equals("") ) {
                                		s += "<th></th>";
                                 	}
                                 	else {
                                 		s+="<th>Date</th>";
                                 	}
                                     s+="</tr>";
                                    }
                                    s+="<tr>";
                                    
                                	if (xna.equals("")) {
                                		s += "<td></td>";
                                	}
                                	else {
                                		s+="<td class=\"td-gap\">"+xna+"</td>";
                                	}
                                	if (xk.equals("")) {
                                		s += "<td></td>";
                                	}
                                	else {
                                		s+="<td class=\"td-gap\">"+xk+"</td>";
                                	}
                                	if (xcl.equals("")) {
                                		s += "<td></td>";
                                	}
                                	else {
                                		s+="<td class=\"td-gap\">"+xcl+"</td>";
                                	}	
                                	if (xca.equals("")) {
                                		s += "<th></th>";
                                	}
                                	else {
                                		s+="<td class=\"td-gap\">"+xca+"</td>";
                                	}
                                	if (xmg.equals("")) {
                                 		s += "<td></td>";
                                 	}
                                 	else {
                                 		s+="<td class=\"td-gap\">"+xmg+"</td>";
                                 	}
                                	if (xhco3.equals("")) {
                                 		s += "<td></td>";
                                 	}
                                 	else {
                                 		s+="<td class=\"td-gap\">"+xhco3+"</td>";
                                 	}
                                	if (xserumelecdt.equals("1899-12-30") || xserumelecdt.equals("2998-12-28") || xserumelecdt.equals("2999-01-01")|| xserumelecdt.equals("")) {
                                 		s += "<td></td>";
                                 	}
                                	else if (xna.equals("") && xhco3.equals("")&& xmg.equals("") &&  xca.equals("") && xcl.equals("") && xk.equals("") ) {
                                		s += "<td></td>";
                                 	}
                                 	else {
                                 		s+="<td class=\"td-gap\">"+xserumelecdt+"</td>";
                                 	}

                                	s+="</tr>";
                                }
                                
                                s+="</table>";
                                
                                rs24 = stmt.executeQuery(sql24);
                                rs24.setFetchSize(10000);
                                s+="<table style=\" margin-left: 20px;\">";
                                while (rs24.next()) {
                                	records24++;
                                	
                                  String xett = rs24.getString("xett");
                                  String xett1 = rs24.getString("xett1");
                                  String xett2 = rs24.getString("xett2");
                                  String xettdate = rs24.getString("xettdate");
                              
                                  if (records24==1) {
                                    	  if (xett.equals("") && xett1.equals("") && xett2.equals("") && xettdate!="") 
                                          {
                                    		  s+="<table style=\"margin-left: 20px;display: none;\">";
                                          }
                                          else {
                                        	  
                          					s += "<h6 class=\"d-header\" style=\"margin-left: 10px;\">ETT </h6>\r\n";
                          				  } 
                                    s+="<tr>";
                                    if (xett.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>ETT</th>";
                                   	}
                                   	
                                   	if (xett1.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>HR (%)</th>";
                                   	}
                                   	if (xett2.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>METS</th>";
                                   	}
                                   	if (xettdate.equals("1899-12-30") || xettdate.equals("2998-12-28") || xettdate.equals("2999-01-01")|| xettdate.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                  	else if (xett.equals("") && xett1.equals("")&& xett2.equals("") ) {
                                  		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>Date</th>";
                                   	}
                                     s+="</tr>";
                                  	}
                                    s+="<tr>";
                                    	if (xett.equals("")) {
                                    		s += "<td></td>";
                                    	}
                                    	else {
                                    		s+="<td class=\"td-gap\">"+xett+"</td>";
                                    	}
                                    	
                                    	if (xett1.equals("")) {
                                    		s += "<td></td>";
                                    	}
                                    	else {
                                    		s+="<td class=\"td-gap\">"+xett1+"</td>";
                                    	}
                                    	if (xett2.equals("")) {
                                    		s += "<td></td>";
                                    	}
                                    	else {
                                    		s+="<td class=\"td-gap\">"+xett2+"</td>";
                                    	}
                                    	if (xettdate.equals("1899-12-30") || xettdate.equals("2998-12-28") || xettdate.equals("2999-01-01")|| xettdate.equals("")) {
                                   		s += "<td></td>";
                                   	}
                                    	else if (xett.equals("") && xett1.equals("")&& xett2.equals("") ) {
                                  		s += "<td></td>";
                                   	}
                                   	else {
                                   		s+="<td class=\"td-gap\">"+xettdate+"</td>";
                                   	}
                    	            s+="</tr>";
                                }
                                s+="</table>";

                                rs22 = stmt.executeQuery(sql22);
                                rs22.setFetchSize(10000);
                                s+="<table style=\" margin-left: 20px;\">";
                                while (rs22.next()) {
                                	records22++;
                                	
                                	String xtc = rs22.getString("xtc");
                                    String xhb = rs22.getString("xhb");
                                    String xplatelate = rs22.getString("xplatelate");
                              
                                  if (records22==1) {
                                    	  if (xtc.equals("") &&xhb.equals("") && xplatelate.equals("")) 
                                          {
                                    		  s+="<table style=\"margin-left: 20px;display: none;\">";
                                          }
                                          else {
                                        	  
                          					s += "<h6 class=\"d-header\" style=\"margin-left: 10px;\">CBC </h6>\r\n";
                          				  }
                                    s+="<tr>";
                                    if (xtc.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>TC</th>";
                                   	}
                                   	
                                   	if (xhb.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>HB (%)</th>";
                                   	}
                                   	if (xplatelate.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>Platelet</th>";
                                   	}
                                     s+="</tr>";
                                  }
                                    
                                    s+="<tr>";
                                    	if (xtc.equals("")) {
                                    		s += "<td></td>";
                                    	}
                                    	else {
                                    		s+="<td class=\"td-gap\">"+xtc+"</td>";
                                    	}
                                    	
                                    	if (xhb.equals("")) {
                                    		s += "<td></td>";
                                    	}
                                    	else {
                                    		s+="<td class=\"td-gap\">"+xhb+"</td>";
                                    	}
                                    	if (xplatelate.equals("")) {
                                    		s += "<td></td>";
                                    	}
                                    	else {
                                    		s+="<td class=\"td-gap\">"+xplatelate+"</td>";
                                    	}
                    	            s+="</tr>";
                                }
                                s+="</table>";
                                
                                rs23 = stmt.executeQuery(sql23);
                                rs23.setFetchSize(10000);
                                s+="<table style=\"margin-left: 20px;\">";
                                while (rs23.next()) {
                                	records23++;
                                	
                                	String xlprofilealt = rs23.getString("xlprofilealt");
                                    String xast = rs23.getString("xast");

                                  if (records23==1) {
                                    	  if (xlprofilealt.equals("") &&xast.equals("") ) 
                                          {
                                    		  s+="<table style=\"margin-left: 20px;display: none;\">";
                                          }
                                          else {
                                        	  
                          					s += "<h6 class=\"d-header\" style=\"margin-left: 10px;\">LFT (U/L) </h6>\r\n";
                          				  }

                                    s+="<tr>";
                                    if (xlprofilealt.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>ALT</th>";
                                   	}
                                   	
                                   	if (xast.equals("")) {
                                   		s += "<th></th>";
                                   	}
                                   	else {
                                   		s+="<th>AST</th>";
                                   	}
                                     s+="</tr>";
                                  }
                                   
                                    s+="<tr>";
                                    	if (xlprofilealt.equals("")) {
                                    		s += "<td></td>";
                                    	}
                                    	else {
                                    		s+="<td class=\"td-gap\">"+xlprofilealt+"</td>";
                                    	}
                                    	if (xast.equals("")) {
                                    		s += "<td></td>";
                                    	}
                                    	else {
                                    		s+="<td class=\"td-gap\">"+xast+"</td>";
                                    	}
                    	            s+="</tr>";
                                }
                                s+="</table>";
                                
                              s += " <div class=\"symptoms\">";
                              rs14 = stmt.executeQuery(sql14);
                              rs14.setFetchSize(10000);
                              while (rs14.next()) {
                            	
                            	String xcreatinine = rs14.getString("xcreatinine");
                                String xtdate1 = rs14.getString("xcreatininedt");
                                String xcreatinineunit = rs14.getString("xcreatinineunit");
                                if (xcreatinine.equals("")) {
                                	s += "";
                            	}
                            	else {
                            		s+="";
                            		s += "<b>S.Creatinine: </b>"+"<span >" + xcreatinine + "  "+xcreatinineunit+"  "+" ("+xtdate1+")"+"</span></br>\r\n";	
                            	}
                              }
                              s += " </div>\r\n";
                       
                              s += "<div class=\"symptoms\">";          
                              rs18 = stmt.executeQuery(sql18);
                              rs18.setFetchSize(10000);
                              while (rs18.next()) {
                            	//records13++;
                            	
                            	String xtroponin = rs18.getString("xtroponin");
                                String xtroponindt = rs18.getString("xtroponindt");
                                String xtroponindtunit = rs18.getString("xtroponindtunit");
                                if (xtroponin.equals("")) {
                                	s += "";
                            	}
                            	else {
                            		s+="";
                            		s += "<b>Troponin I: </b>"+"<span >" + xtroponin + "  "+xtroponindtunit+"  "+" ( "+xtroponindt+" ) "+"</span></br>\r\n";
                            	}
                              }
                              s += " </div>\r\n";
                            
                              s += " <div class=\"symptoms\">";
                              rs21 = stmt.executeQuery(sql21);
                              rs21.setFetchSize(10000);
                              while (rs21.next()) {
                            
                            	String xhstroponin = rs21.getString("xhstroponin");
                                String xhstroponindt = rs21.getString("xhstroponindt");
                                String xhstroponindtunit = rs21.getString("xhstroponindtunit");
                                if (xhstroponin.equals("")) {
                                	s += "";
                            	}
                            	else {
                            		s+="";
                            		s += "<b>HS Troponin I: </b>"+"<span >" + xhstroponin + "  "+xhstroponindtunit+"  "+" ("+xhstroponindt+")"+"</span></br>\r\n";	
                            	}
                              }
                              	s += " </div>\r\n";
                             
                                s += " <div class=\"symptoms\" >\r\n";
                                rs16 = stmt.executeQuery(sql16);
                                rs16.setFetchSize(10000);
                                while (rs16.next()) {
                                	
                                   String xunacr = rs16.getString("xunacr"); 
                                   String xcxrpa = rs16.getString("xcxrpa");
                                   String xecg = rs16.getString("xecg");
                                   String xfbs = rs16.getString("xfbs");
                                   String xesr = rs16.getString("xesr");
                                   String xpbf = rs16.getString("xpbf");
                                   String xhb1ac = rs16.getString("xhb1ac");
                                   String xnt1 = rs16.getString("xnt");
                                   String xvitaminD3 = rs16.getString("xvitaminD3");
                                   String xtsh = rs16.getString("xtsh");
                                    
                                	if (xunacr.equals("")) {
                                		s += "";
                                	}
                                	else {
                                		s += "<b>Urine ACR : </b>"+"<span >" + xunacr + "</span></br>\r\n";
                                	}
                                	if (xcxrpa.equals("")) {
                                		s += "";
                                	}
                                	else {
                                		s += "<b>CXR P/A : </b>"+"<span >" + xcxrpa + "</span></br>\r\n";
                                		
                                	}	      
                                	if (xecg.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		
                                 		s += "<b>ECG : </b>"+"<span >" + xecg + "</span></br>\r\n";
                                 	}
                                	
                                	if (xfbs.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		
                                 		s += "<b>FBS : </b>"+"<span >" + xfbs + "</span></br>\r\n";
                                 	}
                                	if (xnt1.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		
                                 		s += "<b>NT - pro BNP (pg/ml) : </b>"+"<span >" + xnt1 + "</span></br>\r\n";
                                 	}
                                	if (xhb1ac.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		s += "<b>HbA1C : </b>"+"<span >" + xhb1ac + "</span></br>\r\n";
                                 	}
                                	if (xesr.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		s += "<b>ESR : </b>"+"<span >" + xesr + "</span></br>\r\n";
                                 		
                                 	}
                                	if (xpbf.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		s += "<b>PBF : </b>"+"<span >" + xpbf + "</span></br>\r\n";
                                 		
                                 	}
                                	if (xtsh.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		s += "<b>TSH : </b>"+"<span >" + xtsh + "</span></br>\r\n";
                                 		
                                 	}
                                	if (xvitaminD3.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		s += "<b>Vitamin D3 : </b>"+"<span >" + xvitaminD3 + "</span></br>\r\n";
                                 		
                                 	}
                                }
                                s += " </div>\r\n";
                                s += " <div class=\"symptoms\" >\r\n";
                                rs16 = stmt.executeQuery(sql16);
                                rs16.setFetchSize(10000);
                                while (rs16.next()) {
                         
                                   String xother1 = rs16.getString("xother");
                                  																																								
                                	if (xother1.equals("")) {
                                 		s += "";
                                 	}
                                 	else {
                                 		s += "<b>Others : </b>"+"<span >" + xother1 + "</span></br>\r\n";
                                 	} }
                                s += " </div>\r\n";
                          
                                rs2 = stmt.executeQuery(sql2);
                                while (rs2.next()) {
                                    xdrughistory = rs2.getString("xdrughistory");
                                    xdrughistoryothers = rs2.getString("xdrughistoryothers");
                                    xmedicheck = rs2.getString("xmedicheck");
                                 
                                    if (xmedicheck.equals("0")) {
                                        if (xdrughistory.equals("") && xdrughistoryothers.equals("")) {
                                            s += "                        <div>\r\n"
                                                +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Current Medication Details</h6>\r\n" +
                                                "								<p>" + xdrughistory + "<p/>" +
                                                "								<p>" + xdrughistoryothers + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    } else {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } }
                                
                                s += " <div class=\"symptoms\">\r\n";
                                rs9 = stmt.executeQuery(sql9);
                                rs9.setFetchSize(10000);
                                while (rs9.next()) {
                                	String xcag = rs9.getString("xcag");
                                  String xptcadt1 = rs9.getString("xptcadt1");
                                  String xptcadt2 = rs9.getString("xptcadt2");
                                  String xptcadt3 = rs9.getString("xptcadt3");
                                  String xcabgdt = rs9.getString("xcabgdt");
                                  String xcabg = rs9.getString("xcabg");
                                  String xsrgothers = rs9.getString("xsrgothers");
                                  String xptca1 = rs9.getString("xptca1");
                                  String xptca2 = rs9.getString("xptca2");
                                  String xptca3 = rs9.getString("xptca3");
                                  																																								
                                    if (xcag.equals("") && (xptcadt1.equals("") || xptcadt1.equals("1899-12-30")) && (xptcadt2.equals("") ||  xptcadt2.equals("1899-12-30")) && (xptcadt3.equals("") || xptcadt3.equals("1899-12-30")) && (xcabgdt.equals("") || xcabgdt.equals("1899-12-30")) && xcabg.equals("") && xsrgothers.equals("") && xptca1.equals("") && xptca2.equals("") && xptca3.equals("")) {
                                        s += "";
                                    } else {
                                        s += "<h6 class=\"d-header\">Intervention/Surgery History </h6>\r\n";
                                    }

                                    s += "<p><p/>";
                                  
                                    if (xcag.equals("")) {
                                        s += "";
                                    } else {
                                        s += " <p><b>CAG : </b>" + xcag + "</p>\r\n";
                                    }
                                    if ((xptca1.equals("")) && (xptcadt1!="" || xptcadt1.equals("1899-12-30"))) {
                                        s += "";
                                    } else {
                                        s += "<p><b>PTCA : </b>" + xptca1 +"   ("+xptcadt1+")</p>\r\n";
                                    }
                                    if ((xptca2.equals("")) && (xptcadt2!="" ||  xptcadt2.equals("1899-12-30"))) {
                                        s += "";
                                    } else {
                                        s += "<p><b>PTCA : </b>" + xptca2 +"   ("+xptcadt2+")</p>\r\n";
                                    }
                                    if (xptca3.equals("") && (xptcadt3!="" || xptcadt3.equals("1899-12-30"))) {
                                        s += "";
                                    } else {
                                        s += "<p><b>PTCA : </b>" + xptca3 +"   ("+xptcadt3+")</p>\r\n";
                                    }

                                    if ((xcabg.equals(""))&& (xcabgdt!="" || xcabgdt.equals("1899-12-30"))) {
                                        s += "";
                                    } else {
                                        s += "<p><b>CABG : </b>" + xcabg + "   ("+xcabgdt+")</p>\r\n";
                                    }
                                   
                                    if (xsrgothers.equals("")) {
                                        s += "";
                                    } else {
                                        s += "<p>OTHERS : " + xsrgothers + "</p>\r\n";
                                    }
                                }
                                s += " </div>\r\n";

                                s += " <div class=\"symptoms\">\r\n";
                                rs3 = stmt.executeQuery(sql3);
                                rs3.setFetchSize(10000);
                                while (rs3.next()) {
                                    xbp = rs3.getString("xbp");
                                    xrespiration = rs3.getString("xrespiration");
                                    xtemperature = rs3.getString("xtemperature");
                                    xspo2 = rs3.getString("xspo2");
                                    xheightvit = rs3.getString("xheightvit");
                                    xweightvit = rs3.getString("xweightvit");
                                    xbmigyn = rs3.getString("xbmigyn");
                                    xbsa = rs3.getString("xbsa");
                                    xpulserate = rs3.getString("xpulserate");
                                    String xanemia = rs3.getString("xanemia");
                                    String xavpu = rs3.getString("xavpu");
                                    String xofc = rs3.getString("xofc");
                                    String xpalor = rs3.getString("xpalor");
                                    String xodema = rs3.getString("xodema");
                                    String xnote = rs3.getString("xnote");
                                    String xjaundice = rs3.getString("xjaundice");
                                    
                                    if (xbp.equals("") && xrespiration.equals("") && xtemperature.equals("") && xspo2.equals("") && xheightvit.equals("0.00") && xweightvit.equals("0.00") && xbmigyn.equals("0.00") && xbsa.equals("0") && xpulserate.equals("") && xanemia.equals("") && xavpu.equals("") && xofc.equals("") && xnote.equals("") && xpalor.equals("") && xodema.equals("") && xjaundice.equals("")) {
                                                              s += "";
                                   } else {
                                        s += "                            <h6 class=\"d-header\">Vital Signs</h6>\r\n";
                                    }
                                   s += "<p><p/>";
                                    if (xbp.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>BP(mmHg) : " + xbp + "</p>\r\n";
                                    }
                                    if (xpulserate.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>PR(min) : " + xpulserate + "</p>\r\n";
                                    }
                                    if (xrespiration.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>RR(min) : " + xrespiration + "</p>\r\n";
                                    }
                                    if (xtemperature.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>TEMP(°F) : " + xtemperature + "</p>\r\n";
                                    }
                                    if (xspo2.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>SPO2(%) : " + xspo2 + "</p>\r\n";
                                    }
                                    if (xheightvit.equals("0.00")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>HT(cm) : " + xheightvit + "</p>\r\n";
                                    }
                                    if (xweightvit.equals("0.00")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>WT(Kg) : " + xweightvit + "</p>\r\n";
                                    }
                                    if (xbmigyn.equals("0.00")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>BMI(Kg/m2) : " + xbmigyn + "</p>\r\n";
                                    }
                                    if (xbsa.equals("0")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>BSA(m2) : " + xbsa + "</p>\r\n";
                                    }
                                    if (xanemia.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>Anaemia : " + xanemia + "</p>\r\n";
                                    }
                                    if (xavpu.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>Ankle Oedema : " + xavpu + "</p>\r\n";
                                    }
                                    
                                    if (xjaundice.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>Jaundice : " + xjaundice + "</p>\r\n";
                                    }
                                    if (xofc.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>JVP : " + xofc + "</p>\r\n";
                                    }
                                    if (xpalor.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>Pallor : " + xpalor + "</p>\r\n";
                                    }
                                    if (xodema.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>Oedema : " + xodema + "</p>\r\n";
                                    }
                                    if (xnote.equals("")) {
                                        s += "";
                                    } else {
                                        s += "                            <p>Others : " + xnote + "</p>\r\n";
                                    }
                                }
                                s += " </div>\r\n";
                                
                                rs5 = stmt.executeQuery(sql5);
                                rs5.setFetchSize(10000);
                                while (rs5.next()) {
                                    records3++;
                                    xrow = rs5.getString("xrow");
                                    String xlong = rs5.getString("xlong");
                                    String xcode = rs5.getString("xcode");
                                    String xnote = rs5.getString("xnote");

                                    if (xrow.equals("")) {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    } else {
                                        if (records3 == 1) {
                                            s += "                            <h6 class=\"d-header\">Physical Examination</h6>\r\n";
                                        }
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "								<p>" + xrow + ". " + xlong + " - " + xcode + " <p/>" +
                                            "                            	<p>" + xnote + "</p>\r\n";
                                        }
                                    s += "                        </div>\r\n";

                                }
                                rs6 = stmt.executeQuery(sql6);
                                while (rs6.next()) {
                                    String xgeneral = rs6.getString("xgeneral");
                                    String xchest = rs6.getString("xchest");
                                    String xoutlook = rs6.getString("xoutlook");
                                    String xfeel = rs6.getString("xfeel");
                                    String xmove = rs6.getString("xmove");
                                    String xpae = rs6.getString("xpae");
                                    String xphysicianassist = rs6.getString("xphysicianassist");
                                    String xpr = rs6.getString("xpr");
                                    String xfetalheart = rs6.getString("xfetalheart");
                                    String xlungs = rs6.getString("xlungs");
                                    String xabdomen = rs6.getString("xabdomen");
                                    String xepinspection = rs6.getString("xepinspection");
                                    String xeppalpation = rs6.getString("xeppalpation");
                                    String xeppercussion = rs6.getString("xeppercussion");
                                    String xepauscultation = rs6.getString("xepauscultation");
                                    String xps = rs6.getString("xps");
                                    String xoncology = rs6.getString("xoncology");
                                    String xpve = rs6.getString("xpve");
                                    String xsysenquiry = rs6.getString("xsysenquiry");
                                    String xgcs = rs6.getString("xgcs");
                   					String xvision = rs6.getString("xvision");
                   					String xhearing = rs6.getString("xhearing");
                   					String xspeach = rs6.getString("xspeach");
                   					String xgate = rs6.getString("xgate");
                   					String xstance = rs6.getString("xstance");
                   					String xcerebellar = rs6.getString("xcerebellar");
                   					String xsensation = rs6.getString("xsensation");
                   					String xmusclepower = rs6.getString("xmusclepower");
                   					String xreflexes = rs6.getString("xreflexes");
                   					String xplantar = rs6.getString("xplantar");
                   					String xholtman = rs6.getString("xholtman");
                   					String xexamperipheral = rs6.getString("xexamperipheral");
                   					String xcardiovascular = rs6.getString("xcardiovascular");
                   					String xhead = rs6.getString("xhead");
                   					String xface = rs6.getString("xface");
                   					String xeyeball = rs6.getString("xeyeball");
                   					String xorbit = rs6.getString("xorbit");
                   					String xeyelibs = rs6.getString("xeyelibs");
                   					String xlacrimalsac = rs6.getString("xlacrimalsac");
                   					String xlacrimalgland = rs6.getString("xlacrimalgland");
                   					String xsclera = rs6.getString("xsclera");
                   					String xconjunctive = rs6.getString("xconjunctive");
                   					String xcorhea = rs6.getString("xcorhea");
                   					String xeyeac = rs6.getString("xeyeac");
                   					String xpupil = rs6.getString("xpupil");
                   					String xiris = rs6.getString("xiris");
                   					String xlens = rs6.getString("xlens");
                   					String xiop = rs6.getString("xiop");
                   					String xfurdi = rs6.getString("xfurdi");
                   					
                                    if (xoncology.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                  <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Physical Examination</h6>\r\n" +
                                            "								<p>" + xoncology + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xgeneral.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">General</h6>\r\n" +
                                            "								<p>" + xgeneral + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xchest.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Chest</h6>\r\n" +
                                            "								<p>" + xchest + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xoutlook.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Look</h6>\r\n" +
                                            "								<p>" + xoutlook + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xfeel.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Feel</h6>\r\n" +
                                            "								<p>" + xfeel + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xmove.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Move</h6>\r\n" +
                                            "								<p>" + xmove + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xpae.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">P/A/E</h6>\r\n" +
                                            "								<p>" + xpae + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xphysicianassist.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Abdomen</h6>\r\n" +
                                            "								<p>" + xphysicianassist + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    
                                    if (xpr.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">P/R</h6>\r\n" +
                                            "								<p>" + xpr + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xfetalheart.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Heart</h6>\r\n" +
                                            "								<p>" + xfetalheart + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xlungs.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Lungs</h6>\r\n" +
                                            "								<p>" + xlungs + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xabdomen.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Abdomen</h6>\r\n" +
                                            "								<p>" + xabdomen + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    
                                    if (xcardiovascular.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Cardiovascular System</h6>\r\n" +
                                            "								<p>" + xcardiovascular + "<p/>" +
                                            "                        </div>\r\n";
                                    }


                                    if (xepinspection.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Inspection</h6>\r\n" +
                                            "								<p>" + xepinspection + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xeppalpation.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Palpation</h6>\r\n" +
                                            "								<p>" + xeppalpation + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xeppercussion.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Percussion</h6>\r\n" +
                                            "								<p>" + xeppercussion + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xepauscultation.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Auscultation</h6>\r\n" +
                                            "								<p>" + xepauscultation + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xps.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">P/S</h6>\r\n" +
                                            "								<p>" + xps + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xpve.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">P/V/E</h6>\r\n" +
                                            "								<p>" + xpve + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xsysenquiry.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Other Systemic</h6>\r\n" +
                                            "								<p>" + xsysenquiry + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xgcs.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">GCS</h6>\r\n" +
                                            "								<p>" + xgcs + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    
                                    if (xvision.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Vision</h6>\r\n" +
                                            "								<p>" + xvision + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xhearing.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Hearing</h6>\r\n" +
                                            "								<p>" + xhearing + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    
                                    if (xspeach.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Speech</h6>\r\n" +
                                            "								<p>" + xspeach + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xgate.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Gait</h6>\r\n" +
                                            "								<p>" + xgate + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xstance.equals("")) {
                                        s += "<div>\r\n"+
                                        	 "</div>\r\n";
                                    } else {
                                        s += "<div class=\"symptoms\">\r\n" +
                                            "    <h6 class=\"d-header\">Stance</h6>\r\n" +
                                            "	 <p>" + xstance + "<p/>" +
                                            "</div>\r\n";
                                    }
                                    if (xcerebellar.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Cerebeller Sign</h6>\r\n" +
                                            "								<p>" + xcerebellar + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xsensation.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Sensation</h6>\r\n" +
                                            "								<p>" + xsensation + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    
                                    if (xmusclepower.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Muscle Power</h6>\r\n" +
                                            "								<p>" + xmusclepower + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    
                                    if (xreflexes.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Reflexes</h6>\r\n" +
                                            "								<p>" + xreflexes + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xplantar.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Plantar Response</h6>\r\n" +
                                            "								<p>" + xplantar + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xholtman.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Holtman's sign</h6>\r\n" +
                                            "								<p>" + xholtman + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xexamperipheral.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Exam of Head & Peripheral Nerve Lesion</h6>\r\n" +
                                            "								<p>" + xexamperipheral + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xhead.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Head</h6>\r\n" +
                                            "								<p>" + xhead + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xface.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Face</h6>\r\n" +
                                            "								<p>" + xface + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xeyeball.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Eyeball</h6>\r\n" +
                                            "								<p>" + xeyeball + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xorbit.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Orbit</h6>\r\n" +
                                            "								<p>" + xorbit + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xeyelibs.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Eyelids</h6>\r\n" +
                                            "								<p>" + xeyelibs + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xlacrimalsac.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Lacrimal sac</h6>\r\n" +
                                            "								<p>" + xlacrimalsac + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xlacrimalgland.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Lacrimal Gland</h6>\r\n" +
                                            "								<p>" + xlacrimalgland + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xconjunctive.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Conjunctiva</h6>\r\n" +
                                            "								<p>" + xconjunctive + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xsclera.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Sclera</h6>\r\n" +
                                            "								<p>" + xsclera + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xcorhea.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Cornea</h6>\r\n" +
                                            "								<p>" + xcorhea + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xeyeac.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">A/C</h6>\r\n" +
                                            "								<p>" + xeyeac + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xpupil.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Pupil</h6>\r\n" +
                                            "								<p>" + xpupil + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xiris.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Iris</h6>\r\n" +
                                            "								<p>" + xiris + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    
                                    if (xlens.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Lens</h6>\r\n" +
                                            "								<p>" + xlens + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xiop.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">IOP</h6>\r\n" +
                                            "								<p>" + xiop + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xfurdi.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Fundi</h6>\r\n" +
                                            "								<p>" + xfurdi + "<p/>" +
                                            "                        </div>\r\n";
                                    }}
                                rs4 = stmt.executeQuery(sql4);
                                rs4.setFetchSize(10000);
                                while (rs4.next()) {
                                    records2++;
                                    xrow = rs4.getString("xrow");
                                    xrem = rs4.getString("xrem");
                                    xdesc = rs4.getString("xdesc");
                                    if (records2 == 1) {
                                        s += "                        <div class=\"symptoms\">\r\n";
                                        s += "                            <h6 class=\"d-header\">Investigation Advised</h6>\r\n";
                                    }
                                    s += "                            <p>" + records2 + ". " + xdesc + "</p>\r\n" +
                                        "                            <p>" + xrem + "</p>\r\n";
                                }
                                s += " </div>\r\n";
                                rs6 = stmt.executeQuery(sql6);
                                while (rs6.next()) {

                                    String xprovdiagnosis = rs6.getString("xprovdiagnosis");
                                    String xedspecialist = rs6.getString("xedspecialist");
                                    String xfollowupadvice = rs6.getString("xfollowupadvice");
                                    String xadvice = rs6.getString("xadvice");
                                    String xplanecare = rs6.getString("xplanecare");
                                    String xpatadvice = rs6.getString("xpatadvice");
                                    String xadadvice = rs6.getString("xadadvice");
                                    String xoncology = rs6.getString("xoncology");
                                    String xfollwadviceother1 = rs6.getString("xfollwadviceother");
                                    String xrefdoctordesc = rs6.getString("xrefdoctordesc"); 
                                    String designationname1 = rs6.getString("designationname");
                                    String xprofdegree1 = rs6.getString("xprofdegree"); 
                                    String departmentname1 = rs6.getString("departmentname");

                                    if (xprovdiagnosis.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Clinical Diagnosis</h6>\r\n" +
                                            "								<p>" + xprovdiagnosis + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xedspecialist.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Diet Advice</h6>\r\n" +
                                            "								<p>" + xedspecialist + "<p/>" +
                                            "                        </div>\r\n";
                                    	}
                                        if (xfollowupadvice.equals("") && xfollwadviceother1.equals("")) {
                                            s += "                      <div>\r\n" +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                      <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Follow-up & Advice</h6>\r\n" +
                                                "								<p>" + xfollowupadvice + "<p/>" +
                                                "								<p>" + xfollwadviceother1 + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    if (xadvice.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Advice</h6>\r\n" +
                                            "								<p><p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xplanecare.equals("")) {
                                        s += "                        <div>\r\n"

                                            +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Plan:</h6>\r\n" +
                                            "								<p>" + xplanecare + "<p/>" +
                                            "                        </div>\r\n";
                                    }
                                    if (xrefdoctordesc.equals("")) {
                                        s += "                        <div>\r\n"+
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Reffered To </h6>\r\n" +
                                            "								<p><b>" + xrefdoctordesc + "</b><p/>" +
                                            "								<p>" + xprofdegree1 + "<p/>" +
                                            "								<p>" + designationname1 + "<p/>" +
                                            "								<p>" + departmentname1 + "<p/>" +
                                            "                        </div>\r\n";
                                    }

                                    if (xpatadvice.equals("") || xpatadvice.equals("No")) {
                                        s += "                        <div>\r\n" +
                                            "                        </div>\r\n";
                                    } else {
                                        s += "                        <div class=\"symptoms\">\r\n" +
                                            "                            <h6 class=\"d-header\">Need Admission :</h6>\r\n" +
                                            "								<p>" + xpatadvice + "<p/>" +
                                            "                        </div>\r\n";
                                        if (xadadvice.equals("")) {
                                            s += "                        <div>\r\n" +
                                                "                        </div>\r\n";
                                        } else {
                                            s += "                        <div class=\"symptoms\">\r\n" +
                                                "                            <h6 class=\"d-header\">Admission Advice</h6>\r\n" +
                                                "								<p>" + xadadvice + "<p/>" +
                                                "                        </div>\r\n";
                                        }
                                    }
                                    s+="<div style=\"height: 60px;\">\r\n"+"</div>"; 
                                }
                                s += "                </td>\r\n";
                                s += "                <td width=\"1%\" style=\"border-right: 1px solid;\">\r\n" +
                                    "                            <div id=\"vl\" class=\"vl\"></div>\r\n" +
                                    "                  </td>";
                                s += "                <td width=\"63%\" class=\"border-right\" valign=\"top\">\r\n" +
                                    "                    <div class=\"medicine\">\r\n";
                                rs = stmt.executeQuery(sql);
                                rs.setFetchSize(10000);
                                while (rs.next()) {
                                    records1++;
                                    genericdesc = rs.getString("xgenericdesc");
                                    inst = rs.getString("xinst");
                                    dose = rs.getString("xdose");
                                    takingtime = rs.getString("xtakingtime");
                                    xmedcadvice = rs.getString("xrem");
                                    String xroute = rs.getString("xroute");
                                    if (genericdesc.equals("")) {
                                    	s+="";
                                    }
                                    else {
                                    	if (records1==1) {
                                    		s+="                        <section class=\"med_list\">\r\n" +
                                            "							<h6 style=\"font-weight: bold;margin-top: 0px;margin-bottom: 6px;font-size: 14px;\">Medications</h6>";
                                    	}
                                        s += "				<div style=\"display: inline-flex;width: 100%;margin-bottom: 10px;\">" +
                                                "						<div style=\"width: 50%;\"><p>" + records1 + ". " + genericdesc + "<p/></div>" +
                                                "					<div style=\"width: 50%;margin-left: 10px;\" class=\"zero-margin\"><p>" + inst + "<p/><p>" +xroute +"  "+ dose + "  " + takingtime + "<p/><p>" + xmedcadvice + "<p/></div></div>";
                                    }   
                                }      
                                s += "                            </section>\r\n" +
                                    "                        </div>\r\n" +
                                    "                    </td>\r\n" +
                                    "                </tr>\r\n" +
                                    "                </td>\r\n" +
                                    "                </tr>\r\n" +
                                    "            </tbody>"+
                                    "        <tfoot>\r\n" +
                                    "            <tr>\r\n" +
                                    "                <td class=\"footer\">\r\n" +
                                    "                    <div>\r\n" +
                                    "                        <hr style=\"border: 1px solid;\">\r\n" +
                                    "                        <p style=\"font-size: 10px;margin: 0px;\">In case of experiencing any allergic reaction,\r\n" +
                                    "                            skin\r\n" +
                                    "                            rashes or any other\r\n" +
                                    "                            reaction which you think may be due to medication,\r\n" +
                                    "                            <br> Please contact immediately to Emergency department of United Hospital Ltd.\r\n" +
                                    "                            <br> Emergency Contact No:<span style=\"font-weight: bold;\">01914001234</span>,\r\n" +
                                    "                            Hotline: <span style=\"font-weight: bold;\">10666</span> and For Appointment : <span\r\n" +
                                    "                                style=\"font-weight: bold;\">02 22 22 62 466</span> <br>\r\n" +
                                    "                        </p>\r\n" +
                                    "                        <p style=\"font-size: 10px;margin: 5px 0px 2px 0px;\"><span\r\n" +
                                    "                                style=\"font-weight: bold;\">Prescribed By: </span>" + doctorname + "</p>\r\n" +
                                    "                        <hr style=\"border: 0.2px solid black;margin: 0px;\">\r\n" +
                                    "                        <span style=\"display: flex;margin-top: 6px;\">\r\n" +
                                    "                            <span style=\"width: 35%;\">\r\n" +
                                    "                                <p style=\"font-size: 10px;margin: 0px;\">Note : This is Computer Generated\r\n" +
                                    "                                    Prescription. ***** </p>\r\n" +
                                    "                            </span>\r\n" +
                                    "							 <span style=\"width: 40%;\">\r\n" +
                                    "                                <p style=\"font-size: 10px;margin: 0px;\">Call for <span style=\"font-weight: bold;\">Home Sample</span> Collection: <span style=\"font-weight: bold;\">01914 001 220</span> </p>\r\n" +
                                    "                            </span>\r\n" +
                                    "                            <span>\r\n" +
                                    "                                <p style=\"font-size: 10px;margin: 0px;\">Print Date &amp; Time : " + dateString2 + "</p>\r\n" +
                                    "                            </span>\r\n" +
                                    "                        </span>\r\n" +
                                    "                    </div>\r\n" +
                                    "                </td>\r\n" +
                                    "            </tr>\r\n" +
                                    "        </tfoot>"+
                                    "    </table>";
                                
            }
        } catch (SQLException esql) {
        } finally { 
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException esql) {}
            // System.err.println(s);
            writer.println(s);
        }
        pool.releaseConnection(con);
        writer.close();
    }
}