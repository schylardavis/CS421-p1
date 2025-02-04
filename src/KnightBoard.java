import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Assignment p1
 * KnightBoard.java
 * Implements a backtracking approach to solve the Knight's Tour problem. Supports heuristic sorting of moves based on possible future moves (if heuristic I or II is enabled).
 * @author Schylar Davis
 * @version CS421-002 Spring 2025
 */

public class KnightBoard {
    private int n;                  // dimension of board
    private int[][] board;          // creates 2d array for board positions
    private int moveCount;          // tracks number of moves
    private int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
    private int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
    private boolean heuristicI, heuristicII;

    /**
     * Constructor method.
     * @param n
     * @param heuristicI
     * @param heuristicII
     */
    public KnightBoard(int n, boolean heuristicI, boolean heuristicII) {
        this.n = n;
        this.board = new int[n][n];
        this.heuristicI = heuristicI;
        this.heuristicII = heuristicII;
        for (int[] row : board) Arrays.fill(row, -1);
    }

    /**
     * Recursively solves KT problem using backtracking.
     * @param x
     * @param y
     * @return
     */
    public boolean solve(int x, int y) {
        board[x][y] = 0;
        moveCount = 0;
        return backtrack(x, y, 1);
    }

    /**
     * Implements recursive backtracking algorithm to solve K.T. problem on an n x n board.Tries all possible moves recursively. If valid tour is found, returns true immediately, if not, it undoes a move and tries another. If all moves fail, returns false to indicate failure.
     * @param x
     * @param y
     * @param moveIndex
     * @return true if valid solution found, false if backtracking needed
     */
    private boolean backtrack(int x, int y, int moveIndex) {
        boolean result = false;
        if (moveIndex == n * n) {
            result = true;
        } else {
            moveCount++;
            List<Position> moves = generateMoves(x, y);
            if (heuristicI || heuristicII) sortMoves(moves);
            for (Position move : moves) {
                if (isValid(move.x, move.y)) {
                    board[move.x][move.y] = moveIndex;
                    if (backtrack(move.x, move.y, moveIndex + 1)) {
                        result = true;
                        break;
                    }
                    board[move.x][move.y] = -1;
                }
            }
        }
        return result;
    }

    /**
     * Returns list of possible next positions for knight from Position (x, y).
     * @param x
     * @param y
     * @return
     */
    private List<Position> generateMoves(int x, int y) {
        return Arrays.asList(
                new Position(x + dx[0], y + dy[0]),
                new Position(x + dx[1], y + dy[1]),
                new Position(x + dx[2], y + dy[2]),
                new Position(x + dx[3], y + dy[3]),
                new Position(x + dx[4], y + dy[4]),
                new Position(x + dx[5], y + dy[5]),
                new Position(x + dx[6], y + dy[6]),
                new Position(x + dx[7], y + dy[7])
        );
    }

    /**
     * Lambda function that determines the sorting key for each Position.
     * If heuristicI is true, the sorting key is borderDistance(move.x, move.y).
     * If heuristicI is false, the sorting key is countPossibleMoves(move.x, move.y).
     * @param moves
     */
    private void sortMoves(List<Position> moves) {
        moves.sort(Comparator.comparingInt(move -> heuristicI ? borderDistance(move.x, move.y) : countPossibleMoves(move.x, move.y)));
    }

    /**
     * Calculates the distance of the position (x, y) from the edge of the board.
     * If heuristicI is enabled, moves closer to the border may be prioritized.
     * @param x
     * @param y
     * @return int
     */
    private int borderDistance(int x, int y) {
        return Math.min(x, n - 1 - x) + Math.min(y, n - 1 - y);
    }

    /**
     * Returns the number of valid moves from the given position (x, y).
     * If heuristicI is disabled, the position with the fewest possible next moves may be prioritized.
     * @param x
     * @param y
     * @return int
     */
    private int countPossibleMoves(int x, int y) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            if (isValid(nx, ny)) count++;
        }
        return count;
    }

    /**
     * Checks if a move is within bounds and not yet visited.
     * @param x
     * @param y
     * @return true or false
     */
    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < n && y < n && board[x][y] == -1;
    }

    public void printBoard() {
        for (int[] row : board) {
            for (int cell : row) System.out.printf("%2d ", cell);
            System.out.println();
        }
    }

    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(String.format("%2d ", cell));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


