/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.util;

import java.security.MessageDigest;

/**
 *
 * @author Eduardo
 */
public class AgenteMD5 {

    public static String digest(String pBase) throws Exception {
        return digest(pBase, "UTF-8");
    }

    public static String digest(String pBase, String pCharSet) throws Exception {

        MessageDigest wmd = MessageDigest.getInstance("MD5");
        wmd.reset();
        wmd.update(pBase.getBytes(pCharSet));
        byte[] wdg = wmd.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < wdg.length; i++) {
            String w_dup = Integer.toHexString(0xFF & wdg[i]);

            if (w_dup.length() < 2) {
                w_dup = "0" + w_dup;
            }
            hexString.append(w_dup);
        }
        return hexString.toString();
    }
}
