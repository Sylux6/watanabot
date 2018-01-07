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

    @Override
    public String getName() {
	return "(b)lindtest";
    }

}
