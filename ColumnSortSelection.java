/*=============================================================================
 |       Author:  Chance Krueger chancekrueger@arizona.edu
 |     Language:  Java
 |     Packages:  No packages.
 |  Compile/Run:  Eclipse:  Compile: Create a new Java Project; add this class
 |                                   to the 'src' folder; click 'Save'
 |                             Run: Right-click the file; select 
 |                                   'Run As' > 'Java Application'
 |                  JDK:     Compile: javac ColumnSortSelection.java
 |                             Run: java ColumnSortSelection
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  The program implements a structured sorting mechanism that uses 
 |					selection sort and column sort algorithms to organize integer 
 |					values in a 2D array. The input is a text file containing integers, 
 |					which are read and arranged into a matrix form. This matrix is 
 |					then sorted through multiple passes, using both selection sort 
 |					for column ordering and column sort for enhanced structure within 
 |					columns, aligning with specific steps in a sorting sequence.
 |
 |					The selection sort is employed to achieve initial order by 
 | 					repeatedly identifying the smallest values in each column and 
 |					arranging them in sequence, providing a base organization. In 
 |					later steps, a column sort inspired by Leighton’s parallel sorting 
 |					model is applied, particularly suited to managing data within the 
 |					program’s matrix format.
 |
 |					This approach combines efficient sorting of large column-oriented 
 |					data sets, offering a balance of simplicity and effectiveness for 
 |					structured matrix sorting. The final result displays the sorted 
 |					integers in a structured format, along with additional information 
 |					on the matrix dimensions and processing time.
 |                
 |        Input:  Type into the console of a source file, that file
 |					being: One integer per line, if there is a non-Integer,
 |					the program will give a message and terminate.
 |
 |       Output:  Into the console, prints out: 
 |					n: representing n Numbers of items to sort.
 |					r: representing r Rows in the matrix being created.
 |					s: representing s columns in the matrix being created.
 |				  It also outputs into the console the file but in sorted order.
 |				  One integer per line.
 |					
 |
 |   Techniques:  The primary algorithm used in this program is selection sort, 
 |					chosen for its simplicity and efficiency among basic sorting 
 |					methods. Selection sort has predictable O(n²) complexity, which,
 |					while not optimal for large datasets, offers manageable in-place 
 |					sorting without additional memory allocation. This makes it 
 |					suitable for the initial ordering of data, where early moves can 
 |					minimize unnecessary reorganizations in later steps.
 |
 |					Selection sort is used in steps 1, 3, 5, and 7 to sort columns in 
 |					Column-Major Order (CMO). By focusing on this ordering first, the 
 |					program establishes an organized base structure. This choice is 
 |					particularly effective as it allows for consistent O(n) data moves, 
 |					helping streamline the early stages of data sorting.
 |
 | 					For steps 2, 4, 6, and 8, the program utilizes a basic column sort 
 |					inspired by Leighton’s 1984 paper on efficient parallel sorting 
 |					(http://doi.acm.org/10.1145/800057.808667). Column sort is well-suited 
 |					for block-oriented data and complements selection sort by systematically
 | 					arranging data within columns, enhancing structure and readability 
 |					for later access.
 |
 | 					The combination of selection and column sort balances simplicity with 
 |					structured data handling, providing a straightforward yet effective 
 |					approach to data organization. This technique achieves efficient data 
 |					sorting suitable for the program’s needs, with each algorithm chosen 
 |					for its strengths in the overall sorting process.
 |
 |   Required Features Not Included:  All required features are included.
 |                 
 |
 |   Known Bugs:  None; the program operates correctly.
 |
 *===========================================================================*/

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * --------------------------------------------------------- Class: ColumnSortSelection
 *
 * Purpose: This class is designed to handle sorting operations on a 2D array of
 * integers. It includes methods for reading integer data from a file,
 * calculating the appropriate number of rows and columns for the array, and
 * performing various sorting steps, including selection sort and stepwise
 * transformations on the array data.
 *
 * The class manages the state of the 2D array, including operations to build
 * the array from a list of integers, as well as functionality to print the
 * array and the elapsed time of sorting operations.
 *
 * Fields: - Integer[][] array2D: A 2D array of integers to be sorted. -
 * Integer[][] array2dCopy: A copy of the original 2D array for reference during
 * sorting. - int numItems: The total number of items read from the input file.
 * - int ROWS: The number of rows in the 2D array. - int COLS: The number of
 * columns in the 2D array. - Integer[][] sortCol: A temporary storage for
 * column-based sorting.
 *
 * Author: Chance Krueger Date: 10/30/2024
 * ---------------------------------------------------------
 */
public class ColumnSortSelection {

	final static double BILLION = 1000000000.0; // Number of nanoseconds in one second
	private static long startTime; // Start time for elapsed time calculations
	private static long elapsedTime; // Total elapsed time in nanoseconds
	private static double seconds; // Elapsed time in seconds

	private Integer[][] array2D; // 2D array for storing integer values
	private Integer[][] array2dCopy; // Copy of the original 2D array for sorting

	private int numItems; // Total number of items read from the file
	private int ROWS; // Number of rows in the 2D array(also known as r)
	private int COLS; // Number of columns in the 2D array(also known as s)

