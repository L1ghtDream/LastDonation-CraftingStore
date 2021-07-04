package me.lightdream.lastdonation;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public final class LastDonation extends Plugin implements Listener {

    public static LastDonation INSTANCE;
    private LastDonation plugin;
    private String lastDonator = "";
    private Persist persist;
    private Config config;
    private String currentMOTD = "";
    private boolean changeVar = true;

    @Override
    public void onEnable() {
        INSTANCE = this;
        persist = new Persist(Persist.PersistType.YAML, this);
        config = persist.load(Config.class);
        currentMOTD = config.MOTD1;

        getProxy().getPluginManager().registerListener(this, new Listeners());
        getProxy().getPluginManager().registerCommand(this, new Commands("lastdonation", "lastdonation.admin"));

        plugin = this;

        System.out.println("Last Donation - Enabled");

        getProxy().getScheduler().schedule(this, () -> {
            try {
                URL url = new URL("https://api.craftingstore.net/v7/payments");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("token", config.token);

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
        }, 0, 30, TimeUnit.MINUTES);

        getProxy().getScheduler().schedule(this, () -> {
            if (!changeVar)
                currentMOTD = config.MOTD1;
            else
                currentMOTD = config.MOTD2;
            changeVar = !changeVar;
        }, config.changeInterval, config.changeInterval, TimeUnit.SECONDS);


    }

    @Override
    public void onDisable() {
        System.out.println("Last Donation - Disabled");
        persist.save(config);
    }
}
