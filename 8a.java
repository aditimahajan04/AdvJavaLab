/*
8a. Write a java program to insert and display the student information from the database Update the grade from 'A' to 'S' where 
student name = "Ram" using prepared statement. (Consider Student Database with student name, usn, sem, course and grade) 
*/

import java.sql.*;

public class StudentDatabase {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/College"; // Update with your database name
    private static final String USER = "root";
    private static final String PASS = ""; // Update with your password

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pStmt = null;

        try {
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Create the Student table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Student (" +
                    "Name VARCHAR(100), " +
                    "USN VARCHAR(10), " +
                    "Semester INT, " +
                    "Course VARCHAR(100), " +
                    "Grade CHAR(1))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'Student' created or already exists.");

            // Insert sample data
            String insertDataSQL = "INSERT INTO Student (Name, USN, Semester, Course, Grade) VALUES " +
                    "('Ram', '1RV20CS001', 6, 'Computer Science', 'A'), " +
                    "('Sita', '1RV20CS002', 6, 'Electronics', 'B'), " +
                    "('Lakshman', '1RV20CS003', 6, 'Mechanical', 'A'), " +
                    "('John', '1RV20CS004', 5, 'Information Science', 'B'), " +
                    "('Krishna', '1RV20CS005', 4, 'Mathematics', 'A')";
            stmt.executeUpdate(insertDataSQL);
            System.out.println("Sample data inserted into 'Student' table.");

            // Update the grade from 'A' to 'S' where student name is "Ram"
            String updateSQL = "UPDATE Student SET Grade = ? WHERE Name = ?";
            pStmt = conn.prepareStatement(updateSQL);
            pStmt.setString(1, "S");
            pStmt.setString(2, "Ram");
            int rowsUpdated = pStmt.executeUpdate();
            System.out.println("\nGrade updated for " + rowsUpdated + " student(s).");

            // Display all the student details
            String queryAllStudents = "SELECT * FROM Student";
            ResultSet rs = stmt.executeQuery(queryAllStudents);

            System.out.println("\nStudent Details:");
            System.out.println("Name\t\tUSN\t\tSemester\tCourse\t\t\tGrade");
            System.out.println("--------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(
                        rs.getString("Name") + "\t\t" +
                        rs.getString("USN") + "\t" +
                        rs.getInt("Semester") + "\t\t" +
                        rs.getString("Course") + "\t\t" +
                        rs.getString("Grade"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null)
                    stmt.close();
                if (pStmt != null)
                    pStmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
