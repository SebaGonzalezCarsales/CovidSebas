package au.com.carsales.basemodule.util

import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*

@Throws(IOException::class)
fun readFile(file: File): String? {
    val fileContents = StringBuilder(file.length().toInt())
    Scanner(file).use { scanner ->
        while (scanner.hasNextLine()) {
            fileContents.append(scanner.nextLine().toString() + System.lineSeparator())
        }
        return fileContents.toString()
    }
}


fun getFileFromPath(obj: Any, fileName: String?): File? {
    val classLoader = obj.javaClass.classLoader
    val resource: URL = classLoader!!.getResource(fileName)
    return File(resource.path)
}