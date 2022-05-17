import java.util.*;

public class PegSolitaireSolver2 {
    final private static boolean RIGHT = true;
    final private static boolean LEFT = false;
    final private int BOARD_SIZE;
    private Set<BitSet> memory;
    BoardTree.Board initialBoard;
    BoardTree BT;

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


            System.out.println("Press 1 to show solutions");
            System.out.println("Press 2 to show all tries");

            int choice = sc.nextInt();
            while (choice != 1 && choice != 2) {
                System.out.println("Press 1 to show solutions");
                System.out.println("Press 2 to show all tries");
                choice = sc.nextInt();
            }

            PegSolitaireSolver2 pss = new PegSolitaireSolver2(BOARD_SIZE, BLANK_INIT_POS);

            while (true) {
                if (choice == 1)
                    pss.showSolutions();
                else
                    pss.showTries();

                System.out.println("Press 1 to show solutions");
                System.out.println("Press 2 to show all tries");
                System.out.println("Press 3 to enter another board");
                choice = sc.nextInt();
                if (choice == 3)
                    break;
            }
        }
    }

    public PegSolitaireSolver2(int BOARD_SIZE, int BLANK_INIT_POS) {
        this.BOARD_SIZE = BOARD_SIZE;
        solveBoard(BOARD_SIZE, BLANK_INIT_POS);
        BT.traverseTree();
    }

    public Comparator<BitSet> BitSetComparator() {
        return (p1, p2) -> {
            if (p1.size() > p2.size())
                return 1;
            else if (p1.size() < p2.size())
                return -1;
            else {
                int N = Math.max(p1.length(), p2.length());
                for (int i = N; i >= 0; i--)
                    if ((p1.get(i) ^ p2.get(i)) && p1.get(i))
                        return 1;
                    else if ((p1.get(i) ^ p2.get(i)) && p2.get(i))
                        return -1;
                return 0;
            }
        };
    }

    public void solveBoard(int BOARD_SIZE, int BLANK_INIT_POS) {

        // creation of the first BitSet to make the initial board
        BitSet boardBitSet = new BitSet();
        for (int i = 1; i <= BOARD_SIZE; i++)
            boardBitSet.set(i, true);
        boardBitSet.set(BLANK_INIT_POS, false);

        initialBoard = new BoardTree.Board(boardBitSet);
        BT = new BoardTree(initialBoard);
        memory = new TreeSet<>(BitSetComparator());
        memory.add(boardBitSet);

        solveBoard(initialBoard);
    }

    public void solveBoard(BoardTree.Board node) {

        BoardTree.Board tryBoard;
        BitSet tryBitSet;

        // tries moving all pegs to the right of the board
        for (int i = 3; i <= BOARD_SIZE; i++) {
            if (validateMove(node.getBoardBitSet(), i, RIGHT)) {
                tryBitSet = (BitSet) node.getBoardBitSet().clone();
                move(tryBitSet, i, RIGHT);
                tryBoard = new BoardTree.Board(tryBitSet);
                if (!memory.contains(tryBoard.getBoardBitSet())) {
                    node.addChild(tryBoard);
                    memory.add(tryBoard.getBoardBitSet());
                    solveBoard(tryBoard);
                }
            }
        }
        // tries moving all pegs to the left of the board
        for (int i = BOARD_SIZE - 2; i >= 1; i--) {
            if (validateMove(node.getBoardBitSet(), i, LEFT)) {
                tryBitSet = (BitSet) node.getBoardBitSet().clone();
                move(tryBitSet, i, LEFT);
                tryBoard = new BoardTree.Board(tryBitSet);
                if (!memory.contains(tryBoard.getBoardBitSet())) {
                    node.addChild(tryBoard);
                    memory.add(tryBoard.getBoardBitSet());
                    solveBoard(tryBoard);
                }
            }
        }
    }

    private boolean validateMove(BitSet board, int pos, boolean direction) {
        if (direction == LEFT) {
            return board.get(pos)
                    && board.get(pos + 1) && !board.get(pos + 2);
        } else {
            return board.get(pos)
                    && board.get(pos - 1) && !board.get(pos - 2);
        }
    }

    private void move(BitSet board, int pos, boolean direction) {
        if (direction == LEFT) {
            board.set(pos, false);
            board.set(pos + 1, false);
            board.set(pos + 2, true);
        } else {
            board.set(pos, false);
            board.set(pos - 1, false);
            board.set(pos - 2, true);
        }
    }

    public void showSolutions() {
        BoardTree.showCompleteSolution(BT.getTreeInArrayList(), BOARD_SIZE);
    }

    public void showTries() {
        BoardTree.showAllSolution(BT.getTreeInArrayList(), BOARD_SIZE);
    }
}
