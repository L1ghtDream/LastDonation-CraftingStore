package me.lightdream.lastdonation;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.io.IOException;

public class Utils {
    public static void createFile() {
        File file = new File(ProxyServer.getInstance().getPluginsFolder()+ "/LastDonation/config.yml");
        try {
            if (!file.exists()) {
                file.createNewFile();

                //Config
                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                config.set("MOTD", "Your MOTD here");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file);
            }else{
                System.out.println("File already exists");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please create the folder LastDonation");
        }
    }

    public static void load() {
        File file = new File(ProxyServer.getInstance().getPluginsFolder()+ "/LastDonation/config.yml");
        try {
            if (file.exists()) {
                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                LastDonation.setLoads(config.getString("MOTD"));
                System.out.println(config.getString("MOTD"));
            }
            else
                System.out.println("File does not work");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please create the folder LastDonation");
        }
    }

    public static String color(String str)
    {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
