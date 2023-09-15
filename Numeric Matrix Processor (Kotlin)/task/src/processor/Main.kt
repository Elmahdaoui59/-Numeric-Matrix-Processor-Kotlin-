package processor

import java.util.Scanner
import kotlin.math.pow
import kotlin.system.exitProcess

typealias Matrix = MutableList<MutableList<Double>>
typealias Vector = MutableList<Double>

val scanner = Scanner(System.`in`)
fun main() {
    var matrixA: Matrix
    var matrixB: Matrix
    while (true) {
        println(
            "1. Add matrices\n" +
                    "2. Multiply matrix by a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit"
        )
        println("Your choice: ")
        when (scanner.nextInt()) {
            0 -> {
                exitProcess(0)
            }

            1 -> {
                println("Enter size of first matrix:")
                val nbArows = scanner.nextInt()
                val nbAColumns = scanner.nextInt()
                println("Enter first matrix: ")
                matrixA = readMatrix(nbArows, nbAColumns)
                println("Enter size of second matrix:")
                val nbBrows = scanner.nextInt()
                val nbBColumns = scanner.nextInt()
                println("Enter second matrix: ")
                matrixB = readMatrix(nbBrows, nbBColumns)
                println("The result is: ")
                printMatrix(sumMatrices(matrixA, matrixB))
            }

            2 -> {
                println("Enter size of matrix:")
                val nbArows = scanner.nextInt()
                val nbAColumns = scanner.nextInt()
                println("Enter matrix: ")
                matrixA = readMatrix(nbArows, nbAColumns)
                println("Enter constant: ")
                val scalar = scanner.nextDouble()
                println("The result is: ")
                printMatrix(multiplayMatrixByDouble(matrixA, scalar))
            }

            3 -> {
                println("Enter size of first matrix:")
                val nbArows = scanner.nextInt()
                val nbAColumns = scanner.nextInt()
                println("Enter first matrix: ")
                matrixA = readMatrix(nbArows, nbAColumns)
                println("Enter size of second matrix:")
                val nbBrows = scanner.nextInt()
                val nbBColumns = scanner.nextInt()
                println("Enter second matrix: ")
                matrixB = readMatrix(nbBrows, nbBColumns)
                println("The result is: ")
                printMatrix(multiplayMatrices(matrixA, matrixB))
            }

            4 -> {
                println(
                    "1. Main diagonal\n" +
                            "2. Side diagonal\n" +
                            "3. Vertical line\n" +
                            "4. Horizontal line"
                )
                println("Your choice: ")
                when (scanner.nextInt()) {
                    1 -> {
                        println("Enter size of matrix:")
                        val nbArows = scanner.nextInt()
                        val nbAColumns = scanner.nextInt()
                        println("Enter matrix: ")
                        matrixA = readMatrix(nbArows, nbAColumns)
                        println("The result is: ")
                        printMatrix(transposeByMainDiagonal(matrixA))
                    }

                    2 -> {
                        println("Enter size of matrix:")
                        val nbArows = scanner.nextInt()
                        val nbAColumns = scanner.nextInt()
                        println("Enter matrix: ")
                        matrixA = readMatrix(nbArows, nbAColumns)
                        println("The result is: ")
                        printMatrix(transposeBySideDiagonal(matrixA))
                    }

                    3 -> {
                        println("Enter size of matrix:")
                        val nbArows = scanner.nextInt()
                        val nbAColumns = scanner.nextInt()
                        println("Enter matrix: ")
                        matrixA = readMatrix(nbArows, nbAColumns)
                        println("The result is: ")
                        printMatrix(transposeByVerticalLine(matrixA))
                    }

                    4 -> {
                        println("Enter size of matrix:")
                        val nbArows = scanner.nextInt()
                        val nbAColumns = scanner.nextInt()
                        println("Enter matrix: ")
                        matrixA = readMatrix(nbArows, nbAColumns)
                        println("The result is: ")
                        printMatrix(transposeByHorizontaleLine(matrixA))
                    }
                }

            }

            5 -> {
                println("Enter matrix size:")
                val nbArows = scanner.nextInt()
                val nbAColumns = scanner.nextInt()
                println("Enter matrix: ")
                matrixA = readMatrix(nbArows, nbAColumns)
                println("The result is: ")
                println(calculateDeterminant(matrixA))
                //printMatrix(dropMatrixRowAndColumn(matrixA, 0, 1))
            }

            6 -> {
                println("Enter matrix size:")
                val nbArows = scanner.nextInt()
                val nbAColumns = scanner.nextInt()
                println("Enter matrix: ")
                matrixA = readMatrix(nbArows, nbAColumns)
                if (calculateDeterminant(matrixA) == 0.0) {
                    println("This matrix doesn't have an inverse.")
                } else {
                    println("The result is: ")
                    printMatrix(cacultateMatrixInverse(matrixA))
                }
            }
        }
    }
}

