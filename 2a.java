package AdvJava.week3;

import java.sql.*;

public class P3MoviesDB {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbName", USER = "root", PASS = ""; // Update this

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pStmt = null;

        try {
            // Establish the connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Create the Movies table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Movies (" +
                    "ID INT PRIMARY KEY, " +
                    "Movie_Name VARCHAR(100), " +
                    "Genre VARCHAR(50), " +
                    "IMDB_Rating FLOAT, " +
                    "Year INT)";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'Movies' created or already exists.");

            // Insert sample data
            String insertDataSQL = "INSERT INTO Movies (ID, Movie_Name, Genre, IMDB_Rating, Year) VALUES " +
                    "(1, 'Inception', 'Sci-Fi', 8.8, 2010), " +
                    "(2, 'Orphan', 'Mystery', 7.0, 2009), " +
                    "(3, 'Interstellar', 'Sci-Fi', 8.6, 2014), " +
                    "(4, 'La La Land', 'Musical', 8.0, 2016), " +
                    "(5, 'Gifted', 'Drama', 7.6, 2017)";
            stmt.executeUpdate(insertDataSQL);
            System.out.println("Sample data inserted into 'Movies' table.");

            // i. Display details of all the Movies from the table
            String queryAllMovies = "SELECT * FROM Movies";
            ResultSet rs = stmt.executeQuery(queryAllMovies);
            printResultSet(rs);

            // ii. Display details of 5th Movie from the table
            rs.beforeFirst();
            if (rs.absolute(5)) {
                System.out.println("\nDetails of 5th Movie:");
                System.out.println("ID\tMovie_Name\tGenre\tIMDB_Rating\tYear");
                System.out.println(rs.getInt("ID") + "\t" +
                        rs.getString("Movie_Name") + "\t" +
                        rs.getString("Genre") + "\t\t" +
                        rs.getFloat("IMDB_Rating") + "\t" +
                        rs.getInt("Year"));
            }

            // iii. Insert a new row into the table using PreparedStatement and display all the details
            String insertSQL = "INSERT INTO Movies (ID, Movie_Name, Genre, IMDB_Rating, Year) VALUES (?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(insertSQL);
            pStmt.setInt(1, 6);
            pStmt.setString(2, "Morning Glory");
            pStmt.setString(3, "Rom-Com");
            pStmt.setFloat(4, 6.5f);
            pStmt.setInt(5, 2010);
            pStmt.executeUpdate();
            System.out.println("\nNew movie inserted successfully.");

            // Display all the details
            rs = stmt.executeQuery(queryAllMovies);
            printResultSet(rs);

            // iv. Delete a row from the table where the IMDB_Rating is less than 5
            String deleteLowRatingSQL = "DELETE FROM Movies WHERE IMDB_Rating < 5";
            int rowsDeleted = stmt.executeUpdate(deleteLowRatingSQL);
            System.out.println("\nRows deleted with IMDB_Rating < 5: " + rowsDeleted);

            // Display all the details after deletion
            rs = stmt.executeQuery(queryAllMovies);
            printResultSet(rs);

            // v. Update the Genre of a movie with ID as 10 to “Sci-Fi”
            String updateGenreSQL = "UPDATE Movies SET Genre = 'Sci-Fi' WHERE ID = 10";
            int rowsUpdated = stmt.executeUpdate(updateGenreSQL);
            if (rowsUpdated > 0) {
                System.out.println("\nGenre updated to 'Sci-Fi' for movie with ID 10.");
            } else {
                System.out.println("\nNo movie found with ID 10 to update.");
            }

            // Display all the details after the update
            rs = stmt.executeQuery(queryAllMovies);
            printResultSet(rs);

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

    private static void printResultSet(ResultSet rs) throws SQLException {
        System.out.println("\nAll movies:\nID\tMovie_Name\tGenre\tIMDB_Rating\tYear");
        while (rs.next()) {
            System.out.println(rs.getInt("ID") + "\t" +
                    rs.getString("Movie_Name") + "\t" +
                    rs.getString("Genre") + "\t\t" +
                    rs.getFloat("IMDB_Rating") + "\t" +
                    rs.getInt("Year"));
        }
    }
}
