package com.company;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException, IOException {
        Runner runner = new Runner();
        runner.run();
    }
}
