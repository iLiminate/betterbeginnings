package net.einsteinsci.noobcraft.config;

import net.minecraftforge.common.config.Configuration;

public class NoobcraftConfig
{
	public static final String GENERAL = Configuration.CATEGORY_GENERAL;
	
	public static boolean greetUser;
	public static boolean advancedCraftingForLotsOfThings;
	public static boolean canMakeVanillaWorkbench;
	public static boolean canMakeVanillaFurnace;
	public static boolean canMakeChainArmor;
	public static boolean flamingAnimalsDropCharredMeat;
	public static boolean moreBones;
	
	public static int kilnSmeltTime;
	
	public NoobcraftConfig()
	{
		greetUser = true;
		advancedCraftingForLotsOfThings = true;
		canMakeVanillaWorkbench = true;
		canMakeVanillaFurnace = true;
		canMakeChainArmor = true;
		flamingAnimalsDropCharredMeat = true;
		moreBones = true;
		
		kilnSmeltTime = 250;
	}
	
	public static void syncConfig(Configuration config)
	{
		greetUser = config.getBoolean("greetUser", GENERAL, true, "Greet user upon login");
		advancedCraftingForLotsOfThings =
			config.getBoolean("advancedCraftingForLotsOfThings", GENERAL, true,
				"Require Advanced Crafting for things like doors, pistons, chests, etc.");
		canMakeVanillaWorkbench =
			config.getBoolean("canMakeVanillaWorkbench", GENERAL, true,
				"Provide alternative recipe for vanilla crafting table");
		canMakeVanillaFurnace =
			config.getBoolean("canMakeVanillaFurnace", GENERAL, true, "Provide alternative recipe for vanilla furnace");
		canMakeChainArmor =
			config.getBoolean("canMakeChainArmor", GENERAL, true,
				"Allow vanilla chain armor to be craftable from iron nuggets");
		flamingAnimalsDropCharredMeat =
			config.getBoolean("flamingAnimalsDropCharredMeat", GENERAL, true,
				"Animals that die by flame drop charred meat instead of vanilla cooked meat");
		moreBones = config.getBoolean("moreBones", GENERAL, true, "Almost all mobs drop bones, not just skeletons");
		
		kilnSmeltTime =
			config.getInt("kilnSmeltTime", GENERAL, 250, 2, 1000, "Time in ticks for a kiln to smelt something");
		
		if (config.hasChanged())
		{
			config.save();
		}
	}
}
