package com.zhx.usercenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//@SpringBootTest
public class UsercenterApplicationTests {

    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[1024];
        int temp = 0;
        try {
            fis = new FileInputStream("D:\\Tencent\\聊天记录\\550975101\\FileRecv\\audio_only.mp3");
            fos = new FileOutputStream("D:\\Tencent\\聊天记录\\550975101\\FileRecv\\啦啦啦.mp3");
            while (true) {
                temp = fis.read(buffer, 0, buffer.length);
                if (temp == -1) {
                    break;
                }
                File file = new File("D:\\Tencent\\聊天记录\\550975101\\FileRecv\\啦啦啦.mp3");

                if (file.length() <= 47185902) {
                    fos.write(buffer, 0, temp);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception e2) {
                System.out.println(e2);
            }
        }

    }
}

