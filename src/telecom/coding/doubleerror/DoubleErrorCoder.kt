package telecom.coding.doubleerror

import telecom.bits.BinaryMatrix
import telecom.coding.Coder
import telecom.coding.Decoder
import telecom.coding.Encoder

class DoubleErrorCoder : Coder {

    private val parityMatrix = BinaryMatrix.new(
        byteArrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0),
        byteArrayOf(1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0),
        byteArrayOf(1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0),
        byteArrayOf(1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0),
        byteArrayOf(1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0),
        byteArrayOf(1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0),
        byteArrayOf(1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0),
        byteArrayOf(1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1)
    )

    private val codeLength = 16
    private val parityBitsCount = 8
    private val parityMask = 0b1111_1111

    override fun getEncoder(): Encoder = DoubleErrorEncoder(parityMatrix, codeLength, parityBitsCount)

    override fun getDecoder(): Decoder = DoubleErrorDecoder(parityMatrix, codeLength, parityMask, parityBitsCount)
}