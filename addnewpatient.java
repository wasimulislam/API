package zaberp.zab;

import java.sql.*; 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

@SuppressWarnings("serial")
public class addnewpatient extends HttpServlet {
 
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

	protected void doPost(HttpServletRequest requestObj, HttpServletResponse responseObj) throws IOException {
		responseObj.setContentType("text/html");
		responseObj.setHeader("Cache-Control", "no-cache");
		zabInfo zabinfo = new zabInfo();
		PrintWriter writer = responseObj.getWriter();
		Connection con = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		String s = "";
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		String result = "false";
		int id = zabTools.getInt(requestObj.getParameter("zid"));
		String user_id = requestObj.getParameter("user_id");
		String password = requestObj.getParameter("user_pwd");
		String xnamef = requestObj.getParameter("xnamef");
		String xnamem = requestObj.getParameter("xnamem");
		String xnamel = requestObj.getParameter("xnamel");
		String xname = requestObj.getParameter("xname");
		String xbloodgroup = requestObj.getParameter("xbloodgroup");
		String xbirthdatedatediv = requestObj.getParameter("xbirthdatedatediv");
		String xageyear = requestObj.getParameter("xageyear");
		String xmonth = requestObj.getParameter("xmonth");
		String xday = requestObj.getParameter("xday");
		String xborntimetimediv = requestObj.getParameter("xborntimetimediv");
		String xfname = requestObj.getParameter("xfname");
		String xmname = requestObj.getParameter("xmname");
		String xorg = requestObj.getParameter("xorg");
		String xmother = requestObj.getParameter("xmother");
		String xmoname = requestObj.getParameter("xmoname");
		String xcus = requestObj.getParameter("xcus");
		String xmstat = requestObj.getParameter("xmstat");
		String xreligion = requestObj.getParameter("xreligion");
		String xpassport = requestObj.getParameter("xpassport");
		String xnationality = requestObj.getParameter("xnationality");
		String xbirthplace = requestObj.getParameter("xbirthplace");
		String xcitizenship = requestObj.getParameter("xcitizenship");
		String xoccupation = requestObj.getParameter("xoccupation");
		String xmobile = requestObj.getParameter("xmobile");
		String xemail = requestObj.getParameter("xemail");
		String xspouse = requestObj.getParameter("xspouse");
		String xspousemobile = requestObj.getParameter("xspousemobile");
		String xhinsurance = requestObj.getParameter("xhinsurance");
		String xpolicy = requestObj.getParameter("xpolicy");
		String xlocalgurdian = requestObj.getParameter("xlocalgurdian");
		String xlgrelation = requestObj.getParameter("xlgrelation");
		String xlgmobile = requestObj.getParameter("xlgmobile");
		String xstaff = requestObj.getParameter("xstaff");
		String xposition = requestObj.getParameter("xposition");
		String xemploytype = requestObj.getParameter("xemploytype");
		String xpositiondeptdeg = requestObj.getParameter("xpositiondeptdeg");
		String xdum = requestObj.getParameter("xdum");
		String xmadd = requestObj.getParameter("xmadd");
		String xarea = requestObj.getParameter("xarea");
		String xthana = requestObj.getParameter("xthana");
		String xdistrict = requestObj.getParameter("xdistrict");
		String xpadd = requestObj.getParameter("xpadd");
		String xareaper = requestObj.getParameter("xareaper");
		String xthanaper = requestObj.getParameter("xthanaper");
		String xdistrictper = requestObj.getParameter("xdistrictper");
		String xkin = requestObj.getParameter("xkin");
		String xrelation = requestObj.getParameter("xrelation");
		String xkinmobile = requestObj.getParameter("xkinmobile");
		String xkinadd = requestObj.getParameter("xkinadd");
		String xkinphoneres = requestObj.getParameter("xkinphoneres");
		String xpaadmittype = requestObj.getParameter("xpaadmittype");
		String xsameas = requestObj.getParameter("xsameas");
		String xlanguage = requestObj.getParameter("xlanguage");
		String xsex = requestObj.getParameter("xsex");
		String xregtype = requestObj.getParameter("xregtype");
		String table = requestObj.getParameter("mmpatienttemp");
		String xnid = requestObj.getParameter("xnid");

		sql = "select zid from zbusiness where zid=" + id + "";
//    System.out.println(sql);
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			con = pool.getConnection();
		} catch (Exception esql) {
			result = "false";
		}

		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				result = "true";
			}
		} catch (SQLException esql) {
			result = "false";
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException esql) {
				result = "false";
			}
		}

		if (result.equals("false")) {
			writer.println("Authentication Failed");
			return;
		}
		result = "false";

//    sql1="select convert(varchar(8), GETDATE() ,112)+CAST(isnull(max(right(xpatient,len(xpatient)-8)),0)+1 as varchar)as xnum from"
//    	+ " [mmpatienttemp] where left(xpatient,8)=convert(varchar(8), GETDATE() ,112)\r\n";

		sql1 = "select xnum+1 as xnum  from xtrn where zid=" + id + " and xtypetrn='Temp Patient Number' and xtrn='TPID'";
//    System.out.println(sql1);
		rs = null;
		try {
			con = pool.getConnection();
		} catch (Exception esql) {
			result = "false";
		}

		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu");
			LocalDate localDate = LocalDate.now();
