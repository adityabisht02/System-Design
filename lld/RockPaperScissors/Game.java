import java.util.*;

public class Game {

    Player player;
    AI bot;
    GameStatus status;
    Scanner sc;

    public void initialiseGame() {
        player = new Player("p1");
        status = GameStatus.PLAYING;
        bot = new AI();
        sc = new Scanner(System.in);
    }

    public void startGame() {

        while (status == GameStatus.PLAYING) {
            System.out.println("Press 1 to choose rock, 2 to choose scissors, 3 to choose paper");
            int choice = Integer.valueOf(sc.nextLine());
            switch (choice) {
                case 1: {
                    player.piece = PlayingPiece.ROCK;
                }
                    break;
                case 2: {
                    player.piece = PlayingPiece.SCISSOR;
                }
                    break;
                case 3: {
                    player.piece = PlayingPiece.PAPER;
                }
                    break;
                default: {
                    System.out.println("Wrong choice try again !");
                    continue;
                }

            }

            // now check with bot's piece
            PlayingPiece botpiece = bot.getChoice();
            if (player.piece == botpiece) {
                System.out.println("Its a draw try again!");
                continue;
            }
            // check who won
            if (isPlayerWinner(player.piece, botpiece)) {
                System.out.println("HUMAN WINS !!");
            } else {
                System.out.println("BOT WINS !!");
            }
            status = GameStatus.COMPLETED;

        }

    }

    public boolean isPlayerWinner(PlayingPiece playerpiece, PlayingPiece botpiece) {
        if (playerpiece == PlayingPiece.ROCK && botpiece == PlayingPiece.SCISSOR) {
            return true;
        } else if (playerpiece == PlayingPiece.PAPER && botpiece == PlayingPiece.ROCK) {
            return true;
        } else if (playerpiece == PlayingPiece.SCISSOR && botpiece == PlayingPiece.PAPER) {
            return true;
        }

        // else if none of these then bot wins
        return false;

    }

    public static void main(String[] args) {
        Game rockpaperscissor = new Game();
        rockpaperscissor.initialiseGame();
        rockpaperscissor.startGame();
    }
}

class AI {
    Random random = new Random();

    public PlayingPiece getChoice() {
        int num = random.nextInt(2) + 1;
        switch (num) {
            case 1: {
                return PlayingPiece.ROCK;
            }

            case 2: {
                return PlayingPiece.SCISSOR;
            }

            case 3: {
                return PlayingPiece.PAPER;
            }

        }
        return null;
    }
}

enum GameStatus {
    PLAYING, PAUSED, ABANDONED, COMPLETED
}

class Player {

    String name;
    PlayingPiece piece;

    Player(String name) {
        this.name = name;
    }
}

enum PlayingPiece {
    ROCK, PAPER, SCISSOR
}