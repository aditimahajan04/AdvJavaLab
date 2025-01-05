//Question:11a
// Write a Java program to insert and display the employee database information whose salary is above Rs. 50,000 in a table format.
// (Consider Employee Database with Employee name, designation, Dept, and salary)

import java.sql.*;
import java.util.Scanner;

public class EmployeeDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/EmployeeDB"; // Update this
    private static final String USER = "root"; 
    private static final String PASS = ""; // Update this

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Create the 'employees' table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                    "EmpID INT PRIMARY KEY, " +
                    "Name VARCHAR(100), " +
                    "Designation VARCHAR(50), " +
                    "Department VARCHAR(50), " +
                    "Salary DECIMAL(10, 2))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'employees' created or already exists.");

            // Insert sample employee data
            String insertSQL = "INSERT INTO employees (EmpID, Name, Designation, Department, Salary) VALUES (?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertSQL);

            pStmt.setInt(1, 1); pStmt.setString(2, "John Doe"); pStmt.setString(3, "Manager"); pStmt.setString(4, "HR"); pStmt.setBigDecimal(5, new java.math.BigDecimal(60000)); pStmt.executeUpdate();
            pStmt.setInt(1, 2); pStmt.setString(2, "Jane Smith"); pStmt.setString(3, "Assistant Professor"); pStmt.setString(4, "Engineering"); pStmt.setBigDecimal(5, new java.math.BigDecimal(75000)); pStmt.executeUpdate();
            pStmt.setInt(1, 3); pStmt.setString(2, "Alice Johnson"); pStmt.setString(3, "Software Engineer"); pStmt.setString(4, "IT"); pStmt.setBigDecimal(5, new java.math.BigDecimal(50000)); pStmt.executeUpdate();
            pStmt.setInt(1, 4); pStmt.setString(2, "Bob Brown"); pStmt.setString(3, "Senior Analyst"); pStmt.setString(4, "Finance"); pStmt.setBigDecimal(5, new java.math.BigDecimal(65000)); pStmt.executeUpdate();
            pStmt.setInt(1, 5); pStmt.setString(2, "Carol Wilson"); pStmt.setString(3, "Consultant"); pStmt.setString(4, "Marketing"); pStmt.setBigDecimal(5, new java.math.BigDecimal(70000)); pStmt.executeUpdate();

            System.out.println("Sample data inserted into 'employees' table.");

            // Query to select employees with salary > 50000
            String queryHighSalary = "SELECT * FROM employees WHERE Salary > 50000";
            ResultSet rs = stmt.executeQuery(queryHighSalary);

            // Display the results
            System.out.println("\nEmployees with Salary > Rs. 50,000:");
            System.out.println("EmpID\tName\t\tDesignation\tDepartment\tSalary");
            System.out.println("-------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("EmpID") + "\t" +
                        rs.getString("Name") + "\t\t" +
                        rs.getString("Designation") + "\t" +
                        rs.getString("Department") + "\t" +
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
