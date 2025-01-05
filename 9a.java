//a.	Create a	database called ‘Registration’ and	a table called ‘student’ with fields Roll-No., Name, Program and Year-of-Admission. Write a java program to
//(i)	List the name and roll number of all the students who are enrolled in year 2023.
//(ii)	List the Roll-No and Name of all the students of BE Program.


import java.sql.*;

public class RegistrationDB {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Registration"; // Update as needed
    private static final String USER = "root";
    private static final String PASS = ""; // Update as needed

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Create the 'student' table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS student (" +
                    "RollNo INT PRIMARY KEY, " +
                    "Name VARCHAR(100), " +
                    "Program VARCHAR(50), " +
                    "YearOfAdmission INT)";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'student' created or already exists.");

            // Insert sample data into the 'student' table
            String insertDataSQL = "INSERT INTO student (RollNo, Name, Program, YearOfAdmission) VALUES " +
                    "(101, 'Alice', 'BE', 2023), " +
                    "(102, 'Bob', 'MBA', 2022), " +
                    "(103, 'Charlie', 'BE', 2023), " +
                    "(104, 'Diana', 'BSc', 2021), " +
                    "(105, 'Eve', 'BE', 2022)";
            stmt.executeUpdate(insertDataSQL);
            System.out.println("Sample data inserted into 'student' table.");

            // (i) List the name and roll number of all students who are enrolled in the year 2023
            String query2023Students = "SELECT RollNo, Name FROM student WHERE YearOfAdmission = 2023";
            ResultSet rs2023 = stmt.executeQuery(query2023Students);
            System.out.println("\nStudents enrolled in the year 2023:");
            System.out.println("RollNo\tName");
            System.out.println("------------------");
            while (rs2023.next()) {
                System.out.println(rs2023.getInt("RollNo") + "\t" + rs2023.getString("Name"));
            }

            // (ii) List the Roll-No and Name of all the students of BE Program
            String queryBEProgram = "SELECT RollNo, Name FROM student WHERE Program = 'BE'";
            ResultSet rsBE = stmt.executeQuery(queryBEProgram);
            System.out.println("\nStudents in the BE Program:");
            System.out.println("RollNo\tName");
            System.out.println("------------------");
            while (rsBE.next()) {
                System.out.println(rsBE.getInt("RollNo") + "\t" + rsBE.getString("Name"));
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
