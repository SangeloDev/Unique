package space.sangelo.unique;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import space.sangelo.unique.commands.*;
import space.sangelo.unique.listeners.PlayerConnectionListener;
import space.sangelo.unique.listeners.SpawnListener;
import space.sangelo.unique.listeners.VanishLoginListener;
import space.sangelo.unique.listeners.VanishPlayerListener;
import space.sangelo.unique.util.MessageUtil;
import space.sangelo.unique.util.VanishManager;

import java.io.File;

public final class Unique extends JavaPlugin {
	private MessageUtil messageUtil;
	private VanishManager vanishManager;
	//public Config config = new Config("config.yml", this.getDataFolder().getAbsolutePath(), this.getResource("config.yml"));

	@Override
	public void onEnable() {
		messageUtil = new MessageUtil(this, new File(getDataFolder(), "messages.yml"));
		vanishManager = new VanishManager(this);
		loadListeners();
		loadConfig();
		loadCommands();
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[Unique] " + ChatColor.GREEN + "Plugin has started.");
	}

	// Load Commands
	public void loadCommands() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[Unique] " + ChatColor.YELLOW + "Loading commands...");
		getCommand("unique").setExecutor(new UniqueCommand(this));
		getCommand("print").setExecutor(new PrintCommand(this));
		getCommand("god").setExecutor(new GodCommand(this));
		getCommand("fly").setExecutor(new FlyCommand(this));
		getCommand("vanish").setExecutor(new VanishCommand(this));
		getCommand("heal").setExecutor(new HealCommand(this));
		getCommand("feed").setExecutor(new FeedCommand(this));
		getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		getCommand("forcereload").setExecutor(new ForceReloadCommand(this));
		getCommand("gmc").setExecutor(new GamemodeCCommand(this));
		getCommand("gms").setExecutor(new GamemodeSCommand(this));
		getCommand("gmsp").setExecutor(new GamemodeSpCommand(this));
		getCommand("gma").setExecutor(new GamemodeACommand(this));
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[Unique] " + ChatColor.GOLD + "Commands loaded.");
	}

	// Load Config
	public void loadConfig() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[Unique] " + ChatColor.YELLOW + "Loading config...");
		saveResource("config.yml", false);
		reloadConfig();
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[Unique] " + ChatColor.GOLD + "Loading config complete");
	}

	// Load Listeners
	public void loadListeners() {
		final PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new PlayerConnectionListener(this), this);
		pluginManager.registerEvents(new SpawnListener(this), this);
		pluginManager.registerEvents(new VanishLoginListener(this), this);
		pluginManager.registerEvents(new VanishPlayerListener(this), this);
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[Unique] " + ChatColor.GOLD + "Shutting down...");
	}

	public MessageUtil getMessageUtil() {
		return messageUtil;
	}

	public VanishManager getVanishManager() {
		return vanishManager;
	}

}
