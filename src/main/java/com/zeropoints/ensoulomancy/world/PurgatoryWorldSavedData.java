package com.zeropoints.ensoulomancy.world;

import java.util.Iterator;
import java.util.Map;

import com.zeropoints.ensoulomancy.Main;
import com.zeropoints.ensoulomancy.util.Reference;
import com.zeropoints.ensoulomancy.world.gen.structure.WorldGenSpiritTemple;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class PurgatoryWorldSavedData extends WorldSavedData {

	public static PurgatoryWorldSavedData GetExisting(World world) {
		// The IS_GLOBAL constant is there for clarity, and should be simplified into
		// the right branch.
		MapStorage storage = world.getPerWorldStorage();
		PurgatoryWorldSavedData instance = (PurgatoryWorldSavedData) storage
				.getOrLoadData(PurgatoryWorldSavedData.class, Name);

		if (instance == null) {
			Main.log("Creating new world storage");

			instance = new PurgatoryWorldSavedData();
			storage.setData(Name, instance);
		}
		return instance;
	}

	/*
	 * Save the structure data. As its a IWorldGenerated object the
	 * purgatorychunkgenerator doesnt know about it
	 * 
	 */
	public Long2ObjectMap<StructureBoundingBox> spiritTempleLocations = new Long2ObjectOpenHashMap<StructureBoundingBox>();

	public static final String Name = Reference.MOD_ID + "_purgatory_world_saved_data";

	public PurgatoryWorldSavedData(String s) {
		super(s);
		this.markDirty();
		Main.log("PurgatoryWorldSavedData: INIT 1");

	}

	public PurgatoryWorldSavedData() {
		super(Name);
		this.markDirty();
		Main.log("PurgatoryWorldSavedData: INIT 2");

	}

	
	
	
	/*
	 * Could probably optimise space by storing this differently so i dont repeat the same structured data 4-8 times for each structure(as a structure can span over that many chunk borders) 
	 * 
	 * 
	 * (non-Javadoc)
	 * @see net.minecraft.world.storage.WorldSavedData#readFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		spiritTempleLocations.clear();

		NBTTagList tagList = nbt.getTagList(WorldGenSpiritTemple.Name, Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {

			NBTTagCompound metaData = tagList.getCompoundTagAt(i);
			spiritTempleLocations.put(metaData.getLong("metadata_pos"),
					new StructureBoundingBox(metaData.getIntArray("metadata_box")));
		}

		Main.log("Read Template Length: " + spiritTempleLocations.size());

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {

		Main.log("Write Template Length: " + spiritTempleLocations.size());

		NBTTagList tagList = new NBTTagList();

		Iterator<?> it = spiritTempleLocations.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			NBTTagCompound metaData = new NBTTagCompound();
			metaData.setLong("metadata_pos", (long) pair.getKey());
			metaData.setIntArray("metadata_box",
					((StructureBoundingBox) pair.getValue()).toNBTTagIntArray().getIntArray());
			tagList.appendTag(metaData);

			// it.remove(); // avoids a ConcurrentModificationException
		}

		compound.setTag(WorldGenSpiritTemple.Name, tagList);

		return compound;
	}

}
