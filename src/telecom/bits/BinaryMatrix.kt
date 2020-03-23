package telecom.bits

import java.lang.IllegalArgumentException

/**
 * Stores a binary matrix as arrays of integers and provides part of standard
 * matrix operations (multiplication, transposition) and useful binary operations for
 * error correction.
 */
class BinaryMatrix private constructor(private val matrix: Array<out ByteArray>) {

    private val size = matrix.size to matrix.first().size

    init {
        for (row in matrix) {

            for (bit in row.asStream().map { it.toInt() }) {

                if (bit != 0 && bit != 1) {
                    throw IllegalArgumentException("Any binary matrix can contain only 0 and 1")
                }
            }
        }
    }

    /**
     * Inverts pointed bit in matrix
     * @param row bit row in matrix
     * @param col bit column in matrix
     */
    fun invBit(row: Int, col: Int) {
        matrix[row][col] = if (matrix[row][col].toInt() == 1) 0 else 1
    }

    /**
     * @return transposed form for given BinaryMatrix
     */
    fun transposed(): BinaryMatrix = new(
        *Array(matrix[0].size) { row ->
            ByteArray(matrix.size) { matrix[it][row] }
        }
    )

    fun rows() = size.first

    fun cols() = size.second

    fun getRow(row: Int): ByteArray = matrix[row]

    fun getColumnAsMatrix(column: Int) = column(*ByteArray(rows()) { this[it][column] })

    fun getRowsIndices() = matrix.indices

    fun getColumnsIndices() = matrix.first().indices

    /**
     * Its designed for vectors (one-row matrices)
     * @return first row as Int
     */
    fun vectorAsNumber(): Int {
        var number = 0

        val reversedRow = matrix[0].reversedArray()
        for (bitIndex in reversedRow.indices) {
            number += reversedRow[bitIndex].toInt() shl bitIndex
        }

        return number
    }

    /**
     * @return index of given column in the matrix. If it does not present, -1 is returned
     */
    fun findColumn(column: BinaryMatrix): Int {

        if (column.cols() != 1) {
            throw IllegalArgumentException("THe passed matrix is not a column!")
        }

        if (rows() != column.rows()) {
            throw IllegalArgumentException("The column has incompatible length to matrix!")
        }

        for (col in getColumnsIndices()) {

            var bad = false

            for (row in getRowsIndices()) {

                if (this[row][col] != column[row][0]) {
                    bad = true
                }
            }

            if (!bad) {
                return col
            }
        }

        return -1
    }

    /**
     * Its designed for vectors (one-row matrices)
     * @return true if first row is [0, 0, ..., 0]
     */
    fun isZeroVector(): Boolean {

        for (bit in matrix[0]) {

            if (bit.toInt() != 0) {
                return false
            }
        }

        return true
    }

    override fun toString(): String {
        return matrix.joinToString(separator = "\n") { it.contentToString() } +
                " (${size.first} x ${size.second})"
    }

    companion object {

        /**
         * @param values for column (only zeros and ones)
         * @return one-column BinaryMatrix for given values
         */
        fun column(vararg values: Byte) = BinaryMatrix(Array(values.size) { byteArrayOf(values[it]) })

        /**
         * @param values for vector (only zeros and ones)
         * @return one-row BinaryMatrix for given values
         */
        fun vector(vararg values: Byte) = BinaryMatrix(arrayOf(values))

        /**
         * @param rows for matrix (only zeros and ones)
         * @return BinaryMatrix for given rows
         */
        fun new(vararg rows: ByteArray) = BinaryMatrix(rows)

        fun empty(rows: Int, cols: Int) = new(*Array(rows) { ByteArray(cols) { 0 } })
    }
}

/**
 * @return product of 2 matrices multiplication
 */
operator fun BinaryMatrix.times(rhs: BinaryMatrix): BinaryMatrix {

    if (cols() != rhs.rows()) {
        throw IllegalArgumentException("Incompatible matrices! $this // $rhs")
    }

    val result = BinaryMatrix.new(*Array(rows()) { ByteArray(rhs.cols()) { 0 } })

    for (i in this.getRowsIndices()) {

        for (j in rhs.getColumnsIndices()) {
            var tmp: Byte = 0

            for (k in rhs.getRowsIndices()) {

                tmp = tmp xor (this[i][k] and rhs[k][j])
            }
            result[i][j] = tmp
        }
    }

    return result
}

operator fun BinaryMatrix.get(index: Int): ByteArray = this.getRow(index)

infix fun BinaryMatrix.xor(rhs: BinaryMatrix): BinaryMatrix {

    if (this.rows() != rhs.rows() || this.cols() != rhs.cols()) {
        throw IllegalArgumentException("XORing two matrices with different sizes is illegal!")
    }

    val result = BinaryMatrix.empty(rows(), cols())

    for (row in getRowsIndices()) {

        for (col in getColumnsIndices()) {

            result[row][col] = this[row][col] xor rhs[row][col]
        }
    }

    return result
}

/**
 * Converts integer to binary column. Highest bit is in the first row.
 * @return integer in binary form (as column of ones and zeros).
 */
fun Int.toBinaryColumn(n: Int): BinaryMatrix {
    val bits = mutableListOf<Byte>()
    var number = this

    for (i in 1..n) {
        bits.add((number % 2).toByte())
        number = number shr 1
    }

    return BinaryMatrix.column(*bits.reversed().toByteArray())
}

/**
 * Converts integer to binary vector. Highest bit is in the first column.
 * @return integer in binary form (as column of ones and zeros).
 */
@Suppress("unused")
fun Int.toBinaryVector(n: Int): BinaryMatrix {
    val bits = mutableListOf<Byte>()
    var number = this

    for (i in (1..n)) {
        bits.add((number % 2).toByte())
        number = number shr 1
    }

    return BinaryMatrix.vector(*bits.toByteArray())
}