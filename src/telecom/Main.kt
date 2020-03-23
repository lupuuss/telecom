package telecom

import telecom.coding.Coder
import java.io.File
import java.lang.Exception
import java.nio.file.Path

fun main(args: Array<String>) {

    val (type, operation, inputPath) = try {
        Utils.parseArguments(args)
    } catch (e: ArgumentsParseException) {

        println("Bad args => ${e.message}")
        println("Use --help to get the options!")
        return
    } catch (e: HelpRequired) {
        println(helpMsg)
        return
    } catch (e: Exception) {
        println("Unexpected error occured! Stack trace: ")
        e.printStackTrace()
        return
    }

    val coder = Coder.getByType(type)

    val extension = inputPath.toFile().extension.let { if (it.isEmpty()) "" else ".$it" }
    val op = operation.toString().toLowerCase()

    val outputFileName = "${inputPath.toFile().nameWithoutExtension}_$op$extension"
    val outputDir = inputPath.parent ?: Path.of("")

    val outputFile: File = Path.of(outputDir.toString(), outputFileName).toFile()

    if (!outputFile.exists()) {
        outputFile.createNewFile()
    }

    val output = outputFile.outputStream()
    val input = inputPath.toFile().inputStream()

    if (operation == Coder.Operation.Encode) {
        coder.getEncoder().encode(input, output)
    } else {
        coder.getDecoder().decode(input, output)
    }

}

private val helpMsg = """
            Usage: telecom INPUT [Options]
                
            Arguments:
                INPUT - File to decode/encode
            
            Options:
                --single, -1 - Single error correction
                --double, -2 - Double error correction
                --encode, -e - Encoding
                --decode, -d - Decoding
        """.trimIndent()