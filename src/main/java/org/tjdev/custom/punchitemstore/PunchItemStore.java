package org.tjdev.custom.punchitemstore;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.tjdev.util.tjpluginutil.spigot.PluginUtil;
import org.tjdev.util.tjpluginutil.spigot.command.CustomCommand;
import org.tjdev.util.tjpluginutil.spigot.command.SpigotCommand;
import org.tjdev.util.tjpluginutil.spigot.object.IncludedListener;
import org.tjdev.util.tjpluginutil.spigot.object.ItemColorAPI;
import org.tjdev.util.tjpluginutil.spigot.object.TJPlugin;
import org.tjdev.util.tjpluginutil.spigot.object.config.TJAction;

import static org.tjdev.custom.punchitemstore.Config.CONFIG;
import static org.tjdev.util.tjpluginutil.object.TJPluginExtension.requester;

public final class PunchItemStore extends TJPlugin implements IncludedListener {
    @Override
    public void enable() {
        requester = "RedstoneMiner18";
        targetVersion = "Spigot 1.8";
        requestType = PluginUtil.PLUGIN_TYPE.FREE;
        TJAction.init();

        new CustomCommand(getName().toLowerCase()) {{
            addDefault("reload");
            addReload();

            init(new SpigotCommand(getName(), "Main command.").command);
        }};
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Player p = e.getPlayer();
        if (!CONFIG.enabledWorlds.contains(p.getWorld().getName())) return;
        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) return;
        Block target = e.getClickedBlock();
        assert target != null;
        if (!(target.getState() instanceof Chest)) return;
        ItemStack item = e.getItem();
        if (item == null) return;
        int n = p.isSneaking() && CONFIG.sneakOne ? 1 : item.getAmount();
        Chest chest = (Chest) target.getState();
        ItemStack asQuantity = item.clone();
        asQuantity.setAmount(n);
        ItemStack clone = asQuantity.clone(); // addItem will change ItemStack
        if (chest.getBlockInventory().addItem(asQuantity).isEmpty()) {
            item.setAmount(item.getAmount() - n);
            p.setItemInHand(item);
            chest.update(true, true);
            CONFIG.stored.replace("item", ItemColorAPI.of(clone)).execute(p);
        } else CONFIG.full.execute(p);
    }
}
