package com.DFSExercise;

public class SolveSudoku {
    public static void main(String[] args){
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        //PrintBoard(board);
        solveSudoku(board);
    }
    public static void solveSudoku(char[][] board) {
        backTrack(board);
        PrintBoard(board);
    }
    //这里是二位递归，需要二维信息确定棋盘的一个位置
    public static boolean backTrack(char[][] board) {
        for (int i=0; i<board.length; ++i) {
            for (int j=0; j<board[0].length; ++j) {
                if (board[i][j] != '.') {
                    continue;
                }
                for (char k='1'; k<='9'; ++k){
                    if (isValid(board,i, j, k)){
                        board[i][j] = k;
                        if (backTrack(board)) {
                            return true;
                        }
                        board[i][j] = '.';
                    }

                }
                return false;
            }
        }
        return true;
    }
//    public static Boolean backtracking(char[][] board){
//        for(int i=0;i<9;i++){
//            for(int j=0;j<9;j++){
//                if(board[i][j]!='.') continue;
//                for(char k='1';k<='9';k++){  //尝试放置9个数字
//                    if(isValid(board,i,j,k)){
//                        board[i][j]=k;
//                        if(backtracking(board)) return true;
//                        board[i][j]='.';
//                    }
//                }
//                return false;
//            }
//        }
//        return true;
//    }
//public static boolean isValid(int row, int col, int k, char[][] board) {
//    for (int i=0; i<board.length; i++) {
//        if (board[i][col] == k) {
//            return false;
//        }
//    }
//
//    for (int j=0; j<board[0].length; ++j) {
//        if (board[row][j] == k) {
//            return false;
//        }
//    }
//
//    // 判断一个九宫内是否有重复的值
//    int startIndexRow = (row / 3) * 3;
//    int startIndexCol = (col / 3) * 3;
//    for (int i=startIndexRow; i<startIndexRow+3; ++i) {
//        for (int j=startIndexCol; j<startIndexCol+3; ++j) {
//            if (board[i][j] == k) {
//                return false;
//            }
//        }
//    }
//    return true;
//
//}
    //自己写的判断逻辑有问题
    public static boolean isValid(char[][] board,int row,int col,char k){
        //查看一行是否冲突
        for(int i=0;i<9;i++){
            if(board[row][i]==k) return false;
            else continue;
        }
        //查看一列是否有冲突
        for(int i=0;i<9;i++){
            if(board[i][col]==k) return false;
            else continue;
        }
        //查看九宫格是否冲突，这一块忘记*3了，麻了
        for(int i=(row/3)*3;i<(row/3)*3+3;i++)
            for(int j=(col/3)*3;j<(col/3)*3+3;j++){
                if(board[i][j]==k) return false;
                else continue;
            }
        return true;
    }
    public static void PrintBoard(char[][] board){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++)
              System.out.print(board[i][j]+" ");
            System.out.println();
        }
    }
}