fun cacultateMatrixInverse(matrix: Matrix): Matrix {

    val det = calculateDeterminant(matrix)
    val Cmatrix: Matrix = mutableListOf<MutableList<Double>>()
    repeat(matrix.size) {
        val row = mutableListOf<Double>()
        repeat(matrix.size) {
            row.add(0.0)
        }
        Cmatrix.add(row)
    }
    for (i in 0 until matrix.size) {
        for (j in 0 until matrix.size) {
            Cmatrix[i][j] = (-1.0).pow(j+i) * calculateDeterminant(
                dropMatrixRowAndColumn(matrix, i, j)
            )
        }
    }
    val transposedCmatrix = transposeByMainDiagonal(Cmatrix)
    //println("transpose:")
    //printMatrix(transposedCmatrix)
    //val tMultByDouble = multiplayMatrixByDouble(transposedCmatrix, 4.0)
    //println("transposé mult by double:")
    //printMatrix(tMultByDouble)
    val result = multiplayMatrixByDouble(transposedCmatrix, 1/det)
    //println("the result is : ")
    //printMatrix(result)
    return result
}

fun calculateDeterminant(matrix: Matrix): Double {
    return if (matrix.size == 2) {
        matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1]
    } else {
        var d = 0.0
        for (j in 0 until matrix.size) {
            d += matrix[0][j] * (-1.0).pow(j) * calculateDeterminant(
                dropMatrixRowAndColumn(matrix, 0, j)
            )
        }
        d
    }
}

/*
1 7 7
6 6 4
4 2 1

2 -1 0
0 1 2
1 1 0
 */
fun dropMatrixRowAndColumn(matrix1: Matrix, i: Int, j: Int): Matrix {
    val matrix = mutableListOf<MutableList<Double>>()
    matrix1.forEach {
        val newRow = mutableListOf<Double>()
        it.forEach {
            newRow.add(it)
        }
        matrix.add(newRow)
    }
    matrix.removeAt(i)
    for (row in matrix) {
        row.removeAt(j)
    }
    return matrix
}

fun transposeByHorizontaleLine(matrix1: Matrix): Matrix {
    val tMatrix: Matrix = mutableListOf()
    repeat(matrix1.size) {
        val row = mutableListOf<Double>()
        repeat(matrix1.size) {
            row.add(0.0)
        }
        tMatrix.add(row)
    }
    for (j in 0 until matrix1.size) {
        for (i in 0 until matrix1.size) {
            tMatrix[i][j] = extractMatrixCoumn(j, matrix1).reversed()[i]
        }
    }
    return tMatrix
}

fun transposeByVerticalLine(matrix1: Matrix): Matrix {
    val tMatrix: Matrix = mutableListOf(mutableListOf<Double>())
    for (row in matrix1) {
        tMatrix.add(row.reversed().toMutableList())
    }
    return tMatrix
}

