package telecom

import telecom.coding.Coder
import java.io.File
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.nio.file.Path

fun parseArguments(args: Array<String>): Triple<Coder.Type, Coder.Operation, Path> {
    var coderType = Coder.Type.SingleError
    var operation = Coder.Operation.Encode
    var path: Path? = null

    for (arg in args) {
        when (arg) {
            "--single", "-1" -> coderType = Coder.Type.SingleError
            "--double", "-2" -> coderType = Coder.Type.DoubleError
            "--decode", "-d" -> operation = Coder.Operation.Decode
            "--encode", "-e" -> operation = Coder.Operation.Encode
            else -> path = Path.of(arg)
        }
    }

    path?.let {

        if (!path.toFile().exists()) {
            throw IllegalArgumentException("Path { $path } does not exist!")
        }
    } ?: throw IllegalStateException("Missing path to file!")

    return Triple(coderType, operation,  path)
}

fun main(args: Array<String>) {


    val (type, operation, path) = parseArguments(args)

    val coder = Coder.getByType(type)

    val outputFile: File = Path.of(path.parent.toString(), "output.txt").toFile()

    if (!outputFile.exists()) {
        outputFile.createNewFile()
    }

    val output = outputFile.outputStream()
    val input = path.toFile().inputStream()

    if (operation == Coder.Operation.Encode) {
        coder.getEncoder().encode(input, output)
    } else {
        coder.getDecoder().decode(input, output)
    }

}