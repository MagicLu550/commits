package net.noyark.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileSystem {

    public static Properties setProperties() throws IOException {
        File file = new File(System.getProperty("user.dir")).getParentFile();
        String propName = file+"/info.properties";
        if(!new File(propName).exists()){
            Properties properties = new Properties();
            properties.setProperty("password","");
            properties.setProperty("login-name","");
            properties.setProperty("repo","");
            properties.setProperty("start","7:00");
            properties.setProperty("end","23:00");
            properties.setProperty("round-ms","1000");
            FileOutputStream out = new FileOutputStream(propName);
            properties.store(out,"密码都知道，loginname是你的固定名字.repo指定存储库名字");
            out.flush();
            return properties;
        }else{
            Properties properties = new Properties();
            properties.load(new FileInputStream(propName));
            return properties;
        }
    }

}
