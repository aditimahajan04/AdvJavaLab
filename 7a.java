/*
7.	a. Write a java program that connects to the database College with Student table. Assume appropriate attributes for the
    Student table. Write a program to display the details of those Students who have CGPA less than 9. Also update the Student
    table to change the CGPA of student named “John” from 8.50 to 9.4 using updatable result set. Finally display the results and 
    disconnect from the database.
*/


package AdvJava.week2;

import java.sql.*;

public class P1StudentDB {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/College";
    private static final String USER = "root";
    private static final String PASS = ""; // Update this

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Create the students table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                    "USN VARCHAR(20) PRIMARY KEY, " +
                    "Name VARCHAR(100), " +
                    "Semester INT, " +
                    "CGPA FLOAT)";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'students' created or already exists.");

            // Insert sample data
            String insertDataSQL = "INSERT IGNORE INTO students (USN, Name, Semester, CGPA) VALUES " +
                    "('1MS22CS301', 'Anika Sharma', 5, 8.5), " +
                    "('1MS22CS302', 'Rahul Patel', 5, 7.5), " +
                    "('1MS23CS303', 'Priya Gupta', 3, 9.0), " +
                    "('1MS22CS304', 'Vivek Kumar', 5, 8.0), " +
                    "('1MS22CS305', 'Neha Singh', 3, 8.3), " +
                    "('1MS22CS306', 'John', 5, 8.5), " +
                    "('1MS21CS307', 'Meera Nair', 7, 7.9), " +
                    "('1MS22CS308', 'Rohan Raj', 5, 8.1)";
            stmt.executeUpdate(insertDataSQL);
            System.out.println("Sample data inserted into 'students' table.");

            // i. Display details of students with CGPA < 9.0
            String queryLowCGPA = "SELECT * FROM students WHERE CGPA < 9.0";
            ResultSet rsLowCGPA = stmt.executeQuery(queryLowCGPA);
            System.out.println("\nStudents with CGPA < 9.0:");
            System.out.println("USN\t\tName\t\tSemester\tCGPA");
            while (rsLowCGPA.next()) {
                System.out.println(rsLowCGPA.getString("USN") + "\t" +
                        rsLowCGPA.getString("Name") + "\t" +
                        rsLowCGPA.getInt("Semester") + "\t\t" +
                        rsLowCGPA.getFloat("CGPA"));
            }

            // ii. Update CGPA for student named "John" to 9.4 using updatable ResultSet
            String queryUpdateJohn = "SELECT * FROM students WHERE Name = 'John'";
            ResultSet rsUpdate = stmt.executeQuery(queryUpdateJohn);
            if (rsUpdate.next()) {
                rsUpdate.updateFloat("CGPA", 9.4f);
                rsUpdate.updateRow();
                System.out.println("\nUpdated CGPA for 'John' to 9.4.");
            }

            // iii. Display all students after the update
            String queryAllStudents = "SELECT * FROM students";
            ResultSet rsAll = stmt.executeQuery(queryAllStudents);
            System.out.println("\nAll Students (After Update):");
            System.out.println("USN\t\tName\t\tSemester\tCGPA");
            while (rsAll.next()) {
                System.out.println(rsAll.getString("USN") + "\t" +
                        rsAll.getString("Name") + "\t" +
                        rsAll.getInt("Semester") + "\t\t" +
                        rsAll.getFloat("CGPA"));
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
