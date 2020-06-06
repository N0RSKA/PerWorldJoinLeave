package dev.norska.pwjl.config;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.norska.pwjl.PerWorldJoinLeave;

public class PWJLConfigHandler {

	private PerWorldJoinLeave main;

	public PWJLConfigHandler(PerWorldJoinLeave main) {
		this.main = main;
	}

	public void createConfigurations() {
		if (!main.config.exists()) {
			main.saveResource("config.yml", false);
		}
	}

	public void reloadConfigC() {
		main.configC = YamlConfiguration.loadConfiguration(main.config);
	}

	public void saveConfigC() {
		try {
			main.configC.save(main.config);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage("§ePerWorldJoinLeave: §fYAML §7> §f[§cconfig.yml§f] failed to save!");
			Bukkit.getConsoleSender().sendMessage(" ");
			e.printStackTrace();
		}
	}

	public void loadYAML() {
		try {
			main.configC.load(main.config);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage("§ePerWorldJoinLeave: §fYAML §7> §f[§cconfig.yml§f] failed to load!");
			Bukkit.getConsoleSender().sendMessage(" ");
			e.printStackTrace();
		}
	}
	
	public YamlConfiguration getConfigC() {
		return main.configC;
	}

}
