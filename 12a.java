// Question:12a
// a. Develop a Java program using JDBC to execute the following operations on an Employee table:
// i. Delete records of employees working on a project specified by the user.
// ii. Retrieve and display details of all employees sorted by their salary in descending order.

import java.sql.*;
import java.util.Scanner;

public class EmployeeOperations {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/EmployeeDB"; // Update this
    private static final String USER = "root"; 
    private static final String PASS = ""; // Update this

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pStmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Create the 'employees' table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                    "EmpID INT PRIMARY KEY, " +
                    "Name VARCHAR(100), " +
                    "Project VARCHAR(100), " +
                    "Salary DECIMAL(10, 2))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'employees' created or already exists.");

            // Insert sample data
            String insertSQL = "INSERT INTO employees (EmpID, Name, Project, Salary) VALUES (?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertSQL);
            pStmt.setInt(1, 1); pStmt.setString(2, "John Doe"); pStmt.setString(3, "ProjectA"); pStmt.setBigDecimal(4, new java.math.BigDecimal(60000)); pStmt.executeUpdate();
            pStmt.setInt(1, 2); pStmt.setString(2, "Jane Smith"); pStmt.setString(3, "ProjectB"); pStmt.setBigDecimal(4, new java.math.BigDecimal(75000)); pStmt.executeUpdate();
            pStmt.setInt(1, 3); pStmt.setString(2, "Alice Johnson"); pStmt.setString(3, "ProjectC"); pStmt.setBigDecimal(4, new java.math.BigDecimal(50000)); pStmt.executeUpdate();
            pStmt.setInt(1, 4); pStmt.setString(2, "Bob Brown"); pStmt.setString(3, "ProjectA"); pStmt.setBigDecimal(4, new java.math.BigDecimal(65000)); pStmt.executeUpdate();
            pStmt.setInt(1, 5); pStmt.setString(2, "Carol Wilson"); pStmt.setString(3, "ProjectB"); pStmt.setBigDecimal(4, new java.math.BigDecimal(70000)); pStmt.executeUpdate();

            System.out.println("Sample data inserted into 'employees' table.");

            // i. Delete records of employees working on a project specified by the user
            System.out.print("Enter the project name to delete employees: ");
            String projectNameToDelete = scanner.nextLine();
            String deleteSQL = "DELETE FROM employees WHERE Project = ?";
            pStmt = conn.prepareStatement(deleteSQL);
            pStmt.setString(1, projectNameToDelete);
            int rowsDeleted = pStmt.executeUpdate();
            System.out.println(rowsDeleted + " records deleted from project: " + projectNameToDelete);

            // ii. Retrieve and display details of all employees sorted by their salary in descending order
            String querySortedSalary = "SELECT * FROM employees ORDER BY Salary DESC";
            ResultSet rs = stmt.executeQuery(querySortedSalary);

            System.out.println("\nEmployees sorted by salary in descending order:");
            System.out.println("EmpID\tName\t\tProject\tSalary");
            while (rs.next()) {
                System.out.println(rs.getInt("EmpID") + "\t" +
                        rs.getString("Name") + "\t" +
                        rs.getString("Project") + "\t" +
                        rs.getBigDecimal("Salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pStmt != null) pStmt.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }
}
