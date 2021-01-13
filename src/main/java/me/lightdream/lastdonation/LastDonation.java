package me.lightdream.lastdonation;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;


import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public final class LastDonation extends Plugin implements Listener {

    private static LastDonation plugin;

    private static String MOTD;

    private static String lastDonator;

    @Override
    public void onEnable() {
        System.out.println();

        Utils.createFile();
        Utils.load();

        getProxy().getPluginManager().registerListener(this, this);

        plugin = this;

        System.out.println("Last Donation - Enabled");

        getProxy().getScheduler().schedule(this, new Runnable() {
            @Override
            public void run() {
                System.out.println("Last Dontor updated");
                try {
                    URL url = new URL("https://api.craftingstore.net/v7/payments");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("token", "EAn2MRQq6IJbXGEOoUGq3VLGaUZXZg5v6kdQzEE3MEWpPRGmQP");

                    InputStream is = con.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();

                    lastDonator = new Gson().fromJson(response.toString(), JsonObject.class).getAsJsonArray("data").get(0).getAsJsonObject().get("inGameName").getAsString();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 30, TimeUnit.MINUTES);

    }

    @Override
    public void onDisable() {
        System.out.println("Last Donation - Disabled");
    }

    public static String getLastDonator()
    {
        return lastDonator;
    }

    public static String getMotd()
    {
        return MOTD;
    }

    public static void setLoads(String motd)
    {
        MOTD = motd;
    }


    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent event) {
        ServerPing serverPing = event.getResponse();
        serverPing.setDescription(Utils.color(String.format(LastDonation.getMotd(), LastDonation.getLastDonator())));
    }
}
