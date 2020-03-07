package telecom.coding.singleerror

import telecom.bits.BinaryMatrix
import telecom.bits.times
import telecom.bits.toBinaryColumn
import telecom.coding.Encoder
import java.io.InputStream
import java.io.OutputStream

class SingleErrorEncoder(parityMatrix: BinaryMatrix) : Encoder(parityMatrix,12, 4)