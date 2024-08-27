package io.openim.android.sdk.utils;


import android.text.TextUtils;
import android.util.Base64;
import io.openim.android.sdk.internal.log.LogcatHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {

    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

    /**
     * 判断byte[]是否是Gzip格式
     */
    public static boolean isGzip(byte[] data) {
        int header = (int) ((data[0] << 8) | data[1] & 0xFF);
        return header == 0x1f8b;
    }

    public static byte[] compress(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(data);
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();

    }

    public static byte[] decompress(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try (
            GZIPInputStream gis = new GZIPInputStream(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
