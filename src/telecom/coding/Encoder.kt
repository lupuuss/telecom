package telecom.coding

import telecom.bits.BinaryMatrix
import telecom.bits.times
import telecom.bits.toBinaryColumn
import java.io.InputStream
import java.io.OutputStream

@Suppress("MemberVisibilityCanBePrivate")
abstract class Encoder(
    protected val parityMatrix: BinaryMatrix,
    protected val codeLength: Int,
    protected val parityBitsCount: Int
) {
    open fun encode(input: InputStream, output: OutputStream) {

        do {

            val byte = input.read()

            val codeword = byte shl parityBitsCount

            // Multiplication of parity matrix and codeword. Products of this operation are parity bits.
            val parityCol = parityMatrix * codeword.toBinaryColumn(codeLength)

            // Converts parity bits from binary column form to integer form
            val parity = parityCol.transposed().vectorAsNumber()

            output.write(byte)
            output.write(parity)

        } while (byte != -1)
    }
}