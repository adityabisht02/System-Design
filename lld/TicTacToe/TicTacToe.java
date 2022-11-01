import java.util.*;

class TicTacToe {
    Board board;
    Player players[];
    GameStatus gamestatus;
    Player currentPlayer;

    public void initialiseGame() {
        board = new Board();
        board.initialiseBoard(3);
        players = new Player[2];
        Player player1 = new Player("Player1", new PlayingPiece(PlayingPieceType.ZERO));
        Player player2 = new Player("Player2", new PlayingPiece(PlayingPieceType.CROSS));
        // if we are starting with player1
        currentPlayer = player1;
        players[0] = player1;
        players[1] = player2;

    }

    public void startGame() {
        gamestatus = GameStatus.PLAYING;
        Scanner sc = new Scanner(System.in);

        // game loop
        while (gamestatus == GameStatus.PLAYING) {
            System.out.println("[" + currentPlayer.name + "'s turn]");
            System.out.println("Enter row and column of the position u wanna mark :");
            String input = sc.nextLine();
            String inputarr[] = input.split(",");
            int row = Integer.valueOf(inputarr[0]);
            int column = Integer.valueOf(inputarr[1]);

            if (!board.isValidMove(row, column)) {
                System.out.println("This move is invalid!! Please enter again");
                continue;
            }
            board.board[row][column] = currentPlayer.piece;

            // check if currentplayer has won
            if (board.isThereAWinner(row, column)) {
                System.out.println(currentPlayer.name + " is the winner !!");
                gamestatus = GameStatus.FINISHED;
                continue;
            }

            // print the board
            board.printBoard();

            if (players[0] == currentPlayer) {
                currentPlayer = players[1];
            } else {
                currentPlayer = players[0];
            }
        }

    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.initialiseGame();
        game.startGame();

    }
}

class Board {
    PlayingPiece board[][];
    int size;

    public void initialiseBoard(int size) {
        this.size = size;
        board = new PlayingPiece[size][size];
        // board will be null at the start so fill the board with empty pieces

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                PlayingPiece p = new PlayingPiece();
                board[i][j] = p;
            }
        }
    }

    public void resetBoard() {
        board = new PlayingPiece[size][size];
    }

    public boolean isValidMove(int r, int c) {
        // if the square is already taken
        if (board[r][c].piecetype != PlayingPieceType.EMPTY) {
            return false;
        }
        return true;
    }

    public boolean isThereAWinner(int r, int c) {

        PlayingPieceType temp = board[r][c].piecetype;

        // boolean flags for checking if player won
        boolean isRowComplete = true, isColumnComplete = true, isDiagonalComplete = true, isAntiDiagonalComplete = true;
        // check column
        for (int i = 0; i < board.length; i++) {
            if (board[i][c].piecetype == PlayingPieceType.EMPTY || board[i][c].piecetype != temp) {
                isColumnComplete = false;
            }

        }

        // check row
        for (int i = 0; i < board[0].length; i++) {
            if (board[i][c].piecetype == PlayingPieceType.EMPTY || board[i][c].piecetype != temp) {
                isRowComplete = false;
            }

        }

        // there are two diagonals so check the diagonal and the anti diagonal

        // check diagonal
        for (int i = 0, j = 0; i < board.length; i++, j++) {
            if (board[i][j].piecetype == PlayingPieceType.EMPTY || board[i][j].piecetype != temp) {
                isDiagonalComplete = false;
            }
        }
        // check anti diagonal
        for (int i = 0, j = board.length - 1; i < board.length; i++, j--) {
            if (board[i][j].piecetype == PlayingPieceType.EMPTY || board[i][j].piecetype != temp) {
                isAntiDiagonalComplete = false;
            }
        }
        // if any one of these is complete
        return isAntiDiagonalComplete || isDiagonalComplete || isRowComplete || isColumnComplete;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].piecetype == PlayingPieceType.CROSS) {
                    System.out.print("X ");
                }
                ;
                if (board[i][j].piecetype == PlayingPieceType.ZERO) {
                    System.out.print("O ");
                }
                if (board[i][j].piecetype == PlayingPieceType.EMPTY) {
                    System.out.print("  ");
                }

                System.out.print("| ");
            }
            System.out.println();
        }
    }
}

class PlayingPiece {
    PlayingPieceType piecetype;

    PlayingPiece() {
        piecetype = PlayingPieceType.EMPTY;
    }

    PlayingPiece(PlayingPieceType piecetype) {
        this.piecetype = piecetype;
    }

    public boolean isEmpty() {
        if (piecetype == PlayingPieceType.EMPTY) {
            return true;
        }
        return false;
    }

}

enum PlayingPieceType {
    EMPTY, ZERO, CROSS, STAR, SQUARE
}

class Player {
    String name;
    PlayingPiece piece;

    Player(String name, PlayingPiece piece) {
        this.name = name;
        this.piece = piece;
    }
}

enum GameStatus {
    PLAYING,
    PAUSED,
    FINISHED,
    ABANDONED
}