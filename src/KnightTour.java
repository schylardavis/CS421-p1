import java.io.FileWriter;
import java.io.IOException;

/**
 * Assignment p1
 * KnightTour.java
 * Driver program for solving Knight's Tour problem. Parses command-line arguments, initializes the KnightBoard and attempts to solve the problem. Then prints out the solution and the total moves tried to both console and an output README file.
 * @author Schylar Davis
 * @version CS421-002 Spring 2025
 */
public class KnightTour {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java KnightTour <0/1/2 (no/heuristicI/heuristicII)> <n> <x> <y>");
            return;
        }

        int searchOption = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int x = Integer.parseInt(args[2]);
        int y = Integer.parseInt(args[3]);

        boolean heuristicI = searchOption == 1;
        boolean heuristicII = searchOption == 2;

        KnightBoard board = new KnightBoard(n, heuristicI, heuristicII);
        boolean solved = board.solve(x, y);

        System.out.println("Solution Found: " + solved);

        try (FileWriter writer = new FileWriter("OUTPUT.txt", true)) {
            writer.write("Search Option: " + searchOption + "\n");
            writer.write("Board Size: " + n + "x" + n + "\n");
            writer.write("Starting Position: (" + x + ", " + y + ")\n");
            writer.write("Solution Found: " + solved + "\n");

            if (solved) {
                board.printBoard();
                writer.write("Solution:\n");
                writer.write(board.toString() + "\n");
            }

            writer.write("Total Moves Tried: " + board.getMoveCount() + "\n\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