	/*--------------------------------------------------- ColumnSortSelection -----
	 |  Constructor ColumnSortSelection
	 |
	 |  Purpose:  This constructor initializes a new instance of the 
	 |      ColumnSortSelection class by setting the provided 2D array, number of 
	 |      items, number of rows, and number of columns. It also 
	 |      creates a copy of the original 2D array for internal 
	 |      manipulation during sorting operations.
	 |
	 |  Pre-condition: The input parameters must be valid and 
	 |      appropriately sized; the `array2D` should not be null.
	 |
	 |  Post-condition: The instance variables `array2D`, `numItems`, 
	 |      `ROWS`, `COLS`, and `array2dCopy` are initialized with 
	 |      the provided values.
	 |
	 |  Parameters: 
	 |      Integer[][] array2D - a 2D array of integers to be 
	 |          sorted and manipulated.
	 |      int items - the total number of items present in the 
	 |          array.
	 |      int row - the number of rows in the 2D array.
	 |      int col - the number of columns in the 2D array.
	 *
	 *-------------------------------------------------------------------*/
	public ColumnSortSelection(Integer[][] array2D, int items, int row, int col) {
		setArray2D(array2D);
		setNumItems(items);
		setRows(row);
		setCols(col);
		this.array2dCopy = this.array2D;
	}

	/*--------------------------------------------------- getArray2D -----
	 |  Method: getArray2D
	 |
	 |  Purpose:  This method retrieves the current 2D array of integers 
	 |      stored in the instance. It allows access to the internal 
	 |      representation of the array without modifying it.
	 |
	 |  Pre-condition: The method assumes that the array has been 
	 |      initialized and is not null.
	 |
	 |  Post-condition: The original 2D array remains unchanged after 
	 |      this method is called.
	 |
	 |  Returns: 
	 |      Integer[][] - the current 2D array of integers.
	 *-------------------------------------------------------------------*/
	public Integer[][] getArray2D() {
		return this.array2D;
	}

	/*--------------------------------------------------- getNumItems -----
	 |  Method: getNumItems
	 |
	 |  Purpose:  This method retrieves the total number of items 
	 |      stored in the instance. It provides a way to access 
	 |      the count of items without modifying the underlying 
	 |      data.
	 |
	 |  Pre-condition: The method assumes that the numItems variable 
	 |      has been initialized and represents the correct count.
	 |
	 |  Post-condition: The original value of numItems remains 
	 |      unchanged after this method is called.
	 |
	 |  Returns: 
	 |      int - the total number of items in the instance.
	 *-------------------------------------------------------------------*/
	public int getNumItems() {
		return this.numItems;
	}

	/*--------------------------------------------------- getRows -----
	 |  Method: getRows
	 |
	 |  Purpose:  This method retrieves the number of rows in the 
	 |      two-dimensional array managed by this instance. It provides 
	 |      a way to access the row count without altering the 
	 |      underlying data structure.
	 |
	 |  Pre-condition: The method assumes that the ROWS variable 
	 |      has been initialized and reflects the correct number of rows.
	 |
	 |  Post-condition: The original value of ROWS remains 
	 |      unchanged after this method is called.
	 |
	 |  Returns: 
	 |      int - the number of rows in the two-dimensional array.
	 *-------------------------------------------------------------------*/
	public int getRows() {
		return this.ROWS;
	}

	/*--------------------------------------------------- getCols -----
	 |  Method: getCols
	 |
	 |  Purpose:  This method retrieves the number of columns in the 
	 |      two-dimensional array managed by this instance. It provides 
	 |      a way to access the column count without modifying the 
	 |      underlying data structure.
	 |
	 |  Pre-condition: The method assumes that the COLS variable 
	 |      has been initialized and reflects the correct number of columns.
	 |
	 |  Post-condition: The original value of COLS remains 
	 |      unchanged after this method is called.
	 |
	 |  Returns: 
	 |      int - the number of columns in the two-dimensional array.
	 *-------------------------------------------------------------------*/
	public int getCols() {
		return this.COLS;
	}

	/*--------------------------------------------------- setArray2D -----
	 |  Method: setArray2D
	 |
	 |  Purpose:  This method sets the two-dimensional array for 
	 |      this instance. It allows external code to update the 
	 |      internal array structure managed by this class.
	 |
	 |  Pre-condition: The input parameter must not be null and should 
	 |      represent a valid two-dimensional array of integers.
	 |
	 |  Post-condition: The internal array2D is updated to reference 
	 |      the provided array, allowing future operations to use 
	 |      the new data.
	 |
	 |  Parameters:
	 |      Integer[][] array2D - the new two-dimensional array to 
	 |      set for this instance.
	 *-------------------------------------------------------------------*/
	public void setArray2D(Integer[][] array2D) {
		this.array2D = array2D;
	}

	/*--------------------------------------------------- setNumItems -----
	 |  Method: setNumItems
	 |
	 |  Purpose:  This method sets the number of items for this 
	 |      instance. It allows external code to update the 
	 |      count of items managed by this class.
	 |
	 |  Pre-condition: The input parameter must be a non-negative 
	 |      integer, as it represents the count of items.
	 |
	 |  Post-condition: The internal numItems variable is updated 
	 |      to reflect the new count of items.
	 |
	 |  Parameters:
	 |      int items - the new number of items to set for this 
	 |      instance.
	 *-------------------------------------------------------------------*/
	public void setNumItems(int items) {
		this.numItems = items;
	}

