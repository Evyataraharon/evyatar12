* Hello, my name is Evyatar Aharon. ID 212556047 
 * I am a first year student at Ariel. This is our assignment. 
 * I created a calculator for the sum and product of numbers in different bases.
 *
 *# Function Documentation

## Functions

### 1. `number2Int(String num)`
Converts the number (num) to decimal representation (as an integer).
If the number is not in a valid format, the function returns -1.
- **Parameters:**
- `num`: A string representing a number in base [2,16]
- **Returns:** An integer.

### 2. `boolean isNumber(String g)`
Checks whether the given string (g) is in a valid number format.
- **Parameters:**
- `g`: A string representing a number
- **Returns:** `true` if the string is in a valid number format.
 * 
 * ### 3. `String int2Number(int num, int base)`
Calculates the number representation (in base) of the given natural number (represented as an integer).
If `num < 0` or `base` is not in the range [2,16], the function returns an empty string.
- **Parameters:**
- `num`: An integer representing a natural number.
- `base`: An integer representing the base.
- **Returns:** A string representing the number in the given base.

### 4. `boolean equals(String n1, String n2)`
Checks whether two numbers have the same value.
- **Parameters:**
- `n1`: The first number.
- `n2`: The second number.
- **Returns:** `true` if both numbers have the same value.
 * 
 * ## 5. `int maxIndex(String[] arr)`
A static function that finds the index of the largest number (approximately) in an array.
In case there is more than one with a maximum value, returns the first index.
- **Parameters:**
- `arr`: An array of strings representing numbers.
- **Returns:** Index of the largest number in the array.
