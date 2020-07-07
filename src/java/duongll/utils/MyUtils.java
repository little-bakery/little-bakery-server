/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.utils;

import java.io.File;

/**
 *
 * @author duong
 */
public class MyUtils {

    public static String getXSLTFile(String source) throws Exception {
        File f = new File(source);
        return f.getPath();
    }
}
