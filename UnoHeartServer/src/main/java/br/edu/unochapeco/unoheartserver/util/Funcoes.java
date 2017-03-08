/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.util;

import java.util.Base64;

/**
 *
 * @author Eduardo
 */
public class Funcoes {
    
    public static boolean isNull(String value) {
        return value == null || value.equals("");
    }
    
    public static String encode(String value) throws Exception {
        return value != null ? AgenteMD5.digest(value) : null;
        
    }            
    
}
