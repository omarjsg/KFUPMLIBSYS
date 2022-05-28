package com.example.librarysystem;
import Entities.Borrowes;
import Entities.Member;
import Entities.PhysicalBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class brrBookController {

    @FXML
    public TextField rDate;

    private ApplicationController controller = new ApplicationController();

    private PhysicalBook book;

    public void borrow(ActionEvent event) throws IOException, SQLException {
        if(rDate.getText().length() != 0) {
            String returnDate = rDate.getText();
            //take data from text fields above then assign it to queries.
            //update book sql query.
            //add borrow sql query.
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String sdate=dtf.format(now);
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
            PreparedStatement myStmt = myConn.prepareStatement("INSERT INTO `library`.`borrowes` (`ISBN`, `MID`, `SDate`, `RDate`) VALUES (?, ?, ?, ?)");
            myStmt.setString(1,book.getISBN()+"");
            myStmt.setString(2, controller.getCurrid());
            myStmt.setString(3,sdate);
            myStmt.setString(4,returnDate);
            myStmt.execute();
            controller.brrBCScreen(event);
        }
    }

    public void switchToBookListScreen(ActionEvent event) throws IOException, SQLException {
        controller.switchToBookListScreen(event);
    }

    public void switchToReserveScreen(ActionEvent event) throws IOException {
        controller.switchToReserveScreen(event);
    }

    public void switchToMemberHomeScreen(ActionEvent event) throws IOException, SQLException {
        controller.switchToMemberHomeScreen(event);
    }

    public void logOut(ActionEvent event) throws IOException {
        controller.logOut(event);
    }
    public void initialize() {
        book = ApplicationController.getBookData();
    }
}
