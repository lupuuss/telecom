package telecom.coding.singleerror

import telecom.bits.BinaryMatrix
import telecom.bits.times
import telecom.bits.toBinaryColumn
import telecom.coding.Encoder
import java.io.InputStream
import java.io.OutputStream

class SingleErrorEncoder(parityMatrix: BinaryMatrix,
                         codeLength: Int,
                         parityBitsCount: Int) : Encoder(parityMatrix,codeLength, parityBitsCount)