package org.tjdev.custom.punchitemstore;

import org.bukkit.Sound;
import org.tjdev.util.colorful.api.defaults.MineDown;
import org.tjdev.util.tjpluginutil.config.mapper.MappedConfig;
import org.tjdev.util.tjpluginutil.object.NewThis;
import org.tjdev.util.tjpluginutil.spigot.locale.LangDownload;
import org.tjdev.util.tjpluginutil.spigot.object.ItemColorAPI;
import org.tjdev.util.tjpluginutil.spigot.object.config.TJAction;

import java.util.Collections;
import java.util.List;

import static org.tjdev.util.tjpluginutil.IConstant.DEFAULT_COLOR_API;
import static org.tjdev.util.tjpluginutil.spigot.object.action.ActionFactory.messageSound;

public class Config extends MappedConfig implements NewThis {
    public static Config CONFIG;

    public Config() {
        load();
        CONFIG = this;
        LangDownload.init(lang);
        DEFAULT_COLOR_API = new ItemColorAPI(new MineDown(), itemFormat);
    }

    String lang = "en_us";
    String itemFormat = "&f%name% &fx%amt%";
    public List<String> enabledWorlds = Collections.singletonList("world");
    public boolean sneakOne = true;
    public TJAction stored = messageSound("&eYou have stored &f%item% &eto the chest.", Sound.NOTE_PLING);
    public TJAction full = messageSound("&cThe chest is full.", Sound.VILLAGER_NO);
}
