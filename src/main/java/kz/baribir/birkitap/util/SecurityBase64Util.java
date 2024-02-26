package kz.baribir.birkitap.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityBase64Util {

    private SecurityBase64Util() {}

    private static final String salt = "f7T8glE(M9bS6dLt";

    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());

            return byte2Hex(secretBytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String symmetricEncAes(String content, String key) {
        try {
            byte[] encodeFormat = key.getBytes();
            SecretKeySpec secret = new SecretKeySpec(encodeFormat, "AES");

            Cipher cipher = Cipher.getInstance("AES");

            byte[] byteContent = content.getBytes("UTF-8");

            cipher.init(Cipher.ENCRYPT_MODE, secret);

            byte[] result = cipher.doFinal(byteContent);
            return Hex.encodeHexString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String symmetricDecAes(String content, String key) {
        try {
            byte[] byteContent = Hex.decodeHex(content.toCharArray());
            byte[] encodeFormat = key.getBytes();
            SecretKeySpec secret = new SecretKeySpec(encodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] result = cipher.doFinal(byteContent);
            return new String(result, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String String2SHA256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    public static String base64_encode(byte[] data) {
        Base64 base64 = new Base64();
        return new String(base64.encode(data));
    }

    public static byte[] base64_decode(String bstr) {
        Base64 base64 = new Base64();
        return base64.decode(bstr.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean password_check(String password) {
        return password != null && password.length() >= 6 && password.length() <= 32;
    }

    public static String sha256password_hash(String password) {
        String generatedPassword = null;
        String salt = "be44361a-96c2-4edd-8789-9d0b1593af61";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
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

    public static String PBKDF2_hash(String password, String salt, int itercount) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), itercount, 20 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return base64_encode(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
