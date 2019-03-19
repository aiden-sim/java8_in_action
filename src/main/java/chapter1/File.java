package chapter1;

import java.io.FileFilter;

/**
 * Created by simjunbo on 2018-02-11.
 */
public class File {
    public void 일급시민() {
        new java.io.File(".").listFiles(java.io.File::isHidden);
    }

    public void 이급시민() {
        new java.io.File(".").listFiles(new FileFilter() {
            @Override
            public boolean accept(java.io.File pathname) {
                return pathname.isHidden();
            }
        });
    }
}
