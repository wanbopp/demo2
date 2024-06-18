package com.example.demo.test;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/5 18:17
 * @注释
 */
public class P12CertificateParser {
    public static void main(String[] args) {
        String password = "548253";

        try {
            // 加载证书文件
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("888010053000001.p12");
            keystore.load(fis, password.toCharArray());

            // 获取证书
            Certificate certificate = keystore.getCertificate("alias");  // 根据别名获取证书，如果不知道别名，可以遍历所有别名

            // 进行进一步的操作，例如打印证书信息
            System.out.println("Certificate Type: " + certificate.getType());
            System.out.println("Certificate Public Key: " + certificate.getPublicKey());
            // 其他操作...

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
