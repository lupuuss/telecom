package telecom.coding

import telecom.coding.doubleerror.DoubleErrorCoder
import telecom.coding.singleerror.SingleErrorCoder

interface Coder {

    enum class Type {
        SingleError,
        DoubleError
    }

    enum class Operation {
        Encode,
        Decode
    }

    fun getEncoder(): Encoder
    fun getDecoder(): Decoder

    companion object {
        private val singleError = SingleErrorCoder()
        private val doubleError = DoubleErrorCoder()

        fun getByType(type: Type): Coder {

            return when (type) {
                Type.SingleError -> singleError
                Type.DoubleError -> doubleError
            }
        }
    }
}