/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 *
 * @author gg005249
 */
public class Hash {
    
    public static String criptografar(String senha){
        MessageDigest algorithm = null;
        try
        {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex)
        {
            ex.getMessage();
        }
        byte messageDigest[] = null;
        try
        {
            messageDigest = algorithm.digest(senha.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex)
        {
            ex.getMessage();
        }
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhaCriptografada = hexString.toString();
        return senhaCriptografada;
    }
}
