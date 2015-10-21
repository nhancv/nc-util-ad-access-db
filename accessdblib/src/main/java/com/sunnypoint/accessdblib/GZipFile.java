package com.sunnypoint.accessdblib;

/**
 * Created by NhanCao on 21-Oct-15.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipFile {
    private static GZipFile instance = new GZipFile();
    public static int BUFFER_SIZE=8*1024;
    public static GZipFile getInstance() {
        return instance;
    }

    /**
     * GZip it
     */
    public void gzipIt(String sourceFile, String ouputGzipFile) {

        byte[] buffer = new byte[BUFFER_SIZE];

        try {

            GZIPOutputStream gzos =
                    new GZIPOutputStream(new FileOutputStream(ouputGzipFile));

            FileInputStream in =
                    new FileInputStream(sourceFile);

            int len;
            while ((len = in.read(buffer)) > 0) {
                gzos.write(buffer, 0, len);
            }

            in.close();

            gzos.finish();
            gzos.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * GunZip it
     */
    public void gunzipIt(String inputGzipFile, String outputFile) {

        byte[] buffer = new byte[BUFFER_SIZE];

        try {

            GZIPInputStream gzis =
                    new GZIPInputStream(new FileInputStream(inputGzipFile));

            FileOutputStream out =
                    new FileOutputStream(outputFile);

            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            gzis.close();
            out.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}