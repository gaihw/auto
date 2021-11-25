package com.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("appId","fadsfas");
        hashMap.put("uid","fdfsfdsaf");
        if (!hashMap.isEmpty()) {
            for (Map.Entry<String,String> e : hashMap.entrySet()){
                System.out.println(e.getKey()+"::"+e.getValue());
//                post.setRequestHeader(it.next().getKey(),it.next().getValue());
            }
        }
        System.out.println("test...");
    }
}
