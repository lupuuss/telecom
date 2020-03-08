package telecom.coding.singleerror

import telecom.bits.BinaryMatrix
import telecom.bits.times
import telecom.bits.toBinaryColumn
import telecom.coding.Decoder
import java.io.InputStream
import java.io.OutputStream

class SingleErrorDecoder(
    parityMatrix: BinaryMatrix,
    codeLength: Int,
    parityMask: Int,
    parityBitsCount: Int
) : Decoder(parityMatrix, codeLength, parityMask, parityBitsCount) {

    override fun decode(input: InputStream, output: OutputStream) {

        while (true) {

            val msgByte = input.read()
            val parityByte = input.read()

            if (parityByte == -1) break

            val codeword = mergeMessageAndParity(msgByte, parityByte)

            val codewordColumn = codeword.toBinaryColumn(codeLength)

            val verColumn = parityMatrix * codewordColumn


            if (!verColumn.transposed().isZeroVector()) {

                print("Error occured => ")

                val badBitIndex = parityMatrix.findColumn(verColumn)

                if (badBitIndex == -1) {

                    println("Error correction failed! Message might be incorrect!")

                } else {

                    println("Correction: ")
                    println(codewordColumn.transposed())

                    codewordColumn.invBit(badBitIndex, 0)

                    println(codewordColumn.transposed())
                    println()
                }
            }

            output.write(codewordColumn.transposed().vectorAsNumber() shr parityBitsCount)

        }
    }
}