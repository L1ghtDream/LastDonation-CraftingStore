package me.lightdream.lastdonation;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Commands extends Command {
    public Commands(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length==1){
            if(strings[0].equalsIgnoreCase("reload")){
                LastDonation.INSTANCE.setConfig(LastDonation.INSTANCE.getPersist().load(Config.class));
                LastDonation.INSTANCE.setCurrentMOTD(LastDonation.INSTANCE.getConfig().MOTD1);
            }
        }
    }
}
