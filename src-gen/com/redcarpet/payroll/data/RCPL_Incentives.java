/*
 *************************************************************************
 * The contents of this file are subject to the Openbravo  Public  License
 * Version  1.1  (the  "License"),  being   the  Mozilla   Public  License
 * Version 1.1  with a permitted attribution clause; you may not  use this
 * file except in compliance with the License. You  may  obtain  a copy of
 * the License at http://www.openbravo.com/legal/license.html
 * Software distributed under the License  is  distributed  on  an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific  language  governing  rights  and  limitations
 * under the License.
 * The Original Code is Openbravo ERP.
 * The Initial Developer of the Original Code is Openbravo SLU
 * All portions are Copyright (C) 2008-2011 Openbravo SLU
 * All Rights Reserved.
 * Contributor(s):  ______________________________________.
 ************************************************************************
*/
package com.redcarpet.payroll.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openbravo.base.structure.ActiveEnabled;
import org.openbravo.base.structure.BaseOBObject;
import org.openbravo.base.structure.ClientEnabled;
import org.openbravo.base.structure.OrganizationEnabled;
import org.openbravo.base.structure.Traceable;
import org.openbravo.model.ad.access.User;
import org.openbravo.model.ad.system.Client;
import org.openbravo.model.common.enterprise.Organization;
/**
 * Entity class for entity RCPL_Incentives (stored in table RCPL_Incentives).
 *
 * NOTE: This class should not be instantiated directly. To instantiate this
 * class the {@link org.openbravo.base.provider.OBProvider} should be used.
 */
public class RCPL_Incentives extends BaseOBObject implements Traceable, ClientEnabled, OrganizationEnabled, ActiveEnabled {
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "RCPL_Incentives";
    public static final String ENTITY_NAME = "RCPL_Incentives";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_CLIENT = "client";
    public static final String PROPERTY_ORGANIZATION = "organization";
    public static final String PROPERTY_ACTIVE = "active";
    public static final String PROPERTY_CREATIONDATE = "creationDate";
    public static final String PROPERTY_CREATEDBY = "createdBy";
    public static final String PROPERTY_UPDATED = "updated";
    public static final String PROPERTY_UPDATEDBY = "updatedBy";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_DESCRIPTION = "description";
    public static final String PROPERTY_ISDEDUCTIONS = "isDeductions";
    public static final String PROPERTY_PROCESS = "process";
    public static final String PROPERTY_PROCESSED = "processed";
    public static final String PROPERTY_VARIABLESET = "variableSet";
    public static final String PROPERTY_RCPLEMPDEDINCENTIVESLIST = "rCPLEmpDedIncentivesList";
    public static final String PROPERTY_RCPLEMPPAYINCENTIVESLIST = "rCPLEmpPayIncentivesList";
    public static final String PROPERTY_RCPLINCENTIVELINESLIST = "rCPLIncentiveLinesList";
    public static final String PROPERTY_RCPLPAYROLLINCENTIVELINESLIST = "rCPLPayrollIncentiveLinesList";

    public RCPL_Incentives() {
        setDefaultValue(PROPERTY_ACTIVE, true);
        setDefaultValue(PROPERTY_ISDEDUCTIONS, false);
        setDefaultValue(PROPERTY_PROCESS, false);
        setDefaultValue(PROPERTY_PROCESSED, false);
        setDefaultValue(PROPERTY_RCPLEMPDEDINCENTIVESLIST, new ArrayList<Object>());
        setDefaultValue(PROPERTY_RCPLEMPPAYINCENTIVESLIST, new ArrayList<Object>());
        setDefaultValue(PROPERTY_RCPLINCENTIVELINESLIST, new ArrayList<Object>());
        setDefaultValue(PROPERTY_RCPLPAYROLLINCENTIVELINESLIST, new ArrayList<Object>());
    }

    @Override
    public String getEntityName() {
        return ENTITY_NAME;
    }

    public String getId() {
        return (String) get(PROPERTY_ID);
    }

    public void setId(String id) {
        set(PROPERTY_ID, id);
    }

    public Client getClient() {
        return (Client) get(PROPERTY_CLIENT);
    }

    public void setClient(Client client) {
        set(PROPERTY_CLIENT, client);
    }

    public Organization getOrganization() {
        return (Organization) get(PROPERTY_ORGANIZATION);
    }

    public void setOrganization(Organization organization) {
        set(PROPERTY_ORGANIZATION, organization);
    }

