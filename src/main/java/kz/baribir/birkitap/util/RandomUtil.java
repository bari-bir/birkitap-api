package kz.baribir.birkitap.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    public static String generateRandomString(int len) {
        String chars = "1234567890-=!@#$%^&*()_+qwertyuiop[]QWERTYUIOP{}|asdfghjkl;'ASDFGHJKL:zxcvbnm,./ZXCVBNM<>?";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public static String random_number(int len) {
        String chars = "1234567890";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public static String random_uuid() {
        return UUID.randomUUID().toString();
    }

    public static float random(float begin, float end) {
        Random r = new Random();
        return r.nextFloat() * (end - begin) + begin;
    }
}
