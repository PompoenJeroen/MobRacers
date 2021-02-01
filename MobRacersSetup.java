package me.winterguardian.mobracers;

import java.io.File;
import java.util.HashMap;

import me.winterguardian.core.game.state.StateGameSetup;
import me.winterguardian.core.world.SerializableLocation;
import me.winterguardian.mobracers.vehicle.VehicleType;

import org.bukkit.configuration.file.YamlConfiguration;

public class MobRacersSetup extends StateGameSetup
{
	private HashMap<VehicleType, SerializableLocation> vehicleLocations;
	
	public MobRacersSetup(File file)
	{
		super(file);
		this.vehicleLocations = new HashMap<>();
	}

	@Override
	protected void load(YamlConfiguration config)
	{
		super.load(config);
		for(VehicleType type : VehicleType.values())
			if(config.isString("vehicle-location." + type.name()))
				this.vehicleLocations.put(type, SerializableLocation.fromString(config.getString("vehicle-location." + type.name())));
	}

	@Override
	protected void save(YamlConfiguration config)
	{
		super.save(config);
		for(VehicleType type : VehicleType.values())
			if(this.vehicleLocations.get(type) != null)
				config.set("vehicle-location." + type.name(), "" + this.vehicleLocations.get(type));
			else
				config.set("vehicle-location." + type.name(), null);
	}
	
	public HashMap<VehicleType, SerializableLocation> getVehicleLocations()
	{
		return this.vehicleLocations;
	}

	public boolean isBadRegion()
	{
		if(getRegion() == null)
			return false;
		
		if(getLobby() != null && !getRegion().contains(getLobby()))
			return true;
		
		if(getExit() != null && getRegion().contains(getExit()))
			return true;
		
		for(SerializableLocation loc : this.vehicleLocations.values())
			if(loc != null && loc.getLocation() != null && !getRegion().contains(loc.getLocation()))
				return true;
		
		return false;
	}
}
