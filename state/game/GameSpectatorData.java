package me.winterguardian.mobracers.state.game;

import java.util.Arrays;

import me.winterguardian.core.game.PlayerData;
import me.winterguardian.core.util.PlayerUtil;
import me.winterguardian.mobracers.CourseMessage;
import me.winterguardian.mobracers.MobRacersConfig;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameSpectatorData extends PlayerData
{
	private GameState currentGame;
	private int spectatorLocationId;
	
	private long lastItemUse;
	
	public GameSpectatorData(GameState state, Player p)
	{
		super(p);
		this.currentGame = state;
		this.spectatorLocationId = 0;
		
		this.lastItemUse = 0;
	}
	
	public void useItem(int slot)
	{
		switch(slot)
		{
		case 0:
			useCompass();
			break;
			
		case 8:
			getPlayer().performCommand("mobracers leave");
			break;
		}
	}
	
	public void useCompass()
	{
		if(this.lastItemUse + 1000000000 > System.nanoTime())
			return;
		
		this.spectatorLocationId++;
		if(this.spectatorLocationId >= this.currentGame.getArena().getSpectatorLocations().size())
			this.spectatorLocationId -= this.currentGame.getArena().getSpectatorLocations().size();
		
		this.getPlayer().teleport(this.currentGame.getArena().getSpectatorLocations().get(this.spectatorLocationId).getLocation());
		this.lastItemUse = System.nanoTime();
	}
	
	public static ItemStack getSpectatorTool()
	{
		ItemStack item = new ItemStack(Material.COMPASS, 1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§r" + CourseMessage.GAME_COMPASS_NAME.toString());
		itemMeta.setLore(Arrays.asList(CourseMessage.GAME_COMPASS_LORE.toString()));
		item.setItemMeta(itemMeta);
		return item;
		
	}
	
	public static ItemStack getLeaveItem()
	{
		ItemStack item = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName("§r" + CourseMessage.GAME_LEAVEITEM_NAME.toString());
		itemMeta.setLore(Arrays.asList(CourseMessage.GAME_LEAVEITEM_LORE.toString()));
		item.setItemMeta(itemMeta);
		return item;
		
	}

	@Override
	public void onJoin()
	{
		PlayerUtil.clearInventory(getPlayer());
		getPlayer().getInventory().setItem(0, GameSpectatorData.getSpectatorTool());
		getPlayer().getInventory().setItem(8, GameSpectatorData.getLeaveItem());
		getPlayer().teleport(this.currentGame.getArena().getSpectatorLocations().get(this.spectatorLocationId).getLocation());
		if(((MobRacersConfig) currentGame.getGame().getConfig()).gamemode3Spectators())
			getPlayer().setGameMode(GameMode.SPECTATOR);
	}

	@Override
	public void onLeave()
	{
		
	}
}
