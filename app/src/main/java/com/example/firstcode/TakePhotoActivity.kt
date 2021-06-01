package com.example.firstcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.firstcode.databinding.ActivityTakePhotoBinding
import java.io.File

class TakePhotoActivity : AppCompatActivity() {
    private lateinit var activityTakePhotoBinding: ActivityTakePhotoBinding
    private lateinit var outputImg: File
    private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTakePhotoBinding = ActivityTakePhotoBinding.inflate(layoutInflater)
        setContentView(activityTakePhotoBinding.root)
        initData()
    }

    private fun initData() {
        activityTakePhotoBinding.btnTakePhoto.setOnClickListener {
            outputImg = File(externalCacheDir, "output_img.jpg")
            if (outputImg.exists()) {
                outputImg.delete()
            }
            outputImg.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(this, "$packageName.fileprovider", outputImg)
            } else {
                Uri.fromFile(outputImg)
            }

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 50)
            } else {
                //  启动相机
                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, 10)
            }
        }

        activityTakePhotoBinding.btnAlbum.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, 20)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            50 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //  启动相机
                    val intent = Intent("android.media.action.IMAGE_CAPTURE")
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    startActivityForResult(intent, 10)
                } else {
                    Toast.makeText(this, "你拒绝了相机权限无法拍照", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> {
                if (resultCode == RESULT_OK) {
                    val bitmap =
                        BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    activityTakePhotoBinding.imgPhoto.setImageBitmap(rotateIfRequire(bitmap))
                }
            }
            20 -> {
                if (resultCode == RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        val bitmap = contentResolver.openFileDescriptor(uri, "r").use {
                            BitmapFactory.decodeFileDescriptor(it?.fileDescriptor)
                        }
                        activityTakePhotoBinding.imgPhoto.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun rotateIfRequire(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImg.path)
        return when (exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotatedBitmap
    }
}