//      System.out.println(dtf.format(localDate));

			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sql1);
			while (rs1.next()) {
				int xnum = rs1.getInt("xnum");
				
				
				sql2="update xtrn set xnum='" + xnum+ "' where xtypetrn='Temp Patient Number' and xtrn='TPID'";
				stmt2 = con.createStatement();
				stmt2.executeUpdate(sql2);
				

				sql = "INSERT INTO mmpatienttemp([ztime],[zutime],[zauserid] ,[zuuserid] ,[zid] ,[xpatient] ,[xname] ,[xmadd] ,[xpadd] ,[xbirthdate] ,[xsex] ,[xmstat] ,[xage] \r\n"
						+ ",[xdate] ,[xdiagnosis] ,[xoxygen] ,[xposturechange] ,[xprofile] ,[xgcus] ,[xstatuscus] ,[xfname] ,[xmname] ,[xcus] ,[xkin]\r\n"
						+ ",[xcase],[xtype],[xregtype] ,[xstatusar] ,[xreligion] ,[xbloodgroup] ,[xbirthplace] ,[xspouse] ,[xnationality] ,[xpassport] \r\n"
						+ ",[xphoneres] ,[xphoneoff],[xmobile],[xrelation] ,[xkinadd] ,[xkinphoneres] ,[xkinphoneoff] ,[xkinmobile] ,[xclinic] ,[xoccupation]\r\n"
						+ ",[xstaff] ,[xvoterid] ,[xnid] ,[xmid],[xheight] ,[xweight] ,[xoffadd] ,[xemail] ,[xpatienttype],[xphysician] ,[xtypediabetes] \r\n"
						+ ",[xdurationdibetes] ,[xarea] ,[xthana] ,[xdistrict] ,[xeducation] ,[xeducator] ,[xcitizenship],[xlanguage] ,[zactive],[xvillage] \r\n"
						+ ",[xspousemobile] ,[xlocalgurdian] ,[xlgrelation] ,[xlgmobile] ,[xhinsurance] ,[xorg] ,[xageyear] ,[xareaper] ,[xthanaper] \r\n"
						+ ",[xdistrictper] ,[xbimage] ,[xnamef] ,[xnamem] ,[xnamel] ,[xpaadmittype] ,[xidsup] ,[xsuperior2] ,[xsuperior3] ,[xsignatory1] \r\n"
						+ ",[xsigndate1] ,[xsignatory2] ,[xsigndate2] ,[xsignatory3] ,[xsigndate3] ,[xsignatory4] ,[xsigndate4] ,[xsignatory5]\r\n"
						+ ",[xsigndate5] ,[xsignatory6] ,[xsigndate6] ,[xsignatory7] ,[xsigndate7] ,[xsignreject] ,[xdatereject] ,[xmother]  ,[xpvillage]\r\n"
						+ ",[xpolicy] ,[xposition] ,[xemploytype] ,[xmonth] )\r\n"
						+ "VALUES (getdate(),getdate(),'api','api'," + "" + id + ",'" + dtf.format(localDate) + ""
						+ xnum + "','" + xnamef + " " + xnamel + "','" + xmadd + "','" + xpadd + "','"
						+ xbirthdatedatediv + "','" + xsex + "'\r\n" + ",'" + xmstat + "' ,'" + xageyear
						+ "' ,getdate() ,'' ,'' ,'' \r\n" + ",'' ,'' ,'' ,'" + xfname + "' ,'" + xmname + "' ,'' ,'"
						+ xkin + "' ,''\r\n" + ",'' ,'Normal' ,'' ,'" + xreligion + "' ,'" + xbloodgroup + "' ,'' \r\n"
						+ ",'" + xspouse + "' ,'" + xnationality + "' ,'" + xpassport + "' ,'' ,'' ,'" + xmobile
						+ "' \r\n" + ",'" + xrelation + "' ,'" + xkinadd + "' ,'' ,'' ,'" + xkinmobile + "' ,''\r\n"
						+ ",'" + xoccupation + "' ,'" + xstaff + "' ,'' ,'" + xnid + "' ,'' ,0 ,0 ,'','" + xemail
						+ "'\r\n" + ",'' ,'' ,'' ,0,'" + xarea + "' ,'" + xthana + "' ,'" + xdistrict + "' \r\n"
						+ ",'' ,'' ,'" + xcitizenship + "' ,'' ,1 ,'' ,'" + xspousemobile + "' \r\n" + ",'' ,'' ,'' ,'"
						+ xhinsurance + "' ,'' ,'" + xageyear + "','" + xareaper + "'\r\n" + ",'" + xthanaper + "' ,'"
						+ xdistrictper + "','' ,'" + xnamef + "' ,'' ,'" + xnamel + "' ,''\r\n"
						+ ",'' ,'' ,'' ,'' ,getdate() ,'' ,getdate(),''\r\n"
						+ ",GETDATE(),'' ,getdate() ,'' ,getdate() ,'' ,getdate() \r\n"
						+ ",'' ,getdate(),'' ,getdate() ,'' ,'' ,'' \r\n" + ",'' ,'General' ," + xmonth
						+ "  ) ";
				s += "" + dtf.format(localDate) + xnum + "";

				stmt = con.createStatement();
				stmt.executeQuery(sql);

			}

		} catch (SQLException esql) {
			// System.err.println(esql.getMessage());
			result = "false";
		} finally {

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException esql) {
				result = "false";
			}
		}

		writer.println(s);
		writer.close();
	}
}