	/*--------------------------------------------------- setRows ---------
	 |  Method: setRows
	 |
	 |  Purpose:  This method sets the number of rows for this 
	 |      instance. It allows external code to update the 
	 |      row count used in the context of the 2D array.
	 |
	 |  Pre-condition: The input parameter must be a non-negative 
	 |      integer, as it represents the count of rows.
	 |
	 |  Post-condition: The internal ROWS variable is updated 
	 |      to reflect the new row count.
	 |
	 |  Parameters:
	 |      int row - the new number of rows to set for this 
	 |      instance.
	 *-------------------------------------------------------------------*/
	public void setRows(int row) {
		this.ROWS = row;
	}

	/*--------------------------------------------------- setCols ---------
	 |  Method: setCols
	 |
	 |  Purpose:  This method sets the number of columns for this 
	 |      instance. It allows external code to update the 
	 |      column count used in the context of the 2D array.
	 |
	 |  Pre-condition: The input parameter must be a non-negative 
	 |      integer, as it represents the count of columns.
	 |
	 |  Post-condition: The internal COLS variable is updated 
	 |      to reflect the new column count.
	 |
	 |  Parameters:
	 |      int col - the new number of columns to set for this 
	 |      instance.
	 *-------------------------------------------------------------------*/
	public void setCols(int col) {
		this.COLS = col;
	}

	/*--------------------------------------------------- selectionSort -----
	 |  Method selectionSort 
	 |
	 |  Purpose:  This method sorts the odd-indexed columns (1, 3, 5, 7) 
	 |      of a 2D array using the selection sort algorithm. It extracts 
	 |      each specified column into a temporary array, sorts that array, 
	 |      and then updates the original array with the sorted values.
	 |
	 |  Pre-condition: The 2D array has been initialized with a specified 
	 |      number of rows and columns. The array must contain Integer values.
	 |
	 |  Post-condition: The specified odd-indexed columns of the 2D array 
	 |      are sorted, while even-indexed columns remain unchanged.
	 |
	 |  Parameters: 
	 |      None
	 |
	 |  Returns:  None (the original array is modified in place)
	 *---------------------------------------------------------------------*/
	public void selectionSort() {

		int ColAt = 0; // Index to track the current column being sorted

		for (int col = 0; col < this.COLS; col++) {

			Integer[] temp = new Integer[this.ROWS]; // Temporary array to hold the current column's values

			// Populate the temporary array with values from the current column
			for (int row = 0; row < this.ROWS; row++) {
				temp[row] = this.array2D[row][col];
			}

			// Sort the values in the temporary array and place them back into the original
			// array
			selectRow(temp, ColAt);

			ColAt++; // Move to next column
		}

		this.array2D = this.array2dCopy.clone(); // Clone the sorted copy back to the original array
	}

	/*--------------------------------------------------- selectRow -----
	 |  Method selectRow 
	 |
	 |  Purpose:  This method performs a selection sort on a provided 
	 |      array of integers. It iteratively finds the minimum value 
	 |      in the unsorted portion of the array and swaps it with the 
	 |      current index, effectively sorting the array in ascending 
	 |      order. The sorted values are then stored in a copy of the 
	 |      original 2D array at the specified column index.
	 |
	 |  Pre-condition: The input array (temp) must be initialized and 
	 |      contain Integer values. The colAt parameter should be a valid 
	 |      index for the destination 2D array (array2dCopy).
	 |
	 |  Post-condition: The provided array (temp) is sorted in ascending 
	 |      order, and the sorted values are copied to the specified column 
	 |      of the array2dCopy.
	 |
	 |  Parameters: 
	 |      temp (IN) -- an array of Integer values to be sorted
	 |      colAt (IN) -- the index of the column in array2dCopy where 
	 |          the sorted values will be stored
	 |
	 |  Returns:  None (the array2dCopy is modified in place)
	 *---------------------------------------------------------------------*/
	private void selectRow(Integer[] temp, int colAt) {

		// Iterate through the temporary array to perform selection sort
		for (int index = 0; index < temp.length; index++) {

			int min = temp[index]; // Initialize the minimum value with the current index's value
			int switchNum = index; // Track the index of the minimum value found

			// Find the minimum value in the unsorted portion of the array
			for (int checkAt = index + 1; checkAt < temp.length; checkAt++) {
				if (temp[checkAt] < min) {
					min = temp[checkAt];
					switchNum = checkAt;
				}
			}

			// Swap the found minimum element with the element at the current index
			int tempNum = temp[index];
			temp[index] = temp[switchNum];
			temp[switchNum] = tempNum;
		}

		// Update the original array copy with the sorted values from the temporary
		// array
		for (int row = 0; row < this.ROWS; row++) {
			this.array2dCopy[row][colAt] = temp[row];
		}
	}

