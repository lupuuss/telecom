package telecom.coding.singleerror

import telecom.bits.BinaryMatrix
import telecom.coding.Encoder


class SingleErrorEncoder(
    parityMatrix: BinaryMatrix,
    codeLength: Int,
    parityBitsCount: Int
) : Encoder(parityMatrix, codeLength, parityBitsCount)