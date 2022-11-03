import java.util.*;

class Game {
    Board gameboard;
    // for switching players its better to keep a Queue so u can simply get the
    // currentplayer by popping
    Player players[];
    Dice dice; // there can be multiple die
    Player currentPlayer;
    GameStatus gamestatus;

    public void initialiseGame() {
        Scanner sc = new Scanner(System.in);
        gameboard = new Board();
        System.out.println("Enter the size of the board :");
        int size = Integer.valueOf(sc.nextLine());
        gameboard.initialiseBoard(size);
        dice = new Dice(1);
        // we can take input for players as well
        players = new Player[2];
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        currentPlayer = player1;
        players[0] = player1;
        players[1] = player2;

        // creating random snakes and ladders for testing
        // ladder1
        Jumper jumper1 = new Jumper();
        jumper1.startPosition = 10;
        jumper1.endPosition = 20;
        gameboard.board[0][9].jumper = jumper1;

        // snake1
        Jumper jumper2 = new Jumper();
        jumper2.startPosition = 5;
        jumper2.endPosition = 0;
        gameboard.board[0][5].jumper = jumper2;

        gamestatus = GameStatus.PLAYING;

    }

    public void startGame() {
        // game loop
        while (gamestatus == GameStatus.PLAYING) {
            int currentPlayerPosition = currentPlayer.currentPosition;
            System.out.println("[" + currentPlayer.name + "'s turn]");
            System.out.println("Current position :" + currentPlayerPosition);
            int number = dice.generateRandomNumber();
            // check if position exceeds max board position then invalid move
            if (currentPlayerPosition + number > gameboard.winningPosition) {
                System.out.println("INVALID MOVE!");
                switchCurrentPlayer();
                continue;
            }
            // check if winner
            if (currentPlayerPosition + number == gameboard.winningPosition) {
                System.out.println(currentPlayer.name + " WINS THE GAME!");
                gamestatus = GameStatus.COMPLETED;
                continue;
            }
            // check if jumper
            if (gameboard.checkJumper(currentPlayerPosition + number, currentPlayer)) {
                switchCurrentPlayer();
                continue;
            }
            ;
            // otherwise just change player position and switch player
            currentPlayer.currentPosition = currentPlayerPosition + number;
            switchCurrentPlayer();
        }
    }

    public void switchCurrentPlayer() {
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            System.out.println(currentPlayer.name);
            currentPlayer = players[0];
        }
    }

    public static void main(String[] args) {
        Game snakeladder = new Game();
        snakeladder.initialiseGame();
        snakeladder.startGame();
        // Dice dice = new Dice(1);
        // for (int i = 0; i < 50; i++) {
        // System.out.print(" " + dice.generateRandomNumber());
        // }
    }

}

class Board {
    Cell board[][];
    int winningPosition;

    public void initialiseBoard(int size) {
        board = new Cell[size][size];

        // winning position will be size*size
        winningPosition = size * size;

        // traverse the board and put cell positions
        int position = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // add cell object to board[i][j] then add position
                Cell cell = new Cell();
                cell.position = position;
                board[i][j] = cell;
                position++;
            }
        }
    }

    // this method will check whether jumper is present and change player's position
    public boolean checkJumper(int cellPosition, Player player) {
        // traverse the matrix
        int position = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (position == cellPosition) {
                    // if jumper is present (snake/ladder present)
                    if (board[i][j].jumper != null) {
                        player.currentPosition = board[i][j].jumper.endPosition;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addSnake(Cell startingCell, Cell endingCell) {

        // we only need to change the starting cell's jumper, ending cell jumper would
        // remain as it is
        startingCell.jumper.startPosition = startingCell.position;
        startingCell.jumper.endPosition = endingCell.position;
        return true;

    }

    public boolean addLadder(Cell startingCell, Cell endingCell) {
        startingCell.jumper.startPosition = startingCell.position;
        startingCell.jumper.endPosition = endingCell.position;
        return true;
    }

}

class Dice {
    int noOfDie;

    Dice(int no) {
        noOfDie = no;
    }

    public int generateRandomNumber() {
        Random random = new Random();
        int sum = 0;
        for (int i = 0; i < noOfDie; i++) {
            sum += random.nextInt(6) + 1;

        }
        return sum;
    }
}

class Cell {
    Jumper jumper;
    int position;
}

class Jumper {
    int startPosition;
    int endPosition;
}

class Player {
    String name;
    int currentPosition;

    Player(String name) {
        this.name = name;
        currentPosition = 0;
    }

    public boolean changeName(String name) {
        this.name = name;
        return true;
    }

}

enum GameStatus {
    PAUSED, COMPLETED, ABANDONED, PLAYING
}