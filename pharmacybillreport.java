package zaberp.zab;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

@SuppressWarnings("serial")
public class pharmacybillreport extends HttpServlet {
	final String thisName = "";
	JDBCpool pool = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		pool = zabInfo.GetPool(this, thisName);
	}

	protected void doGet(HttpServletRequest requestObj, HttpServletResponse responseObj) throws IOException {
		responseObj.setContentType("text/html");
		responseObj.setHeader("Cache-Control", "no-cache");
		zabInfo zabinfo = new zabInfo();
		PrintWriter writer = responseObj.getWriter();
		Connection con = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		Statement stmt4 = null;
		Statement stmt5 = null;
		Statement stmt6 = null;
		Statement stmt7 = null;
		Statement stmt8 = null;
		Statement stmt9 = null;
		Statement stmt10 = null;
		Statement stmt11 = null;
		Statement stmt12 = null;
		Statement stmt13 = null;
		Statement stmt14 = null;
		Statement stmt15 = null;
		Statement stmt16 = null;
		Statement stmt17 = null;
		Statement stmt01 = null;

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
		String sql11 = "";
		String sql12 = "";
		String sql13 = "";
		String sql14 = "";
		String sql15 = "";
		String sql16 = "";
		String sql17 = "";
		String sql01 = "";
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		/**** GETTING PARAMETER FROM URL/SCREEN ********************/
		int id = zabTools.getInt(requestObj.getParameter("id"));
		String table = requestObj.getParameter("table");
		String dornum = requestObj.getParameter("dornum");
		String email = requestObj.getParameter("email");
		sql = "select xgitemdesc, round(sum(xlineamt),2) as xlineamt from billprintview where zid=" + id
				+ " and xdornum='" + dornum + "' " + "group by xgitemdesc order by xgitemdesc";
		// System.err.println(sql);
		sql3 = "select a.xpatient,b.xname,isnull(a.xage,'') as xage,(select dbo.CheckValue(b.xbirthdate,'Age')) as xbirthdate,isnull(c.xname,'') as dname,b.xmadd,isnull(a.xpreparer,'')as xpreparer,a.xbillno,isnull(cast(a.xbilldate as varchar) ,'') as xbilldate,\r\n"
				+ "a.xepisodetype,isnull(a.xepisodeno,'') as xepisodeno,isnull(a.xcus,'') as xcus,d.xorg,d.xmadd as cxmadd,isnull(e.xname,'') as username,isnull(xdotype,'') as xdotype,b.xsex from opdoheader (NOLOCK) a\r\n"
				+ "join mmpatient (NOLOCK) b on a.zid=b.zid and a.xpatient=b.xpatient"
				+ " left join cacusrptview d on a.zid=d.zid and a.xcus=d.xcus"
				+ " left join pdsign1 c on a.zid=c.zid and a.xdoctor=c.xstaff"
				+ " left join pdsign2 e on a.zid=e.zid and a.xpreparer=e.xposition where a.zid='" + id
				+ "' and a.xdornum='" + dornum + "'";
		// System.err.println(sql3);
		
		sql01 = "select xname from pdmstview1 where zid = '" + id + "' and xposition = '" + email + "'";
		
		sql4 = "select round(sum(xtotamt),0) as xlineamt from opdoheaderrptview where zid=" + id + " and xdornum='"
				+ dornum + "'";
		// System.err.println(sql4);

		sql5 = "select convert(VARCHAR, xdate, 106) as xdate,xcardtype,xcardamt from opposmultiplecard (NOLOCK) where zid="
				+ id + " and xdornum='" + dornum + "'"; // and xcardtype='Allocation'";
		// System.err.println(sql5);

		sql6 = "select isnull(round(sum(xcardamt),0),0) as xcardamt from opposmultiplecard (NOLOCK) where zid=" + id
				+ " and xdornum='" + dornum + "' and xcardtype='Allocation'";
		// System.err.println(sql6);

		sql7 = "select xvatamt from opdoheaderrptview where zid=" + id + " and xdornum='" + dornum + "'";
		// System.err.println(sql7);

		sql8 = "select (xtotamt+xroundingchange+xvatamt)-(xdiscamt+xsupptaxamt)-xcrnamt as xtotal from opdoheaderrptview where zid="
				+ id + " and xdornum='" + dornum + "'";
		// System.err.println(sql8);

		sql9 = "select isnull(round(sum(xcardamt),0),0) as xcardamt from opcollectionrptview (NOLOCK) where zid=" + id
				+ " and xdornum='" + dornum + "' and xcardtype not in ('Allocation','Corporate due')";
		// System.err.println(sql9);

		sql10 = "select isnull((select sum(xcardamt)as xcardamt from opcollectionrptview where xdornum='" + dornum
				+ "' and xcardtype='Corporate due'),0) +\r\n"
				+ "(select ROUND(((xtotamt+xroundingchange+xvatamt)-(xdiscamt+xsupptaxamt)-xcardamt)-xcrnamt,0) as xdue from opdoheaderrptview where xdornum='"
				+ dornum + "') -\r\n" + "isnull((select sum(xcardamt) from opcollectionrptview where xdornum='" + dornum
				+ "' and xcardtype='Corporate Paid'),0) as xdue";
		// System.err.println(sql10);

		sql11 = "select cast(sum(xsign*xrate*xqtyord)as decimal(20,2)) as xtotamount,"
				+ "sum((CASE WHEN xqtyord > 0 THEN (xdiscamt/xqtyord)*(isnull(xqtyord,0)-isnull(xqtycrn,0)) ELSE 0 END)*xsign) as xdiscount"
				+ " from opdodetailrptview where zid=" + id + " and xdornum='" + dornum + "'";
		// System.err.println("final"+sql11);

		sql12 = "select sum((CASE WHEN xqtyord > 0 THEN (xdiscamt/xqtyord)*(isnull(xqtyord,0)-isnull(xqtycrn,0)) ELSE 0 END)*xsign) as xdiscount "
				+ "from opdodetailrptview where zid=" + id + " and xdornum='" + dornum + "' ";
		// System.err.println("discount"+sql12);
		sql13 = "select isnull(xdiscamt,0) as xdiscamt,xdotype from opdoheader (NOLOCK) where zid=" + id
				+ " and xdornum='" + dornum + "' ";
		// System.err.println(sql13);

		sql14 = "select xvatregno from zbusiness where zid='" + id + "'";

		sql15 = "select xcrnamt from opdoheaderrptview where zid='" + id + "' and xdornum='" + dornum + "'";

		sql16 = "select isnull(sum(isnull(xdiscamt,0)*xsign),0)as xdiscamt from opdodetail  where zid ='" + id
				+ "' and xdornum ='" + dornum + "'";
		// System.err.println(sql16);

		sql17 = "select convert(VARCHAR, xdate, 106) as xdate,xcardtype,xcardamt as xcorporatepaid from opcollectionrptview where"
				+ " xdornum='" + dornum + "' and xcardtype='Corporate Paid'";
		// System.err.println(sql17);

		String xrow = "";
		String xvatregno = "";
		String xcus = "";
		String xorg = "";
		String xmadd = "";
		String uname = "";
		String patient = "";
		String pname = "";
		String age = "";
		String gender = "";
		String xbirthdate = "";
		String dname = "";
		String address = "";
		String userid = "";
		String billdate = "";
		String episodetype = "";
		String episodeno = "";
		String Date = "";
		String item = "";
		String itemdesc = "";
		String doctorname = "";
		String batch = "";
		String dateexp = "";
		String lineamt = "";
		String qty = "";
		float ratemrp = 0;
		String billno = "";
		String gitemdesc = "";
		String totalamt = "";
		String xdate = "";
		String xcardtype = "";
		String xcardamt = "";
		String xdotype = "";
		float xvatamt = 0;
		float xcrnamt = 0;
		String xtotal = "";
		String xamtreceive = "";
		String genericname = "";
		String xdue = "";
		String xtotamount = "";
		float totaldiscount = 0;
		String xlineamt = "";
		float xamount = 0;
		float xdiscount = 0;
		float xdiscamt = 0;
		float xdiscount1 = 0;
		float xdiscount2 = 0;
		float xcorporatepaid = 0;
		int records = 0;
		int records3 = 0;
		int billrecords = 0;
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		ResultSet rs6 = null;
		ResultSet rs7 = null;
		ResultSet rs8 = null;
		ResultSet rs9 = null;
		ResultSet rs10 = null;
		ResultSet rs11 = null;
		ResultSet rs12 = null;
		ResultSet rs13 = null;
		ResultSet rs14 = null;
		ResultSet rs15 = null;
		ResultSet rs16 = null;
		ResultSet rs17 = null;
		ResultSet rs01 = null;
		
		try {
			con = pool.getConnection();
		} catch (Exception esql) {
			zabinfo.log("zErr0002msg", esql.getMessage(), "Return Code = 0");
		}

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			stmt3 = con.createStatement();
			rs3 = stmt3.executeQuery(sql3);

			s += "<style id=\"table_style\" type=\"text/css\">\r\n" + "p {\r\n" + "            margin: 0;\r\n"
					+ "        }\r\n" + "\r\n" + "        table {\r\n"
					+ "            font-family: Arial, Helvetica, sans-serif;\r\n" + "        }\r\n" + "\r\n"
					+ "        .table {\r\n" + "            font-family: Arial, Helvetica, sans-serif;\r\n"
					+ "        }\r\n" + "\r\n" + "        .header {\r\n" + "            text-align: start;\r\n"
					+ "            overflow: auto;\r\n" + "            font-size: 12px;\r\n"
					+ "            margin: 0px;\r\n" + "            width: 100%;\r\n" + "        }\r\n" + "\r\n"
					+ "        .footer {\r\n" + "            font-size: 7px;\r\n" + "            padding: 2px;\r\n"
					+ "            font-weight: lighter;\r\n" + "        }\r\n" + "\r\n" + "        .address {\r\n"
					+ "            margin-left: 5px;\r\n" + "            font-size: 12px;\r\n"
					+ "            font-weight: lighter;\r\n" + "            width: 100px;\r\n" + "        }\r\n"
					+ "\r\n" + "        .header-table {\r\n" + "            font-size: 12px;\r\n"
					+ "            width: 100%;\r\n" + "            font-weight: lighter;\r\n" + "        }\r\n"
					+ "\r\n" + "        .mushak {\r\n" + "            position: fixed;\r\n"
					+ "            right: 0px;\r\n" + "            top: 0px;\r\n" + "            font-size: 12px;\r\n"
					+ "            font-weight: normal;\r\n" + "        }\r\n" + "\r\n" + "        .bold {\r\n"
					+ "            font-weight: bold !important;\r\n" + "            text-align: start;\r\n"
					+ "            font-size: 8px;\r\n" + "        }\r\n" + "\r\n" + "        .table th,\r\n"
					+ "        .table td {\r\n" + "            text-align: start;\r\n"
					+ "            font-size: 12px;\r\n" + "            border-bottom: 0.01px solid #dddddd;\r\n"
					+ "            padding: 1px;\r\n" + "            font-weight: lighter;\r\n" + "        }\r\n"
					+ "\r\n" + "        .table-tr-th tr th {\r\n" + "            text-align: start;\r\n" + "\r\n"
					+ "        }\r\n" + "\r\n" + "        #barcode rect {\r\n" + "            height: 17px;\r\n"
					+ "        }\r\n" + "\r\n" + "        #barcode2 rect {\r\n" + "            height: 17px;\r\n"
					+ "        }\r\n" + "        .footerspan {\r\n" + "            font-size: 8px;\r\n"
					+ "            text-align: start;\r\n" + "            font-weight: lighter;\r\n" + "        }\r\n"
					+ "\r\n" + "        .barcode{\r\n" + "               font-weight: normal;\r\n"
					+ "            }\r\n" + "\r\n" + "\r\n" + "        @page {\r\n"
					+ "            margin: 24mm 10mm 10mm 10mm;\r\n" + "        }\r\n" + "\r\n"
					
					//+ "            #barcode2 {\r\n" + "               width:165px;\r\n" + "            }\r\n"
					
					+ "        @media print {\r\n" + "            button {\r\n" + "                display: none;\r\n"
					+ "            }\r\n" + "\r\n" + "            table {\r\n"
					+ "                font-family: Arial, Helvetica, sans-serif;\r\n" + "            }\r\n" + "\r\n"
					+ "            .table {\r\n" + "                font-family: Arial, Helvetica, sans-serif;\r\n"
					+ "            }\r\n" + "\r\n" + "            .header {\r\n"
					+ "                text-align: start;\r\n" + "                overflow: hidden;\r\n"
					+ "                font-size: 9px;\r\n" + "                margin: 0px;\r\n"
					+ "                width: 100%;\r\n" + "                height: 140px;\r\n" + "            }\r\n"
					+ "\r\n" + "            .footer {\r\n" + "                font-size: 7px;\r\n"
					+ "                padding: 2px;\r\n" + "                font-weight: lighter;\r\n"
					+ "            }\r\n" + "\r\n" + "            .address {\r\n"
					+ "                margin-left: 5px;\r\n" + "                font-size: 10px;\r\n"
					+ "                font-weight: lighter;\r\n" + "                width: 100px;\r\n"
					+ "            }\r\n" + "\r\n" + "            .header-ta {\r\n"
					+ "                font-size: 9px;\r\n" + "                width: 100%;\r\n"
					+ "                font-weight: lighter;\r\n" + "            }\r\n" + "\r\n"
					+ "            .mushak {\r\n" + "                position: fixed;\r\n"
					+ "                right: 0px;\r\n" + "                top: 0px;\r\n"
					+ "                font-size: 10px;\r\n" + "                font-weight: normal;\r\n"
					+ "            }\r\n" + "\r\n" + "            .bold {\r\n"
					+ "                font-weight: bold !important;\r\n" + "                text-align: start;\r\n"
					+ "                font-size: 8px;\r\n" + "            }\r\n" + "\r\n"
					+ "            .table th,\r\n" + "            .table td {\r\n"
					+ "                text-align: start;\r\n" + "                font-size: 10px;\r\n"
					+ "                border-bottom: 0.01px solid #dddddd;\r\n" + "                padding: 1px;\r\n"
					+ "                font-weight: lighter;\r\n" + "            }\r\n" + "\r\n"
					+ "            .table-tr-th tr th {\r\n" + "                text-align: start;\r\n" + "\r\n"
					+ "            }\r\n" + "\r\n" + "            hr {\r\n"
					+ "                border-bottom: 0.01px solid #dddddd;\r\n" + "                margin: 2px;\r\n"
					+ "            }\r\n" + "\r\n" + "            #barcode rect {\r\n"
					+ "                height: 17px;\r\n" + "            }\r\n" + "\r\n"
					+ "            #barcode2 rect {\r\n" + "                height: 17px;\r\n" + "            }\r\n"
					+ "            .header-align{\r\n" + "                right: 0% !important; \r\n"
					+ "                position: absolute;\r\n" + "                width : 240px;\r\n"
					+ "                display: inline-grid;\r\n" + "            }\r\n" + "\r\n"
					+ "            .barcode{\r\n" + "                font-weight: normal;\r\n"
					+ "                position: absolute;\r\n" + " top: 10%;     width: 100px;    right: 10%;\r\n" + "            }\r\n"
					+ "\r\n" + "            .footerspan {\r\n" + "            font-size: 8px;\r\n"
					+ "            text-align: start;\r\n" + "            font-weight: lighter;\r\n" + "        }\r\n"
					//+ "            #barcode2 {\r\n" + "               width:165px;\r\n" + "            }\r\n"
					+ "}\r\n" + "    </style>";
        
        rs3.setFetchSize(10000); 
        while (rs3.next()){
        	records3++;
        	//System.err.println(records3);
        	patient = rs3.getString("xpatient");
        	pname = rs3.getString("xname");
        	age = rs3.getString("xage");
        	gender = rs3.getString("xsex");
        	xbirthdate = rs3.getString("xbirthdate");
        	dname = rs3.getString("dname");
        	address = rs3.getString("xmadd");
        	userid = rs3.getString("xpreparer");
        	billno = rs3.getString("xbillno");
        	billdate = rs3.getString("xbilldate");
        	episodetype = rs3.getString("xepisodetype");
        	episodeno = rs3.getString("xepisodeno");
        	xcus = rs3.getString("xcus");
        	xorg = rs3.getString("xorg");
        	xmadd = rs3.getString("cxmadd"); 
        	uname = rs3.getString("username");
        	xdotype = rs3.getString("xdotype");
        	if (records3==1) {
        		s+="<div class=\"container\">";
             	s+="<div class=\"container-fluid\" style=\"margin-top: 10%;\">";
             	//System.out.println(uname);
             	if (xdotype.equals("P")) {
             		s+="<a class=\"btn btn-primary\" type=\"button\" href=\"login?screen=oppospharmacy&command=Show&xdornum=" + dornum + "\" "
                    + "style=\"text-decoration: none;position: fixed;left: 100px;top: 100px;\">Bill Entry</a>";
             	}
             	else {
             		s+="<a class=\"btn btn-primary\" type=\"button\" href=\"login?screen=oppos&command=Show&xdornum=" + dornum + "\" "
                     + "style=\"text-decoration: none;position: fixed;left: 100px;top: 100px;\">Bill Entry</a>";
             	}
             	
             	s+="<input class=\"btn btn-primary\" type=\"button\" value=\"Print\" onclick=\"printDiv()\" style=\"position: fixed;right: 100px;top: 100px;\"> ";
             	s+="</div>";
             	s+="</div>";
    		    s+="<div id=\"GFG\" class=\"container\">";
        		s+="<div class=\"container-fluid\">\r\n"
//************************************************************patient bill header start****************************************************************

				+ "        <table style=\"margin:auto\">\r\n"
				+ "            <thead>\r\n"
				+ "                <tr>\r\n"
				+ "                    <th>\r\n"
				+ "\r\n"
    //*********************************<!-- Extra header added END-----**************************************
				+ "                        <div class=\"\" style=\"display: flex;\">\r\n"
				+ "\r\n"
				+ "                            <table class=\"header-table\" style=\"border: 1px solid #dee2e6;text-align: start;\">\r\n"
				+ "                                <tbody class=\"table-tr-th\">\r\n"
				+ "                                    <tr>\r\n"
				+ "                                        <th>Drug license DC-21975 </th>\r\n";
				
     		    stmt14 = con.createStatement();
     		    rs14 = stmt14.executeQuery(sql14);
     		    while (rs14.next()){
     		    	xvatregno = rs14.getString("xvatregno");
				s+="               <th style=\"text-align: right;\"><span style=\"font-size: 10px;\">mushak-6.3</span> VAT Registration No:"+xvatregno+" </th>";
     		    
     		    }
     			//System.out.println(xdotype);
     		   s+="                                    </tr>\r\n"
				+ "                                    <tr>\r\n"
				+ "                                        <th style=\"text-align: right;\" ></th>\r\n"
				+ "                                        <th style=\"text-align: right; font-size: 14px;\">Queue No. 12<span style=\"font-weight: normal;\"></span></th>\r\n"
				+ "                                    </tr>\r\n"
				+ "                                    \r\n"
				+ "\r\n"
				+ "\r\n"
				+ "                                    <tr>\r\n"
				+ " <th colspan=\"2\" style=\"font-weight: bold; font-size: 15px; \" >\r\n"
				+ "                                             <div style=\"display: flex;\">      \r\n"
				+ "                                                <span style=\"width: 70%; text-align: center;  position: relative; \" ><p style=\"padding-left: 35px;\">OUT-PATIENT PHARMACY <br> PHARMACY SALE</p></span>\r\n";
				
				if (billno.equals(""))
    			{
				
				s+= "<span style=\" position: relative; font-weight: normal; \">Provisional Bill<div id=\"barcodeTargetbill\" class=\"barcodeTargetbill\"></div></span>\r\n";
    			
    			}else {
    				s+= " <input style=\"display: none;\" type=\"text\" id=\"barcodeValuebill\" value="+billno+" " + "class=\"input_box\">"
	    			+ "<span class=\"barcode\" style=\" position: relative; top: 5px; font-weight: normal;\"><svg id=\"barcode2\"></svg></span>\r\n";
	    			//+ "			<span>"+billno+"</span></th>\r\n"
	    			
    			}
				
				s+="   </div>\r\n";
				s+="   </th>";
				
				
				s+="                                    </tr>\r\n"
				+ "                                </tbody>\r\n"
				+ "                            </table>\r\n"
				+ "                        </div>\r\n"
				+ "\r\n"
				+ "                    </th>\r\n"
				+ "                </tr>\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "                <!--<thead-->\r\n"
				+ "            </thead>\r\n"
				+ "\r\n"
				+ "            <tbody>\r\n"
				+ "\r\n"
				+ "                <tr>\r\n"
				+ "                    <td>\r\n"
				+ "                        <table class=\"table\" style=\"width: 100%;text-align: start;\">\r\n"
				+ "                            <tr>\r\n"
				+ "\r\n"
				+ "  <span   style=\"width: 50%;font-size: 12px;width: 240px;display: inline-grid;\">Issue No: 22-23/1235</span> ";
    			if(billdate.equals("")) {
    			s+= "<span class=\"header-align\" style=\"font-size: 12px;right: 29%;position: absolute; width: 240px;display: inline-grid\"> </span><br>\r\n";
    			}
    			else {
    			s+="  <span class=\"header-align\" style=\"font-size: 12px;right: 29%;position: absolute; width: 240px;display: inline-grid\"> Bill Date: "+billdate+"</span><br>\r\n";
    			}		
    		   s+= " <span   style=\"width: 50%;font-size: 12px;width: 240px;display: inline-grid;\">Patient Name: "+pname+"</span>"
    		   	+ " <span class=\"header-align\" style=\"font-size: 12px;right: 29%;position: absolute; width: 240px;display: inline-grid\"> UHID : "+patient+"</span><br>\r\n";
    			if(billno.equals("")) {
    				   s+= " <span   style=\"width: 50%;font-size: 12px;width: 240px;display: inline-grid;\">Age/Gender: "+xbirthdate+" / "+gender+"</span> ";
    			}else {
    			 s+= " <span   style=\"width: 50%;font-size: 12px;width: 240px;display: inline-grid;\">Age/Gender: "+age+" / "+gender+"</span> ";		
    			}
    			s+= " <span class=\"header-align\" style=\"font-size: 12px;right: 29%;position: absolute; width: 240px;display: inline-grid\">Consultant : "+dname+"</span><br>\r\n"				
    					
    		    + "              <span   style=\"width: 50%;font-size: 12px;width: 240px;display: inline-grid;\">Payer: CASH PATIENT</span> \r\n"
				+ "                         \r\n"
				+ "                            </tr>\r\n"
				+ "                            <tr>\r\n"
				+ "                                \r\n"
				+ "                            </tr>\r\n"
				+ "                            <tr>\r\n"
				+ "\r\n"
				+ "                            </tr>\r\n"
				+ "\r\n"
//*********************************<!-- Extra header added END----->**************************************
				+ "                            <tr>\r\n"
				+ "                                <th>S/N</th>\r\n"
				+ "                                <th>Particulars</th>\r\n"
				+ "                                <th>Batch No</th>\r\n"
				+ "                                <th>Expiry</th>\r\n"
				+ "                                <th style=\"text-align: right;\">Quantity</th>\r\n"
				+ "                                <th style=\"text-align: right;\">MRP</th>\r\n"
				+ "                                <th style=\"text-align: right;\"> </th>\r\n"
				+ "                                <th style=\"text-align: right;\"> </th>\r\n"
				+ "                                <th style=\"text-align: right;\">Net Amount</th>\r\n"
				+ "                            </tr>\r\n"
				+ "                            <tr>\r\n"
				+ "                            </tr>";
             	
		rs.setFetchSize(10000); 
        while (rs.next()){
        	records++;
      //  	System.err.println(records);
        	gitemdesc = rs.getString("xgitemdesc");
        	xlineamt = rs.getString("xlineamt");
      //  	System.err.println(gitemdesc);
        	//fw.write(rs.getString("xpatient") + " " + rs.getString("xname") + "\n");

        	s+= "        <tr>\r\n"
			+ "            <td colspan=\"9\" style=\"font-weight: bold;text-decoration: underline;\">"+gitemdesc+"</td>\r\n";
    		s+= "        </tr>\r\n";
    		
    		sql2 = "select xrow,xgenericname,xdesc,xbatch,isnull( FORMAT(cast(xdateexp as date),'dd/MM/yyyy') ,'') as xdateexp,cast(xqtyord as decimal(10,2)) as xqtyord,xlineamt,xrate,isnull(xbillno,'') as xbillno,xgitemdesc"
				+ ",(xrate*xqtyord)-(xqtycrn) as xamount,"
				+ "CASE WHEN xqtyord > 0 THEN (xdiscamt/xqtyord)*(isnull(xqtyord,0)-isnull(xqtycrn,0)) ELSE 0 END AS xdiscount"
				+ " from "+table+" where zid='"+id+"' "
				+ "and xdornum='"+dornum+"' and xgitemdesc='"+gitemdesc+"' order by xgitemdesc,xrow";
    		
    		//System.err.println("main"+sql2);
	    	stmt2 = con.createStatement();
	   	//System.err.println(stmt2);
	        rs2 = stmt2.executeQuery(sql2);
	    //    System.err.println(rs2);
		    while (rs2.next()){
		    	xrow = rs2.getString("xrow"); 
	        	genericname = rs2.getString("xgenericname");
	        	itemdesc = rs2.getString("xdesc");
	        	dateexp = rs2.getString("xdateexp"); 
	        	batch = rs2.getString("xbatch");
	        	lineamt = rs2.getString("xlineamt");
	        	qty = rs2.getString("xqtyord");
	        	ratemrp = rs2.getFloat("xrate");
	        	billno = rs2.getString("xbillno");
	        	gitemdesc = rs2.getString("xgitemdesc");
	        	xamount = rs2.getFloat("xamount");
	        	xdiscount2 = rs2.getFloat("xdiscount");
	        	
	        	if(gitemdesc.equals(gitemdesc)) {
	        	
	        	 
				s += "<tr style='border-bottom: 1px solid #dddddd;'>";
				s += "<td style=\"width: 4%;\">"+xrow+"</td>";
				s += "<td style=\"width: 48%;\">" + itemdesc + " ("+genericname+")"+"</td>";
				s += "<td style=\"width: 12%;\">" + batch + "</td>";
				s += "<td style=\"text-align: right;\">" + dateexp + "</td>";
				s += "<td style=\"text-align: right;\">" + qty + "</td>";
				//System.err.println(xdiscount2);
	        	if (gitemdesc.equals(gitemdesc)) {
	        	//	System.err.println("if-loop" +xdiscount);
	        	s += "<td style=\"text-align: right;\">" + ratemrp + "</td>";
	        	}
	        	else {
	        	//	System.err.println("else-loop" +xdiscount);
	        		s += "<td>" + ratemrp + " </td>";
	        	}
	        	
	        	if (xdiscount2>0) {
		        	//	System.err.println("if-loop" +xdiscount);
		        	s += "<td style=\"text-align: right;\">" + xamount + "</td>";
		        	}
		        	else {
		        	//	System.err.println("else-loop" +xdiscount);
		        		s += "<td> </td>";
		        	}
	        	
	        	if (xdiscount2>0) {
	        	//	System.err.println("if-loop" +xdiscount);
	        	s += "<td style=\"text-align: right;\">" + xdiscount2 + "</td>";
	        	}
	        	else {
	        	//	System.err.println("else-loop" +xdiscount);
	        		s += "<td> </td>";
	        	}
				s += "<td style=\"text-align: right;\">" + lineamt + "</td>";
				s += "</tr>"; 
	        	}
	        	else {
	        		s += "<td style='padding: 12px 15px;'></td>";
	        	}
		    }
        	s+= "        <tr>\r\n"
			+ "            <td colspan=\"9\" style=\"font-weight: bold;text-decoration: underline;text-align: right;\">"+xlineamt+"</td>\r\n";
    		s+= "        </tr>\r\n";
        	}
        stmt4 = con.createStatement();
   //     System.err.println("stmt: " +stmt4);
        rs4 = stmt4.executeQuery(sql4);
        while (rs4.next()){
        	billrecords++;
        	totalamt = rs4.getString("xlineamt"); 
     //   	System.err.println(totalamt);
        	if(billrecords==1) {      		
        	s+= "        <tr>\r\n"
    		+ "            <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
    		+ "            <th colspan=\"2\" class=\"bold\">Total Amount</th>\r\n";
    		stmt11 = con.createStatement();
    		rs11 = stmt11.executeQuery(sql11);
    		while (rs11.next()){
    			xtotamount = rs11.getString("xtotamount"); 
    			xdiscount1 = rs11.getFloat("xdiscount"); 
    			
    			if (xdiscount1>0)
    			{
    	    		s+= "          <th class=\"bold\" style=\"text-align: right;\"><span>"+xtotamount+"</span></th>\r\n";
    	    	//	+ "            <th class=\"bold\" style=\"text-align: right;padding-right: 5px;\"><span>"+xdiscount1+"</span></th>\r\n";
    			}
    			else {
    				s+= "          <th style=\"text-align: right;\"> </th>\r\n";
    	    	  //  + "            <th style=\"text-align: right;\"> </th>\r\n";
    			}
    		}
    		
    		stmt16 = con.createStatement();
    		rs16 = stmt16.executeQuery(sql16);
    		while (rs16.next()){
    			xdiscamt = rs16.getFloat("xdiscamt");
    			
    			if (xdiscamt>0)
    			{
    	    		s+= "            <th class=\"bold\" style=\"text-align: right;padding-right: 5px;\"><span>"+String.format("%.2f", xdiscamt)+"</span></th>\r\n";
    			}
    			else {
    				s+= "          <th style=\"text-align: right;\"> </th>\r\n";
    			}
    		}
    		
    		s+= "            <th class=\"bold\" style=\"text-align: right;\"><span>"+totalamt+"</span></th>\r\n"
    		+ "        </tr>\r\n";
        	}
        }
        
        stmt15 = con.createStatement();
        rs15 = stmt15.executeQuery(sql15);
        while (rs15.next()){
        	xcrnamt = rs15.getFloat("xcrnamt");
        	//System.out.println(xcrnamt);
        	//System.out.println(String.format("%.2f", xcrnamt));
        	if (xcrnamt>0) {
            	s+= "        <tr>\r\n"
        		+ "            <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
        		+ "            <th colspan=\"3\" class=\"bold\">Sales Return Adj. / Write Off (-)</th>\r\n";
        		s+= "          <th class=\"bold\" style=\"text-align: right;\"><span>"+String.format("%.2f", xcrnamt)+"</span></th>\r\n"
        		+ "        </tr>\r\n";
        	}

        	
        }
        
        
		stmt7 = con.createStatement();
		rs7 = stmt7.executeQuery(sql7);
		while (rs7.next()){
			xvatamt = rs7.getFloat("xvatamt"); 
			if (xvatamt>0) {
	        	s+= "        <tr>\r\n"
        		+ "            <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
        		+ "            <th class=\"bold\" colspan=\"3\">VAT</th>\r\n"
        		+ "            <th class=\"bold\" style=\"text-align: right;\"><span>"+xvatamt+"</span></th>\r\n"
        		+ "        </tr>\r\n";
			}

		}
		
		stmt13 = con.createStatement();
		rs13 = stmt13.executeQuery(sql13);
		while (rs13.next()){
			totaldiscount = rs13.getFloat("xdiscamt"); 
			//System.err.println("discount"+totaldiscount);
	        	
        if (totaldiscount>0) {
        		s+= "        <tr>\r\n"
    	        + "            <th colspan=\"4\" style=\"border: none;\"></th>\r\n";
    			s+= "          <th class=\"bold\" colspan=\"3\">Discount</th>\r\n";        
    			s+= "          <th class=\"bold\" style=\"text-align: right;\"><span>"+totaldiscount+"</span></th>\r\n";
    			s+= "        </tr>\r\n";
        		}
        		
		}
		
		stmt11 = con.createStatement();
		rs11 = stmt11.executeQuery(sql11);
		while (rs11.next()){
			xtotamount = rs11.getString("xtotamount"); 
			xdiscount1 = rs11.getFloat("xdiscount"); 
			//System.err.println(xvatamt);
			//System.err.println(totaldiscount);
			if (xvatamt>0 || totaldiscount>0 || xcrnamt>0)
			{
				s+= "        <tr>\r\n"
	    		+ "          <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
	    		+ "          <th class=\"bold\" colspan=\"3\" style=\font-weight: bold;\">Total</th>\r\n";
			}
//			else if(totaldiscount>0)
//			{
//				s+= "        <tr>\r\n"
//	    		+ "          <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
//	    		+ "          <th class=\"bold\" colspan=\"3\" style=\font-weight: bold;\">Total</th>\r\n";
//			}
				
		}
		
		stmt8 = con.createStatement();
		rs8 = stmt8.executeQuery(sql8);
		while (rs8.next()){
			xtotal = rs8.getString("xtotal");
			if (xvatamt>0 || totaldiscount>0 || xcrnamt>0)
			{
				s+= "            <th class=\"bold\" style=\"text-align: right;\"><span>"+xtotal+"</span></th>\r\n"
			    + "        </tr>\r\n";
			}
//			else if(totaldiscount>0)
//			{
//				s+= "            <th class=\"bold\" style=\"text-align: right;\"><span>"+xtotal+"</span></th>\r\n"
//				+ "        </tr>\r\n";
//			}
    		
		}
		

    	stmt5 = con.createStatement();
    //    System.err.println("stmt: " +stmt5);
        rs5 = stmt5.executeQuery(sql5);
        while (rs5.next()){
        	xdate = rs5.getString("xdate"); 
        	xcardtype = rs5.getString("xcardtype"); 
        	xcardamt = rs5.getString("xcardamt"); 
      //  	System.err.println(xcardamt);
			s+= "        <tr>\r\n"
			+ "            <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
			+ "            <th class=\"bold\" colspan=\"3\" style=\font-weight: bold;\">"+xdate+"</th>\r\n"
			+ "            <th class=\"bold\" style=\"text-align: right;font-weight: bold;\"><span>"+xcardtype+"</span></th>\r\n"
			+ "            <th class=\"bold\" style=\"text-align: right;\"><span>"+xcardamt+"</span></th>\r\n"
			+ "        </tr>\r\n";
        }
        
        
//	************* Corporate Paid start*****************
        
        
    	stmt17 = con.createStatement();
        rs17 = stmt17.executeQuery(sql17);
        while (rs17.next()){
        	xdate = rs17.getString("xdate"); 
        	xcardtype = rs17.getString("xcardtype"); 
        	xcorporatepaid = rs17.getFloat("xcorporatepaid"); 
        	if (xcorporatepaid>0) {
        		s+= "        <tr>\r\n"
				+ "            <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
				+ "            <th class=\"bold\" colspan=\"2\" style=\font-weight: bold;\">"+xdate+"</th>\r\n"
				+ "            <th class=\"bold\" style=\"text-align: right;font-weight: bold;\"><span>"+xcardtype+"</span></th>\r\n"
				+ "            <th class=\"bold\" style=\"text-align: right;\"><span>"+xcorporatepaid+"</span></th>\r\n"
				+ "        </tr>\r\n";
        	}
        	else {
        		
        	}
        	
			
        }
        
        
        
// ****************** Corporate Paid End*****************
        
        
        stmt6 = con.createStatement();
 //       System.err.println("stmt: " +stmt6);
        rs6 = stmt6.executeQuery(sql6);
        while (rs6.next()){
        	xcardamt = rs6.getString("xcardamt"); 
    //    	System.err.println(xcardamt);
			s+= "        <tr>\r\n"
			+ "            <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
			+ "            <th class=\"bold\" colspan=\"4\" style=\"border-top: 1px solid;\">Advance Adjusted</th>\r\n"
			+ "            <th class=\"bold\"style=\"text-align: right;border-top: 1px solid;\"><span>"+xcardamt+"</span></th>\r\n"
			+ "        </tr>\r\n";
        }
        stmt9 = con.createStatement();
 //       System.err.println("stmt: " +stmt6);
        rs9 = stmt9.executeQuery(sql9);
        while (rs9.next()){ 
        	xamtreceive = rs9.getString("xcardamt"); 
    //    	System.err.println(xcardamt);
			s+= "        <tr>\r\n"
			+ "            <th colspan=\"4\" style=\"border: none;\"></th>\r\n"
			+ "            <th class=\"bold\" colspan=\"4\" style=\font-weight: bold;\">Amount Received/ Adjustment</th>\r\n"
			+ "            <th class=\"bold\" style=\"text-align: right;\"><span>"+xamtreceive+"</span></th>\r\n"
			+ "        </tr>\r\n";
        }
        stmt10 = con.createStatement();
 //       System.err.println("stmt: " +stmt6);
        rs10 = stmt10.executeQuery(sql10);
        while (rs10.next()){
        	xdue = rs10.getString("xdue"); 
    //    	System.err.println(xcardamt);
			s+= "        <tr>\r\n"
			+ "            <th colspan=\"4\" style=\"border: none;text-align: right;\"></th>\r\n"
			+ "            <th class=\"bold\" colspan=\"4\" style=\font-weight: bold;\">Due Amount</th>\r\n"
			+ "            <th class=\"bold\" style=\"text-align: right;\"><span class=\"due-amount\">"+xdue+"</span></th>\r\n"
			+ "        </tr>\r\n";
        }
    		s+= "    </tbody>";
    		s+= "</table>"
    		+ "<br/><br/>";
		    s+="<div/>";
		    s+="<div/>";
        	}
        
        
        if (records>12) {
        	
            s = s + "</tbody>"
    		+ "<tfoot>\r\n"
    		+ "            <tr>\r\n"
    		+ "                <th>\r\n"
    		+ "                    <footer>\r\n"
    		+ "                        <table class=\"table2\" style=\"width: 100%;text-align: start; padding-top: 10px; margin-top: 20px;\">\r\n"
    		+ "                            <tbody>\r\n"
    				+ "                                <tr style=\"text-align: center; font-size: 12px;\">\r\n"
    				+ "                                    <th  style=\"text-align: left;\">testtttttttttttttttttttt</th>\r\n"
    				+ "                                    <th>testtttttttttttttttttttt</th>\r\n"
    				+ "                                    <th>testtttttttttttttttttttt</th>\r\n"
    				+ "                                    <th style=\"text-align: right;\">testtttttttttttttttttttt </th>\r\n"
    				+ "                                </tr>"
    		
    		+ "                                <tr style=\"text-align: center; font-size: 12px; text-decoration:overline\">\r\n"
    		+ "                                    <th  style=\"text-align: left;\">Billed By</th>\r\n"
    		+ "                                    <th>Pulled By</th>\r\n"
    		+ "                                    <th>Reviewed By (Pharmacist)</th>\r\n"
    		+ "                                    <th style=\"text-align: right;\"> Dispensed By</th>\r\n"
    		+ "                                </tr>\r\n"
    		+ "                        \r\n"
    		+ "                            </tbody>\r\n"
    		+ "                        </table> \r\n"
    		+ "\r\n"
    		+ "                        <hr>\r\n"
    		+ "                        <p class=\"footerspan\"> Note:1. Return will only be accepted within 7 days of purchase.</p>\r\n"
    		+ "                        <p class=\"footerspan\"> 2. Syrups and products that need to be maintained between 2-8 degree Celsius will not be accepted for return.</p>\r\n"
    		+ "                        <p class=\"footerspan\">3. Receipt of purchase is also required.</p>\r\n"
    		+ "\r\n"
    		+ "                            <hr><span> <table class=\"footertable\" style=\"width: 100%;text-align: start;\">\r\n"
    		+ "                                <tbody>\r\n"
    		+ "                                    <tr style=\"text-align: center; font-size: 8px; font-weight: normal;\">\r\n";
    		 stmt01 = con.createStatement();
	        System.err.println("stmt: " +stmt01);
	        rs01 = stmt01.executeQuery(sql01);
	        while (rs01.next()){
	        	email = rs01.getString("xname"); 
	        	s+="                  <th>Printed By: "+email+"</th>\r\n";
	        }
	        	//System.out.println(email);
	        		s+="                                        <th>Prepared By: "+uname+"</th>\r\n"
    		+ "                                        <th>Printed Date: "+dateFormat.format(cal.getTime())+"</th>\r\n"
    		+ "                                    </tr>\r\n"
    		+ "                            \r\n"
    		+ "                                </tbody>\r\n"
    		+ "                            </table>   </span>\r\n"
    		+ "                            \r\n"
    		+ "                        <hr> <span class=\"footer\"> Powered By ZAB Framework © All Rights Reserved by Orange Solutions Ltd.\r\n"
    		+ "                            www.makeitorange.com.bd Printed on: "+dateFormat.format(cal.getTime())+"</span>\r\n"
    		+ "                    </footer>\r\n"
    		+ "                </th>\r\n"
    		+ "            </tr>\r\n"
    		+ "        </tfoot>";
        	
        }
        else {
            s = s + "</tbody>"
    		+ "<tfoot>\r\n"
    		+ "            <tr>\r\n"
    		+ "                <th>\r\n"
    		+ "                    <footer>\r\n"
    		+ "                        <table class=\"table2\" style=\"width: 100%;text-align: start; padding-top: 10px; margin-top: 20px;\">\r\n"
    		+ "                            <tbody>\r\n"
    		+ "                                <tr style=\"text-align: center; font-size: 12px; text-decoration:overline\">\r\n"
    		+ "                                    <th  style=\"text-align: left;\">Billed By</th>\r\n"
    		+ "                                    <th>Pulled By</th>\r\n"
    		+ "                                    <th>Reviewed By (Pharmacist)</th>\r\n"
    		+ "                                    <th style=\"text-align: right;\"> Dispensed By</th>\r\n"
    		+ "                                </tr>\r\n"
    		+ "                        \r\n"
    		+ "                            </tbody>\r\n"
    		+ "                        </table> \r\n"
    		+ "\r\n"
    		+ "                        <hr>\r\n"
    		+ "                        <p class=\"footerspan\"> Note:1. Return will only be accepted within 7 days of purchase.</p>\r\n"
    		+ "                        <p class=\"footerspan\"> 2. Syrups and products that need to be maintained between 2-8 degree Celsius will not be accepted for return.</p>\r\n"
    		+ "                        <p class=\"footerspan\">3. Receipt of purchase is also required.</p>\r\n"
    		+ "\r\n"
    		+ "                            <hr><span> <table class=\"footertable\" style=\"width: 100%;text-align: start;\">\r\n"
    		+ "                                <tbody>\r\n"
    		+ "                                    <tr style=\"text-align: center; font-size: 8px; font-weight: normal;\">\r\n";
            stmt01 = con.createStatement();
    		        System.err.println("stmt: " +stmt01);
    		        rs01 = stmt01.executeQuery(sql01);
    		        while (rs01.next()){
    		        	email = rs01.getString("xname"); 
    		s+="                  <th>Printed By: "+email+"</th>\r\n";
    		      }
    		//System.out.println(email);
    		s+="                                        <th>Prepared By: "+uname+"</th>\r\n"
    		+ "                                        <th>Printed Date:"+dateFormat.format(cal.getTime())+"</th>\r\n"
    		+ "                                    </tr>\r\n"
    		+ "                            \r\n"
    		+ "                                </tbody>\r\n"
    		+ "                            </table>   </span>\r\n"
    		+ "                            \r\n"
    		+ "                        <hr> <span class=\"footer\"> Powered By ZAB Framework © All Rights Reserved by Orange Solutions Ltd.\r\n"
    		+ "                            www.makeitorange.com.bd Printed on: "+dateFormat.format(cal.getTime())+"</span>\r\n"
    		+ "                    </footer>\r\n"
    		+ "                </th>\r\n"
    		+ "            </tr>\r\n"
    		+ "        </tfoot>";
        }
        

        s = s + "</table>"
		+ "</td>\r\n"
		+ "            </tr>\r\n"
		+ "            </<tbody>\r\n"
		+ "    </table>";
        s = s + "</div>";
        s = s + "</div>"
        + "<br/>"
        + "<br/>"
        + "<br/>";
        }

	} catch (SQLException esql) {
		// System.err.println(esql.getMessage());
		// zabinfo.log("zErr0002msg",esql.getMessage(),"Return Code = 0");
	} finally {
		try {
			if (stmt != null)
				stmt.close();

		} catch (SQLException esql) {
		}
		// System.err.println(s);
		writer.println(s);
	}

	pool.releaseConnection(con);
	writer.close();

}
}