package model.statements;

import model.Company;

public abstract class StatementFactory {

    public static String createCompanyTable() {
        return "CREATE TABLE IF NOT EXISTS company (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name VARCHAR(50) UNIQUE NOT NULL\n" +
                ");";
    }

    public static String insertCompany(Company company) {
        return String.format(
                "INSERT INTO company (name)\n" +
                        "VALUES ('%s');", company.getName()
        );
    }

    public static String readCompanyTable() {
        return "SELECT * FROM company;";
    }
}
