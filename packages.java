package zaberp.zab;

import java.sql.*;
import java.io.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;

@SuppressWarnings("serial")
public class packages extends HttpServlet {

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
		// set the content type
		responseObj.setContentType("text/html");

		responseObj.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = responseObj.getWriter();
		Connection con = null;
		Statement stmt = null;
		String s = "";
		String sql = "";
		String result = "false";

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
		JSONObject obj1 = new JSONObject();
		JSONArray list = new JSONArray();
		sql = "select xitem,xdesc,isnull (xipprice,0)as xipprice ,isnull(xopprice,0)as xopprice,"
				+ "isnull(xtitem,0)as xtitem,isnull(xcatitem,'')as xcatitem from caitem where xgitem='1030'";

		rs = null;
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
				JSONObject obj = new JSONObject();
				obj.put("Item", rs.getString("xitem"));
				obj.put("Description", rs.getString("xdesc"));
				obj.put("IPD Price", rs.getString("xipprice"));
				obj.put("OPD Price", rs.getString("xopprice"));
				obj.put("Item Type", rs.getString("xtitem"));
				obj.put("Package Nature", rs.getString("xcatitem"));
				list.add(obj);
//            System.out.println(obj);
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

		// obj1.put("productlist", list);
		if (result.equals("true"))
			writer.println(list.toJSONString());
		else
			writer.println("false");
		writer.close();
	}
}