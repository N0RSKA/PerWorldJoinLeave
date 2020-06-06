package dev.norska.pwjl.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import dev.norska.pwjl.PerWorldJoinLeave;
import me.clip.placeholderapi.PlaceholderAPI;

public class PWJLWorldChange implements Listener{
	
	private PerWorldJoinLeave main;

	public PWJLWorldChange(PerWorldJoinLeave main) {
		this.main = main;
	}
	
    @EventHandler
    public void WC(final PlayerChangedWorldEvent e) {
    	
        final Player p = e.getPlayer();
        final World left = e.getFrom();
        final World joined = p.getWorld();
        String message;
        
        for(Player leftPlayers : left.getPlayers()) {
        	if(!leftPlayers.getName().equals(p.getName())) {
        		for(String ma : main.configHandler.getConfigC().getStringList("settings." + left.getName() + ".leave")) {
        			
        			message = ChatColor.translateAlternateColorCodes('&', ma
        					.replace("$player", p.getName()));
        			
        	        if(PerWorldJoinLeave.PAPI) message = PlaceholderAPI.setPlaceholders(p, message);

        	        leftPlayers.sendMessage(message);
        	    }
        	}
        	
        }
        
        for(Player joinedPlayers : joined.getPlayers()) {
        	if(!joinedPlayers.getName().equals(p.getName())) {
        		for(String ma : main.configHandler.getConfigC().getStringList("settings." + joined.getName() + ".join")) {
        			
        			message = ChatColor.translateAlternateColorCodes('&', ma
        					.replace("$player", p.getName()));
        			
        	        if(PerWorldJoinLeave.PAPI) message = PlaceholderAPI.setPlaceholders(p, message);

        	        joinedPlayers.sendMessage(message);
        	    }
        	}
        	
        }
        
    }

}
