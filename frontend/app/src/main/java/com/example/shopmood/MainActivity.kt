package com.example.shopmood

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream

import android.media.MediaRecorder
import java.io.File

class MainActivity : AppCompatActivity() {
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var audioFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRecord = findViewById<Button>(R.id.btnRecord)
        btnRecord.setOnClickListener {
            if (mediaRecorder == null) {
                startRecording()
                btnRecord.text = "Stop"
            } else {
                stopRecording()
                btnRecord.text = "Record"
            }
        }
    }

    private fun startRecording() {
        audioFile = File(externalCacheDir!!.absolutePath + "/audio.3gp")
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(audioFile.absolutePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            prepare()
            start()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    override fun onStop() {
        super.onStop()
        mediaRecorder?.release()
        mediaRecorder = null
    }
}


// Visual emotion recognition
//class MainActivity : AppCompatActivity() {
//    private lateinit var imageView: ImageView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        imageView = findViewById(R.id.image_view)
//        val buttonCamera = findViewById<Button>(R.id.button_camera)
//        buttonCamera.setOnClickListener {
//            dispatchTakePictureIntent()
//        }
//    }
//
//    private fun dispatchTakePictureIntent() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imageView.setImageBitmap(imageBitmap)
//            sendImageToServer(imageBitmap)
//        }
//    }
//
//    private fun sendImageToServer(imageBitmap: Bitmap) {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)
//
//        val json = JSONObject()
//        json.put("image", encodedImage)
//
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("http://your-server-url/recognize_emotion")
//            .post(json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()))
//            .build()
//
//        client.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//            val responseBody = response.body?.string()
//            val responseJson = JSONObject(responseBody)
//
//            val emotion = responseJson.getString("emotion")
//            val productSuggestions = responseJson.getJSONArray("product_suggestions")
//
//            runOnUiThread {
//                val textViewResults = findViewById<TextView>(R.id.text_view_results)
//                textViewResults.text = "Emotion: $emotion"
//
//                val textViewProducts = findViewById<TextView>(R.id.text_view_products)
//                textViewProducts.text = "Product suggestions: $productSuggestions"
//            }
//        }
//    }
//
//
//    companion object {
//        const val REQUEST_IMAGE_CAPTURE = 1
//    }
//}
