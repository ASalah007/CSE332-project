import solution2.BoardSet;

import java.util.*;

public class BoardTree {
    private final Board head;
    ArrayList<ArrayList<Board>> solutionAL;

    public static void main(String[] args) {

    }

    static class Board {
        private final ArrayList<Integer> boardAL;
        private final long boardINT;
        private final ArrayList<Board> children;

        public Board(ArrayList<Integer> boardAL) {
            this.boardAL = new ArrayList<>(boardAL);
            boardINT = getBoardInNum(this.boardAL);
            children = new ArrayList<>();
        }

        public ArrayList<Integer> getBoardAL() {
            return boardAL;
        }

        public long getBoardINT() {
            return boardINT;
        }

        public ArrayList<Board> getChildren() {
            return children;
        }

        public void addChild(Board b) {
            this.children.add(b);
        }

        private static long setBitOne(long num, int pos) {
            return num | (1L << pos);
        }

        private static long setBitZero(long num, int pos) {
            return num & ~(1L << pos);
        }

        // not needed
        private static long getBoardInNum(int BOARD_SIZE, int blank_pos) {
            long num = 0;
            for (int i = 1; i <= BOARD_SIZE; i++) {
                num |= (1L << i);
            }
            num &= ~(1L << blank_pos);

            return num;
        }

        private static long getBoardInNum(ArrayList<Integer> integerAL) {
            long num = 0;
            for (int i = 1; i < integerAL.size(); i++) {
                if (integerAL.get(i) == 1) {
                    num |= (1L << i);
                }
            }
            return num;
        }

        private static ArrayList<Integer> getBoardInArrayList(long num, int BOARD_SIZE) {
            ArrayList<Integer> intAL = new ArrayList<>();
            int temp = 0;

            intAL.add(0, temp);
            for (int i = 1; i <= BOARD_SIZE; i++) {
                temp = (int) ((num & (1 << i)) >> i);
                intAL.add(i, temp);
            }
            return intAL;
        }

        public static void showBoard(Board b) {
            for (int i = 1;  i < b.boardAL.size(); i++) {
                System.out.print(b.boardAL.get(i) + "\t");
            }
        }
    }

    public BoardTree(Board head) {
        this.head = head;
    }

    public BoardTree(ArrayList<Integer> bAL) {
        this.head = new Board(bAL);
    }

    public Board getHead() {
        return head;
    }

    public ArrayList<ArrayList<Board>> getSolutionAL() {
        solutionAL = new ArrayList<>();
        ArrayList<Board> tempBoardsQueueMain = new ArrayList<>();
        Board chainingBoard = head;

        tempBoardsQueueMain.add(chainingBoard);
        if (chainingBoard.children.size() != 0) {
            getSolutionAL(chainingBoard.children.get(0), new ArrayList<>(tempBoardsQueueMain));
        }
        for (int i = 1; i < chainingBoard.children.size(); i++) {
            getSolutionAL(chainingBoard.children.get(i), new ArrayList<>(tempBoardsQueueMain));
        }
        return solutionAL;
    }

    public void getSolutionAL(Board node, ArrayList<Board> queueMain) {

        queueMain.add(node);
        if (node.children.size() != 0) {
            getSolutionAL(node.children.get(0), new ArrayList<>(queueMain));
        } else {
            solutionAL.add(queueMain);
        }
        for (int i = 1; i < node.children.size(); i++) {
            getSolutionAL(node.children.get(i), new ArrayList<>(queueMain));
        }
    }

    public static void showAllSolution(ArrayList<ArrayList<Board>> ArrArrBoard) {
        for (ArrayList<Board> ArrBoard : ArrArrBoard) {
            for (Board b : ArrBoard) {
                Board.showBoard(b);
                System.out.println();
            }
        }
    }

    public static void showCompleteSolution(ArrayList<ArrayList<Board>> arrArrBoard, int target) {
        int solutionNo = 0;
        int largestArrBoard = 0;
        for (ArrayList<Board> arrBoard : arrArrBoard) {
            if (arrBoard.size() == target) {
                System.out.println("//****************************************************//");
                System.out.println("Solution number: " + ++solutionNo);
                System.out.println();
                int i = 0;
                for (Board b : arrBoard) {
                    System.out.print("Step " + i++ + " : ");
                    Board.showBoard(b);
                    System.out.println();
                }
                System.out.println("//****************************************************//");
            }
            if (arrBoard.size() > largestArrBoard){
                largestArrBoard = arrBoard.size();
            }
        }
        if (solutionNo == 0) {
            System.out.println("Unsolvable Board");
            System.out.println("Largest tries : ");
            for (ArrayList<Board> arrBoard : arrArrBoard) {
                if (arrBoard.size() == largestArrBoard) {
                    System.out.println("//****************************************************//");
                    System.out.println("Largest try number: " + ++solutionNo);
                    System.out.println();
                    int i = 0;
                    for (Board b : arrBoard) {
                        System.out.print("Step " + i++ + " : ");
                        Board.showBoard(b);
                        System.out.println();
                    }
                    System.out.println("Unsolvable Board");
                    System.out.println("//****************************************************//");
                    System.out.println();
                }
            }
        }
    }
}