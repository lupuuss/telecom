package telecom

import telecom.coding.Coder
import java.lang.Exception
import java.nio.file.InvalidPathException
import java.nio.file.Path

class ArgumentsParseException(msg: String) : Exception(msg)
class HelpRequired : Exception()

object Utils {

    @Throws(ArgumentsParseException::class)
    fun parseArguments(args: Array<String>): Triple<Coder.Type, Coder.Operation, Path> {

        var coderType = Coder.Type.DoubleError
        var operation = Coder.Operation.Encode
        var path: Path? = null

        for (arg in args) {
            when (arg) {
                "--single", "-1" -> coderType = Coder.Type.SingleError
                "--double", "-2" -> coderType = Coder.Type.DoubleError
                "--decode", "-d" -> operation = Coder.Operation.Decode
                "--encode", "-e" -> operation = Coder.Operation.Encode
                "--help", "-h" -> throw HelpRequired()
                else -> path = try {
                    Path.of(arg)
                } catch (e: InvalidPathException) {
                    throw ArgumentsParseException("Unexpected token: $arg")
                }
            }
        }

        path?.let {

            if (!path.toFile().exists()) {
                throw ArgumentsParseException("Path $path does not exist!")
            }

        } ?: throw ArgumentsParseException("A missing path to file!")

        return Triple(coderType, operation, path)
    }
}
