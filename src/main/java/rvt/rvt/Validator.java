package rvt;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidVards(String vards) {
        // Atļaujam burtus un minimālo garumu 3
        return vards != null && vards.trim().length() >= 3;
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }

    public static boolean isValidPK(String pk) {
        // Pārbauda: 6 cipari, tad domuzīme, tad 5 cipari
        // .trim() noņems nejaušas atstarpes sākumā vai beigās
        return pk.trim().matches("\\d{6}-\\d{5}");
    }
}