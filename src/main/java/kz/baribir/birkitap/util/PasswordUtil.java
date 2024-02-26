package kz.baribir.birkitap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    public static String encodeSHA256(String plaintext, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(plaintext.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static boolean checkPassword(String password, String real_password){
        String encodedPassword = PojUtil.sha256password_hash(password);
        return real_password.equals(encodedPassword);
    }

    public static boolean validPassword(String password){
        boolean isContainsUppercase = false;
        boolean isContainsLowercase = false;
        boolean isContainsDigit = false;
        for(Character ch: password.toCharArray()){
            if(Character.isDigit(ch))
                isContainsDigit = true;
            else if (Character.isLowerCase(ch))
                isContainsLowercase = true;
            else if (Character.isUpperCase(ch))
                isContainsUppercase = true;
        }
        return isContainsDigit && isContainsLowercase && isContainsUppercase && password.length() >= 8;
    }
}
