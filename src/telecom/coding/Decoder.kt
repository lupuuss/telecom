package telecom.coding

import telecom.bits.BinaryMatrix
import java.io.InputStream
import java.io.OutputStream

@Suppress("MemberVisibilityCanBePrivate")
abstract class Decoder(
    protected val parityMatrix: BinaryMatrix,
    protected val codeLength: Int,
    protected val parityMask: Int,
    protected val parityBitsCount: Int
) {

    protected fun mergeMessageAndParity(msg: Int, parity: Int) = (msg shl parityBitsCount) or (parity and parityMask)

    /**
     * Decodes input bytes into output stream
     * @returns pair of ints. First is the number of corrected errors. Second is the number of unsolved errors.
     */
    abstract fun decode(input: InputStream, output: OutputStream): Pair<Int, Int>
}