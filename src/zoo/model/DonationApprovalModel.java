package zoo.model;


import java.sql.Date;

public class DonationApprovalModel {
    private String Donation_ID ;
    private String Employee_ID;
    private String Visitor_ID;
    private Date Approval_Date;

    public DonationApprovalModel(String donation_ID, String employee_ID, String visitor_ID, Date approval_Date) {
        Donation_ID = donation_ID;
        Employee_ID = employee_ID;
        Visitor_ID = visitor_ID;
        Approval_Date = approval_Date;
    }

    public String getDonation_ID() {
        return Donation_ID;
    }

    public String getEmployee_ID() {
        return Employee_ID;
    }

    public String getVisitor_ID() {
        return Visitor_ID;
    }

    public Date getApproval_Date() {
        return Approval_Date;
    }
}
