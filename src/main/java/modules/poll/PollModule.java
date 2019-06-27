package modules.poll;

import modules.AbstractModule;

public class PollModule extends AbstractModule {

    @Override
    public void populate() {
        commands.put("new", (event, args) -> {

        });
    }

    @Override
    public String getName() {
        return "poll";
    }
}
