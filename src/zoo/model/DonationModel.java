package zoo.model;


import java.sql.Date;

public class DonationModel {
   private String Donation_ID;
   private Date date;
    private double Amount;
    private String Status;

    public DonationModel(String donation_ID, Date date, double amount, String status) {
        Donation_ID = donation_ID;
        this.date = date;
        Amount = amount;
        Status = status;
    }

    public String getDonation_ID() {
        return Donation_ID;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return Amount;
    }

    public String getStatus() {
        return Status;
    }
}
