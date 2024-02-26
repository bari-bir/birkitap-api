package kz.baribir.birkitap.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class PojUtil {
    public static String random_uuid()
    {
        return UUID.randomUUID().toString();
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
    public static String sha256password_hash(String password)
    {
        String generatedPassword = null;
        String salt="be44361a-96c2-4edd-8789-9d0b1593af61";
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

    public static Date projectStartDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DAY_OF_MONTH, 5);

        return calendar.getTime();
    }

}
