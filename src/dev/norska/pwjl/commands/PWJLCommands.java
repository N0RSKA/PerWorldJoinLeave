package dev.norska.pwjl.commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev.norska.pwjl.PerWorldJoinLeave;

public class PWJLCommands implements CommandExecutor {
	
	private PerWorldJoinLeave main;

	public PWJLCommands(PerWorldJoinLeave main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender cms, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("perworldjoinleave") && cms.hasPermission("pwjl.commands")) {
			
			if(args.length == 1) {
				
				if(args[0].equalsIgnoreCase("reload") && cms.hasPermission("pwjl.reload")) {
					
					 long nt = System.nanoTime();
					 main.configHandler.createConfigurations();
					 main.configHandler.reloadConfigC();
						nt = System.nanoTime() - nt;
						int a = (int) TimeUnit.NANOSECONDS.toMillis(nt);
					
					for(String ma : main.configHandler.getConfigC().getStringList("messages.reload")) {
				        cms.sendMessage(ChatColor.translateAlternateColorCodes('&', ma)
				        		.replace("$ms", Integer.toString(a)));
				    }
					
				}else if (args[0].equalsIgnoreCase("version") && cms.hasPermission("pwjl.version")) {

						if (PerWorldJoinLeave.update) {
	    					cms.sendMessage("");
		    				cms.sendMessage(" §8(§e§lPerWorldJoinLeave§8) §7§oA new update is available!");
		    				cms.sendMessage(" §7Running on §c" + main.version + " §7while latest is §a" + PerWorldJoinLeave.latestUpdate + "§7!");
		    				cms.sendMessage(" §e" + PerWorldJoinLeave.downloadLink);
		    				cms.sendMessage(" §b" + PerWorldJoinLeave.downloadLink1);
		    				cms.sendMessage("");
                        }
                        else {
                        	cms.sendMessage("");
							cms.sendMessage(" §8(§e§lPerWorldJoinLeave§8) §7§oNo updates available! Running on §a§o" + main.version + "§7§o!");
							cms.sendMessage("");
                        }

					
					
				}else if(!cms.hasPermission("pwjl.version") || !cms.hasPermission("pwjl.reload")) {
					for(String ma : main.configHandler.getConfigC().getStringList("messages.noPermission")) {
				        cms.sendMessage(ChatColor.translateAlternateColorCodes('&', ma));
				    }
				}
			}else {
				
				for(String ma : main.configHandler.getConfigC().getStringList("messages.menu")) {
			        cms.sendMessage(ChatColor.translateAlternateColorCodes('&', ma));
			    }
				
			}
			
			
		}else if(!cms.hasPermission("pwjl.commands")) {
			for(String ma : main.configHandler.getConfigC().getStringList("messages.noPermission")) {
		        cms.sendMessage(ChatColor.translateAlternateColorCodes('&', ma));
		    }
		}
		return false;
	}

	
}
