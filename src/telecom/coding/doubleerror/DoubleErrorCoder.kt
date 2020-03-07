package telecom.coding.doubleerror

import telecom.bits.BinaryMatrix
import telecom.coding.Coder
import telecom.coding.Decoder
import telecom.coding.Encoder

class DoubleErrorCoder : Coder {

    private val parityMatrix = BinaryMatrix.new(
        intArrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0),
        intArrayOf(1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0),
        intArrayOf(1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0),
        intArrayOf(1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0),
        intArrayOf(1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0),
        intArrayOf(1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1)
    )

    override fun getEncoder(): Encoder = DoubleErrorEncoder(parityMatrix)

    override fun getDecoder(): Decoder = DoubleErrorDecoder(parityMatrix)
}