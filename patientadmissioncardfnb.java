package zaberp.zab;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;   
import java.util.Date;  
import java.util.Calendar;

public class patientadmissioncardfnb implements zabInterface { 
    public zabInfo zabinfo = null;
    public zabFields field = null;
    public zabForm form = null;
    public String id = null;
    public String xuser = null;
    
    static final char DQUOTE = '"';
    String[] sa = null;
    String[] result = null;
    String[][] resultrow = null;
    String[][] resultcusrow = null;
    String[] datarow = null;
    String sql = "";
    String s = "";
    String imgPath = "D:\\Tomcat9\\webapps\\zab\\images\\carimg\\";

    public patientadmissioncardfnb() {
    }
    

    public void setData(zabInfo zabinfo) {
        this.zabinfo = zabinfo;
        this.id = (String)zabinfo.ses.getAttribute("id");
        this.xuser = (String)zabinfo.ses.getAttribute("email");
    }

    public void setForm(zabForm form) {
        this.form = form;
    }

    public void setField(zabFields field) {
        this.field = field;
    }

    public String getResult(String param) {
        Argument a = new Argument(param);
        Argument z = new Argument(param, '{', '}');
        switch(a.c0) {
        case 's':
            return this.Search(z.arg, z.command);
        default:
            return "";
        }
    }

    public String Search(String param, String command) {
       // System.out.println(command.charAt(8));
        switch(command.charAt(8)) {
        case 'a':
            return this.SearchByAdmission();
        }
		return command;
    }

