package kz.baribir.birkitap.util;

import java.util.regex.Pattern;

public class FormatUtil {
    public static String format_kz_phone(String phone) {
        if (phone == null)
            return phone;
        phone = phone.replaceAll("\\s", "");
        if (phone.startsWith("8")) {
            phone = phone.substring(1);
            phone = "+7" + phone;

        }
        if (!phone.startsWith("+"))
            phone = "+7" + phone;
        return phone;
    }

    public static boolean email_format_is_valid(String email) {
        if (email == null || email.isEmpty())
            return false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static boolean phone_format_is_valid(String phone) {
/*        phone = format_kz_phone(phone);

        if (kazakh_phone_format_is_valid(phone) || china_phone_format_is_valid(phone))
            return true;*/

        try{
            checkPhone(phone);
        }catch (Exception e)
        {
            return false;
        }

        return true;
    }

    public static String formatPhone(String countryId,String phone)
    {
        switch (countryId){
            case "KZ" ->{
                return format_kz_phone(phone);
            }
            case "KG" ->{
                return format_kg_phone(phone);
            }
            case "UZ" ->{
                return format_uz_phone(phone);
            }
        }
        return phone;
    }

    public static String format_uz_phone(String phone)
    {
        if(phone==null)
            return phone;
        phone= phone.replaceAll("\\s","");
        if(!phone.startsWith("+"))
            phone="+998"+phone;
        return phone;
    }

    public static String format_kg_phone(String phone)
    {
        if(phone==null)
            return phone;
        phone= phone.replaceAll("\\s","");
        if(!phone.startsWith("+"))
            phone="+996"+phone;
        return phone;
    }

    public static boolean check_tracecode(String tracecode) {
        if (tracecode == null) return false;
        tracecode = tracecode.trim();
        //^[a-zA-Z0-9]*$
        return Pattern.matches("^[a-zA-Z0-9]*$", tracecode);
    }

    public static boolean check_tncode(String tncode) {
        if (tncode == null) return false;
        tncode = tncode.trim();
        if (tncode.isEmpty())
            return false;
        //^[a-zA-Z0-9]*$
        return Pattern.matches("^[a-zA-Z0-9]*$", tncode);
    }

    public static String getCountryCodeByPhone(String phone)
    {
        if(phone==null)
            return "";
        phone=phone.trim();
        if(phone.startsWith("+7"))
            return "KZ";//10
        if(phone.startsWith("+996"))
            return "KG";//9
        if(phone.startsWith("+998"))
            return "UZ";//9
        if(phone.startsWith("+86"))
            return "CN";//11
        return "";
    }

    public static void checkPhone(String phone)
    {
        if(phone==null)
            throw new RuntimeException("phone format error:["+phone+"]");
        String[] phoneRegs={
                "^\\+7\\d{10}$",
                "^\\+996\\d{9}$",
                "^\\+998\\d{9}$",
                "^\\+86\\d{11}$",
        };
        boolean match=false;
        for(var reg:phoneRegs)
            if(phone.matches(reg))
            {
                match=true;
                break;
            }

        if(!match)
            throw new RuntimeException("phone format error:["+phone+"]");

    }

    public static String getDefaultLanguageByCountry(String country)
    {
        if(country==null)
            return "kk";
        country=country.trim();
        switch (country)
        {
            case "KZ" -> {return "kk";}
            case "KG" -> {return "ru";}
            case "UZ"->{return "uz";}
            case "RU"->{return "ru";}
            case "CN"->{return "cn";}
            //case "EN"->{return "cn";}
        }
        return "kk";
    }

    public static String getProviderCodeByCountry(String country)
    {
        if(country==null)
            return "INEXPORT";
        country=country.trim();
        switch (country)
        {
            case "KZ","kazakhstan"-> {return "INEXPORT";}
            case "UZ"->{return "INEXPORT_UZ";}
            case "KG"->{return "INEXPORT_KG";}
        }
        return "INEXPORT";
    }
}
