package com.newcoder.community;

import java.io.IOException;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 22:38
 * 测试WK
 */
public class WKTest {
    public static void main(String[] args) {
        String cmd = "F:\\wkToPDF\\wkhtmltopdf\\bin\\wkhtmltoimage --quality 75 http://www.nowcoder.com F:\\javaProject\\data\\ToPDFPicter\\3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