	/*--------------------------------------------------- step2OfSort -----
	 |  Method step2OfSort 
	 |
	 |  Purpose:  This method processes a 2D array of integers by dividing 
	 |      it into columns and then performing a sorting step on each 
	 |      column. The method collects the integer values from each column, 
	 |      sorts them using a helper method, and then updates a copy of 
	 |      the original array with the sorted values.
	 |
	 |  Pre-condition: The 2D array (array2D) must be initialized and 
	 |      populated with Integer values. The numItems and COLS should 
	 |      be set to valid values such that numItems is divisible by COLS.
	 |
	 |  Post-condition: The original 2D array (array2D) is updated with 
	 |      sorted values based on the specified sorting operation, 
	 |      where each column has been processed and sorted independently.
	 |
	 |  Parameters:  None (the method operates on instance variables)
	 |
	 |  Returns:  None (the array2D is modified in place)
	 *---------------------------------------------------------------------*/
	private void step2OfSort() {

		int amountNumsInEach = this.numItems / this.COLS;

		int rowAt = 0; // Keeps track of the current row position for updating array

		for (int col = 0; col < this.COLS; col++) {

			Integer[] temp = new Integer[amountNumsInEach]; // Temporary array for the current column

			// Collect all values from the current column into the temporary array
			for (int row = 0; row < this.ROWS; row++) {
				temp[row] = this.array2D[row][col]; // Fill temporary array with column values
			}

			// Sort the temporary array using a helper method
			Integer[][] tempArray = step2SortHelper(temp);

			int indexArray = 0; // Index for tracking position in sorted tempArray

			// Update the copy of the original array with sorted values
			for (int row = rowAt; row < rowAt + (this.ROWS / this.COLS); row++) {

				this.array2dCopy[row] = tempArray[indexArray];
				indexArray++; // Next index in the sorted array
			}

			// Increment rowAt to point to next block of rows to be updated
			rowAt += this.ROWS / this.COLS;

		}

		// Clone the updated copy back to the original array
		this.array2D = this.array2dCopy.clone();

	}

	/*--------------------------------------------------- step2SortHelper -----
	 |  Method step2SortHelper 
	 |
	 |  Purpose:  This method takes a 1D array of integers and organizes 
	 |      it into a 2D array format. The method populates a new 2D array 
	 |      with values from the input array, structured with a specified 
	 |      number of rows and columns.
	 |
	 |  Pre-condition: The input array must be initialized and must contain 
	 |      enough elements to fill the resulting 2D array. The dimensions 
	 |      of the resulting array are determined by the number of rows 
	 |      (ROWS / COLS) and the number of columns (COLS).
	 |
	 |  Post-condition: A 2D array is returned, populated with the values 
	 |      from the input array, organized into the specified number of 
	 |      rows and columns.
	 |
	 |  Parameters:
	 |      array (IN) -- a 1D array of Integer values to be organized 
	 |      into a 2D array.
	 |
	 |  Returns:  A 2D array (tempArray) containing values from the input 
	 |      array, arranged in a structured format.
	 *---------------------------------------------------------------------*/
	private Integer[][] step2SortHelper(Integer[] array) {

		int index = 0;

		// Create a new 2D array with dimensions based on the r/s x s
		Integer[][] tempArray = new Integer[this.ROWS / this.COLS][this.COLS];

		for (int row = 0; row < tempArray.length; row++) {
			for (int col = 0; col < tempArray[0].length; col++) {
				tempArray[row][col] = array[index]; // Assign value from input array to 2D array
				index++;
			}
		}
		return tempArray;
	}

	/*--------------------------------------------------- step4Sort -----
	 |  Method step4Sort 
	 |
	 |  Purpose:  This method reorganizes the elements of a 2D array into 
	 |      a single 1D ArrayList and then repopulates the 2D array with 
	 |      those elements in a column-major order. This ensures that the 
	 |      elements are arranged sequentially from top to bottom, column by 
	 |      column.
	 |
	 |  Pre-condition: The 2D array (array2D) must be initialized and must 
	 |      contain a defined number of rows (ROWS) and columns (COLS) filled 
	 |      with Integer values.
	 |
	 |  Post-condition: The original 2D array is updated with values from 
	 |      the ArrayList, maintaining the same dimensions but altering the 
	 |      arrangement of the elements according to the specified order.
	 |
	 |  Parameters: 
	 |      None
	 |
	 |  Returns:  None. The method modifies the instance's 2D array in place.
	 *---------------------------------------------------------------------*/
	private void step4Sort() {

		ArrayList<Integer> arrayList = new ArrayList<Integer>();

		// Flatten the 2D array into a 1D ArrayList
		for (int row = 0; row < this.ROWS; row++) {
			for (int col = 0; col < this.COLS; col++) {
				arrayList.add(this.array2D[row][col]);
			}

		}
		int index = 0;
		// Re-populate the 2D array in CMO
		for (int col = 0; col < this.COLS; col++) {
			for (int row = 0; row < this.ROWS; row++) {
				this.array2D[row][col] = arrayList.get(index);
				index++;
			}
		}
	}

