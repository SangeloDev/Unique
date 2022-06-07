package space.sangelo.unique.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import space.sangelo.unique.Unique;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessageUtil {

	private final FileConfiguration externalConfig;
	private final FileConfiguration internalConfig;
	private final Component prefix;

	public MessageUtil(final Unique plugin, final File messageConfig) {
		if (!messageConfig.exists()) {
			plugin.saveResource(messageConfig.getName(), false);
		}
		externalConfig = YamlConfiguration.loadConfiguration(messageConfig);

		final InputStream inputStream = plugin.getResource(messageConfig.getName());

		assert inputStream != null;
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		internalConfig = YamlConfiguration.loadConfiguration(inputStreamReader);

		prefix = MiniMessage.miniMessage().deserialize(getString("prefix"));
	}

	public void sendMessage(final CommandSender sender, final String configString, final TagResolver... tagResolvers) {
		sender.sendMessage(getMessage(configString, tagResolvers));
	}

	public void sendMessageWithPrefix(final CommandSender sender, final String configString, final TagResolver... tagResolvers) {
		sender.sendMessage(getMessageWithPrefix(configString, tagResolvers));
	}

	public Component getMessage(final String configString, final TagResolver... tagResolvers) {
		return MiniMessage.miniMessage().deserialize(getString(configString), tagResolvers);
	}


	public Component getMessageWithPrefix(final String configString, final TagResolver... tagResolvers) {
		return prefix.append(getMessage(configString, tagResolvers));
	}

	public String getString(final String configString) {
		if (externalConfig.contains(configString)) {
			return externalConfig.getString(configString);
		} else {
			return internalConfig.getString(configString);
		}
	}
}
