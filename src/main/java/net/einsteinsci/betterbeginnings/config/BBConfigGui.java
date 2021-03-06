package net.einsteinsci.betterbeginnings.config;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.einsteinsci.betterbeginnings.ModMain;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.List;

public class BBConfigGui extends GuiConfig
{
	public BBConfigGui(GuiScreen parent)
	{
		super(parent, getAllCategories(), ModMain.MODID, false, false,
		GuiConfig.getAbridgedConfigPath(ModMain.configFile.toString()));
	}

	public static List<IConfigElement> getAllCategories()
	{
		List<IConfigElement> all = new ConfigElement(ModMain.configFile.getCategory(BBConfig.GENERAL))
				.getChildElements();
		all.addAll(new ConfigElement(ModMain.configFile.getCategory(BBConfig.CRAFTING)).getChildElements());
		all.addAll(new ConfigElement(ModMain.configFile.getCategory(BBConfig.SMELTING)).getChildElements());

		return all;
	}
}
