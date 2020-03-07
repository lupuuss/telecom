package telecom.coding.doubleerror

import telecom.bits.BinaryMatrix
import telecom.coding.Encoder

class DoubleErrorEncoder(parityMatrix: BinaryMatrix) : Encoder(parityMatrix, 16, 8)