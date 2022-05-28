package com.example.librarysystem;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Yaarob201852160.");
        PreparedStatement myStmt = myConn.prepareStatement("Select count( book.ISBN) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN");
        ResultSet rs = myStmt.executeQuery("Select count( book.ISBN) FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN");
        rs.next();
        int j = Integer.parseInt(rs.getString("count( book.ISBN)"));
        System.out.println(j);
        rs = myStmt.executeQuery("SELECT book.ISBN, book.Volume, book.Title, book.Publisher_name, book.Languge, book.Release_date, physicalbook.Barcode, physicalbook.Copy_number, physicalbook.Rack_number, physicalbook.Format, wrote.AuthorID, author.Fname, author.LName FROM book, physicalbook, wrote, author where physicalbook.ISBN=book.ISBN and wrote.AuthorID=author.ID and wrote.ISBN=book.ISBN;");
        rs.next();
        System.out.println(rs.getString("ISBN"));
        System.out.println(rs.getString("Volume"));
        System.out.println(Integer.parseInt(rs.getString("Barcode")));
    }
}
