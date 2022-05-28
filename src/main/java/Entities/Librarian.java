package Entities;

import java.time.LocalDate;

public class Librarian extends Person{
    private double salary, shiftHours;

    public Librarian(String firstName, String lastName, String username, String email, String phoneNum, int id, String joinDate, String birthDate, double salary, double shiftHours) {
        super(firstName, lastName, username, email, phoneNum, id, joinDate, birthDate);
        this.salary = salary;
        this.shiftHours = shiftHours;
    }

}
