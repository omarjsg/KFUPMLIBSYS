package Entities;

import java.time.LocalDate;

public class Borrowes {
    private Book book;
    private Member memberAccount;
    private String startDate, returnDate;
    private double penaltyAmount;

    public Borrowes(Book book, Member memberAccount, String startDate, String returnDate, double penaltyAmount) {
        this.book = book;
        this.memberAccount = memberAccount;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.penaltyAmount = penaltyAmount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(Member memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }
}
