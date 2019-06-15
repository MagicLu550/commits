package net.noyark.www;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

                Calendar calendar = Calendar.getInstance();
                calendar.set(1970,Calendar.JANUARY,1);
                Date date = calendar.getTime();
                Date start = format.parse(properties.getProperty("start"));

                Date end = format.parse(properties.getProperty("end"));

                List<GHCommit> commits =repo.listCommits().asList();
                commits.sort((c1, c2)->{
                    try{
                        return (int)(c2.getCommitDate().getTime()-c1.getCommitDate().getTime());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    return 0;
                });

                if(start.before(date)&&end.after(date)){
                    System.out.println("commit one!");
                    repo.createCommit()
                            .message("commit-one!")
                            .author(properties.getProperty("login-name"),properties.getProperty("email"),date)
                            .parent(commits.get(0).getSHA1())
                            .committer(properties.getProperty("login-name"),properties.getProperty("email"),date)
                            .tree(commits.get(0).getTree().getSha())
                            .create();
                }
                Thread.sleep(Integer.parseInt(properties.getProperty("round-ms")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
