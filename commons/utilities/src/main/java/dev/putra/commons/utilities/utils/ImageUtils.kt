package dev.putra.commons.utilities.utils

/**
 * Created by Chyrus on 3/28/2020.
 */
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.media.ThumbnailUtils
import android.util.Base64
import dev.putra.commons.utilities.extension.getFileCheckSumString
import java.io.ByteArrayOutputStream
import kotlin.math.min

object ImageUtils {

    fun getAlwaysLandScapeImage(bitmap: Bitmap, isLandScape: Boolean): ByteArrayOutputStream {
        ByteArrayOutputStream().use {
            if (bitmap.height > bitmap.width && isLandScape) {
                val matrix = Matrix()
                matrix.postRotate(-90F)
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                    .compress(Bitmap.CompressFormat.JPEG, 60, it)
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, it)
            }
            return it
        }
    }

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, drawableId)
    }

    fun getBitmapVisualCodeLogo(overlay: Bitmap, bitmap: Bitmap): Bitmap {
        val height = bitmap.height
        val width = bitmap.width
        val combined = Bitmap.createBitmap(width, height, bitmap.config)
        val canvas = Canvas(combined)
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height

        canvas.drawBitmap(bitmap, Matrix(), null)
        val logo = getResizedBitmap(overlay, height.div(4), width.div(4))
        val centreX = (canvasWidth - logo.width).div(2)
        val centreY = (canvasHeight - logo.height).div(2)
        canvas.drawBitmap(logo, centreX.toFloat(), centreY.toFloat(), null)

        return combined
    }

    private fun getResizedBitmap(
        bitmap: Bitmap,
        newWidth: Int,
        newHeight: Int
    ): Bitmap {
        val scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val ratioX = newWidth / bitmap.width.toFloat()
        val ratioY = newHeight / bitmap.height.toFloat()
        val middleX = newWidth / 2.0f
        val middleY = newHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        Canvas(scaledBitmap).apply {
            setMatrix(scaleMatrix)
            drawBitmap(
                bitmap,
                middleX - bitmap.width / 2,
                middleY - bitmap.height / 2,
                Paint(Paint.FILTER_BITMAP_FLAG)
            )
        }

        return scaledBitmap
    }

    fun getBase64FromBitmap(bitmap: Bitmap?): String? {
        ByteArrayOutputStream().use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 60, it)
            return Base64.encodeToString(it.toByteArray(), Base64.DEFAULT)
        }
    }

    fun getBase64FromImagePath(imagePath: String?): String? {
        val bitmapFactory = BitmapFactory.decodeFile(imagePath)
        ByteArrayOutputStream().use {
            bitmapFactory.compress(Bitmap.CompressFormat.JPEG, 60, it)
            it.toByteArray().toString().getFileCheckSumString()
            return Base64.encodeToString(it.toByteArray(), Base64.DEFAULT)
        }
    }

    fun cropCenterWide(sourceArrayBitmap: ByteArray): ByteArray {
        val sourceBitmap =
            BitmapFactory.decodeByteArray(sourceArrayBitmap, 0, sourceArrayBitmap.size)
        val dimension = min(sourceBitmap.width, sourceBitmap.height)
        ByteArrayOutputStream().use {
            ThumbnailUtils
                .extractThumbnail(sourceBitmap, dimension, (dimension * 0.56F).toInt())
                .compress(Bitmap.CompressFormat.JPEG, 60, it)
            return it.toByteArray()
        }
    }

}