	/*--------------------------------------------------- step6Sort -----
	 |  Method step6Sort 
	 |
	 |  Purpose:  This method organizes the elements of a 2D array into 
	 |      a single 1D ArrayList, then creates a new 2D array with an 
	 |      additional column. It fills this new array with the values from 
	 |      the ArrayList, placing Integer.MIN_VALUE in the first column 
	 |      (for the first half of the rows) and Integer.MAX_VALUE in the 
	 |      last column (for the second half of the rows). This setup is 
	 |      typically used to handle boundary conditions in sorting 
	 |      algorithms.
	 |
	 |  Pre-condition: The original 2D array (array2D) must be initialized 
	 |      and must contain a defined number of rows (ROWS) and columns 
	 |      (COLS) filled with Integer values.
	 |
	 |  Post-condition: A new 2D array (array2dCopy) is created and filled 
	 |      with values from the ArrayList, adjusting for the additional 
	 |      column and setting boundary values appropriately.
	 |
	 |  Parameters: 
	 |      None
	 |
	 |  Returns:  None. The method modifies the instance's 2D array copy 
	 |      in place.
	 *---------------------------------------------------------------------*/
	private void step6Sort() {

		ArrayList<Integer> arrayList = new ArrayList<Integer>();

		// Flatten the 2D array into the ArrayList
		for (int col = 0; col < this.COLS; col++) {
			for (int row = 0; row < this.ROWS; row++) {
				arrayList.add(this.array2D[row][col]);
			}

		}

		// Create a new 2D array with an additional column
		Integer[][] tempArray = new Integer[this.ROWS][this.COLS + 1];

		// Calculate the number of rows to be filled with Integer.MIN_VALUE/MAX_VALUE
		int infinities = tempArray.length - ((tempArray.length / 2) + (tempArray.length % 2));
		int index = 0; // Index to track the current position in the ArrayList

		// Populate the new 2D array
		for (int col = 0; col < tempArray[0].length; col++) {
			for (int row = 0; row < tempArray.length; row++) {

				// Fill the first column with Integer.MIN_VALUE for the first half of the rows
				if (col == 0 && row < this.ROWS / 2) {

					tempArray[row][col] = Integer.MIN_VALUE;

					// Fill the last column with Integer.MAX_VALUE for the second half of the rows
				} else if (col == tempArray[0].length - 1 && row >= infinities) {

					tempArray[row][col] = Integer.MAX_VALUE;

					// Fill the remaining cells with values from the ArrayList
				} else {

					tempArray[row][col] = arrayList.get(index);
					index++;

				}

			}
		}
		// Clone the new array into array2dCopy
		this.array2dCopy = tempArray.clone();

	}

	/*--------------------------------------------------- step7SelectionSort -----
	 |  Method step7SelectionSort 
	 |
	 |  Purpose:  This method performs a selection sort on the columns of 
	 |      the 2D array (array2dCopy). It iterates through each column, 
	 |      extracting the column values into a temporary array, then 
	 |      sorting those values using a helper method. The sorted 
	 |      values are placed back into a new temporary array, which 
	 |      ultimately replaces the original array2dCopy.
	 |
	 |  Pre-condition: The 2D array (array2dCopy) must be initialized 
	 |      and must contain a defined number of rows (ROWS) and columns 
	 |      (COLS) filled with Integer values.
	 |
	 |  Post-condition: The original 2D array copy (array2dCopy) is updated 
	 |      with sorted column values after the selection sort process is 
	 |      complete.
	 |
	 |  Parameters: 
	 |      None
	 |
	 |  Returns:  None. The method modifies the instance's 2D array copy 
	 |      in place.
	 *--------------------------------------------------------------------------*/
	private void step7SelectionSort() {

		Integer[][] tempArray = this.array2dCopy;

		int ColAt = 0;

		for (int col = 0; col < this.COLS; col++) {

			Integer[] temp = new Integer[this.ROWS]; // Temporary array for column values.

			// Extract the current column's values into the temporary array.
			for (int row = 0; row < this.ROWS; row++) {
				temp[row] = this.array2dCopy[row][col];
			}

			// Sort the temporary column values using a helper method and update the
			// tempArray.
			tempArray = selectRowHelper7(temp, ColAt, tempArray);

			ColAt++;
		}
		this.array2dCopy = tempArray.clone();
	}

	/*--------------------------------------------------- selectRowHelper7 -----
	 |  Method selectRowHelper7 
	 |
	 |  Purpose:  This method performs a selection sort on a temporary 
	 |      array of integers (temp) and updates a specified column 
	 |      (colAt) in a given 2D array (tempArray) with the sorted values. 
	 |      It identifies the minimum value in the array and swaps it 
	 |      with the current index until the array is sorted.
	 |
	 |  Pre-condition: The temporary array (temp) must be initialized 
	 |      and contain Integer values, and the column index (colAt) 
	 |      must be a valid index for the tempArray.
	 |
	 |  Post-condition: The specified column in tempArray is updated 
	 |      with the sorted values from the temporary array after the 
	 |      selection sort process is complete.
	 |
	 |  Parameters: 
	 |      temp (IN)       -- the array of integers to be sorted
	 |      colAt (IN)     -- the index of the column to update in tempArray
	 |      tempArray (IN) -- the 2D array to be updated with sorted values
	 |
	 |  Returns:  tempArray, updated with sorted values in the specified 
	 |      column.
	 *--------------------------------------------------------------------------*/
	private Integer[][] selectRowHelper7(Integer[] temp, int colAt, Integer[][] tempArray) {

		// Selection sort on the temporary array
		for (int index = 0; index < temp.length; index++) {

			int min = temp[index];
			int switchNum = index;

			// Find the minimum value in the unsorted portion of the array
			for (int checkAt = index + 1; checkAt < temp.length; checkAt++) {
				if (temp[checkAt] < min) {
					min = temp[checkAt];
					switchNum = checkAt;
				}
			}

			// Swap the found minimum element with the current element
			int tempNum = temp[index];
			temp[index] = temp[switchNum];
			temp[switchNum] = tempNum;
		}

		// Update the specified column in the tempArray with sorted values
		for (int row = 0; row < this.ROWS; row++) {
			tempArray[row][colAt] = temp[row];
		}

		return tempArray;
	}

