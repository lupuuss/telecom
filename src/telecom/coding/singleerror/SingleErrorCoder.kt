package telecom.coding.singleerror

import telecom.bits.BinaryMatrix
import telecom.coding.Coder
import telecom.coding.Decoder
import telecom.coding.Encoder

class SingleErrorCoder : Coder {

    val parityMatrix = BinaryMatrix.new(
        intArrayOf(1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0),
        intArrayOf(1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0),
        intArrayOf(0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0),
        intArrayOf(1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1)
    )

    val parityBitsCount = 4

    override fun getEncoder(): Encoder {
        return SingleErrorEncoder(parityMatrix)
    }

    override fun getDecoder(): Decoder {
        return SingleErrorDecoder(parityMatrix)
    }

}