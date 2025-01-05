/*
4.	a.   Write a java Program to create a database to store country details namely, country_code, capital, continent, population and
    display at least 5 country details in ascending order of their population in a table format.	 
*/


import java.sql.*;

public class CountryDetailsDB {

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

            // Create the CountryDetails table if it does not exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS CountryDetails (" +
                    "Country_Code VARCHAR(5) PRIMARY KEY, " +
                    "Capital VARCHAR(100), " +
                    "Continent VARCHAR(50), " +
                    "Population BIGINT)";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'CountryDetails' created or already exists.");

            // Insert sample data
            String insertDataSQL = "INSERT INTO CountryDetails (Country_Code, Capital, Continent, Population) VALUES " +
                    "('US', 'Washington, D.C.', 'North America', 331000000), " +
                    "('IN', 'New Delhi', 'Asia', 1400000000), " +
                    "('JP', 'Tokyo', 'Asia', 126000000), " +
                    "('BR', 'Brasília', 'South America', 213000000), " +
                    "('AU', 'Canberra', 'Oceania', 26000000)";
            stmt.executeUpdate(insertDataSQL);
            System.out.println("Sample data inserted into 'CountryDetails' table.");

            // Query to fetch country details in ascending order of population
            String queryCountryDetails = "SELECT * FROM CountryDetails ORDER BY Population ASC";
            ResultSet rs = stmt.executeQuery(queryCountryDetails);

            // Display the result in table format
            System.out.println("\nCountry Details in Ascending Order of Population:");
            System.out.printf("/t15s /t20s /t20s /t15s%n", "Country_Code", "Capital", "Continent", "Population");
            System.out.println("-------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("/t15s /t20s /t20s /t15d%n",
                        rs.getString("Country_Code"),
                        rs.getString("Capital"),
                        rs.getString("Continent"),
                        rs.getLong("Population"));
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
