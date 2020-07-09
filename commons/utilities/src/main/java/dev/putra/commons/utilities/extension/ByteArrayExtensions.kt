package dev.putra.commons.utilities.extension

/**
 * Convert any byte array to hexadecimal string.
 *
 * @return Hexadecimal string.
 */
fun ByteArray.toHex() = joinToString("") {
    "%02x".format(it)
}
