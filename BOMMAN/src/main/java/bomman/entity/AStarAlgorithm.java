package bomman.entity;

import java.util.*;
import static java.lang.Math.abs;

class Cell {
	// to hold the parent of current cell
	int parent_i, parent_j;
	int f_value, g_value, h_value;

	Cell(int f, int g, int h, int pi, int pj) {
		this.f_value = f;
		this.g_value = g;
		this.h_value = h;
		this.parent_i = pi;
		this.parent_j = pj;
	}
}


// class to store the f value and the indices in the stack
class pPair {
	int i, j;
	int f;

	pPair(int f_value, int i_value, int j_value) {
		this.f = f_value;
		this.i = i_value;
		this.j = j_value;
	}
}

// class to store cell indices
class pair {
	int i, j;

	pair(int i_value, int j_value) {
		this.i = i_value;
		this.j = j_value;
	}
}

public class AStarAlgorithm {
	public boolean foundDestination = false;

	public ArrayList<CommonEntity.DIRECTION> pathToDes = new ArrayList<>();


	public AStarAlgorithm() {
		this.foundDestination = false;

	}

	public boolean isValid(pair obj, int grid[][]) {
		int i_value = obj.i, j_value = obj.j;
		int row = grid.length, col = grid[0].length;
		if (i_value < 0 || i_value >= row || j_value < 0 || j_value >= col)
			return false;
		return true;
	}

	public boolean isValid(int i, int j, int grid[][]) {
		int i_value = i, j_value = j;
		int row = grid.length, col = grid[0].length;
		if (i_value < 0 || i_value >= row || j_value < 0 || j_value >= col)
			return false;
		return true;
	}

	public boolean isDestination(int i, int j, pair destination) {
		return (i == destination.i && j == destination.j);
	}

	public int manhattan_Distance(int row, int col, pair dest) {
		return abs(row - dest.i) + abs(col - dest.j);
	}

	public void tracePath(Cell cellDetails[][], pair destination) {
		// tracing the path
        // cellDetailsPrint(cellDetails);
		int row_des = destination.i;
		int col_des = destination.j;
		Stack<pair> tracePath = new Stack<pair>();
		while (!(cellDetails[row_des][col_des].parent_i == row_des
				&& cellDetails[row_des][col_des].parent_j == col_des)) {
			/**
			 * idea : track từ destination về source theo cell Parent
			 */
			tracePath.push(new pair(row_des, col_des));
			int temp_row = cellDetails[row_des][col_des].parent_i;
			int temp_col = cellDetails[row_des][col_des].parent_j;
			row_des = temp_row;
			col_des = temp_col;
		}
		// push the starting cell's i and j
		tracePath.push(new pair(row_des, col_des));

		// print the stack trace
		int prevX = -1, prevY = -1, curX , curY;
		while (!tracePath.isEmpty()) {
			pair p = tracePath.pop();
			curX = p.i;
			curY = p.j;
			if(prevX != -1) {
				if(curX - prevX == 1){
					pathToDes.add(CommonEntity.DIRECTION.DOWN);
				}
				else if(curX - prevX == -1){
					pathToDes.add(CommonEntity.DIRECTION.UP);
				}
				else if(curY - prevY == 1){
					pathToDes.add(CommonEntity.DIRECTION.RIGHT);
				}
				else if(curY - prevY == -1){
					pathToDes.add(CommonEntity.DIRECTION.LEFT);
				}
			}
			prevX = p.i;
			prevY = p.j;
		}
	}

	public boolean isUnblocked(int[][] grid, int i, int j) {
		return (grid[i][j] == 0 || grid[i][j] == 4 || grid[i][j] == 5 || grid[i][j] == 6 || grid[i][j] == 7);
	}

