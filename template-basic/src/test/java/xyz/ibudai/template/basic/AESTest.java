package xyz.ibudai.template.basic;

import xyz.ibudai.template.basic.util.AESUtil;

public class AESTest {

    public static void main(String[] args) throws Exception {
        String encrypt = AESUtil.encrypt("123456");
        System.out.println(encrypt);
    }
}
