package telecom.bits

import kotlin.streams.asStream

infix fun Byte.shl(n: Int): Byte = (this.toInt() shl n).toByte()
infix fun Byte.and(b: Byte): Byte = (this.toInt() and b.toInt()).toByte()
infix fun Byte.xor(b: Byte): Byte = (this.toInt() xor b.toInt()).toByte()

fun ByteArray.asStream() = this.asSequence().asStream()