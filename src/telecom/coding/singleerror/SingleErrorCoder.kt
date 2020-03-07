package telecom.coding.singleerror

import telecom.bits.BinaryMatrix
import telecom.coding.Coder
import telecom.coding.Decoder
import telecom.coding.Encoder

class SingleErrorCoder : Coder {

    private val parityMatrix = BinaryMatrix.new(
        intArrayOf(0, 1, 1, 1, 0, 1, 0, 1,   1, 0, 0, 0),
        intArrayOf(1, 0, 1, 1, 1, 0, 1, 0,   0, 1, 0, 0),
        intArrayOf(1, 1, 0, 1, 0, 1, 1, 0,   0, 0, 1, 0),
        intArrayOf(1, 1, 1, 0, 1, 0, 0, 1,   0, 0, 0, 1)
    )

    private val codeLength = 12
    private val parityBitsCount = 4
    private val parityMask = 0b1111

    override fun getEncoder(): Encoder {
        return SingleErrorEncoder(parityMatrix, codeLength, parityBitsCount)
    }

    override fun getDecoder(): Decoder {
        return SingleErrorDecoder(parityMatrix, codeLength, parityMask, parityBitsCount)
    }

}