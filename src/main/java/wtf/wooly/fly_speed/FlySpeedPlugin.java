package wtf.wooly.fly_speed;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import wtf.wooly.fly_speed.commands.*;
import wtf.wooly.fly_speed.listeners.*;

public final class FlySpeedPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(), this);
        getCommand("fly-speed").setExecutor(new FlySpeed());
    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers())
            player.setFlySpeed(.1F);
    }

}