package telecom

import telecom.coding.Coder
import java.io.File
import java.lang.Exception
import java.nio.file.Path

fun main(args: Array<String>) {

    val (type, operation, path) = try {
        Utils.parseArguments(args)
    } catch (e: Exception) {
        println("Bad args => ${e.message}")
        return
    }

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