	public void successorTrace(int addDistance, int original_i, int original_j, PriorityQueue<pPair> openList, int i, int j,
	                           int[][] grid, pair destination, Cell[][] cellDetails, boolean[][] closedList) {
		// check for the valid i j and cell blocker
		if (isValid(i, j, grid)) {

			int gNew = cellDetails[original_i][original_j].g_value + addDistance;
			int hNew = manhattan_Distance(i, j, destination);
			int fNew = gNew + hNew;
			// if destination is same as this cell
			if (isDestination(i, j, destination)) {
				cellDetails[i][j].parent_i = original_i;
				cellDetails[i][j].parent_j = original_j;
				System.out.println("Destination is found");
				tracePath(cellDetails, destination);
				foundDestination = true;
			}
			// check if the successor is already chosen in some other path
			else if (!closedList[i][j] && isUnblocked(grid, i, j)) {

				// if the cell is not in the openList, add it with appropriate cellDetails set
				// OR if it is already in the openList, check if the new fValue is smaller
				if (cellDetails[i][j].f_value == Integer.MAX_VALUE || fNew < cellDetails[i][j].f_value) {
					openList.add(new pPair(fNew, i, j));

					// update the cell details
					cellDetails[i][j].f_value = fNew;
					cellDetails[i][j].g_value = gNew;
					cellDetails[i][j].h_value = hNew;
					cellDetails[i][j].parent_i = original_i;
					cellDetails[i][j].parent_j = original_j;
				}
			}
		}

	}
	public void cellDetailsPrint(Cell cellDetails[][]) {
		int row = cellDetails.length;
		int col = cellDetails[0].length;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(cellDetails[i][j].f_value == Integer.MAX_VALUE)
					System.out.print(10 + "\t");
				else
					System.out.print(cellDetails[i][j].f_value + "\t");
			}
			System.out.println();
		}
	}
	public void aStarSearch(int grid[][], pair source, pair destination) {
		// checking the corner cases
		// 1. source or destination is outside the given range
		if (!isValid(source, grid) || !isValid(destination, grid) || !isUnblocked(grid, source.i, source.j)
				|| !isUnblocked(grid, destination.i, destination.j)) {
			System.out.println("Out of range or Inaccessible");
		}
		// if destination is same as source
		if (isDestination(source.i, source.j, destination)) {
			return;
		}

		int row = grid.length;
		int col = grid[0].length;

		// graph traversal visited matrix
		boolean[][] closedList = new boolean[row][col];

		// 2d array of cell details, assign to Integer MAX value and parents to -1
		// initially

		Cell[][] cellDetails = new Cell[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				cellDetails[i][j] = new Cell(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, -1, -1);
			}
		}
		// Initializing the parameter of the starting node
		int i = source.i;
		int j = source.j;
		cellDetails[i][j].f_value = 0;
		cellDetails[i][j].g_value = 0;
		cellDetails[i][j].h_value = 0;
		cellDetails[i][j].parent_i = i;
		cellDetails[i][j].parent_j = j;

		// An open list, act as stack to insert the nodes that trace the path
		// Set<pPair> openList = new LinkedHashSet<pPair>();
		PriorityQueue<pPair> openList = new PriorityQueue<pPair>((node0, node1) -> Integer.compare(node0.f, node1.f));

		openList.add(new pPair(0, i, j));
		while (!openList.isEmpty()) {
			pPair node = openList.iterator().next();
			// get the i and j and make this cell, a visited one
			i = node.i;
			j = node.j;
			openList.remove(node);
			closedList[i][j] = true;
			// int gNew = 0, hNew = 0, fNew = 0;
			// 4 successors check
			if (!foundDestination)
				successorTrace(grid[i][j], i, j, openList, i - 1, j, grid, destination, cellDetails, closedList
				);// North
			if (!foundDestination)
				successorTrace(grid[i][j], i, j, openList, i + 1, j, grid, destination, cellDetails, closedList
				);// south
			if (!foundDestination)
				successorTrace(grid[i][j], i, j, openList, i, j + 1, grid, destination, cellDetails, closedList
				);// east
			if (!foundDestination)
				successorTrace(grid[i][j], i, j, openList, i, j - 1, grid, destination, cellDetails, closedList
				);// west
			if(foundDestination) break;

		}
		if(foundDestination)
			tracePath(cellDetails, destination);
	}

	public void aStarSearch(int grid[][], int startX, int startY, int endX, int endY){
		foundDestination = false;
		pathToDes.clear();
		pair st = new pair(startX, startY);
		pair end = new pair(endX, endY);
		aStarSearch(grid, st, end);
	}

	public ArrayList getPathToDes() {
		return this.pathToDes;
	}
}
