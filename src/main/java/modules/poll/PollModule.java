package modules.poll;

import modules.AbstractModule;
import modules.poll.entity.Poll;
import net.dv8tion.jda.client.entities.Application;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.BotUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollModule extends AbstractModule {

    public static Map<Integer, String> choiceToEmote = Map.of(
            1, "1⃣",
            2, "2⃣",
            3, "3⃣",
            4, "4⃣",
            5, "5⃣",
            6, "6⃣",
            7, "7⃣",
            8, "8⃣",
            9, "9⃣",
            10, "\uD83D\uDD1F"
    );

    public static Map<String, Integer> emoteToChoice = Map.of(
            "1⃣", 1,
            "2⃣", 2,
            "3⃣", 3,
            "4⃣", 4,
            "5⃣", 5,
            "6⃣", 6,
            "7⃣", 7,
            "8⃣", 8,
            "9⃣", 9,
            "\uD83D\uDD1F", 10
    );

    @Override
    public void populate() {
        commands.put("new", (event, args) -> {
            newPoll(event, args, false);
        });

        commands.put("newx", (event, args) -> {
            newPoll(event, args, true);
        });
    }

    @Override
    public String getName() {
        return "poll";
    }

    private void newPoll(MessageReceivedEvent event, List<String> args, boolean multiple) {
        args.remove(0);
        String[] elements = String.join(" ", args).split("\\|");
        if (elements.length < 3) {
            BotUtils.sendMessage(
                    event.getChannel(),
                    "`o7 poll new 'topic' | 'choice1' | 'choice2' [ | ... ]` (up to 10 choices)"
            );
            return;
        }
        Poll poll = new Poll(event.getMember(), elements.length - 1, multiple, elements[0].trim());
        HashMap<Integer, ArrayList<String>> userResponses = new HashMap<>();
        for (int i = 1; i < elements.length; i++) {
            poll.getChoices().put(i, elements[i].trim());
        }
        BotUtils.sendPollMessage(event.getChannel(), poll);
    }
}
