package telecom.coding.doubleerror

import telecom.bits.*
import telecom.coding.Decoder
import java.io.InputStream
import java.io.OutputStream

class DoubleErrorDecoder(
    parityMatrix: BinaryMatrix
) : Decoder(parityMatrix, 16, 0b1111_1111, 8) {

    private fun resolveDoubleError(verColumn: BinaryMatrix): Pair<Int, Int> {

        for (col in parityMatrix.getColumnsIndices()) {

            val checkColumn = parityMatrix.getColumnAsMatrix(col)

            val xorResult = checkColumn xor verColumn
            val findResult = parityMatrix.findColumn(xorResult)

            if (findResult != -1) {

                return Pair(col, findResult)
            }

        }

        return Pair(-1, -1)
    }

    private fun correctError(codewordColumn: BinaryMatrix, verColumn: BinaryMatrix) {

        print("Error occured => ")
        val checkSingleError = parityMatrix.findColumn(verColumn)

        if (checkSingleError != -1) {
            println("Single error correction:\n${codewordColumn.transposed()}")

            codewordColumn.invBit(checkSingleError, 0)

            println(codewordColumn.transposed())

            return
        }

        val (firstError, secondError) = resolveDoubleError(verColumn)

        if (firstError != -1 && secondError != -1) {

            println("Double Error Correction:\n${codewordColumn.transposed()}")

            codewordColumn.invBit(firstError, 0)
            codewordColumn.invBit(secondError, 0)

            println(codewordColumn.transposed())

        } else {

            println("Correction failed! Message might be incorrect!")
        }

    }

    override fun decode(input: InputStream, output: OutputStream) {

        var msgByte = input.read()
        var parityByte = input.read()

        do {

            val codeword = mergeMessageAndParity(msgByte, parityByte)

            val codewordColumn = codeword.toBinaryColumn(codeLength)

            val verColumn = parityMatrix * codewordColumn

            val verificationResult = verColumn.transposed().isZeroVector()

            if (!verificationResult) {

                correctError(codewordColumn, verColumn)
            }

            output.write(codewordColumn.transposed().vectorAsNumber() shr parityBitsCount)

            msgByte = input.read()
            parityByte = input.read()

        } while (parityByte != -1)
    }

}