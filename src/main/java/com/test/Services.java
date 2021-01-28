package com.test;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class Services
 */
public class Services extends HttpServlet {
	@Resource(name = "jdbc/hana")
	private static DataSource ds;
	private static final Logger logger = LoggerFactory.getLogger(Services.class); 

	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Services() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 logger.error("This is slf4j error"); 
	     
	     //reading environment variables
		 JSONObject jsonObj = new JSONObject(System.getenv("VCAP_SERVICES"));
		
		 //reading hanatrial
		 JSONArray jsonArr = jsonObj.getJSONArray("hanatrial");
		 JSONObject hanaCredentials =
		 jsonArr.getJSONObject(0).getJSONObject("credentials");
		 String user = hanaCredentials.getString("hdi_user");
		 String pwd = hanaCredentials.getString("hdi_password");
			
	      
	     try {
		 
	     Context ctx = new InitialContext(); 
	     DataSource ds  = (DataSource) ctx.lookup("java:comp/env/jdbc/hana");
	     Connection cn = ds.getConnection(user,pwd);
	     String schema = cn.getSchema();
		response.getWriter().append("schema name: "+ schema);
	     }
	     catch(Exception e) {
	    	 
	     }
	     
	     

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
