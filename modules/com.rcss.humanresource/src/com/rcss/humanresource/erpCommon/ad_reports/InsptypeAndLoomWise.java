package com.rcss.humanresource.erpCommon.ad_reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.erpCommon.ad_actionButton.ActionButtonDefaultData;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.ComboTableData;
import org.openbravo.erpCommon.utility.DateTimeData;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.xmlEngine.XmlDocument;
import org.openbravo.scheduling.ProcessBundle;
import org.openbravo.service.db.DalConnectionProvider;
import org.openbravo.dal.service.OBDal;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;


public class InsptypeAndLoomWise extends HttpSecureAppServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) {
		super.init(config);
		boolHist = false;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		VariablesSecureApp vars = new VariablesSecureApp(request);

		if (vars.commandIn("DEFAULT")) {
			
		String strDateFrom = vars.getGlobalVariable("inpDateFrom",
					"InsptypeAndLoomWise|dateFrom", "");
		String strDateTo = vars.getGlobalVariable("inpDateTo",
					"InsptypeAndLoomWise|dateTo", "");

		String strloomId = vars.getGlobalVariable("inpLoomcategory",
				"InsptypeAndLoomWise|loomcategory", "");
		String strremarkId = vars.getGlobalVariable("inpInspectontype",
				"InsptypeAndLoomWise|remark", "");
		//String strOrder = vars.getGlobalVariable("inpOrder", "InsptypeAndLoomWise|Order", "PunchOrder");
			printPage(response, vars, strDateFrom, strDateTo, strremarkId, strloomId );
		} 
		else if (vars.commandIn("PRINT_HTML")) {
			String strDateFrom = vars.getGlobalVariable("inpDateFrom",
					"InsptypeAndLoomWise|dateFrom");
		String strDateTo = vars.getGlobalVariable("inpDateTo",
					"InsptypeAndLoomWise|dateTo");
		
		String strloomId = vars.getRequestGlobalVariable("inpLoomcategory",
				"InsptypeAndLoomWise|loomcategory");
		String strremarkId = vars.getRequestGlobalVariable("inpInspectontype",
				"InsptypeAndLoomWise|remark");
		//String strOrder = vars.getGlobalVariable("inpOrder", "InsptypeAndLoomWise|Order", "PunchOrder");
			printPagePopUp(request, response, vars, strDateFrom, strDateTo, strremarkId, strloomId, "html");
		      //setHistoryCommand(request, "FIND");
		      
		    }else if (vars.commandIn("PRINT_XLS")) {
		    	String strDateFrom = vars.getGlobalVariable("inpDateFrom",
						"InsptypeAndLoomWise|dateFrom");
			String strDateTo = vars.getGlobalVariable("inpDateTo",
						"InsptypeAndLoomWise|dateTo");
			
			String strloomId = vars.getRequestGlobalVariable("inpLoomcategory",
					"InsptypeAndLoomWise|loomcategory");
			String strremarkId = vars.getRequestGlobalVariable("inpInspectontype",
					"InsptypeAndLoomWise|remark");
			//String strOrder = vars.getGlobalVariable("inpOrder", "InsptypeAndLoomWise|Order", "PunchOrder");
				printPagePopUp(request, response, vars, strDateFrom, strDateTo, strremarkId, strloomId, "xls");
			      //setHistoryCommand(request, "FIND");
		       // printPageDataHtml(response, vars, strDate, strProductCategory, strmProductId, strX, strY,
		           // strZ, "xls");
		      }

		else if (vars.commandIn("PRINT_PDF")) {
			String strDateFrom = vars.getGlobalVariable("inpDateFrom",
					"InsptypeAndLoomWise|dateFrom");
		String strDateTo = vars.getGlobalVariable("inpDateTo",
					"InsptypeAndLoomWise|dateTo");
		
		String strloomId = vars.getRequestGlobalVariable("inpLoomcategory",
				"InsptypeAndLoomWise|loomcategory");
		String strremarkId = vars.getRequestGlobalVariable("inpInspectontype",
				"InsptypeAndLoomWise|remark");
		//String strOrder = vars.getGlobalVariable("inpOrder", "InsptypeAndLoomWise|Order", "PunchOrder");
			printPagePopUp(request, response, vars, strDateFrom, strDateTo, strremarkId, strloomId, "pdf");
		}


