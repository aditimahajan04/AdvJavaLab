/*
 * Question:10a
 * Read student name, USN, branch, and admission type (CET or COMED_K) from the user and store it in a database.
 * Write a Java program to extract the students who are admitted through CET and whose branch is CSE, 
 * and display the results in a proper format.
 */

import java.sql.*;
import java.util.Scanner;

public class StudentAdmissionDB {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/College"; // Update as needed
    private static final String USER = "root";
    private static final String PASS = ""; // Update as needed

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Create the 'students' table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                    "USN VARCHAR(10) PRIMARY KEY, " +
                    "Name VARCHAR(100), " +
                    "Branch VARCHAR(50), " +
                    "AdmissionType VARCHAR(20))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'students' created or already exists.");

            // Read student details from the user
            System.out.print("Enter the number of students to add: ");
            int n = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String insertSQL = "INSERT INTO students (USN, Name, Branch, AdmissionType) VALUES (?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertSQL);

            for (int i = 0; i < n; i++) {
                System.out.print("Enter USN: ");
                String usn = scanner.nextLine();

                System.out.print("Enter Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Branch: ");
                String branch = scanner.nextLine();

                System.out.print("Enter Admission Type (CET or COMED_K): ");
                String admissionType = scanner.nextLine();

                pStmt.setString(1, usn);
                pStmt.setString(2, name);
                pStmt.setString(3, branch);
                pStmt.setString(4, admissionType);

                pStmt.executeUpdate();
                System.out.println("Student record inserted successfully.");
            }

            // Query to extract students admitted through CET and whose branch is CSE
            String queryCETCSE = "SELECT USN, Name FROM students WHERE AdmissionType = 'CET' AND Branch = 'CSE'";
            ResultSet rs = stmt.executeQuery(queryCETCSE);

            // Display the results in a proper format
            System.out.println("\nStudents admitted through CET in CSE branch:");
            System.out.println("USN\t\tName");
            System.out.println("------------------------");
            while (rs.next()) {
                System.out.println(rs.getString("USN") + "\t" + rs.getString("Name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pStmt != null)
                    pStmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }
}
