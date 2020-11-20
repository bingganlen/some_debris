package com.megain.nfctemp.utils;

import java.util.regex.Pattern;

public class IsUtils {
    private static final String PATTERN_ALPHABETIC_OR_NUMBERIC = "[A-Za-z0-9]*";

    /**
     * 字符串是否由字面或数字组成
     *
     * @param src
     * @return
     */
    public static boolean isAlphabeticOrNumberic(String src) {
        return Pattern.compile(PATTERN_ALPHABETIC_OR_NUMBERIC).matcher(src).matches();
    }


    /**
     * 判断字符串是否为空
     *
     * @param src
     * @return
     */
    public static boolean isNullOrEmpty(String src) {
        return (src == null || src.length() == 0);
    }

    /**
     * 判断对象是否为空
     *
     * @param src
     * @return
     */
    public static boolean isNull(final Object src) {
        return src == null;
    }

    /**
     * 判断对象是否为空
     *
     * @param src
     * @return
     */
    public static boolean isNonNull(final Object src) {
        return !isNull(src);
    }

    /**
     * 判断一组字符串是否有一个为空
     *
     * @param strs
     * @return
     */
    public static boolean isNullOrEmpty(final String... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        }
        for (String str : strs) {
            if (str == null || str.length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断子字符串是否有出现在指定字符串中
     *
     * @param str
     * @param c
     * @return
     */
    public static boolean find(String str, String c) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.indexOf(c) > -1;
    }

    public static boolean findIgnoreCase(String str, String c) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.toLowerCase().indexOf(c.toLowerCase()) > -1;
    }

    /**
     * 比较两个字符串是否相同
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean equals(String src, String dst) {
        return src == null && dst == null || src != null && src.equals(dst);
    }

    /**
     * 比较数据是否等于0
     *
     * @param src
     * @return
     */
    public static boolean isZero(int src) {
        return src == 0;
    }

    /**
     * 比较数据是否等于0
     *
     * @param src
     * @return
     */
    public static boolean isZero(long src) {
        return src == 0;
    }

    /**
     * @param src
     * @param dst
     * @return true，如果src>dst
     */
    public static boolean isGreater(int src, int dst) {
        return src > dst;
    }

    /**
     * 比较两个整数是否相等
     *
     * @param src
     * @param dst
     * @return true，如果两个整数相等
     */
    public static boolean equals(int src, int dst) {
        return src == dst;
    }

    /**
     * 比较两个整数是否不相等
     *
     * @param src
     * @param dst
     * @return true，如果两个整数不相等
     */
    public static boolean unequals(int src, int dst) {
        return !equals(src, dst);
    }
}
