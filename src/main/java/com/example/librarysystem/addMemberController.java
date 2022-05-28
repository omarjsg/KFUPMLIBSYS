package com.example.librarysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class addMemberController {

    @FXML
    public TextField fNameField, lNameField, phNumberField, emailField, userName, pass1Field, pass2Field, barcodeField, birthDate;

    private ApplicationController controller = new ApplicationController();

    public void addMember(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {//Done
        if (!pass1Field.getText().equals(pass2Field.getText()) || pass1Field.getText().length() == 0 || pass2Field.getText().length() == 0) {
            //not match
        } else {
            //take data from text fields above then assign it to queries.
            // String driverName = "com.mysql.jdbc.Driver";
            // Class.forName(driverName);
            String id;
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
            PreparedStatement myStmt = myConn.prepareStatement("SELECT max(person.ID) from person");
            ResultSet rs = myStmt.executeQuery("SELECT max(person.ID) from person");
            rs.next();
            id = rs.getString("max(person.ID)");
            int ID = Integer.parseInt(id)+1 ;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            //insert to person
            myStmt = myConn.prepareStatement("INSERT INTO `library`.`person` (`ID`, `Fname`, `LName`, `Phone_number`, `BDate`, `Email`, `JDate`) VALUES (?, ?, ?, ?, ?, ?, ?)");
            myStmt.setString(1, ID+"");
            myStmt.setString(2, fNameField.getText());
            myStmt.setString(3, lNameField.getText());
            myStmt.setString(4, phNumberField.getText());
            myStmt.setString(5, birthDate.getText());
            myStmt.setString(6, emailField.getText());
            myStmt.setString(7, dtf.format(now));
            myStmt.execute();
            //insrt to member
            myStmt = myConn.prepareStatement("INSERT INTO `library`.`lmember` (`MID`, `Barcode`) VALUES (? , ?)");
            myStmt.setString(1, ID+"");
            myStmt.setString(2, barcodeField.getText());
            myStmt.execute();
            //insert to authenticator
            myStmt = myConn.prepareStatement("INSERT INTO `library`.`authenticator` (`Email`, `Epassword`, `Type`) VALUES (?, ?, ?);");
            myStmt.setString(1, emailField.getText());
            myStmt.setString(2, pass1Field.getText());
            myStmt.setString(3, "M");
            myStmt.execute();
            //rs = stmt.executeQuery("SELECT max(person.ID) from person");
            //add member sql query.
            controller.aMCScreen(event);
        }
    }

    public void switchToMLScreen(ActionEvent event) throws IOException, SQLException {
        controller.switchToMLScreen(event);
    }

    public void switchToReportsScreen(ActionEvent event) throws IOException, SQLException {
        controller.switchToReportsScreen1(event);
    }

    public void switchToLibrarianHomeScreen(ActionEvent event) throws IOException, SQLException {
        controller.switchToLibrarianHomeScreen(event);
    }

    public void switchToBLScreen(ActionEvent event) throws IOException, SQLException {
        controller.switchToBLScreen(event);
    }

    public void logOut(ActionEvent event) throws IOException {
        controller.logOut(event);
    }

}
