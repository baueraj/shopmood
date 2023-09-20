package com.example.shopmood

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var audioFile: File
    private lateinit var wavFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize MediaRecorder and files here
        setupMediaRecorder()

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

        val btnSendAudio = findViewById<Button>(R.id.btnSendAudio)
        btnSendAudio.setOnClickListener {
            sendAudioToServer()
        }
    }

    private fun setupMediaRecorder() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        }

        audioFile = File(cacheDir, "temp_audio.3gp")
        mediaRecorder.setOutputFile(audioFile.absolutePath)
    }

    private fun startRecording() {
        audioFile = File(externalCacheDir!!.absolutePath + "/audio.3gp")
        mediaRecorder.apply {
            prepare()
            start()
        }
    }

    private fun stopRecording() {
        mediaRecorder.apply {
            stop()
            release()
        }
        mediaRecorder = null
        convertAudioToWav()
    }

    private fun convertAudioToWav() {
        wavFile = File(cacheDir, "converted_audio.wav")
        val returnCode = FFmpeg.execute("-i ${audioFile.absolutePath} ${wavFile.absolutePath}")
        if (returnCode == RETURN_CODE_SUCCESS) {
            // Conversion successful
            // You can now send this file to your server
        } else {
            // Handle error
        }
    }

    private fun sendAudioToServer() {
        val requestFile = RequestBody.create(MediaType.parse("audio/wav"), wavFile)
        val body = MultipartBody.Part.createFormData("audio", wavFile.name, requestFile)

        RetrofitClient.instance.sendAudioFile(body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful response
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
            }
        })
    }

    override fun onStop() {
        super.onStop()
        mediaRecorder?.release()
        mediaRecorder = null
    }
}
