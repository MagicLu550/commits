package net.noyark.www;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CommitEveryDay {

    public static void main(String[]args){
        try{
            File file = new File(System.getProperty("user.home"),".github");
            if(!file.exists()) {
                Properties githubProperties = new Properties();
                githubProperties.setProperty("oauth", "");
                githubProperties.setProperty("login", "jsmod2");
                githubProperties.setProperty("password", "85027859yhn*");
                githubProperties.setProperty("endpoint", "https://github.com/");
                FileOutputStream stream = new FileOutputStream(file);
                githubProperties.store(stream, "");
                stream.flush();
            }
            Properties properties = FileSystem.setProperties();
            GitHub gitHub = GitHub.connectUsingPassword(properties.getProperty("login-name"),properties.getProperty("password"));
            GHRepository repo = gitHub.getUser(properties.getProperty("login-name")).getRepository(properties.getProperty("repo"));
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            while (true){
                Date start = format.parse(properties.getProperty("start"));

                Date end = format.parse(properties.getProperty("end"));

                Date date = new Date();
                if(!start.before(date)&&end.after(date)){
                    repo.createCommit().message("commit one!").create();
                }
                Thread.sleep(Integer.parseInt(properties.getProperty("round-ms")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
