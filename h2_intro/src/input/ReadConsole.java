package input;

import java.util.Scanner;

public class ReadConsole implements IReader {

    private Scanner scanner;

    public ReadConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int readInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public String readText() {
        return scanner.nextLine();
    }
}