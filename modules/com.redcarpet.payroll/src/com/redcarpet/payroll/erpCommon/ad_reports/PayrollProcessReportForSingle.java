package com.redcarpet.payroll.erpCommon.ad_reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rcss.humanresource.data.RchrAttdProcess;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.filter.IsIDFilter;
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
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.database.ConnectionProvider;

import com.rcss.humanresource.data.RchrEmployee;
import org.openbravo.database.ConnectionProvider;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;


public class PayrollProcessReportForSingle extends HttpSecureAppServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) {
		super.init(config);
		boolHist = false;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String stremployeeId="";
		String strdeptId="";
		VariablesSecureApp vars = new VariablesSecureApp(request);

		if (vars.commandIn("DEFAULT")) {

		String strPayrollId = vars.getRequestGlobalVariable("inpPayrollId",
					"PayrollProcessReportForSingle|payrollid");

			String strDateFrom = vars.getRequestGlobalVariable("inpDateFrom",
					"PayrollProcessReportForSingle|dateFrom");
			String strDateTo = vars.getRequestGlobalVariable("inpDateTo",
					"PayrollProcessReportForSingle|dateTo");

			String strInprchrEmployeeId_IN = vars.getRequestInGlobalVariable("inprchrEmployeeId_IN",
					"PayrollProcessReportForSingle|employee", IsIDFilter.instance);
		String strempType = vars.getRequestGlobalVariable("inpEmployeeType", "PayrollProcessReport|employeetype");

      	stremployeeId = vars.getRequestGlobalVariable("inpEmployee",
				"PayrollProcessReportForSingle|employee");

		strdeptId = vars.getRequestGlobalVariable("inpDept",
				"PayrollProcessReportForSingle|dept");
		String strsubdeptId = vars.getRequestGlobalVariable("inpSubDept",
				"PayrollProcessReportForSingle|subdept");

		String strPayroll = vars.getGlobalVariable("inppayroll",
				"PayrollProcessReportForSingle|payroll", "payrollDisplay");
		String strPf = vars.getRequestGlobalVariable("inppf",
				"PayrollProcessReportForSingle|pf");
		String strBank = vars.getRequestGlobalVariable("inpbank",
				"PayrollProcessReportForSingle|bank");
		String strBranch = vars.getRequestGlobalVariable("inpbranch",
				"PayrollProcessReportForSingle|branch");
		String attachmentValue="";
			log4j.info("Employees are 1 "+strInprchrEmployeeId_IN);
			printPage(response, vars, strDateFrom, strDateTo, strInprchrEmployeeId_IN);

		//printPage(response, vars, strPayrollId, strdeptId, strsubdeptId, stremployeeId, strempType, strPayroll, strPf, strBank, strBranch);
		}

		else if (vars.commandIn("PRINT_PDF")) {

		String strPayrollId = vars.getGlobalVariable("inpPayrollId",
					"PayrollProcessReportForSingle|payrollid", "");


			String strDateFrom = vars.getRequestGlobalVariable("inpDateFrom",
					"PayrollProcessReportForSingle|dateFrom");
			String strDateTo = vars.getRequestGlobalVariable("inpDateTo",
					"PayrollProcessReportForSingle|dateTo");

		stremployeeId = vars.getRequestGlobalVariable("inpEmployee",
				"PayrollProcessReportForSingle|employee");
			String strInprchrEmployeeId_IN = vars.getRequestInGlobalVariable("inprchrEmployeeId_IN",
					"PayrollProcessReportForSingle|employee", IsIDFilter.instance);




		strdeptId = vars.getRequestGlobalVariable("inpDept",
					"PayrollProcessReportForSingle|dept");
		String strsubdeptId = vars.getRequestGlobalVariable("inpSubDept",
				"PayrollProcessReportForSingle|subdept");
		String strempType = vars.getRequestGlobalVariable("inpEmployeeType", "PayrollProcessReport|employeeType");
		String strPayroll = vars.getGlobalVariable("inppayroll",
				"PayrollProcessReportForSingle|payroll", "payrollDisplay");

		String strPf = vars.getRequestGlobalVariable("inppf",
				"PayrollProcessReportForSingle|pf");
		String strBank = vars.getRequestGlobalVariable("inpbank",
				"PayrollProcessReportForSingle|bank");
		 String strBranch = vars.getRequestGlobalVariable("inpbranch",
					"PayrollProcessReportForSingle|branch");
		 String strPunchNos = vars.getRequestGlobalVariable("inpPunchNo",
					"PayrollProcessReportForSingle|PuncheNo");
		 String strType = vars.getRequestGlobalVariable("inpcontains",
					"PayrollProcessReportForSingle|Contains");
		 log4j.info("Employees are 2 "+strInprchrEmployeeId_IN);
		//String strempType = vars.getRequestGlobalVariable("inpEmployeeType","PayrollProcessReport|empType");
		//System.out.println("empid.."+stremployeeId);
			printPagePopUp(request, response, vars, strDateFrom, strDateTo, strInprchrEmployeeId_IN, "pdf");
		//printPagePopUp(request, response, vars, strPayrollId, strdeptId, strsubdeptId, stremployeeId,
				//strempType, strPayroll, strPf, strBank, strBranch, strPunchNos,strType, "pdf");
		}


