package com.example.myappapivolley;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public String getMD5(String s) throws NoSuchAlgorithmException {
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(s.getBytes(),0,s.length());
        //System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));
        return new BigInteger(1,m.digest()).toString(16);
    }

}
