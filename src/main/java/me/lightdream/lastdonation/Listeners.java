package me.lightdream.lastdonation;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Listeners implements Listener {
    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent event) {
        ServerPing serverPing = event.getResponse();

        String descriptionRaw = LastDonation.INSTANCE.getCurrentMOTD();
        descriptionRaw = descriptionRaw.replace("%last-donation%", LastDonation.INSTANCE.getLastDonator());
        //BaseComponent[] description = TextComponent.fromLegacyText(Utils.color(descriptionRaw));

        //serverPing.setDescriptionComponent(description[0]);
        serverPing.setDescription(Utils.color(descriptionRaw));
    }
}