		else
		{
			pageErrorPopUp(response);
		}
	}

	private void printPage(HttpServletResponse response,
						   VariablesSecureApp vars, String strDateFrom, String strDateTo, String strInprchrEmployeeId_IN) throws IOException,
			ServletException {


		XmlDocument xmlDocument = xmlEngine.readXmlTemplate("com/redcarpet/payroll/erpCommon/ad_reports/PayrollProcessReportForSingle").createXmlDocument();

		xmlDocument
				.setParameter("calendar", vars.getLanguage().substring(0, 2));
		xmlDocument.setParameter("language",
				"defaultLang=\"" + vars.getLanguage() + "\";");
		xmlDocument.setParameter("directory", "var baseDirectory = \""
				+ strReplaceWith + "/\";\n");

		xmlDocument.setParameter("dateFrom", strDateFrom);
		xmlDocument.setParameter("dateTo", strDateTo);
		//xmlDocument.setParameter("rcbmProjectId", strprojectId);
		//xmlDocument.setParameter("rchrEmpDeptId", strdeptId);
		//xmlDocument.setParameter("rcprSubDeptId", strsubdeptId);
		//xmlDocument.setParameter("rchrAttdProcessId", strPayrollId);
		//xmlDocument.setParameter("rchrEmployeeId", stremployeeId);
		//xmlDocument.setParameter("rchrEmpType", strempType);

		//xmlDocument.setParameter("rcprShiftId", strshiftId);
		//xmlDocument.setParameter("rcprSubDeptId", strsubdeptId);
		//xmlDocument.setParameter("rcprDesignationId", strdesignationId);

		xmlDocument.setParameter("dateFromdisplayFormat",
				vars.getSessionValue("#AD_SqlDateFormat"));
		xmlDocument.setParameter("dateFromsaveFormat",
				vars.getSessionValue("#AD_SqlDateFormat"));
		xmlDocument.setParameter("dateTodisplayFormat",
				vars.getSessionValue("#AD_SqlDateFormat"));
		xmlDocument.setParameter("dateTosaveFormat",
				vars.getSessionValue("#AD_SqlDateFormat"));

		ToolBar toolbar = new ToolBar(this, vars.getLanguage(),
				"PayrollProcessReport", false, "", "", "", false, "ad_process",
				strReplaceWith, false, true);
		toolbar.prepareSimpleToolBarTemplate();
		xmlDocument.setParameter("toolbar", toolbar.toString());

		// New interface paramenters
		try {
			WindowTabs tabs = new WindowTabs(this, vars,
					"com.redcarpet.payroll.erpCommon.ad_reports.PayrollProcessReportForSingle");

			xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
			xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
			xmlDocument.setParameter("childTabContainer", tabs.childTabs());
			xmlDocument.setParameter("theme", vars.getTheme());
			NavigationBar nav = new NavigationBar(this, vars.getLanguage(),
					"PayrollProcessReport.html", classInfo.id, classInfo.type,
					strReplaceWith, tabs.breadcrumb());
			xmlDocument.setParameter("navigationBar", nav.toString());
			LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(),
					"PayrollProcessReport.html", strReplaceWith);
			xmlDocument.setParameter("leftTabs", lBar.manualTemplate());
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		/*
		try {
			ComboTableData comboTableData = new ComboTableData(vars, this,
					"TABLEDIR", "RCHR_EMP_DEPT_ID", "", "", Utility.getContext(
					this, vars, "#User_Org", "PayrollProcessReport"),
					Utility.getContext(this, vars, "#User_Client",
							"PayrollProcessReport"), 0);
			Utility.fillSQLParameters(this, vars, null, comboTableData,
					"PayrollProcessReport", "");
			xmlDocument.setData("reportRCHR_EMPDEPTID", "liststructure",
					comboTableData.select(false));
			comboTableData = null;
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

		try {
			ComboTableData comboTableData = new ComboTableData(vars, this,
					"TABLEDIR", "RCHR_EMPLOYEE_ID", "", "", Utility.getContext(
					this, vars, "#User_Org", "PayrollProcessReport"),
					Utility.getContext(this, vars, "#User_Client",
							"PayrollProcessReport"), 0);
			Utility.fillSQLParameters(this, vars, null, comboTableData,
					"PayrollProcessReport", "");
			xmlDocument.setData("reportRCHR_EMPLOYEEID", "liststructure",
					comboTableData.select(false));
			comboTableData = null;
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

		try {
			ComboTableData comboTableData = new ComboTableData(vars, this,
					"TABLEDIR", "RCHR_SUBDEPARTMENT_ID", "", "", Utility.getContext(
					this, vars, "#User_Org", "ShiftForeCastingReport"),
					Utility.getContext(this, vars, "#User_Client",
							"ShiftForeCastingReport"), 0);
			Utility.fillSQLParameters(this, vars, null, comboTableData,
					"PayrollProcessReport", "");
			xmlDocument.setData("reportRCHR_SUBDEPARTMENTID", "liststructure",
					comboTableData.select(false));
			comboTableData = null;
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

		try{
			ComboTableData comboTableData = new ComboTableData(vars, this,
					"TABLEDIR", "RCHR_ATTD_PROCESS_ID", "", "", Utility.getContext(
					this, vars, "#User_Org", "PayrollProcessReport"),
					Utility.getContext(this, vars, "User_Client",
							"PayrollProcessReport"),0);
			Utility.fillSQLParameters(this, vars, null, comboTableData,
					"PayrollProcessReport", "");
			xmlDocument.setData("reportRCHR_ATTDPROCESSID", "liststructure",
					comboTableData.select(false));
			comboTableData = null;
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		*/
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(xmlDocument.print());
		out.close();
	}









	private void printPagePopUp(HttpServletRequest request,HttpServletResponse response,
								VariablesSecureApp vars, String strDateFrom, String strDateTo, String strInprchrEmployeeId_IN, String strOutput) throws IOException, ServletException {
		XmlDocument xmlDocument = xmlEngine.readXmlTemplate("com/redcarpet/payroll/erpCommon/ad_reports/PayrollProcessReportForSingle").createXmlDocument();

		String documentNos = "";
		String discard[] = { "sectionPartner" };

		//response.setHeader();



		if (strInprchrEmployeeId_IN.equals("")){
			advisePopUp(request, response, "Error",
					Utility.messageBD(this, "ProcessStatus-W", vars.getLanguage()),
					Utility.messageBD(this, "Rchr_MandatoryFields", vars.getLanguage()));
		} else {
			RcplPayrollReportData[] data = null;

			OBError myMessage = null;
			myMessage = new OBError();
			try {
				data = RcplPayrollReportData.selectPayrollData(this,
						Utility.getContext(this, vars, "#User_Client", "PayrollProcessReportForSingle"), strDateFrom, strDateTo, strInprchrEmployeeId_IN);
			} catch (ServletException ex) {
				myMessage = Utility.translateError(this, vars, vars.getLanguage(), ex.getMessage());
			}

			log4j.info("Date is "+data);



			if (data == null || data.length == 0) {
				advisePopUp(request, response, "WARNING",
						Utility.messageBD(this, "ProcessStatus-W", vars.getLanguage()),
						Utility.messageBD(this, "NoDataFound", vars.getLanguage()).concat("Employee PunchNo"));
			} else {
				String 	strReportName="";
				StringBuffer attachmentName=new StringBuffer("StaffPayrollReportFromandToDates");
				//System.out.println("under staff");
				strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/StaffPayrollReportFromandToDates.jrxml";
				//attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_").append("PayrollProcessReport");
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				//System.out.println("processid.."+strPayrollId);
				// System.out.println("employeeid.."+stremployeeId);
				// System.out.println("dept id.."+strdeptId);
				log4j.info("from date1:" + strDateFrom);
				log4j.info("from date1:" + strDateTo);
				//System.out.println("Project:" + strprojectId);
				//System.out.println("Product:" + strshiftId);
				//System.out.println("Bpartner:" + stremployeeId);
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


				log4j.info(" after Parsed date1:" + fromDate);
				log4j.info(" after Parsed date2:" + toDate);



				//parameters.put("fromDate", fromDate);
				//parameters.put("toDate", toDate);

				//parameters.put("Dept_ID", strdeptId);
				//parameters.put("EmpType", empTypeTemp);
				//parameters.put("SubDept_ID", strsubdeptId);
				//parameters.put("Emp_PF", strPf);
				//parameters.put("Emp_Bank", strBank);
				//parameters.put("Dyieng", strBranch);
				//parameters.put("type", type);
				//parameters.put("documentno", documentNos);

				renderJR(vars, response, strReportName,attachmentName.toString(), strOutput, parameters, data, null);
			}

		}











	}


	private void printPagePopUp(HttpServletRequest request,HttpServletResponse response,
			VariablesSecureApp vars, String strPayrollId,
		String strdeptId, String strsubdeptId, String stremployeeId, String strempType,
		String strPayroll, String strPf, String strBank, String strBranch,String strPunchNos,String strType, String strOutput) throws IOException, ServletException {
		XmlDocument xmlDocument = xmlEngine.readXmlTemplate("com/redcarpet/payroll/erpCommon/ad_reports/PayrollProcessReportForSingle").createXmlDocument();

		String documentNos = "";

		if(!strPunchNos.equals("") || strPunchNos != null){
			documentNos = getDocumentnos(strPunchNos);
		}

		String type = "";
		System.out.println("documentNos are "+documentNos);

		if(strType.equals("Y")){
			type="in";
		}else if(strType.equals("N")){
			type="not in";
		}

		StringBuffer attachmentName = new StringBuffer("");
		if(!strPayrollId.equals("") ){
			RchrAttdProcess payrollProcessYear = OBDal.getInstance().get(RchrAttdProcess.class,strPayrollId);
			if(payrollProcessYear.getMonthname().contains("-")){
				String name = payrollProcessYear.getMonthname().replace("-","_");
				attachmentName.append(name);
			}else
				attachmentName.append(payrollProcessYear.getMonthname().substring(0,2));
		}




		//response.setHeader();


		String 	strReportName="";


		String  empTypeTemp;
			if(stremployeeId!=""||stremployeeId.length()!=0){
			RchrEmployee employee= OBDal.getInstance().get(RchrEmployee.class, stremployeeId);
			empTypeTemp=employee.getEmployeeType();
			if(employee.getEmployeeType().equals(new String("Operator"))){
				if(!strempType.equals("Operator")){
					String language = OBContext.getOBContext().getLanguage().getLanguage();
					ConnectionProvider conn = new DalConnectionProvider(false);
					throw new OBException(Utility.messageBD(conn, "Please select Opeartor Type..", language));

				}
			}else{
				if(employee.getEmployeeType().equals("Staff")){
					if(!strempType.equals("Staff")){
					String language = OBContext.getOBContext().getLanguage().getLanguage();
					ConnectionProvider conn = new DalConnectionProvider(false);
					throw new OBException(Utility.messageBD(conn, "Please select Staff Type..", language));


					}
				}
				else if(!strempType.equals("Semi Staff")){
					String language = OBContext.getOBContext().getLanguage().getLanguage();
					ConnectionProvider conn = new DalConnectionProvider(false);
					throw new OBException(Utility.messageBD(conn, "Please select Semi Staff Type..", language));


				}
			}
		} else {
				empTypeTemp=strempType;
		}
		String branch = "BranchALL";
		if(strBranch.equals("Y")){
			branch="Dyeing";
		}else if(strBranch.equals("N")){
			branch = "Weaving";
		}
		String pf = "PFAll";
		if(strPf.equals("Y")){
			pf="PF";
		}else if(strPf.equals("N"))
			pf="NonPF";

		String bankType = "BankALL";
		if(strBank.equals("Y"))
			bankType = "Bank";
		else if(strBank.equals("N"))
			bankType ="Non Bank";




		if(strempType.equals("Operator")){

			if (!strempType.equals("") && strPayroll.equals("payrollDisplay")) {
				//strReportType="Daily Over Time Report";
				if(strBank.equals("Y")){
					strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/OperatorDisplayBank.jrxml";
					attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_")
							.append("DisplayReport");
				}else{
	            	strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/OperatorDisplayReport.jrxml";
					attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_")
							.append(branch).append("_").append("DisplayReport");
				}
				//attachmentName = attachmentName.append(strempType).append(pf).append(branch).append("Display").append("NonBank");
	        } else if(!strempType.equals("") && strPayroll.equals("payrollAccounts")){
				strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/OperatorDisplayAccounts.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(branch).append("_").append("AccountsDisplayReport");
			}else if(!strempType.equals("") && strPayroll.equals("payrollMonthly")){
	        	//strReportType="Payroll Over Time Report";
				strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/OperatorPayrollReportDisplay.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_").append("ProjectionReport");
	        }else if (strPayroll.equals("payrollMonthlyExcluded")) {
				//strReportType="Payroll Over Time Report";
				strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/ExcludedSalariesForBank.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_")
						.append("ExcludedSalariesForBank");
			} else if (strPayroll.equals("payrollMonthlyExcludedDetails")) {
				//strReportType="Payroll Over Time Report";
				strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/ExcludedSalariesDetails.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_")
						.append("ExcludedSalariesForBank");
			} else {
	        	strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/OperatorPayrollReport.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_").append("DetailedReport");
	        }
		} else if(strempType.equals("Staff") || strempType.equals("Semi Staff")){

			if (!strempType.equals("") && strPayroll.equals("payrollDisplay")) {
				//strReportType="Daily Over Time Report";
				if(strBank.equals("Y")){
					strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/StaffDisplayBank.jrxml";
					attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_")
							.append("DisplayReport");
				}else{
	            	strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/StaffDisplayReport.jrxml";
					attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_")
							.append("DisplayReport");
				}
	        }else if(!strempType.equals("") && strPayroll.equals("payrollAccounts")){

				strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/StaffDisplayAccounts.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(branch).append("_").append("AccountsDisplayReport");
			} else if (strPayroll.equals("payrollMonthlyExcluded")) {
	        	//strReportType="Payroll Over Time Report";
	        	strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/ExcludedSalariesForBank.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_")
						.append("ExcludedSalariesForBank");
	        } else if (strPayroll.equals("payrollMonthlyExcludedDetails")) {
				//strReportType="Payroll Over Time Report";
				strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/ExcludedSalariesDetails.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_")
						.append("ExcludedSalariesForBank");
			} else {
				//strReportType="Payroll Over Time Report";
				strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/StaffPayrollReport.jrxml";
				attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_")
						.append("DetailedReport");
			}

		} else {
			//System.out.println("under staff");
		    strReportName = "@basedesign@/com/redcarpet/payroll/erpCommon/ad_reports/PayrollProcessReport.jrxml";
			attachmentName = attachmentName.append("_").append(strempType).append("_").append(pf).append("_").append(bankType).append("_").append(branch).append("_").append("PayrollProcessReport");
		}
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    //System.out.println("processid.."+strPayrollId);
   // System.out.println("employeeid.."+stremployeeId);
   // System.out.println("dept id.."+strdeptId);
		if (empTypeTemp.equals(""))
    		log4j.info("empTypeTemp "+empTypeTemp);
    parameters.put("ProcessID", strPayrollId);
	parameters.put("Employee_ID", stremployeeId);
	parameters.put("Dept_ID", strdeptId);
	parameters.put("EmpType", empTypeTemp);
	parameters.put("SubDept_ID", strsubdeptId);
	parameters.put("Emp_PF", strPf);
	parameters.put("Emp_Bank", strBank);
	parameters.put("Dyieng", strBranch);
	parameters.put("type", type);
	parameters.put("documentno", documentNos);
    renderJR(vars, response, strReportName,attachmentName.toString(), strOutput, parameters, null, null);


	}
	public String getDocumentnos(String strPunchNos){
		StringBuffer documentNos = new StringBuffer("");
		StringTokenizer st = new StringTokenizer(strPunchNos, ",", false);

		while (st.hasMoreTokens())
		{

			documentNos.append("'").append(st.nextToken()).append("',");
			//System.out.println("Appending is "+sb.toString());

		}
		System.out.println("Length is "+documentNos.length());

		if(documentNos.length()>0){
			documentNos.deleteCharAt(documentNos.length()-1);
		}


		return documentNos.toString();
	}
}