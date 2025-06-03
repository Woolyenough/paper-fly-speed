package wtf.wooly.fly_speed.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import java.util.List;


public class FlySpeed implements CommandExecutor, TabCompleter {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("fs.use")) {

            if (args.length == 0 || args.length > 2) {
                sender.sendMessage(formatMsg("<#ff3333>Invalid amount of arguments!"));
                sendHelp(sender);
                return true;
            }

            float speed;
            try {
                speed = Float.parseFloat(args[0]);
            } catch (NumberFormatException ignored) {
                sender.sendMessage(formatMsg("<#ff3333>That's not a number!"));
                return true;
            }

            if (-10f > speed || speed > 10f) {
                sender.sendMessage(formatMsg("<#ff3333>Invalid number! <#ff805e>Ensure it's between -10 and 10"));
                return true;
            }

            Player player = (Player) sender;
            if (args.length == 2) {

                if (!sender.hasPermission("fs.others")) {
                    sender.sendMessage(formatMsg("<#ff3333>You do not have permission to modify other peoples' speed"));
                    return true;
                }

                player = Bukkit.getPlayer(args[1]);

                if (player == null) {
                    sender.sendMessage(formatMsg("<#5eb0ff>Player not found! <#ff805e>They may be offline"));
                    return true;
                }
            }

            player.setFlySpeed(speed / 10F);

            String name = player.getName().equals(sender.getName()) ? "Your<#5eb0ff>" : player.getName() + "<#5eb0ff>'s";
            sender.sendMessage(formatMsg("<#5ae19f>" + name + " fly speed has been changed to <#5ae19f>" + speed));

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender.hasPermission("fs.use")) {

            if (args.length == 1) {
                if (args[0].isEmpty()) {
                    return List.of("-10..10");
                }
            }

            if (args.length == 2) {
                if (sender.hasPermission("fs.others")) {
                    return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).toList();
                }
            }

        }
        return List.of();
    }

    private void sendHelp(CommandSender sender) {
        String others = sender.hasPermission("fs.others") ? " (player)" : "";
        sender.sendMessage(formatMsg(" <#ef3737>Usage: <#ff5e5e>/fs <#ff835e><number>"+others));
    }

    private Component formatMsg(String message) {
        return miniMessage.deserialize(message);
    }
}