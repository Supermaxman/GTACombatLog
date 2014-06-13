package me.supermaxman.gtacombatlog;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;



public class GTACombatLog extends JavaPlugin {
	public static GTACombatLog plugin;
	public static final Logger log = Logger.getLogger("Minecraft");
	public static final HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	
	public static FileConfiguration conf;

	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new GTACombatLogListener(), plugin);
		conf = getConfig();
		log.info(getName() + " has been enabled.");
	}
	
	public void onDisable() {
		
		log.info(getName() + " has been disabled.");
	}
	
}