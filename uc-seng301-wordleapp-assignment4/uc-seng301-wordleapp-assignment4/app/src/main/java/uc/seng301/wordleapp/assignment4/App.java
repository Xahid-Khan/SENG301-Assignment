package uc.seng301.wordleapp.assignment4;

import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import uc.seng301.wordleapp.assignment4.accessor.GameRecordAccessor;
import uc.seng301.wordleapp.assignment4.accessor.UserAccessor;
import uc.seng301.wordleapp.assignment4.dictionary.DictionaryQuery;
import uc.seng301.wordleapp.assignment4.dictionary.DictionaryQueryImpl;
import uc.seng301.wordleapp.assignment4.game.Game;
import uc.seng301.wordleapp.assignment4.game.Wordle;
import uc.seng301.wordleapp.assignment4.guesser.ManualGuesser;
import uc.seng301.wordleapp.assignment4.model.GameRecord;
import uc.seng301.wordleapp.assignment4.model.User;

public class App {
    private static final Logger LOGGER = LogManager.getLogger(App.class);
    private final GameRecordAccessor gameRecordAccessor;
    private final UserAccessor userAccessor;
    private final InputStream inputStream;
    private final Scanner cli;
    private final Random random;

    public App(InputStream inputStream) {
        LOGGER.info("Starting application...");
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        userAccessor = new UserAccessor(sessionFactory);
        gameRecordAccessor = new GameRecordAccessor(sessionFactory);
        this.inputStream = inputStream;
        cli = new Scanner(inputStream);
        random = new Random(301L);
        runCli();
    }

    public static void main(String[] args) {
        new App(System.in);
    }

    /**
     * Simple welcome message for command line operation
     */
    private void welcome() {
        System.out.println("""
                ######################################################
                             Welcome to Wordle Clone App
                ######################################################""");
    }

    /**
     * prints help page for commands to console
     */
    private void printHelp() {
        System.out.println("Available Commands:");
        System.out.println("\"create <name>\" to create a new player");
        System.out.println("\"play <name>\" to play with the provided player");
        System.out.println("\"highscores\" to view the top 10 highscores");
        System.out.println("\"exit\" or \"!q\" to quit");
    }

    /**
     * Runs the command line interface and reads inputs until the user quits or
     * terminates the program
     */
    public void runCli() {
        welcome();
        printHelp();
        while (true) {
            String input = cli.nextLine();
            switch (input.split(" ")[0]) {
                case "create":
                    String[] uInputs = input.split(" ");
                    if (uInputs.length == 1) {
                        System.out.println("Command incorrect use \"help\" for more information");
                        continue;
                    }
                    User u = new User();
                    u.setUserName(uInputs[1]);
                    Long userId = userAccessor.persistUser(u);
                    System.out.println(String.format("Created user %s id %d", u.getUserName(), userId));
                    break;
                case "play":

                    DictionaryQuery dictionaryQuery = new DictionaryQueryImpl();
                    List<String> words = dictionaryQuery.guessWord("").getWords();
                    Wordle wordle = new Wordle(words.get(random.nextInt(words.size())));
                    String[] inputs = input.split(" ");
                    if (inputs.length == 1) {
                        System.out.println("Command incorrect use \"help\" for more information");
                        continue;
                    }
                    User user = userAccessor.getUserByName(inputs[1]);
                    if (user == null) {
                        System.out.println(String.format("User %s does not exist", inputs[1]));
                        continue;
                    }
                    ManualGuesser guesser = new ManualGuesser(wordle, new Scanner(inputStream));
                    Game game = new Game(user, guesser, wordle);
                    GameRecord gameRecord = game.playGame();
                    if (gameRecord == null) {
                        continue;
                    }
                    Long gameRecordId = gameRecordAccessor.persistGameRecord(gameRecord);
                    System.out.println(gameRecord.toString() + ". Saved record with id: " + gameRecordId);
                    break;
                case "highscores":
                    List<GameRecord> highScores = gameRecordAccessor.getHighscores();
                    if (highScores.size() == 0) {
                        System.out.println("No games have been recorded yet");
                    }
                    for (int i = 0; i < highScores.size(); i++) {
                        System.out.println(String.format("%d: %s", i + 1, highScores.get(i)));
                    }
                    break;
                case "help":
                    printHelp();
                    break;
                case "exit":
                case "!q":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command, use \"help\" for more info");
                    break;
            }
        }
    }
}
