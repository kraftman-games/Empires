package es.themin.empires.util;

import org.bukkit.ChatColor;

import es.themin.empires.empires;

public class MsgManager {
	
	public static String plprefix = empires.plprefix;
	public String warprefix = empires.warprefix;
	public static String toofewargs = ChatColor.RED + "Too few arguments.";
	public static String notinemp = plprefix + ChatColor.RED + "You are not in an empire";
	public static String warwin = "After numurous victories the enemy has been defeated, you have won this war.";
	public static String warloose = "The enemy has won this war, you fought with honour but you must know when you are defeated.";
	public static String noempperm = plprefix + ChatColor.RED + "The owner of your empire has not given you permission to use this command";
	public static String createTitle(String message, ChatColor color) {
		int length = message.length();
		int dashlength = 50 - length;
		dashlength = (int) dashlength / 2 -4;
		StringBuilder str = new StringBuilder();
		str.append(color + ".oOo");
		for (int i = 1 ; i < dashlength ; i++) {
			str.append("=");
		}
		str.append(ChatColor.RESET + message + color);
		for (int i = 1 ; i < dashlength ; i++) {
			str.append("=");
		}
		str.append(color + "oOo.");
		return str.toString();
	}
	public static String colourUp(String string) {
		string.replaceAll("&0", ChatColor.BLACK + "");
		string.replaceAll("&1", ChatColor.DARK_BLUE + "");
		string.replaceAll("&2", ChatColor.DARK_GREEN + "");
		string.replaceAll("&3", ChatColor.DARK_AQUA + "");
		string.replaceAll("&4", ChatColor.DARK_RED + "");
		string.replaceAll("&5", ChatColor.DARK_PURPLE + "");
		string.replaceAll("&6", ChatColor.GOLD + "");
		string.replaceAll("&7", ChatColor.GRAY + "");
		string.replaceAll("&8", ChatColor.DARK_GRAY + "");
		string.replaceAll("&9", ChatColor.BLUE + "");
		string.replaceAll("&a", ChatColor.GREEN + "");
		string.replaceAll("&b", ChatColor.AQUA + "");
		string.replaceAll("&c", ChatColor.RED + "");
		string.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
		string.replaceAll("&e", ChatColor.YELLOW + "");
		string.replaceAll("&f", ChatColor.WHITE + "");
		string.replaceAll("&k", ChatColor.MAGIC + "");
		string.replaceAll("&l", ChatColor.BOLD + "");
		string.replaceAll("&m", ChatColor.STRIKETHROUGH + "");
		string.replaceAll("&n", ChatColor.UNDERLINE + "");
		string.replaceAll("&o", ChatColor.ITALIC + "");
		string.replaceAll("&r", ChatColor.RESET + "");
		return string;
	}
}
