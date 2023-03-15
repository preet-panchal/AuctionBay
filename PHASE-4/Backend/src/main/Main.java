package main;

public class Main {

    public static void main(String[] args) {
        String transFile = "daily_transaction.txt";
        FileController fc = new FileController();
        fc.load(transFile);
    }
}
