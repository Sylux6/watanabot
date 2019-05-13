package modules.anime;

import com.github.Doomsdayrs.Jikan4java.connection.Anime.AnimeConnection;
import com.github.Doomsdayrs.Jikan4java.types.Main.Anime.Anime;
import modules.AbstractModule;
import org.json.simple.parser.ParseException;
import utils.BotUtils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AnimeModule extends AbstractModule {
    @Override
    public void populate() {
        commands.put("info", (event, args) -> {
            if (args.size() < 2) {
                BotUtils.sendMessageAt(event.getChannel(), event.getAuthor(), "I don't know what to search for you");
                return;
            }
            try {
                args.remove(0);
                String title = String.join(" ", args);
                AnimeConnection animeConnection = new AnimeConnection();
                Anime anime = animeConnection.search(title).get();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getName() {
        return "anime";
    }
}
