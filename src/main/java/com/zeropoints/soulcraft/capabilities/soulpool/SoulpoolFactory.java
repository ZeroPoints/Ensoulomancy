package com.zeropoints.soulcraft.capabilities.soulpool;

import java.util.concurrent.Callable;
import org.apache.logging.log4j.Level;
import com.zeropoints.soulcraft.Main;

public class SoulpoolFactory implements Callable<ISoulpool> {

  @Override
  public ISoulpool call() throws Exception {
	  Main.log(Level.INFO, "Soulpool factory");
	  return new Soulpool();
  }
  
}