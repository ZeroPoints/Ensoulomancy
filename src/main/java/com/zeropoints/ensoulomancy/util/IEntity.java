package com.zeropoints.ensoulomancy.util;

public interface IEntity {

	public IEntity.MobType GetMobType();
	
	public void RegisterEntityRenderer();
	
	public enum MobType {
		NONE, GHOST, HALLOWED, PROFANE
	}
	
}
