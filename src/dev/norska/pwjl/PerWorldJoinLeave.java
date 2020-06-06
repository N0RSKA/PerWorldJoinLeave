package dev.norska.pwjl;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import dev.norska.pwjl.commands.PWJLCommands;
import dev.norska.pwjl.config.PWJLConfigHandler;
import dev.norska.pwjl.listeners.PWJLWorldChange;
import dev.norska.pwjl.metrics.Metrics;
import dev.norska.pwjl.update.PWJLJoinNotifier;
import dev.norska.pwjl.update.PWJLUpdateChecker;

public class PerWorldJoinLeave extends JavaPlugin{
	
	private int a;
	private long nt;
	
	public static Boolean PAPI;
	
	public File config = null;
	public YamlConfiguration configC = new YamlConfiguration();
    
    public PWJLConfigHandler configHandler = new PWJLConfigHandler(this);
	
	public static Boolean update, checkUpdate;
	public static String latestUpdate;
	public static int resourceID = 51514;
	public static String downloadLink = "https://www.spigotmc.org/resources/" + resourceID + "/";
	public static String downloadLink1 = "https://www.mc-market.org/resources/8809/";
	public String version = getDescription().getVersion();
    
    public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage("§ePerWorldJoinLeave §7" + version + ", a free resource by §fNorska §e- §aExecuting startup sequence...");
		Bukkit.getConsoleSender().sendMessage(" ");
		
		this.generateFiles();
		checkPlaceholderAPI();
		this.registerListeners();
		this.registerCommands();
		final Metrics metrics = new Metrics((Plugin)this);
		this.completedMessage();
		this.checkUpdates();
    }
    
    private void registerListeners() {
		nt = System.nanoTime();
		Bukkit.getPluginManager().registerEvents(new PWJLWorldChange(this), this);
		Bukkit.getPluginManager().registerEvents(new PWJLJoinNotifier(this), this);
		nt = System.nanoTime() - nt;
		a = (int) TimeUnit.NANOSECONDS.toMillis(nt);
		Bukkit.getConsoleSender().sendMessage("§e[§a+§e] Registered listeners §7(took " + a + "§7ms)");
	}
    
    private void registerCommands() {
		nt = System.nanoTime();
		this.getCommand("perworldjoinleave").setExecutor(new PWJLCommands(this));
		nt = System.nanoTime() - nt;
		a = (int) TimeUnit.NANOSECONDS.toMillis(nt);
		Bukkit.getConsoleSender().sendMessage("§e[§a+§e] Registered commands §7(took " + a + "§7ms)");
	}
    
    public void generateFiles() {
		config = new File(getDataFolder(), "config.yml");
		this.configHandler.createConfigurations();
		this.configHandler.loadYAML();
	}
    
    private void completedMessage() {
		Bukkit.getConsoleSender().sendMessage(" ");
		Bukkit.getConsoleSender().sendMessage("§e[§a+§e] §aCompleted startup. Enjoy the plugin!");
		Bukkit.getConsoleSender().sendMessage(" ");
	}
    
    private void checkPlaceholderAPI() {
		Bukkit.getConsoleSender().sendMessage("§e[§a+§e] Checking for PlaceholderAPI...");
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				PAPI = true;
				Bukkit.getConsoleSender().sendMessage("§e[§a+§e] §aSuccess! §7Hooked into PlaceholderAPI!");
		} else {
			Bukkit.getConsoleSender().sendMessage("§e[§a+§e] §cFailure! §7Could not detect PlaceholderAPI!");
			Bukkit.getConsoleSender().sendMessage("§e[§a+§e] §7You will not be able to use placeholders!");
			PAPI = false;
		}
	}
    
	 private void checkUpdates() {
	    	PWJLUpdateChecker updater = new PWJLUpdateChecker(this, resourceID);
		    try {
		        if (updater.checkForUpdates()) {
		    		new BukkitRunnable() {
		    			@Override
		    			public void run() {
		    				if(configHandler.getConfigC().contains("updates.notifications")) {
		    					checkUpdate = configHandler.getConfigC().getBoolean("updates.notifications");
		    				} else {
		    					checkUpdate = true;
		    				}
		    				if(checkUpdate) {
		    					Bukkit.getConsoleSender().sendMessage("");
			    				Bukkit.getConsoleSender().sendMessage(" §8(§e§lPerWorldJoinLeave§8) §7§oA new update is available!");
			    				Bukkit.getConsoleSender().sendMessage(" §7Running on §c" + version + " §7while latest is §a" + PerWorldJoinLeave.latestUpdate + "§7!");
			    				Bukkit.getConsoleSender().sendMessage(" §e" + PerWorldJoinLeave.downloadLink);
			    				Bukkit.getConsoleSender().sendMessage(" §b" + PerWorldJoinLeave.downloadLink1);
			    				Bukkit.getConsoleSender().sendMessage("");
		    				}
		    			}
		    		}.runTaskLater(this, 20 * 5);
		        	update = true;
		        	latestUpdate = updater.getLatestVersion();
		        }else {
		        	update = false;
		        }
		    } catch (Exception e) {
		        update = false;
		    }
	    }
    
}

