package exemples

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {
    val f = File.listRoots()[0]
    println(listDirectory(f))
}

fun listDirectory(f: File) {
    val s = "Llista de fitxers i directoris del directori " + f.path
    var n = 1
    println(s)
    println("-".repeat(s.length))
    if (f.parent == null) {
        println("0.- Directori pare")
    } else {
        println("0.- ${f.parent}")
    }
    for (e in f.listFiles().sorted()) {
        val permisos = directoryPermis(e)
        val modificacion = modifDate(e)
        if (e.isFile()) {
            println(
                "${n}.- " + permisos + "\t " + e.length() +
                        "\t " + modificacion + "\t " + e.name
            )
        }
        if (e.isDirectory()) {
            println(
                "${n}.- " + permisos + "\t " + e.length() +
                        "\t " + modificacion + "\t " + e.name
            )
        }
        n++
    }
    introduceNumber(f)
}

private fun introduceNumber(f: File) {
    println(" ")
    println("Introdueix un numero (-1 per acabar): ")
    val input = BufferedReader(InputStreamReader(System.`in`)).readLine()
    val num = input.toIntOrNull()
    if (num != null) {
        loopNumbers(num, f)
    } else {
        println("Entrada no valida. Introduix un numero válid.")
        println("")
        listDirectory(f)
    }
}

fun loopNumbers(number: Int, f: File) {
    var currentFile: File
        if (number == -1) {
            System.exit(0)
        } else if (number > f.listFiles().size || number < -1) {
            println("Numero incorrecte")
        } else if (number == 0) {
            val parentFile = f.parentFile
            if (parentFile != null && parentFile.exists()) {
                currentFile = f.parentFile
                println(listDirectory(currentFile))
            } else {
                println(listDirectory(f))
            }
        } else {
            currentFile = f.listFiles().sorted()[number - 1]
            if (currentFile.canRead() && currentFile.isDirectory) {
                println(listDirectory(currentFile))
            } else {
                if (!currentFile.canRead()) {
                    println("No tens permisos")
                    println(listDirectory(f))
                } else {
                    println(listDirectory(f))
                }
            }
        }
}

fun directoryPermis(f: File): String {
    val permis = StringBuilder()
    permis.append(if (f.isDirectory) "d" else "-")
    permis.append(if (f.canRead()) "r" else "-")
    permis.append(if (f.canWrite()) "w" else "-")
    permis.append(if (f.canExecute()) "x" else "-")
    return permis.toString()
}

fun modifDate(f: File): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val modifDate = Date(f.lastModified())
    return dateFormat.format(modifDate)
}