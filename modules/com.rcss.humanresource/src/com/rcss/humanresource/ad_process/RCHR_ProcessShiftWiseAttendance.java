package com.rcss.humanresource.ad_process;

import com.rcss.humanresource.data.RchrAttendLine;
import com.rcss.humanresource.data.RchrAttendance;
import com.rcss.humanresource.data.RchrEmployee;
import com.rcss.humanresource.data.RchrRelay;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.scheduling.ProcessBundle;
import org.openbravo.service.db.DalBaseProcess;

/**
 *
 * @author S.A. Mateen
 */
public class RCHR_ProcessShiftWiseAttendance extends DalBaseProcess {

    @Override
    protected void doExecute(ProcessBundle bundle) throws Exception {
        OBError msg = new OBError();
        msg.setType("Success");
        msg.setTitle("Success");
        msg.setMessage("Process completed successfully");

        String id = (String) bundle.getParams().get("Rchr_Attendance_ID");
        RchrAttendance attendance = OBDal.getInstance().get(RchrAttendance.class, id);
        List<RchrEmployee> empList = OBDal.getInstance().createCriteria(RchrEmployee.class).list();
        for (RchrEmployee e : empList) {
            RchrAttendLine attdline = OBProvider.getInstance().get(RchrAttendLine.class);
            attdline.setRchrAttendance(attendance);
            attdline.setRchrEmployee(e);
            attdline.setWeeklyOff(getWeekOff(e.getWeeklyOff(), attendance.getFromdate()));
            try {
                RchrRelay shiftRotation = OBDal.getInstance().createQuery(RchrRelay.class, " as sr where sr.rchrMrelay.id='" + e.getRelay().getId() + "' ").list().get(0);
                System.out.println(shiftRotation + " shiftRotation");
                if (shiftRotation != null) {
                    attdline.setShift(shiftRotation.getShift());
                    attdline.setPunchin(shiftRotation.getShift().getFromTime());
                    attdline.setPunchout(shiftRotation.getShift().getToTime());
                    attdline.setDuration(attendance.getDuration());
                    attdline.setPresent(Boolean.TRUE);
                } else if (e.isWeekOffApplicable()) {
                    if (!StringUtils.isEmpty(e.getWeeklyOff())) {
                        attdline.setPresent(Boolean.TRUE);
                        attdline.setWeeklyOff(Boolean.TRUE);
                    }

                }
                OBDal.getInstance().save(attdline);
                attendance.setProcess(Boolean.TRUE);
                attendance.setProcessShiftWiseAttendance(Boolean.TRUE);
            }catch(IndexOutOfBoundsException e1){
            }catch (Exception ex) {
                System.out.println("Error: Shift not defined in Shift Rotation");
                msg.setType("Error");
                msg.setTitle("Error");
                msg.setMessage(ex.getMessage());
                ex.printStackTrace();
            }

            bundle.setResult(msg);
        }

    }

    private int getDay(String day) {
        int weekOff = -1;
        if (StringUtils.equalsIgnoreCase(day, "SUNDAY")) {
            weekOff = 1;
        } else if (StringUtils.equalsIgnoreCase(day, "MONDAY")) {
            weekOff = 2;
        } else if (StringUtils.equalsIgnoreCase(day, "TUESDAY")) {
            weekOff = 3;
        } else if (StringUtils.equalsIgnoreCase(day, "WEDNESDAY")) {
            weekOff = 4;
        } else if (StringUtils.equalsIgnoreCase(day, "THURSDAY")) {
            weekOff = 5;
        } else if (StringUtils.equalsIgnoreCase(day, "FRIDAY")) {
            weekOff = 6;
        } else if (StringUtils.equalsIgnoreCase(day, "SATURDAY")) {
            weekOff = 7;
        }
        return weekOff;
    }

    private Boolean getWeekOff(String weekOffDay, Date fromdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromdate);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        int day = getDay(weekOffDay);
        if (day == dayofweek) {
            return true;
        } else {
            return false;
        }
    }
}
