package me.ScottSpittle.MuezliPlugin;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;

public class BukkitLogger {

	public static Main plugin;
	
	public BukkitLogger(Main instance){
		plugin = instance;

	}
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public void enabled(boolean enabled){
		PluginDescriptionFile pdfFile = plugin.getDescription();
		if(enabled){
			this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has been Enabled");
		}else {
			this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has been Disabled");
		}
	}
}
