* Hello, my name is Evyatar Aharon. ID 212556047 
 * I am a first year student at Ariel. This is our assignment. Project Goal
The goal of this project is to implement a simple spreadsheet system in Java. The spreadsheet supports cell data entry, basic formulas, and dependency management between cells. It allows users to work with both numerical and textual data and evaluate cell values based on their dependencies.

Class Structure
1. Ex2Sheet
This is the core class of the project, responsible for managing the entire spreadsheet.

Key Methods:
setCell(String cell, String value)
Updates a cell with a value. If the value is a formula, the class manages the dependencies and ensures there are no cyclic references.

getCell(String cell)
Returns the current value of a cell. If the cell contains a formula, it evaluates and returns the result.

eval(CellEntry entry)
A core internal method that evaluates the value of a cell, including formulas and managing dependencies between cells.

Unique Features:
Formula Support –
The system allows formulas in cells, such as =A1+B1 or =B1*2. The class evaluates the formulas based on the referenced cells.

Dependency Management –
The class tracks cell dependencies to calculate the correct evaluation order and prevent errors.

Cyclic Reference Detection –
If a circular dependency occurs (e.g., A1 depends on B1, and B1 depends on A1), the system detects it and marks the cells as an error.

2. SCell
This class represents a single cell in the spreadsheet. It can hold a numeric value, text, or a formula.

Key Methods:
setData(String s) – Updates the cell’s content.
getData() – Returns the cell’s content.
getType() – Returns the type of content (number, text, or formula).
setType(int t) – Updates the content type based on the evaluation result from Ex2Sheet.
getOrder() – Returns the evaluation order of the cell.
3. CellEntry
This class represents the 2D position of a cell in the spreadsheet, using an (X, Y) coordinate system.
For example, A1 represents the cell in column A and row 1.

Key Methods:
CellEntry(String coordinate) – Creates a cell position object from a string like "A1".
isValid() – Checks if the cell position is valid (column A-Z and row 0-99).
toString() – Returns the cell’s position as a string.
Supported Errors
ERR_FORM – Invalid formula.
ERR_CYCLE – Circular reference between cells.
ERR_FORM_FORMAT – Incorrect formula format.
Summary
The Ex2Sheet class provides all the basic functionality needed for a simple spreadsheet application. It’s an excellent project for understanding data structures in Java and applying key concepts like object dependencies, error handling, and runtime evaluations.