    public Boolean isActive() {
        return (Boolean) get(PROPERTY_ACTIVE);
    }

    public void setActive(Boolean active) {
        set(PROPERTY_ACTIVE, active);
    }

    public Date getCreationDate() {
        return (Date) get(PROPERTY_CREATIONDATE);
    }

    public void setCreationDate(Date creationDate) {
        set(PROPERTY_CREATIONDATE, creationDate);
    }

    public User getCreatedBy() {
        return (User) get(PROPERTY_CREATEDBY);
    }

    public void setCreatedBy(User createdBy) {
        set(PROPERTY_CREATEDBY, createdBy);
    }

    public Date getUpdated() {
        return (Date) get(PROPERTY_UPDATED);
    }

    public void setUpdated(Date updated) {
        set(PROPERTY_UPDATED, updated);
    }

    public User getUpdatedBy() {
        return (User) get(PROPERTY_UPDATEDBY);
    }

    public void setUpdatedBy(User updatedBy) {
        set(PROPERTY_UPDATEDBY, updatedBy);
    }

    public String getName() {
        return (String) get(PROPERTY_NAME);
    }

    public void setName(String name) {
        set(PROPERTY_NAME, name);
    }

    public String getDescription() {
        return (String) get(PROPERTY_DESCRIPTION);
    }

    public void setDescription(String description) {
        set(PROPERTY_DESCRIPTION, description);
    }

    public Boolean isDeductions() {
        return (Boolean) get(PROPERTY_ISDEDUCTIONS);
    }

    public void setDeductions(Boolean isDeductions) {
        set(PROPERTY_ISDEDUCTIONS, isDeductions);
    }

    public Boolean isProcess() {
        return (Boolean) get(PROPERTY_PROCESS);
    }

    public void setProcess(Boolean process) {
        set(PROPERTY_PROCESS, process);
    }

    public Boolean isProcessed() {
        return (Boolean) get(PROPERTY_PROCESSED);
    }

    public void setProcessed(Boolean processed) {
        set(PROPERTY_PROCESSED, processed);
    }

    public RCPL_VariableSet getVariableSet() {
        return (RCPL_VariableSet) get(PROPERTY_VARIABLESET);
    }

    public void setVariableSet(RCPL_VariableSet variableSet) {
        set(PROPERTY_VARIABLESET, variableSet);
    }

    @SuppressWarnings("unchecked")
    public List<RCPL_EmpDedIncentives> getRCPLEmpDedIncentivesList() {
        return (List<RCPL_EmpDedIncentives>) get(PROPERTY_RCPLEMPDEDINCENTIVESLIST);
    }

    public void setRCPLEmpDedIncentivesList(List<RCPL_EmpDedIncentives> rCPLEmpDedIncentivesList) {
        set(PROPERTY_RCPLEMPDEDINCENTIVESLIST, rCPLEmpDedIncentivesList);
    }

    @SuppressWarnings("unchecked")
    public List<RCPL_EmpPayIncentives> getRCPLEmpPayIncentivesList() {
        return (List<RCPL_EmpPayIncentives>) get(PROPERTY_RCPLEMPPAYINCENTIVESLIST);
    }

    public void setRCPLEmpPayIncentivesList(List<RCPL_EmpPayIncentives> rCPLEmpPayIncentivesList) {
        set(PROPERTY_RCPLEMPPAYINCENTIVESLIST, rCPLEmpPayIncentivesList);
    }

    @SuppressWarnings("unchecked")
    public List<RCPL_IncentiveLines> getRCPLIncentiveLinesList() {
        return (List<RCPL_IncentiveLines>) get(PROPERTY_RCPLINCENTIVELINESLIST);
    }

    public void setRCPLIncentiveLinesList(List<RCPL_IncentiveLines> rCPLIncentiveLinesList) {
        set(PROPERTY_RCPLINCENTIVELINESLIST, rCPLIncentiveLinesList);
    }

    @SuppressWarnings("unchecked")
    public List<RCPL_PayrollIncentiveLines> getRCPLPayrollIncentiveLinesList() {
        return (List<RCPL_PayrollIncentiveLines>) get(PROPERTY_RCPLPAYROLLINCENTIVELINESLIST);
    }

    public void setRCPLPayrollIncentiveLinesList(List<RCPL_PayrollIncentiveLines> rCPLPayrollIncentiveLinesList) {
        set(PROPERTY_RCPLPAYROLLINCENTIVELINESLIST, rCPLPayrollIncentiveLinesList);
    }

}
