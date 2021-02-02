package me.winterguardian.mobracers.vehicle;

import java.util.ArrayList;
import java.util.List;

import me.winterguardian.mobracers.vehicle.types.BabyCowVehicle;
import me.winterguardian.mobracers.vehicle.types.CaveSpiderVehicle;
import me.winterguardian.mobracers.vehicle.types.ChickenVehicle;
import me.winterguardian.mobracers.vehicle.types.CowVehicle;
import me.winterguardian.mobracers.vehicle.types.ElderGuardianVehicle;
import me.winterguardian.mobracers.vehicle.types.GuardianVehicle;
import me.winterguardian.mobracers.vehicle.types.MagmaCubeVehicle;
import me.winterguardian.mobracers.vehicle.types.MushroomCowVehicle;
import me.winterguardian.mobracers.vehicle.types.OcelotVehicle;
import me.winterguardian.mobracers.vehicle.types.PigVehicle;
import me.winterguardian.mobracers.vehicle.types.RabbitVehicle;
import me.winterguardian.mobracers.vehicle.types.SheepVehicle;
import me.winterguardian.mobracers.vehicle.types.SilverfishVehicle;
import me.winterguardian.mobracers.vehicle.types.SlimeVehicle;
import me.winterguardian.mobracers.vehicle.types.SpiderVehicle;
import me.winterguardian.mobracers.vehicle.types.SquidVehicle;
import me.winterguardian.mobracers.vehicle.types.SuperSheepVehicle;
import me.winterguardian.mobracers.vehicle.types.WolfVehicle;
import me.winterguardian.mobracers.vehicle.types.SkeletonVehicle;

import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import me.winterguardian.mobracers.vehicle.types.ZombieVehicle;
import me.winterguardian.mobracers.vehicle.types.EndermanVehicle;

public enum VehicleType
{
	COW(CowVehicle.class, new TypeCondition()
	{
		@Override
		public boolean isType(Entity entity)
		{
			return entity instanceof Cow && ((Cow)entity).isAdult();
		}
	}),
	MUSHROOM_COW(MushroomCowVehicle.class, getConditionByEntityType(EntityType.MUSHROOM_COW)),
	PIG(PigVehicle.class, getConditionByEntityType(EntityType.PIG)), 
	SHEEP(SheepVehicle.class, new TypeCondition()
	{
		@Override
		public boolean isType(Entity entity)
		{
			return entity.getType() == EntityType.SHEEP && entity.getCustomName() != "jeb_";
		}
	}), 
	SUPER_SHEEP(SuperSheepVehicle.class, new TypeCondition()
	{
		@Override
		public boolean isType(Entity entity)
		{
			return entity.getType() == EntityType.SHEEP && entity.getCustomName() == "jeb_";
		}
	}),
	WOLF(WolfVehicle.class, getConditionByEntityType(EntityType.WOLF)),
	CAVE_SPIDER(CaveSpiderVehicle.class, getConditionByEntityType(EntityType.CAVE_SPIDER)),
	OCELOT(OcelotVehicle.class, getConditionByEntityType(EntityType.OCELOT)), 
	SILVERFISH(SilverfishVehicle.class, getConditionByEntityType(EntityType.SILVERFISH)),

	SKELETON(SkeletonVehicle.class, getConditionByEntityType(EntityType.SKELETON)),
  	ENDERMAN(EndermanVehicle.class, getConditionByEntityType(EntityType.ENDERMAN)),
	SLIME(SlimeVehicle.class, getConditionByEntityType(EntityType.SLIME)),
	MAGMA_CUBE(MagmaCubeVehicle.class, getConditionByEntityType(EntityType.MAGMA_CUBE)),
	SPIDER(SpiderVehicle.class, getConditionByEntityType(EntityType.SPIDER)),
	RABBIT(RabbitVehicle.class, getConditionByEntityType(EntityType.RABBIT)),
	ZOMBIE(ZombieVehicle.class, getConditionByEntityType(EntityType.ZOMBIE)),

	GUARDIAN(GuardianVehicle.class, new TypeCondition()
	{
		@Override
		public boolean isType(Entity entity)
		{
			return entity instanceof Guardian && !((Guardian)entity).isElder();
		}
	}),
	ELDER_GUARDIAN(ElderGuardianVehicle.class, new TypeCondition()
	{
		@Override
		public boolean isType(Entity entity)
		{
			return entity instanceof Guardian && ((Guardian)entity).isElder();
		}
	}),
	SQUID(SquidVehicle.class, getConditionByEntityType(EntityType.SQUID)), 
	
	CHICKEN(ChickenVehicle.class, getConditionByEntityType(EntityType.CHICKEN)),
	BABY_COW(BabyCowVehicle.class, new TypeCondition()
	{
		@Override
		public boolean isType(Entity entity)
		{
			return entity instanceof Cow && !((Cow)entity).isAdult();
		}
	}),
	;
	
	private Class<? extends Vehicle> vehicleClass;
	private TypeCondition condition;
	
	private VehicleType(Class<? extends Vehicle> vehicleClass, TypeCondition condition)
	{
		this.vehicleClass = vehicleClass;
		this.condition = condition;
	}
	
	public Vehicle createNewVehicle()
	{
		try
		{
			return vehicleClass.getConstructor((Class<Object>[])null).newInstance((Object[])null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String getName()
	{
		return createNewVehicle().getName();
	}
	
	public boolean isType(Entity entity)
	{
		if(condition != null)
			return condition.isType(entity);
		return false;
	}
	
	public boolean canChoose(Player p)
	{
		return this.createNewVehicle().canChoose(p);
	}
	
	public static VehicleType getType(Entity entity)
	{
		for(VehicleType vehicle : values())
			if(vehicle.isType(entity))
				return vehicle;
		return null;
	}
	
	private static interface TypeCondition
	{
		public boolean isType(Entity entity);
	}
	
	public static TypeCondition getConditionByEntityType(final EntityType type)
	{
		return new TypeCondition()
		{
			@Override
			public boolean isType(Entity entity)
			{
				return entity.getType() == type;
			}
		};
	}
	
	public static List<VehicleType> getAvailableVehicles(Player p)
	{
		List<VehicleType> availables = new ArrayList<VehicleType>();
		for(VehicleType type : values())
			if(type.createNewVehicle().canChoose(p))
				availables.add(type);
		return availables;
			
	}
}