fun transposeBySideDiagonal(matrix1: Matrix): Matrix {
    val tMatrix: Matrix = mutableListOf(mutableListOf<Double>())
    for (j in 0 until matrix1[0].size) {
        tMatrix.add(extractMatrixCoumn(matrix1.size - j - 1, matrix1).reversed().toMutableList())
    }
    return tMatrix
}

fun transposeByMainDiagonal(matrix1: Matrix): Matrix {
    val tMatrix: Matrix = mutableListOf<MutableList<Double>>()
    for (j in 0 until matrix1[0].size) {
        tMatrix.add(extractMatrixCoumn(j, matrix1))
    }
    return tMatrix
}

fun multiplayMatrices(matrix1: Matrix, matrix2: Matrix): Matrix {
    val matrix = mutableListOf<MutableList<Double>>()
    if (matrix1[0].size != matrix2.size) {
        println("The operation cannot be performed.")
        return matrix
    }
    repeat(matrix1.size) {
        val row = mutableListOf<Double>()
        repeat(matrix2[0].size) {
            row.add(0.0)
        }
        matrix.add(row)
    }
    for (i in 0 until matrix1.size) {
        for (j in 0 until matrix2[0].size) {
            matrix[i][j] = multiplayTwoVectors(
                matrix1[i],
                extractMatrixCoumn(j, matrix2)
            )
            //printMatrix(matrix)
        }
    }
    return matrix
}

fun multiplayTwoVectors(vec1: Vector, vec2: Vector): Double {
    //println("vec1: ${vec1.joinToString(" ")} vec2: ${vec2.joinToString(" ")}")
    var sum = 0.0
    vec1.forEachIndexed { index, d ->
        sum += d * vec2[index]
    }
    return sum
}

fun extractMatrixCoumn(index: Int, matrix: Matrix): Vector {
    val column = mutableListOf<Double>()
    matrix.forEach {
        column.add(it[index])
    }
    return column
}

fun multiplayMatrixByDouble(inMatrix: Matrix, scalar: Double): Matrix {
    val matrix = mutableListOf<MutableList<Double>>()
    for (i in 0 until inMatrix.size) {
        val row = mutableListOf<Double>()
        for (j in 0 until inMatrix[0].size) {
            val nb = inMatrix[i][j] * scalar
            row.add(nb)
        }
        matrix.add(row)
    }
    return matrix
}
fun multiplayMatrixByInt(inMatrix: Matrix, scalar: Int): Matrix {
    val matrix = mutableListOf<MutableList<Double>>()
    for (i in 0 until inMatrix.size) {
        val row = mutableListOf<Double>()
        for (j in 0 until inMatrix[0].size) {
            val nb = inMatrix[i][j] * scalar
            row.add(nb)
        }
        matrix.add(row)
    }
    return matrix
}

fun readMatrix(nbRows: Int, nbColumns: Int)
        : Matrix {
    val matrix = mutableListOf<MutableList<Double>>()
    for (i in 0 until nbRows) {
        val row = mutableListOf<Double>()
        for (j in 0 until nbColumns) {
            val nb = scanner.nextDouble()
            row.add(nb)
        }
        matrix.add(row)
    }
    return matrix
}

fun printMatrix(matrix: Matrix) {
    matrix.forEach { row ->
        println(row.joinToString(" "))
    }
}

fun sumMatrices(matrix1: Matrix, matrix2: Matrix): Matrix {
    val matrix = mutableListOf<MutableList<Double>>()
    if ((matrix1.size != matrix2.size) or (matrix1[0].size != matrix2[0].size)) {
        println("ERROR")
    } else {
        for (i in 0 until matrix1.size) {
            val row = mutableListOf<Double>()
            for (j in 0 until matrix1[0].size) {
                val nb = matrix1[i][j] + matrix2[i][j]
                row.add(nb)
            }
            matrix.add(row)
        }
    }
    return matrix
}
