package com.twirling.lib_cobb.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author xieqiupeng
 */
public abstract class ZipUtil {
    private String _zipFile;
    private String _location;
    private InputStream fin;

    public ZipUtil(String zipFile) {
        _zipFile = zipFile;
        _dirChecker("");
    }

    public ZipUtil(InputStream stream) {
        fin = stream;
        _dirChecker("");
    }

    public abstract void setLocation(String _location);

    public void unzip() {
        try {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            byte b[] = new byte[1024];
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory()) {
//                    _dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    BufferedInputStream in = new BufferedInputStream(zin);
                    BufferedOutputStream out = new BufferedOutputStream(fout);
                    int n;
                    while ((n = in.read(b, 0, 1024)) >= 0) {
                        out.write(b, 0, n);
                    }
                    zin.closeEntry();
                    out.close();
                }
            }
            zin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unzipStream() {
        try {
//            FileInputStream fin = new FileInputStream(descriptor);
            if (fin != null) {
                fin.mark(0);
                ZipInputStream zin = new ZipInputStream(fin);
                byte b[] = new byte[1024];
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    if (ze.isDirectory()) {
                        _dirChecker(ze.getName());
                    } else {
                        FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                        BufferedInputStream in = new BufferedInputStream(zin);
                        BufferedOutputStream out = new BufferedOutputStream(fout);
                        int n;
                        while ((n = in.read(b, 0, 1024)) >= 0) {
                            out.write(b, 0, n);
                        }
                        zin.closeEntry();
                        out.close();
                    }
                }
                zin.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }
}