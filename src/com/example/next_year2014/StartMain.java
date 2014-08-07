package com.example.next_year2014;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by kuangcheng on 2014/8/7.
 */
public class StartMain {
    private static final String INSTALL_ACTION_LABEL = "install";
    private static final String REINSTALL_ACTION_LABEL = "reinstall";
    private static final String UNINSTALL_ACTION_LABEL = "uninstall";

    public static void main(String args[]){
        if(args==null||args.length<2) return;
        PackageInstaller pi=new PackageInstaller();
        if(args[0].equals(INSTALL_ACTION_LABEL)){
            pi.installApp(args[1]);
        }
        if(args[0].equals(REINSTALL_ACTION_LABEL)){
            pi.reInstallApp(args[1]);
        }
        else if(args[0].equals(UNINSTALL_ACTION_LABEL)){
            pi.unInstallApp(args[1]);
        }
    }


}
