package telecom.coding.doubleerror

import telecom.bits.BinaryMatrix
import telecom.coding.Encoder

class DoubleErrorEncoder(
    parityMatrix: BinaryMatrix,
    codeLength: Int,
    parityBitsCount: Int
) : Encoder(parityMatrix, codeLength, parityBitsCount)