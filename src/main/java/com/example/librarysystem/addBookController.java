package com.example.librarysystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.sql.*;

import java.io.IOException;

public class addBookController {
    Connection cn=null;
    PreparedStatement myStmt = null;
    ResultSet myRs = null;

    @FXML
    public TextField title, ISBN, volume, rDate, pName, categories, language, barcode, authors, rackNum;

    private ApplicationController controller = new ApplicationController();

    public void addBook(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {//Done
        //take data from text fields above then assign it to queries.
        //add member sql query.
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName);
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("INSERT INTO `library`.`book` (`ISBN`, `Title`, `Release_date`, `Volume`, `Publisher_name`, `Languge`) VALUES (?, ?, ?, ?, ?, ?)");
        myStmt.setString(1, ISBN.getText());
        myStmt.setString(2,title.getText());
        myStmt.setString(3, rDate.getText());
        myStmt.setString(4, volume.getText());
        myStmt.setString(5, pName.getText());
        myStmt.setString(6, language.getText());
        myStmt.execute();
        ResultSet myRes = myStmt.executeQuery("select * from book");

        myStmt = myConn.prepareStatement("INSERT INTO `library`.`physicalbook` (`ISBN`, `Barcode`, `Format`, `Rack_number`, `Copy_number`) VALUES (?, ?, ?, ?, ?)");
        myStmt.setString(1, ISBN.getText());
        myStmt.setString(2,barcode.getText());
        myStmt.setString(3, volume.getText());
        myStmt.setString(4, rackNum.getText());
        myStmt.setString(5, rackNum.getText());
        myStmt.execute();
        myRes = myStmt.executeQuery("select * from book");

        myStmt = myConn.prepareStatement("INSERT INTO `library`.`wrote` (`AuthorID`, `ISBN`) VALUES (?, ?);");
        myStmt.setString(1, authors.getText());
        myStmt.setString(2,ISBN.getText());
        myStmt.execute();
         myRes = myStmt.executeQuery("select * from book");
        //Insert cat
        myStmt = myConn.prepareStatement("INSERT INTO `library`.`under` (`Catname`, `ISBN`) VALUES (?, ?)");
        myStmt.setString(1, categories.getText());
        myStmt.setString(2,ISBN.getText());
        myStmt.execute();
        myRes = myStmt.executeQuery("select * from book");


        controller.aBCScreen(event);
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
