package me.winterguardian.mobracers.vehicle.types;

import me.winterguardian.core.util.SoundEffect;
import me.winterguardian.mobracers.CourseMessage;
import me.winterguardian.mobracers.item.Item;
import me.winterguardian.mobracers.item.ItemType;
import me.winterguardian.mobracers.item.types.SugarItem;
import me.winterguardian.mobracers.item.types.WallItem;
import me.winterguardian.mobracers.item.types.WallItem.WallBlock;
import me.winterguardian.mobracers.vehicle.Vehicle;
import me.winterguardian.mobracers.vehicle.VehicleGUIItem;
import me.winterguardian.mobracers.vehicle.VehicleType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.entity.Enderman;

public class EndermanVehicle extends Vehicle
{
	
	@Override
	protected EntityType getEntityType()
	{
		return EntityType.ENDERMAN;
	}

	@Override
	public VehicleType getType()
	{
		return VehicleType.ENDERMAN;
	}

	@Override
	public boolean canChoose(Player p)
	{
		return true;
	}
	
	@Override
	public Item getItem(ItemType type)
	{
		switch(type)
		{
		case SUGAR:
			return new SugarItem(new SoundEffect(Sound.ENDERMAN_HIT, 1, 1.5f));
		case WALL:
			return new WallItem(Arrays.asList(new WallBlock(Material.ENDER_STONE, (byte)0)), new SoundEffect(Sound.ENDERMAN_SCREAM, 1, 0.8f));

		default:
			return type.getDefault();
		
		}
	}
	
	@Override
	public String getName()
	{
		return CourseMessage.VEHICLE_ENDERMAN_NAME.toString();
	}

	@Override
	public String getDescription()
	{
		return CourseMessage.VEHICLE_ENDERMAN_DESC.toString();
	}

	@Override
	public VehicleGUIItem getGUIItem()
	{
		return new VehicleGUIItem(getType(), 7, Material.MONSTER_EGG, 1, (short)58, "§5§l" + getName(), new ArrayList<String>());
	}
}
