import controller.Controller;
import input.IReader;
import input.ReadConsole;
import model.CompanyDaoService;
import model.ICompanyDao;
import view.IPrinter;
import view.PrintConsole;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    public static void main(String[] args) {

        String dbName;
        if (args.length > 0 && "-databaseFileName".equals(args[0])) {
            dbName = DB_URL + args[1];
        } else {
            dbName = DB_URL + "carsharing";
        }

        Scanner scanner = new Scanner(System.in);
        IReader reader = new ReadConsole(scanner);
        IPrinter printer = new PrintConsole();
        ICompanyDao service = new CompanyDaoService(JDBC_DRIVER, dbName, USER, PASS);
        Controller controller = new Controller();
        controller.init(reader, printer, service);
        controller.run();
    }
}
