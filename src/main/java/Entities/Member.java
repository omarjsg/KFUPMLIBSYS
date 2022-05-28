package Entities;

import java.time.LocalDate;

public class Member extends Person{
    private int barcode, borrowedBooks;
    private boolean status, active;
    public Member(String firstName, String lastName, String username, String email, String phoneNum, int id, String joinDate, String birthDate, int barcode, int borrowedBooks, boolean status, boolean active) {
        super(firstName, lastName, username, email, phoneNum, id, joinDate, birthDate);
        this.barcode = barcode;
        this.borrowedBooks = borrowedBooks;
        this.status = status;
        this.active = active;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(int borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
