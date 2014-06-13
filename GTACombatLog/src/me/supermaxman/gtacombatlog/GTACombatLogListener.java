package me.supermaxman.gtacombatlog;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class GTACombatLogListener implements Listener {


	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if(e instanceof EntityDamageByEntityEvent) {
			if(((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
				Player p = (Player) ((EntityDamageByEntityEvent) e).getDamager();
				GTACombatLog.cooldowns.put(p.getName(), System.currentTimeMillis());
			}
			if(e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				GTACombatLog.cooldowns.put(p.getName(), System.currentTimeMillis());
			}
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if(p.isOp())return;
		if(GTACombatLog.cooldowns.containsKey(p.getName())) {
			if(GTACombatLog.cooldowns.get(p.getName())+(GTACombatLog.conf.getInt("settings.cooldown")*1000) > System.currentTimeMillis()) {
				e.setCancelled(true);
				p.sendMessage(getColoredText(GTACombatLog.conf.getString("settings.messages.NoCommandMessage")));
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(p.isOp())return;
		if(GTACombatLog.cooldowns.containsKey(p.getName())) {
			if(GTACombatLog.cooldowns.get(p.getName())+(GTACombatLog.conf.getInt("settings.cooldown")*1000) > System.currentTimeMillis()) {
				GTACombatLog.cooldowns.put(p.getName(), -1L);
			}
		}
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		if(p.isOp())return;
		if(GTACombatLog.cooldowns.containsKey(p.getName())) {
			if(GTACombatLog.cooldowns.get(p.getName())==-1L) {
				GTACombatLog.cooldowns.remove(p.getName());
				GTACombatLog.plugin.getServer().getScheduler().scheduleSyncDelayedTask(GTACombatLog.plugin, new Runnable() {
					public void run(){
						if(p!=null) {
							p.sendMessage(getColoredText(GTACombatLog.conf.getString("settings.messages.CombatLogDamageMessage")));
							p.setFallDistance(GTACombatLog.conf.getInt("settings.damage"));
						}
					}
				}, 5L);

			}
		}
	}

	public static String getColoredText(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