		else
		{
			pageErrorPopUp(response);
		}
	}

	private void printPage(HttpServletResponse response,
			VariablesSecureApp vars, String strDateFrom, String strDateTo, String strremarkId, String strloomId
			) throws IOException,
			ServletException {
	

XmlDocument xmlDocument = xmlEngine.readXmlTemplate("com/rcss/humanresource/erpCommon/ad_reports/InsptypeAndLoomWise").createXmlDocument();

		xmlDocument
				.setParameter("calendar", vars.getLanguage().substring(0, 2));
		xmlDocument.setParameter("language",
				"defaultLang=\"" + vars.getLanguage() + "\";");
		xmlDocument.setParameter("directory", "var baseDirectory = \""
				+ strReplaceWith + "/\";\n");
	
		xmlDocument.setParameter("dateFrom", strDateFrom);
		xmlDocument.setParameter("dateTo", strDateTo);
		//xmlDocument.setParameter("rcbmProjectId", strprojectId);
		xmlDocument.setParameter("rcplInsploomctgryId", strloomId);
		xmlDocument.setParameter("rchrInspectiontypeId", strremarkId);

		xmlDocument.setParameter("dateFromdisplayFormat",
				vars.getSessionValue("#AD_SqlDateFormat"));
		xmlDocument.setParameter("dateFromsaveFormat",
				vars.getSessionValue("#AD_SqlDateFormat"));
		xmlDocument.setParameter("dateTodisplayFormat",
				vars.getSessionValue("#AD_SqlDateFormat"));
		xmlDocument.setParameter("dateTosaveFormat",
				vars.getSessionValue("#AD_SqlDateFormat"));

		ToolBar toolbar = new ToolBar(this, vars.getLanguage(),
				"InsptypeAndLoomWise", false, "", "", "", false, "ad_process",
				strReplaceWith, false, true);
		toolbar.prepareSimpleToolBarTemplate();
		xmlDocument.setParameter("toolbar", toolbar.toString());

		// New interface paramenters
		try {
			WindowTabs tabs = new WindowTabs(this, vars,
					"com.rcss.humanresource.erpCommon.ad_reports.InsptypeAndLoomWise");

			xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
			xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
			xmlDocument.setParameter("childTabContainer", tabs.childTabs());
			xmlDocument.setParameter("theme", vars.getTheme());
			NavigationBar nav = new NavigationBar(this, vars.getLanguage(),
					"InsptypeAndLoomWise.html", classInfo.id, classInfo.type,
					strReplaceWith, tabs.breadcrumb());
			xmlDocument.setParameter("navigationBar", nav.toString());
			LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(),
					"InsptypeAndLoomWise.html", strReplaceWith);
			xmlDocument.setParameter("leftTabs", lBar.manualTemplate());
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	/*	{
			OBError myMessage = vars.getMessage("POTracking");
			vars.removeMessage("POTracking");
			if (myMessage != null) {
				xmlDocument.setParameter("messageType", myMessage.getType());
				xmlDocument.setParameter("messageTitle", myMessage.getTitle());
				xmlDocument.setParameter("messageMessage",
						myMessage.getMessage());
			}
		} */

		// //----

		// xmlDocument.setParameter("array", strArray);
		
	/*	try {
			ComboTableData comboTableData = new ComboTableData(vars, this,
					"TABLEDIR", "RCBM_Project_ID", "", "", Utility.getContext(
							this, vars, "#User_Org", "GoodsReceiptReport"),
					Utility.getContext(this, vars, "#User_Client",
							"GoodsReceiptReport"), 0);
			Utility.fillSQLParameters(this, vars, null, comboTableData,
					"GoodsReceiptReport", "");
			xmlDocument.setData("reportRCBM_PROJECTID", "liststructure",
					comboTableData.select(false));
			comboTableData = null;
		} catch (Exception ex) {
			throw new ServletException(ex);
		}*/
				try {
			ComboTableData comboTableData = new ComboTableData(vars, this,
					"TABLEDIR", "RCPL_INSPLOOMCTGRY_ID", "", "", Utility.getContext(
							this, vars, "#User_Org", "InsptypeAndLoomWise"),
					Utility.getContext(this, vars, "#User_Client",
							"InsptypeAndLoomWise"), 0);
			Utility.fillSQLParameters(this, vars, null, comboTableData,
					"InsptypeAndLoomWise", "");
			xmlDocument.setData("reportRCPL_INSPLOOMCTGRYID", "liststructure",
					comboTableData.select(false));
			comboTableData = null;
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
				try {
			ComboTableData comboTableData = new ComboTableData(vars, this,
					"TABLEDIR", "RCHR_INSPECTIONTYPE_ID", "", "", Utility.getContext(
							this, vars, "#User_Org", "InsptypeAndLoomWise"),
					Utility.getContext(this, vars, "#User_Client",
							"InsptypeAndLoomWise"), 0);
			Utility.fillSQLParameters(this, vars, null, comboTableData,
					"InsptypeAndLoomWise", "");
			xmlDocument.setData("reportRCHR_INSPECTIONTYPEID", "liststructure",
					comboTableData.select(false));
			comboTableData = null;
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(xmlDocument.print());
		out.close();
	}

	private void printPagePopUp(HttpServletRequest request,HttpServletResponse response, VariablesSecureApp vars, String strDateFrom, String strDateTo, String strremarkId, String strloomId, String strOutput) throws IOException, ServletException {
		XmlDocument xmlDocument = xmlEngine.readXmlTemplate("com/rcss/humanresource/erpCommon/ad_reports/InsptypeAndLoomWise").createXmlDocument();
	
		String 	strReportName = "@basedesign@/com/rcss/humanresource/erpCommon/ad_reports/InsptypeAndLoomWise.jrxml";

		
        System.out.println("from date1:" + strDateFrom);
        System.out.println("from date1:" + strDateTo);
		//System.out.println("Project:" + strprojectId);
		System.out.println("Product:" + strremarkId);
		System.out.println("Bpartner:" + strloomId);
     	SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = null;
		Date toDate = null;
		
		try{
			fromDate = sdf.parse(strDateFrom);
			toDate = sdf.parse(strDateTo);
			
		}catch(Exception e){
			System.out.println(e);
			//throws new Exception("ERROR: converting date");
		}
		
		
		System.out.println(" aftr from date1:" + fromDate);
		System.out.println(" aftr from date2:" + toDate);
		
		
			
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    
    parameters.put("BeforeDate1", fromDate);
    parameters.put("BeforeDate2", toDate);
	//parameters.put("Project_ID", strprojectId);
	parameters.put("Remark_ID", strremarkId);
	parameters.put("LoomCatgr_ID", strloomId);
	//parameters.put("Orderby_Punch", strOrder);

    renderJR(vars, response, strReportName, strOutput, parameters, null, null);

	
	}

}
