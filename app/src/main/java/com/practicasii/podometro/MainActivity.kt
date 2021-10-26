package com.practicasii.podometro


import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.practicasii.podometro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
            == PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
        }

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorPasos: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        Log.d("SensorExamples",sensorPasos.toString())

        var pasos: Float = 0.0F
        val sensorEventListener: SensorEventListener = object : SensorEventListener{
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                for(value in sensorEvent.values){
                    pasos += value
                }
                binding.tvPasos.text = "Pasos:${pasos}"
                Log.d("SensorExamples", "Pasos: $pasos")
            }
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

            }
        }

        sensorManager.registerListener(sensorEventListener,sensorPasos,SensorManager.SENSOR_DELAY_FASTEST)

    }
}