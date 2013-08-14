package com.example.demo;

import junit.framework.TestCase;

public class Test extends TestCase {

    public void test1() {
        AppInfo info = new AppInfo();
        info.label = "a/b/c";
        
        String title = info.getListTitle("a/");
        System.out.println(title);
        
        title = info.getListTitle("a");
        System.out.println(title);
        
        title = info.getListTitle("a/b/");
        System.out.println(title);
        
        
        title = info.getListTitle("a/b");
        System.out.println(title);
        
        title = info.getListTitle("a/b/c");
        System.out.println(title);
    }
}
