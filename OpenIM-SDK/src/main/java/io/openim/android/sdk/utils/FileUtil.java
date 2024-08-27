package io.openim.android.sdk.utils;

import io.openim.android.sdk.internal.log.LogcatHelper;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil {

    public static boolean fileExist(String path) {
        File file = new File(path);
        return file.exists();
    }


    public static String fileTmpPath(String fullPath, String dbPrefix) {
        var suffix = getExt(fullPath);
        if (suffix.isEmpty()) {
            LogcatHelper.logDInDebug(String.format("fileTmpPath fullPath(%s) suffix err:", fullPath));
        }
        return dbPrefix + getMd5(fullPath) + suffix;
    }

    public static String getExt(String fullPath) {
        int i = fullPath.lastIndexOf(".");
        if (i > 0) {
            return fullPath.substring(i + 1);
        }
        return "";
    }

    public static String getMd5(String input) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add input bytes to digest
            md.update(input.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02x", bytes[i]));
            }

            // Get complete hashed string in hex format
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {

            return String.valueOf(System.currentTimeMillis());
        }
    }
}
