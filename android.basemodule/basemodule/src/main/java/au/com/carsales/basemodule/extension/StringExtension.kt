package au.com.carsales.basemodule.extension

fun String.Companion.empty() = ""

fun String.checkHashTagSimbolInColor(): String {
    if (!this.contains("#")) {
        return "#$this"
    }
    return this
}

fun String?.getFilenameFromFilePath(): String {
    val array = this?.split("/").orEmpty()

    return if (array.isEmpty()) { String.empty() } else { array.last() }
}