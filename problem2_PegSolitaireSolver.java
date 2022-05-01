package solution1;

import java.util.*;

public class PegSolitaireSolver {
    final private static boolean RIGHT = true;
    final private static boolean LEFT = false;

    public static void main(String[] args) {

        while (true) {
            Scanner sc = new Scanner(System.in);
            int BOARD_SIZE;
            System.out.print("The board size (have to be even number or 3 to be solved) = ");
            BOARD_SIZE = sc.nextInt();
            while (!(BOARD_SIZE == 3 || BOARD_SIZE % 2 == 0)) {
                System.out.println("board size have to be even number or 3");
                System.out.print("The board size = ");
                BOARD_SIZE = sc.nextInt();
            }
            System.out.print("The position of the empty slot from 1 to " + BOARD_SIZE + " is ");
            int BLANK_INIT_POS = sc.nextInt();
            boolean reversed = false;

            ArrayList<Integer> board = new ArrayList<>();

            for (int i = 0; i <= BOARD_SIZE; i++) {
                board.add(i, 1);
            }
            if (BLANK_INIT_POS > BOARD_SIZE / 2) {
                BLANK_INIT_POS = BOARD_SIZE - BLANK_INIT_POS + 1;
                reversed = true;
            }
            board.set(BLANK_INIT_POS, 0);

            ArrayList<ArrayList<Integer>> al = solveBoard(board, BLANK_INIT_POS);

            if (al == null)
                System.out.println("This board is unsolvable");

            else {
                System.out.println("//****************************************************//");
                System.out.println("The solution is ");
                System.out.println();
                if (!reversed) {
                    for (int k = 0; k < al.size(); k++) {
                        System.out.print("Step " + k + " : ");
                        for (int i = 1; i <= BOARD_SIZE; i++) {
                            System.out.print(al.get(k).get(i) + "\t");
                        }
                        System.out.println();
                    }
                } else {
                    for (int k = 0; k < al.size(); k++) {
                        System.out.print("Step " + k + " : ");
                        for (int i = BOARD_SIZE; i > 0; i--) {
                            System.out.print(al.get(k).get(i) + "\t");
                        }
                        System.out.println();
                    }
                }
                System.out.println("//****************************************************//");
            }
        }
    }

    public static ArrayList<ArrayList<Integer>> solveBoard(ArrayList<Integer> board, int BLANK_INIT_POS) {
        final int BOARD_SIZE = board.size() - 1;
        if (BLANK_INIT_POS != 2 && BOARD_SIZE == 3) {
            ArrayList<ArrayList<Integer>> aal = new ArrayList<>();
            aal.add(new ArrayList<>(board));
            if (validateMove(board, 3, LEFT)) {
                move(board, 3, LEFT);
            } else {
                move(board, 1, RIGHT);
            }
            aal.add(new ArrayList<>(board));
            return aal;
        } else if ((BLANK_INIT_POS == 2 && BOARD_SIZE != 3)
                || (BLANK_INIT_POS == 5 && BOARD_SIZE != 4)) {
            return solveBoardInner(board, BLANK_INIT_POS);
        } else {
            return null;
        }
    }

    private static ArrayList<ArrayList<Integer>> solveBoardInner(ArrayList<Integer> board, int BLANK_POS) {

        ArrayList<ArrayList<Integer>> solution = new ArrayList<>();
        int noOfConversions = 0;
        final int BOARD_SIZE = board.size() - 1;

        solution.add(new ArrayList<>(board));

        int nextMove;
        boolean direction;
        if (BLANK_POS == 2) {
            nextMove = BLANK_POS + 2;
            direction = LEFT;
        } else {
            if (validateMove(board, 3, RIGHT)) {
                move(board, 3, RIGHT);
            }
            nextMove = BLANK_POS + 1;
            direction = LEFT;
            noOfConversions++;
            solution.add(new ArrayList<>(board));
        }
        while (noOfConversions < BOARD_SIZE - 2) {
            if (validateMove(board, nextMove, direction)) {
                move(board, nextMove, direction);
                if (nextMove + 2 > BOARD_SIZE) {
                    nextMove = 1;
                    direction = RIGHT;
                    solution.add(new ArrayList<>(board));
                    noOfConversions++;
                    continue;
                }
                nextMove = nextMove + 2;
                noOfConversions++;
            }
            solution.add(new ArrayList<>(board));
        }
        return solution;
    }

    private static boolean validateMove(ArrayList<Integer> board, int pos, boolean direction) {
        if (direction == LEFT) {
            return board.get(pos - 1) == 1 && board.get(pos - 2) == 0;
        } else {
            return board.get(pos + 1) == 1 && board.get(pos + 2) == 0;
        }
    }

    private static void move(ArrayList<Integer> board, int pos, boolean direction) {
        if (direction == LEFT) {
            board.set(pos, 0);
            board.set(pos - 1, 0);
            board.set(pos - 2, 1);
        } else {
            board.set(pos, 0);
            board.set(pos + 1, 0);
            board.set(pos + 2, 1);
        }
    }

    private static int setBitOne(int num, int pos) {
        return num |= (1 << pos);
    }

    private static int setBitZero(int num, int pos) {
        return num &= ~(1 << pos);
    }

    private static int setBoard(int SIZE, int blank_pos) {
        int num = 0;
        for (int i = 1; i <= SIZE; i++) {
            num |= (1 << i);
        }
        num &= ~(1 << blank_pos);

        return num;
    }

    private static ArrayList<Integer> getBits(int num, int BOARD_SIZE) {
        ArrayList<Integer> intAL = new ArrayList<>();
        int temp = 0;

        intAL.add(0, temp);
        for (int i = 1; i <= BOARD_SIZE; i++) {
            temp = (num & (1 << i)) >> i;
            intAL.add(i, temp);
        }
        return intAL;
    }
}
