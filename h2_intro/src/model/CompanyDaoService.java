package model;


import model.statements.StatementFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CompanyDaoService implements ICompanyDao {

    private List<Company> companies;

    // JDBC driver name and database URL
    private String JDBC_DRIVER;// = "org.h2.Driver";
    private String DB_URL;// = "jdbc:h2:./carsharing/db/";

    //  Database credentials
    private String USER;// = "sa";
    private String PASS;// = "";

    private Connection conn;
    private Statement stmt;

    public CompanyDaoService(String JDBC_DRIVER, String DB_URL, String USER, String PASS) {
        this.JDBC_DRIVER = JDBC_DRIVER;
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
        this.companies = new ArrayList<>();

        connect();
        companies = receiveData(StatementFactory.readCompanyTable());
    }

    // Data and object mapping
    // ORM (Object-Relational Mapping)
    private void connect() {
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            //conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);

            stmt = conn.createStatement();
            stmt.executeUpdate(StatementFactory.createCompanyTable());
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    // Data and object mapping
    // ORM (Object-Relational Mapping)
    private void disconnect() {
        try {
            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try

    }

    // Data and object mapping
    // ORM (Object-Relational Mapping)
    private List<Company> receiveData(String stmText) {
        //Execute a query
        List<Company> dbTable = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery(stmText);

            // STEP 4: Extract data from result set
            while(rs.next()) {
                // Retrieve by column name
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                dbTable.add(new Company(id, name));
            }
            // Clean-up environment
            rs.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
        return dbTable;
    }

    // Data and object mapping
    // ORM (Object-Relational Mapping)
    private void synchronize(String stmText){
        try {
            //STEP 3: Execute a query
            stmt.executeUpdate(stmText);
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    //Data Access Object Pattern

    @Override
    public List<Company> getAllCompanies() {
        return companies;
    }

    @Override
    public Company getCompany(int id) {

        return companies.stream()
                .filter(company -> company.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public void updateCompany(Company company) {
        companies.stream()
                .filter(company1 -> company1.getId() == company.getId())
                .forEach(company1 -> company1.setName(company.getName()));
    }

    @Override
    public void deleteCompany(Company company) {
        companies.removeIf(company1 -> company1.getId() == company.getId());
    }

    @Override
    public void insertCompany(Company company) {
        companies.add(company);
        synchronize(StatementFactory.insertCompany(company));
    }

    @Override
    public void done() {
        disconnect();
    }
}
