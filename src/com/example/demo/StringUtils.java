package com.example.demo;

public class StringUtils {

    /**
     * delete last character if the last character is /;
     * @param string 
     * @return
     */
    public static final String delteLastPartCharacter(String string) {
        if(string == null) {
            string = "";
        }
        if(string.endsWith("/")) {
            string = (String) string.subSequence(0, string.length() - 1);
        }
        return string;
    }

}
