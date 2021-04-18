package com.zhx.usercenter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Test {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10000000; i++) {
            Files.write(Paths.get(".\\\\1.txt"), (i + "\r\n").getBytes(), StandardOpenOption.APPEND);
        }
    }
}
