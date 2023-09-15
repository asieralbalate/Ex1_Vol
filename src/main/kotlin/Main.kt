package exemples

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {
    val f = File.listRoots()[0]
    println(llistaDirectori(f))
}

fun llistaDirectori(f: File) {
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
        val permisos = obtenerPermisos(e)
        val modificacion = obtenerFechaModificacio(e)
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
    println(" ")
    println("Introdueix un numero (-1 per acabar): ")
    val entrada = BufferedReader(InputStreamReader(System.`in`)).readLine()
    val num = entrada.toIntOrNull()
    if (num != null) {
        bucleNumeros(num, f)
    } else {
        println("Entrada no valida. Introduix un numero válid.")
        println(" ")
        llistaDirectori(f)
    }
}

fun bucleNumeros(numero: Int, f: File) {
    var currentFile: File
    if (numero == -1) {
        System.exit(0)
    } else if (numero > f.listFiles().size || numero < -1) {
        println("Numero incorrecte")
    } else if (numero == 0) {
        val parentFile = f.parentFile
        if (parentFile != null && parentFile.exists()) {
            currentFile = f.parentFile
            println(llistaDirectori(currentFile))
        } else {
            println(llistaDirectori(f))
        }
    } else {
        currentFile = f.listFiles().sorted()[numero - 1]
        if (currentFile.isDirectory) {
            if (currentFile.canRead()) {
                println(llistaDirectori(currentFile))
            } else {
                println("No tens permisos")
                println(llistaDirectori(f))
            }
        } else {
            println(llistaDirectori(f))
        }
    }
}

fun obtenerPermisos(f: File): String {
    val permisos = StringBuilder()
    permisos.append(if (f.isDirectory) "d" else "-")
    permisos.append(if (f.canRead()) "r" else "-")
    permisos.append(if (f.canWrite()) "w" else "-")
    permisos.append(if (f.canExecute()) "x" else "-")
    return permisos.toString()
}

fun obtenerFechaModificacio(f: File): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val fechaModificacion = Date(f.lastModified())
    return dateFormat.format(fechaModificacion)
}