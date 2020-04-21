package telecom

import telecom.coding.Coder
import java.lang.Exception

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
        println("Unexpected error occurred! Stack trace: ")
        e.printStackTrace()
        return
    }


    val output = Utils.generateOutputFileForInput(inputPath, operation).outputStream()
    val input = inputPath.toFile().inputStream()

    val typeVerb = operation.toString().dropLast(1)
    val errorCorrectionTypeString =
        type.toString().toLowerCase().let { it.substring(0, it.indexOf("error")) } + " error"

    println("${typeVerb}ing with $errorCorrectionTypeString correction method...")

    val coder = Coder.getByType(type)

    if (operation == Coder.Operation.Encode) {
        // Encoding part
        coder.getEncoder().encode(input, output)
        println("Finished!")
        return
    }

    // Decoding part
    val (corrected, unsolved) = coder.getDecoder().decode(input, output)

    println()
    println("Finished!")

    if (corrected == 0 && unsolved == 0) {
        println("No errors occured!")
    } else {
        println("Codewords corrected: $corrected")
        println("Unsolved errors: $unsolved")
    }

}

private val helpMsg = """
            Usage: telecom INPUT [Options]
                
            Arguments:
                INPUT - File to decode/encode
            
            Options:
                --single, -1 - Single error correction
                --double, -2 - Double error correction <= default flag
                --encode, -e - Encoding <= default flag
                --decode, -d - Decoding
        """.trimIndent()