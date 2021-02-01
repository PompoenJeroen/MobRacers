package me.winterguardian.mobracers.stats.achivements;

import me.winterguardian.core.Core;
import me.winterguardian.mobracers.CourseMessage;
import me.winterguardian.mobracers.MobRacersConfig;
import me.winterguardian.mobracers.MobRacersPlugin;
import me.winterguardian.mobracers.stats.CourseAchievement;
import me.winterguardian.mobracers.stats.CourseStats;
import me.winterguardian.mobracers.vehicle.VehicleType;
import org.bukkit.entity.Player;

public class OcelotAchievement extends CourseAchievement
{
	private CourseAchievement next;
	private int games;

	public OcelotAchievement(int games)
	{
		this.games = games;
	}

	@Override
	public boolean isComplete(Player p)
	{
		if(!((MobRacersConfig)MobRacersPlugin.getGame().getConfig()).enableStats())
			return true;

		return CourseStats.get(p).getVehicleGamesPlayed(me.winterguardian.mobracers.vehicle.VehicleType.OCELOT.name()) >= games;
	}

	@Override
	public void onComplete(Player p)
	{
		CourseMessage.SEPARATOR_ACHIEVEMENT.say(p);
		CourseMessage.ACHIEVEMENT_COMPLETE.say(p);
		p.sendMessage(getName() + " §f(" + getDescription() + ")");
		CourseMessage.ACHIEVEMENT_OCELOT_UNLOCK.say(p);
		CourseMessage.SEPARATOR_ACHIEVEMENT.say(p);
	}

	@Override
	public String getName()
	{
		return "§9§l" + CourseMessage.ACHIEVEMENT_OCELOT_NAME.toString();
	}

	@Override
	public String getDescription()
	{
		return CourseMessage.ACHIEVEMENT_OCELOT_DESC.toString().replace("#", "" + this.games);
	}

	@Override
	public String getProgression(Player p)
	{
		return "(" + CourseStats.get(p).getTotalGamesPlayed() + " / " + games + ")";
	}
	
	@Override
	public CourseAchievement getNext()
	{
		return this.next;
	}
}