	/*--------------------------------------------------- step8Sort -----
	 |  Method step8Sort 
	 |
	 |  Purpose:  This method processes a 2D array (array2dCopy) to
	 |      extract all valid integer values (excluding 
	 |      Integer.MAX_VALUE and Integer.MIN_VALUE) and store them 
	 |      in a list. The valid values are then reassigned back 
	 |      to the original 2D array (array2D) column by column.
	 |
	 |  Pre-condition: The array2dCopy must be populated with integer 
	 |      values, including Integer.MAX_VALUE and Integer.MIN_VALUE, 
	 |      which are used as markers and should be excluded from 
	 |      processing.
	 |
	 |  Post-condition: The original 2D array (array2D) is updated with 
	 |      the valid integer values from array2dCopy, in the same 
	 |      column order, effectively removing the marker values.
	 |
	 |  Parameters: None
	 |
	 |  Returns:  None. The method updates the array2D in place.
	 *-------------------------------------------------------------------*/
	private void step8Sort() {

		// Create a list to hold valid integers, excluding marker values
		ArrayList<Integer> arrayList = new ArrayList<Integer>();

		for (int col = 0; col < this.array2dCopy[0].length; col++) {
			for (int row = 0; row < this.array2dCopy.length; row++) {
				// Check for valid integers, excluding markers
				if (!((this.array2dCopy[row][col] == Integer.MAX_VALUE)
						|| (this.array2dCopy[row][col] == Integer.MIN_VALUE))) {

					arrayList.add(this.array2dCopy[row][col]);

				}
			}
		}

		int index = 0;
		// Reassign valid values back to the original 2D array
		for (int col = 0; col < this.COLS; col++) {
			for (int row = 0; row < this.ROWS; row++) {
				this.array2D[row][col] = arrayList.get(index);
				index++;
			}
		}
	}

	/*--------------------------------------------------- formatToThreeDecimals -----
	 |  Method formatToThreeDecimals 
	 |
	 |  Purpose:  This method formats a given double value to three 
	 |      decimal places. If the absolute value of the input is 
	 |      less than 0.001, it returns a string representation of 
	 |      "0.000" to ensure consistent formatting for small values.
	 |
	 |  Pre-condition: A double value (value) has been supplied, 
	 |      which may be positive, negative, or zero.
	 |
	 |  Post-condition: The method returns a string representation 
	 |      of the input value formatted to three decimal places, 
	 |      or "0.000" if the absolute value is less than 0.001.
	 |
	 |  Parameters:
	 |      value (IN) -- the double value to be formatted
	 |
	 |  Returns:  A string representing the formatted value to three 
	 |      decimal places.
	 *-------------------------------------------------------------------*/
	public static String formatToThreeDecimals(double value) {
		if (Math.abs(value) < 0.001) {
			return "0.000";
		} else {
			DecimalFormat df = new DecimalFormat("0.000");
			return df.format(value);
		}
	}

	/*--------------------------------------------------- printMessage -----
	 |  Method printMessage 
	 |
	 |  Purpose:  This method prints various details about the sorting 
	 |      process, including the number of items, rows, columns, 
	 |      elapsed time, and the contents of a 2D array. The elapsed 
	 |      time is formatted to three decimal places for better readability.
	 |
	 |  Pre-condition: A double value (seconds) representing the 
	 |      elapsed time has been provided, along with initialized 
	 |      instance variables for numItems, ROWS, COLS, and array2D.
	 |
	 |  Post-condition: The method outputs the number of items, 
	 |      number of rows, number of columns, elapsed time in seconds, 
	 |      and the contents of the 2D array to the console.
	 |
	 |  Parameters:
	 |      seconds (IN) -- the elapsed time in seconds to be printed
	 |
	 |  Returns:  This method does not return a value.
	 *-------------------------------------------------------------------*/
	private void printMessage(double seconds) {

		System.out.println("n = " + this.numItems);

		System.out.println("r = " + this.ROWS);

		System.out.println("s = " + this.COLS);

		String sec = formatToThreeDecimals(seconds);

		System.out.print("Elapsed time = " + sec);
		System.out.println(" seconds.");

		print2dArray(this.array2D);
	}