    public String SearchByAdmission() {
        String[] result = null;
        String xadmissionno1 = "";
        String xwing1 = "";
        String xfloor1 = "";
        String xcostitem1 = "";
//        System.out.println("Start");
        Date date = Calendar.getInstance().getTime(); 
        SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        String strDate = targetDateFormat.format(date); 
        
        s+="<style id=\"table_style\" type=\"text/css\" href=\"homestyle.css\">"
        + "		        p {\r\n"
        + "            margin: 0;\r\n"
        + "        }\r\n"
        + "        @page {\r\n"
        + "            margin: 30mm 10mm 10mm 10mm;\r\n"
        + "        }\r\n"
        + "\r\n"
        + "        @media print {\r\n"
        + "\r\n"
        + "            .footer {\r\n"
        + "                height: 20px;\r\n"
        + "                background: #ffff;\r\n"
        + "            }\r\n"
        + "\r\n"
        + "            .footer>div {\r\n"
        + "                position: fixed;\r\n"
        + "                bottom: 0;\r\n"
        + "                right: 0;\r\n"
        + "                width: 100%;\r\n"
        + "                background: #ffff;\r\n"
        + "            }\r\n"
        + "\r\n"
        + "            table tr th {\r\n"
        + "                text-align: start;\r\n"
        + "                width: 70px;\r\n"
        + "            }\r\n"
        + "\r\n"
        + "            button {\r\n"
        + "                display: none;\r\n"
        + "            }\r\n"
        + "\r\n"
        + "            .bold {\r\n"
        + "                font-weight: bold !important;\r\n"
        + "                text-align: start;\r\n"
        + "                font-size: 8px;\r\n"
        + "            }\r\n"
        + "\r\n"
        + "            .table td {\r\n"
        + "                text-align: start;\r\n"
        + "                font-size: 9px;\r\n"
        + "                border-bottom: 0.01px solid #dddddd;\r\n"
        + "                padding: 1px;\r\n"
        + "                font-weight: lighter;\r\n"
        + "            }\r\n"
        + "        .table th {\r\n"
        + "            text-align: start;\r\n"
        + "            font-size: 11px;\r\n"
        + "            border-bottom: 0.01px solid #dddddd;\r\n"
        + "            padding: 1px;\r\n"
        + "            font-weight: bold;\r\n"
        + "				vertical-align: middle;"
        + "        }\r\n"
        + "\r\n"
        + "            .table-tr-th tr th {\r\n"
        + "                text-align: start;\r\n"
        + "            }\r\n"
        + "        }"
        + "       .containers {\r\n"
        + "            /* align-items: center; */\r\n"
        + "            display: flex;\r\n"
        + "            justify-content: center;\r\n"
        + "        }\r\n"
        + "\r\n"
        + "        .card {\r\n"
        + "            width: 400px;\r\n"
        + "            padding: 16px 26px;\r\n"
        + "        }"
		+ "    </style>";
        
		String xadmissionno = zabDisplay.getField("xadmissionno", this.form.objects).value;
		xadmissionno1= "AND isnull(a.xadmissionno,'')>=(CASE when '"+xadmissionno+"'='' then '' else '"+xadmissionno+"' end)"
				 + "	AND isnull(a.xadmissionno,'')<=(CASE when '"+xadmissionno+"'='' then 'zz' else '"+xadmissionno+"' end)";
		String xwing = zabDisplay.getField("xwing", this.form.objects).value;
		xwing1= "AND isnull(a.xwing,'')>=(CASE when '"+xwing+"'='' then '' else '"+xwing+"' end)"
				+ "	 AND isnull(a.xwing,'')<=(CASE when '"+xwing+"'='' then 'zz' else '"+xwing+"' end)";
		String xfloor = zabDisplay.getField("xfloor", this.form.objects).value;
		xfloor1= "AND isnull(a.xfloor,'')>=(CASE when '"+xfloor+"'='' then '' else '"+xfloor+"' end"
				 + "	 AND isnull(a.xfloor,'')<=(CASE when '"+xfloor+"'='' then 'zz' else '"+xfloor+"' end)";
		String xcostitem = zabDisplay.getField("xcostitem", this.form.objects).value;
		xcostitem1= "AND isnull(a.xcostitem,'')>=(CASE when '"+xcostitem+"'='' then '' else '"+xcostitem+"' end"
				 + "	 AND isnull(a.xcostitem,'')<=(CASE when '"+xcostitem+"'='' then 'zz' else '"+xcostitem+"' end)";
		
		
		

                s+= "<div class='container'>"
                + "<!-- Trigger the modal with a button -->"
                + "<!-- Modal -->"
                + "<div class='modal' id='apptSearchModal' tabindex='-1' role='dialog'>"
                + "<div class='modal-dialog'>"
                + "<!-- Modal content-->"
                + " <div class='modal-content'>"
                + "<input class=\"btn btn-primary\" type=\"button\" value=\"Print\" onclick=\"printDiv()\" style=\"position: fixed;right: 50px;top: 100px;\">"
                + "<div class='modal-header'>"
                + "<button type='button' class='close' data-dismiss='modal' aria-label='Close'>"
                + " <span aria-hidden='true'>&times;</span>"
                + "</button>"
                + "</div>"
                + "<div id=\"GFG\" class='modal-body'>"
                + "<table class=\"table\" style=\"border-collapse: collapse;font-size: 11px;width: 100%;border-radius: 5px 5px 0 0;overflow: hidden;\">\r\n"
        
                + "        <tbody>";
                int records = 0;
                
                String[][] datarow = null;
                
                sql = "	 select xadmissionno,xpatient,xname,xage,xsex,xroom,xbedno,xfloor,xwing,xcurrdiet,xdesc,xlnfooduni1,xbrfooduni1,xbrfooduni2,xbrfooduni3,xbrfooduni4,xdnfooduni1,xdnfooduni2,xdnfooduni3\r\n"
                		+ "	 from patientadmissionfnbview \r\n"
                		+ "	 where \r\n"
                		+ "	 zid=400010\r\n"
                		+ "	 and xstatus='Open'\r\n"
                		+ "	 and left(xadmissionno,3)='ADM'";
               //System.out.println(sql);
                datarow = this.zabinfo.getSqlRows(this.sql);
                
                if (datarow==null) {
                	s+="<h1 style=\"color: red;\">No Data Found</h1>";
                }
                else {
                	for(int i =0;i<datarow.length;i++) {
                    	
        				
		                    s+= "<div class=\"containers\">\r\n"
		                    		+ "        <div class=\"card\">\r\n"
		                    		+ "            \r\n"
		                    		+ "                <p><b>Admission No :"+ datarow[i][0] +"</b></p>\r\n"
		                    		+ "                <p><b>Patient ID :"+ datarow[i][1] +"</b></p>\r\n"
		                    		+ "                <p><b>Patient Name :"+ datarow[i][2] +"</b></p>\r\n"
		                    		+ "                <p><b>Age :"+ datarow[i][3] +"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <b>Gender :"+ datarow[i][4] +"</b></p>\r\n"
		                    		+ "                <p><b>Room :"+ datarow[i][5] +"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    <b>Bed No :"+ datarow[i][6] +"</b></p>\r\n"
		                    		+ "                <p><b>Floor :"+ datarow[i][7] +"</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    <b>Wing :"+ datarow[i][8] +"</b></p>\r\n"
		                    		+ "                <p><b>Type Of Diet :"+ datarow[i][9] +"</b></p>\r\n"
		                    		+ "                <p><b>Food :"+ datarow[i][10] +"</b></p1>\r\n"
		                    		+ "                <hr>\r\n"
		                    		+ "                <p><b>"+ datarow[i][11] +"</b></p1>\r\n"
		                    		+ "                <p><b>"+ datarow[i][12] +"</b></p1>\r\n"
		                    		+ "                <p><b>"+ datarow[i][13] +"</b></p1>\r\n"
		                    		+ "                <p><b>"+ datarow[i][14] +"</b></p1>\r\n"
		                    		+ "                <p><b>"+ datarow[i][15] +"</b></p1>\r\n"
		                    		+ "                <p><b>"+ datarow[i][16] +"</b></p1>\r\n"
		                    		+ "                <p><b>"+ datarow[i][17] +"</b></p1>\r\n"
		                    		+ "                <p><b>"+ datarow[i][18] +"</b></p1><br><br><center>\r\n"
		                    		+ "                <p><b>Print Date :"+strDate+"</b></p>\r\n"
		                    		+ "                </center>\r\n"
		                    		+ "        </div>\r\n"
		                    		+ "    </div>"
		                    		+ "<p style=\"page-break-after: always;\">&nbsp;</p>";
		                    records++; 
		                    
                	}
                }
                
                s+= "</tbody>"
                		
	    		
	    		
                + "</table>"
               
                + "</div>"
                + "<div class='modal-footer'>"
                + "<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>"
                + "</div>"
                + "</div>"
                + "</div>"
                + "</div>"
                + "</div>"
                + "<script>"
                + "$(document).ready(function(){ $('#apptSearchModal').modal('show');});"
                + "</script>";

            return this.s;
        }
    }



