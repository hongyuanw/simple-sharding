package com.yuanwhy.simple.sharding.utils;

/**
 * Created by yuanwhy on 17/4/8.
 */
public class StringUtils {

    public static int countOfChar(String str, char character) {

        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == character) {
                count++;
            }
        }


        return count;
    }

    public static int indexOf(String str, char ch,  int rank) {

        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                count++;

                if (count == rank) {
                    return i;
                }
            }
        }

        return -1;

    }

}