	/*--------------------------------------------------- print2dArray -----
	 |  Method print2dArray 
	 |
	 |  Purpose:  This method prints the elements of a two-dimensional 
	 |      array of integers to the console. Each element is printed 
	 |      in a column-wise manner, iterating through the rows for 
	 |      each column.
	 |
	 |  Pre-condition: A two-dimensional array of integers has been 
	 |      provided, with at least one row and one column.
	 |
	 |  Post-condition: The elements of the array are printed to the 
	 |      console, with each value displayed on a new line.
	 |
	 |  Parameters:
	 |      array (IN) -- the two-dimensional array of integers to be printed
	 |
	 |  Returns:  This method does not return a value.
	 *-------------------------------------------------------------------*/
	private void print2dArray(Integer[][] array) {
		for (int i = 0; i < array[0].length; i++) {
			for (int j = 0; j < array.length; j++) {
				System.out.println(array[j][i]);
			}
		}
	}

	/*--------------------------------------------------- main -----
	 |  Method main 
	 |
	 |  Purpose:  The entry point of the program that initializes the 
	 |      necessary components and orchestrates the sorting process 
	 |      based on the number of columns in the data. It utilizes 
	 |      a `readFile` object to load data and a `ColumnSortSelection` instance to 
	 |      perform the sorting. It measures and prints the elapsed time 
	 |      for the sorting operations.
	 |
	 |  Pre-condition: The program expects a valid input file to be 
	 |      provided by the `readFile` class, containing sortable data 
	 |      in the specified format.
	 |
	 |  Post-condition: The sorting operations are performed based on 
	 |      the number of columns. The elapsed time is calculated and 
	 |      printed alongside the sorted data.
	 |
	 |  Parameters:
	 |      args (IN) -- an array of command-line arguments (not used 
	 |      in this implementation)
	 |
	 |  Throws:  FileNotFoundException if the input file is not found.
	 |
	 |  Returns:  This method does not return a value; it exits the program 
	 |      after execution.
	 *-------------------------------------------------------------------*/
	public static void main(String[] args) throws FileNotFoundException {

		readFile file = new readFile();

		ColumnSortSelection ColumnSortSelection = new ColumnSortSelection(file.sortCol, file.numItems, file.ROWS, file.COLS);

		if (ColumnSortSelection.COLS == 1) {

			startTime = System.nanoTime();

			ColumnSortSelection.selectionSort();

		} else {

			startTime = System.nanoTime();

			ColumnSortSelection.selectionSort();
			ColumnSortSelection.step2OfSort();
			ColumnSortSelection.selectionSort();
			ColumnSortSelection.step4Sort();
			ColumnSortSelection.selectionSort();
			ColumnSortSelection.step6Sort();
			ColumnSortSelection.step7SelectionSort();
			ColumnSortSelection.step8Sort();

		}

		elapsedTime = System.nanoTime() - startTime;
		seconds = (elapsedTime / BILLION);

		ColumnSortSelection.printMessage(seconds);
	}

}

/**
 * --------------------------------------------------------- Class: readFile
 *
 * Purpose: This class is responsible for reading integer data from a specified
 * file and processing that data to create a 2D array suitable for sorting
 * operations. It handles user input for the filename, reads the file content,
 * and manages the conversion of read values into an array format.
 *
 * The class includes methods for: - Reading integer values from a file while
 * handling non-integer values gracefully. - Calculating the number of rows and
 * columns for the 2D array based on the number of integers read. - Building a
 * 2D array from a list of integers.
 *
 * Fields: - int numItems: The total number of integers read from the file. -
 * int ROWS: The number of rows determined for the 2D array. - int COLS: The
 * number of columns determined for the 2D array. - Integer[][] sortCol: A 2D
 * array to store sorted integer values.
 *
 * Author: Chance Krueger Date: 10/26/2024
 * ---------------------------------------------------------
 */
class readFile {

	Integer[][] sortCol; // 2D array used for sorting columns of integers

	int numItems; // Total number of items processed or read
	int ROWS; // Number of rows in the 2D array
	int COLS; // Number of columns in the 2D array

	/*--------------------------------------------------- readFile -----
	 |  Constructor readFile
	 |
	 |  Purpose:  This constructor prompts the user to enter a file name 
	 |      and attempts to read integers from the specified file. It 
	 |      initializes instance variables for the number of items, 
	 |      rows, and columns. After reading the integers, it calculates 
	 |      the appropriate number of rows and columns and builds a 2D 
	 |      array to store the data.
	 |
	 |  Pre-condition: The input file must exist and be accessible 
	 |      for reading; otherwise, a FileNotFoundException will be thrown.
	 |
	 |  Post-condition: The instance variables `numItems`, `ROWS`, and 
	 |      `COLS` are initialized, and a 2D array is constructed based 
	 |      on the integers read from the file.
	 |
	 |  Parameters: 
	 |      None
	 |
	 |  Throws: FileNotFoundException if the specified file does not 
	 |      exist or is not accessible.
	 *-------------------------------------------------------------------*/
	public readFile() throws FileNotFoundException {

		System.out.print("Enter File Name: ");

		Scanner scanner = new Scanner(System.in);

		String fileName = scanner.nextLine();

		File file = new File(fileName);

		this.numItems = 0;
		this.ROWS = 0;
		this.COLS = 0;

		ArrayList<Integer> build2D = readAndBuild(file);

		calculateRowsCols();

		build2DArray(build2D);

		scanner.close();

	}

