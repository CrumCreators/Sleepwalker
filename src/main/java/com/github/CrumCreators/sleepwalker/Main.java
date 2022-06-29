package com.github.CrumCreators.sleepwalker;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.*;
import java.util.Properties;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        String path = FabricLoader.getInstance().getConfigDir() +"/githubauth.properties";
        Properties properties = new Properties();
        File newfile = new File(path);
        //Create File & Load File
        if (!newfile.exists()) {
            try {
                OutputStream output = new FileOutputStream(path);
                properties.setProperty("username", "");
                properties.setProperty("password", "");
                properties.store(output, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            InputStream input = null;
            try {
                input = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Start Auth
        try {
            GitHub github = GitHubBuilder.fromPropertyFile(path).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Search
        try {
            searchMod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void searchMod() throws IOException {
        GitHub github = GitHub.connect();
        var carpetmod = github.getRepository("fabric-carpet").getLatestRelease().getAssetsUrl().startsWith("fabric-carpet-1.19");
        System.out.println(carpetmod);
    }
}
