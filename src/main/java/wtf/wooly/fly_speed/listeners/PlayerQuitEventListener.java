package wtf.wooly.fly_speed.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.getPlayer().setFlySpeed(.1f);
    }

}