	/*--------------------------------------------------- calculateRowsCols -----
	 |  Method calculateRowsCols 
	 |
	 |  Purpose:  This method calculates the number of rows and columns 
	 |      for a 2D array based on the total number of items. It 
	 |      attempts to find a suitable configuration that maximizes 
	 |      the dimensions while ensuring that the total number of items 
	 |      fits perfectly into the calculated rows and columns.
	 |
	 |  Pre-condition: The `numItems` variable must be a positive integer 
	 |      representing the total number of items to be organized into 
	 |      rows and columns.
	 |
	 |  Post-condition: The calculated values for `ROWS` and `COLS` are 
	 |      stored in the corresponding instance variables, allowing 
	 |      for subsequent operations on the 2D array structure.
	 |
	 |  Parameters: 
	 |      None
	 |
	 |  Returns:  This method does not return a value; it updates 
	 |      instance variables `ROWS` and `COLS` directly.
	 *-------------------------------------------------------------------*/
	private void calculateRowsCols() {

		// Start with the highest possible divisor less than numItems
		int test = this.numItems - 1;

		while (true) {

			// Handle special case when there's only one item
			if (test == 1) {
				this.COLS = 1;
				this.ROWS = this.numItems;
				break;
			}

			// Check if test is a divisor of numItems
			if (this.numItems % test == 0) {
				int s = test;
				int r = this.numItems / s;

				// Ensure the calculated dimensions fit the total items
				if (r * s == this.numItems) {
					int calc = (s - 1);
					calc = (int) Math.pow(calc, 2);
					calc = calc * 2;

					// Check if the calculated rows meet the criteria
					if (r >= calc) {
						this.COLS = s;
						this.ROWS = r;
						break;
					}
				}
			}
			// Decrement test to check the next potential column size
			test--;
		}
	}

	/*--------------------------------------------------- build2DArray -----
	 |  Method build2DArray 
	 |
	 |  Purpose:  This method constructs a 2D array from a given 
	 |      ArrayList of integers. It populates the array with values 
	 |      from the ArrayList in a row-wise manner, and fills any 
	 |      remaining spaces in the array with `Integer.MAX_VALUE` 
	 |      when the ArrayList does not contain enough elements.
	 |
	 |  Pre-condition: The `build2D` ArrayList must be initialized 
	 |      and may contain zero or more integer values. The 
	 |      `ROWS` and `COLS` instance variables must be set prior 
	 |      to calling this method.
	 |
	 |  Post-condition: The `sortCol` 2D array is populated with 
	 |      integers from the `build2D` ArrayList, or filled with 
	 |      `Integer.MAX_VALUE` where necessary.
	 |
	 |  Parameters: 
	 |      build2D (IN) -- an ArrayList of integers used to 
	 |      populate the 2D array.
	 |
	 |  Returns:  This method does not return a value; it updates 
	 |      the instance variable `sortCol` directly.
	 *-------------------------------------------------------------------*/
	private void build2DArray(ArrayList<Integer> build2D) {

		this.sortCol = new Integer[this.ROWS][this.COLS];

		int curIndex = 0;

		// Fill the 2D array row-by-row
		for (int row = 0; row < this.ROWS; row++) {
			for (int col = 0; col < this.COLS; col++) {

				// Check if the current index exceeds the ArrayList size
				if (curIndex > build2D.size() - 1) {
					this.sortCol[row][col] = Integer.MAX_VALUE; // Use marker value
				} else {
					this.sortCol[row][col] = build2D.get(curIndex); // Fill with ArrayList value
				}
				curIndex++;
			}
		}
	}

	/*--------------------------------------------------- readAndBuild -----
	 |  Method readAndBuild 
	 |
	 |  Purpose:  This method reads integers from a specified file and 
	 |      constructs an ArrayList containing these integers. It also 
	 |      keeps track of the total number of integers read. If a 
	 |      non-integer value is encountered in the file, the method 
	 |      prints an error message and terminates the program.
	 |
	 |  Pre-condition: The `file` parameter must refer to a valid file 
	 |      that exists and is accessible for reading.
	 |
	 |  Post-condition: An ArrayList containing all valid integers read 
	 |      from the file is returned. The instance variable `numItems` 
	 |      is incremented for each integer read.
	 |
	 |  Parameters: 
	 |      file (IN) -- a File object representing the input file 
	 |      from which integers will be read.
	 |
	 |  Returns: An ArrayList of Integer objects constructed from 
	 |      the values read from the file.
	 *-------------------------------------------------------------------*/
	private ArrayList<Integer> readAndBuild(File file) throws FileNotFoundException {

		ArrayList<Integer> buildArrayStart = new ArrayList<Integer>();

		Scanner scanner = new Scanner(file);

		// Reads each item in the file
		while (scanner.hasNext()) {
			String curNum = scanner.next();
			this.numItems++;

			try {
				Integer newNum = Integer.parseInt(curNum); // Parse string to integer
				buildArrayStart.add(newNum);

			} catch (NumberFormatException e) {
				// Handle non-integer values
				System.out.println("File Contains a non-Integer Value");
				System.exit(0); // Terminate program if a non-integer is found
			}

		}

		scanner.close();

		return buildArrayStart;
	}

}
