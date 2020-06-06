package dev.norska.pwjl.update;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import dev.norska.pwjl.PerWorldJoinLeave;

public class PWJLJoinNotifier implements Listener{
	
	private PerWorldJoinLeave main;

	public PWJLJoinNotifier(PerWorldJoinLeave main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(!PerWorldJoinLeave.checkUpdate) {
			return;
		}
		if(e.getPlayer().isOp() && PerWorldJoinLeave.update) {
			new BukkitRunnable() {
				@Override
				public void run() {
					e.getPlayer().sendMessage("");
					e.getPlayer().sendMessage(" §8(§e§lPerWorldJoinLeave§8) §7§oA new update is available!");
					e.getPlayer().sendMessage(" §7Running on §c" + main.version + " §7while latest is §a" + PerWorldJoinLeave.latestUpdate + "§7!");
					e.getPlayer().sendMessage(" §e" + PerWorldJoinLeave.downloadLink);
					e.getPlayer().sendMessage(" §b" + PerWorldJoinLeave.downloadLink1);
					e.getPlayer().sendMessage("");
				}

			}.runTaskLater(main, 60);
		}
	}

}
