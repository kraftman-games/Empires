package es.themin.empires.util.testing;

import org.bukkit.entity.Player;

import es.themin.empires.cmds.SubCommand;
import es.themin.empires.enums.CoreType;
import es.themin.empires.util.Core;

public class tannertest extends SubCommand{

	@Override
	public boolean onCommand(Player player, String[] args) {
		//Core myCore = new Core();
		//myCore.setType(CoreType.BASE);
		//myCore.build(player);
		return false;
	}

	@Override
	public String name() {
		return "tannertest";
	}

	@Override
	public String info() {
		return "who knows";
	}

	@Override
	public String[] aliases() {
		return new String[] {"tt"};
	}

}
