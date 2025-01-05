/*
6a. Write a java program to create Employee details and update the designation of     
    an employee from Assistant Professor to Associate Professor. (Consider Employee Database with Employee name, designation, dept and salary)
 */

import java.sql.*;

public class EmployeeDetailsDB {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbName"; // Update with your database name
    private static final String USER = "root";
    private static final String PASS = ""; // Update with your password

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Create the EmployeeDetails table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS EmployeeDetails (" +
                    "Emp_Name VARCHAR(100), " +
                    "Designation VARCHAR(50), " +
                    "Department VARCHAR(50), " +
                    "Salary DECIMAL(10, 2))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'EmployeeDetails' created or already exists.");

            // Insert sample data
            String insertDataSQL = "INSERT INTO EmployeeDetails (Emp_Name, Designation, Department, Salary) VALUES " +
                    "('Alice', 'Assistant Professor', 'Computer Science', 60000.00), " +
                    "('Bob', 'Assistant Professor', 'Mathematics', 58000.00), " +
                    "('Charlie', 'Associate Professor', 'Physics', 75000.00), " +
                    "('Diana', 'Professor', 'Chemistry', 90000.00), " +
                    "('Eve', 'Assistant Professor', 'Computer Science', 61000.00)";
            stmt.executeUpdate(insertDataSQL);
            System.out.println("Sample data inserted into 'EmployeeDetails' table.");

            //i) Update the designation of "Assistant Professor" to "Associate Professor"
            String updateSQL = "UPDATE EmployeeDetails SET Designation = 'Associate Professor' WHERE Designation = 'Assistant Professor'";
            int rowsUpdated = stmt.executeUpdate(updateSQL);
            System.out.println("\nDesignation updated for " + rowsUpdated + " employees.");

            // Display all the employee details
            String queryAllEmployees = "SELECT * FROM EmployeeDetails";
            ResultSet rs = stmt.executeQuery(queryAllEmployees);

            System.out.println("\nUpdated Employee Details:");
            System.out.println("Emp_Name\t\tDesignation\t\tDepartment\t\tSalary");
            System.out.println("-------------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(
                        rs.getString("Emp_Name") + "\t\t" +
                        rs.getString("Designation") + "\t\t" +
                        rs.getString("Department") + "\t\t" +
                        rs.getDouble("Salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
