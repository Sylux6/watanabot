package modules.Blindtest;

import java.util.HashMap;
import java.util.Map;

import modules.AbstractModule;

public class BlindtestModule extends AbstractModule {
    
    private final Map<Long, BlindtestInstance> instances;
    
    public BlindtestModule() {
	super();
	instances = new HashMap<>();
    }

    @Override
    public void populate() {
	
    }

}
