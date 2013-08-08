package devnobots.botexample;

import devnobots.ClientApi;
import javafx.scene.paint.Color;
import rest.service.types.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Example of an intelligent tank bot.
 *
 * @author Harry Klerks
 * @since 1.0
 */
public class NotSoCleverBot {

    private final static Logger LOG = Logger.getLogger(NotSoCleverBot.class.getName());
    private final String id;
    private final String playerName;
    private ClientApi api;
    private List<GamePlayer> gamePlayers;
    private List<GameObstacle> gameObstacles;

    public static void main(final String[] args) {

        String hostName = System.getProperty("devnobot.server.baseURL");
        String playerName = System.getProperty("devnobot.bot.playerName");
        String colour = System.getProperty("devnobot.bot.colour");
        if (hostName != null && playerName != null && colour != null) {
            if (isColourValid(colour)) {
                LOG.info("Starting NotSoCleverBot on " + hostName + " for " + playerName + ", colour = " + colour.toUpperCase());
                NotSoCleverBot notSoCleverBot = new NotSoCleverBot(hostName, playerName, colour.toUpperCase());
                notSoCleverBot.play();
            } else {
                LOG.severe("Colour must be a valid web colour, i.e. #FFFF00");
            }
        } else {
            LOG.severe("Usage is NotSoCleverBot -Ddevnobot.server.baseURL={baseURL} -Ddevnobot.bot.playerName={playerName} -Ddevnobot.bot.colour={colour}");
        }
    }

    private static boolean isColourValid(String colour) {
        try {
            Color.web(colour);
            return true;
        } catch (IllegalArgumentException iaex) {
            return false;
        }
    }

    public NotSoCleverBot(String hostName, String playerName, String colour) {

        this.api = new ClientApi(hostName, true);
        this.gameObstacles = api.readLevel();
        this.gamePlayers = api.readPlayers();
        this.id = playerName + "-" + new Date().getTime();
        this.playerName = playerName;

        if (this.api.createPlayer(playerName, colour, id)) {
            LOG.info("Created player " + playerName);
        }

    }

    private void play() {

        World world;
        while (true) {
            world = api.readWorldStatus();
            GameBot me = this.findMe(world);
            GameBot nearestBot = this.findNearestBot(me, world);
            if (nearestBot != null) {
                LOG.info("Found nearest bot at " + nearestBot.getX() + "," + nearestBot.getY());
                api.addAction(this.calculateNextAction(me, nearestBot), this.id);
                //If there is a nearest bot always shoot, you never know what you hit...
                api.addAction(Action.FIRE, this.id);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Action calculateNextAction(GameBot me, GameBot nearestBot) {

        if (me.getX() == nearestBot.getX()) {
            if (me.getY() < nearestBot.getY()) {
                //Nearest bot is straight above me
                if (me.getLastKnownOrientation() != Orientation.UP) {
                    return Action.TURN_RIGHT;
                }
            }
            if (me.getY() > nearestBot.getY()) {
                //Nearest bot is straight below me
                if (me.getLastKnownOrientation() != Orientation.DOWN) {
                    return Action.TURN_RIGHT;
                }
            }
        }

        if (me.getY() == nearestBot.getY()) {
            if (me.getX() < nearestBot.getX()) {
                //Nearest bot is straight to the right of me
                if (me.getLastKnownOrientation() != Orientation.RIGHT) {
                    return Action.TURN_RIGHT;
                }
            }
            if (me.getX() > nearestBot.getX()) {
                //Nearest bot is straight to the left of me
                if (me.getLastKnownOrientation() != Orientation.LEFT) {
                    return Action.TURN_RIGHT;
                }
            }
        }

        if (me.getY() > nearestBot.getY()) {
            if (me.getX() > nearestBot.getX()) {
                //Nearest bot is on my top left
                if (me.getLastKnownOrientation() == Orientation.UP ||
                        me.getLastKnownOrientation() == Orientation.LEFT) {
                    return Action.FORWARD;
                } else {
                    return Action.TURN_LEFT;
                }
            }
            if (me.getX() < nearestBot.getX()) {
                //Nearest bot is on my top right
                if (me.getLastKnownOrientation() == Orientation.UP ||
                        me.getLastKnownOrientation() == Orientation.RIGHT) {
                    return Action.FORWARD;
                } else {
                    return Action.TURN_LEFT;
                }
            }
        }

        if (me.getY() < nearestBot.getY()) {
            if (me.getX() > nearestBot.getX()) {
                //Nearest bot is on my bottom left
                if (me.getLastKnownOrientation() == Orientation.DOWN ||
                        me.getLastKnownOrientation() == Orientation.LEFT) {
                    return Action.FORWARD;
                } else {
                    return Action.TURN_LEFT;
                }
            }
            if (me.getX() < nearestBot.getX()) {
                //Nearest bot is on my bottom right
                if (me.getLastKnownOrientation() == Orientation.DOWN ||
                        me.getLastKnownOrientation() == Orientation.RIGHT) {
                    return Action.FORWARD;
                } else {
                    return Action.TURN_LEFT;
                }
            }
        }
        //In all other cases we fire
        return Action.FIRE;
    }

    private GameBot findMe(World world) {

        for (GameBot gameBot : world.getBots()) {
            if (gameBot.getPlayer().equals(this.playerName)) {
                return gameBot;
            }
        }

        return null;
    }

    private GameBot findNearestBot(GameBot me, World world) {

        GameBot nearestBot = null;
        double distanceToNearest = Double.MAX_VALUE;
        double distanceToBot;
        for (GameBot gameBot : world.getBots()) {
            if (!gameBot.getPlayer().equals(this.playerName)) {
                if (nearestBot == null) {
                    nearestBot = gameBot;
                } else {
                    distanceToBot = calculateDistanceToBot(me, gameBot);
                    if (distanceToBot < distanceToNearest) {
                        distanceToNearest = distanceToBot;
                        nearestBot = gameBot;
                    }
                }
            }
        }

        return nearestBot;
    }

    private double calculateDistanceToBot(GameBot me, GameBot gameBot) {

        double distanceToBot = Integer.MAX_VALUE;
        double deltaX = me.getX() - gameBot.getX();
        double deltaY = me.getY() - gameBot.getY();
        if (deltaX < 0) {
            deltaX = deltaX * -1;
        }
        if (deltaY < 0) {
            deltaY = deltaY * -1;
        }

        distanceToBot = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
        return distanceToBot;
    }
}
