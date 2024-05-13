package com.master.chat.common.utils.file;

import java.io.*;

/**
 * 文件加解密
 *
 * @author: Yang
 * @date: 2023/1/27
 * @version: 1.0.0

 */
public class FileEncryptionAndDecryption {

    /**
     * 加密解密秘钥，双方约定好
     */
    private static final int PASSWORD_OF_ENC_AND_DEC = 123456789;

    /**
     * 文件字节内容
     */
    private static int dataOfFile = 0;

    public static void main(String[] args) {
        // 初始文件
        File srcFile = new File("./conf/req/er.txt");
        // 加密文件
        File encFile = new File("./conf/req/er1.txt");
        // 解密文件
        File decFile = new File("./conf/req/er3.txt");
        try {
            // 加密操作
        } catch (Exception e) {
            fileContentEncryption(srcFile, encFile);
            e.printStackTrace();
        }
        try {
            // 解密操作
            fileContentDecryption(encFile, decFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密文件
     *
     * @param srcFile
     * @param encFile
     */
    private static void fileContentEncryption(File srcFile, File encFile) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            if (srcFile == null || !srcFile.exists()) {
                return;
            }

            if (encFile == null || !encFile.exists()) {
                encFile.createNewFile();
            }
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(encFile);

            while ((dataOfFile = fis.read()) > -1) {
                fos.write(dataOfFile ^ PASSWORD_OF_ENC_AND_DEC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 解密文件
     *
     * @param encFile
     * @param decFile
     */
    private static void fileContentDecryption(File encFile, File decFile) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            if (!encFile.exists()) {
                System.out.println("encrypt file not exixt");
                return;
            }

            if (!decFile.exists()) {
                System.out.println("decrypt file created");
                decFile.createNewFile();
            }
            fis = new FileInputStream(encFile);
            fos = new FileOutputStream(decFile);

            while ((dataOfFile = fis.read()) > -1) {
                fos.write(dataOfFile ^ PASSWORD_OF_ENC_AND_DEC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
