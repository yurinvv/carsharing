package view;

public class PrintConsole implements IPrinter {
    @Override
    public void print(String text) {
        System.out.print(text);
    }
}
