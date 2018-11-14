package com.example.marc9.sound

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sonido.*
import android.Manifest
import android.animation.ArgbEvaluator
import android.animation.TimeAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.example.marc9.sound.MainActivity.Companion.applicationContext
import com.example.marc9.sound.sonido.LevelMethod
import com.example.marc9.sound.sonido.MicLevelReader
import android.graphics.Shader
import android.graphics.LinearGradient
import android.graphics.drawable.GradientDrawable


class SonidoFragment() : Fragment(), MicLevelReader.MicLevelReaderValueListener {


    private var meterValue = 0.0
    private var mMicLevelReader: MicLevelReader? = null
    private var mRecorderThread: Thread? = null


    public fun getvalor(): Double {
        return meterValue
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sonido,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkMicrophoneAccess()
        mMicLevelReader = MicLevelReader(this, LevelMethod.dBFS)

        val animator = setAnimator(fragmentsonido.background as GradientDrawable)
        btnplay.setOnClickListener {

            if (animator!!.isRunning) {
                stopit()
                animator.cancel()
                btnplay.setText("animar")
            } else {
                startit()
                animator.start()
                btnplay.setText("parar")
            }
        }
    }

    fun setAnimator(gradientDrawable: GradientDrawable): ValueAnimator? {
        val start = calculateColor(meterValue)
        val mid = calculateColor(meterValue)
        val end = calculateColor(meterValue)
        val evaluator = ArgbEvaluator()
        val animator = TimeAnimator.ofFloat(0.0f, 1.0f)
        animator.duration = 1500
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.addUpdateListener {
            val fraction = it.animatedFraction
            val newStart = evaluator.evaluate(fraction, start, end) as Int
            val newMid = evaluator.evaluate(fraction, mid, start) as Int
            val newEnd = evaluator.evaluate(fraction, end, mid) as Int
            gradientDrawable.colors = intArrayOf(newStart, newMid, newEnd)
        }
        return animator
    }

    fun calculateColor(valor:Double): Int {
        var color: Int =0

        when(valor){
            in 0..70->{color=Color.YELLOW
            }
            in 70..160->{color= Color.WHITE
            }
            in 160..100000->{color=Color.GREEN
            }
        }
        return color
    }
    //----------PERMISOS DE ACCESO AL MICROFONO---------
    private fun checkMicrophoneAccess(): Boolean {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(applicationContext() as Activity,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    SonidoFragment.REQUEST_RECORD_AUDIO)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            SonidoFragment.REQUEST_RECORD_AUDIO -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(context, "got_microphone", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "need_microphone", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //---------METODOS DE PINTAR--------

    inner class Lienzo(context: Context) : View(context)  {

        var pincel1 = Paint()

        override fun onDraw(canvas: Canvas) {
          val ancho = getWidth()
          val alto = getHeight().toFloat()


           var colores= calculateColor(meterValue)
            //Background del lienzo
            canvas.drawRGB(255,255,255)
            withDelay(800){clearcanvas()}

           // postInvalidateOnAnimation()
           // super.onDraw(canvas)



            pincel1.setARGB(meterValue.toInt(), colores[0], colores[1], colores[2])
            pincel1.setStrokeWidth(5.00f)

            for (x in 1..10000) {
                val alex = (Math.random() * ancho).toFloat()
                val aley = (Math.random() * alto).toFloat()
                canvas.drawPoint(alex, aley, pincel1)

            }
        }



        fun clearcanvas() {
            invalidate()
        }

        fun calculateColor(valor:Double): IntArray {
            lateinit var colores: IntArray

            when(valor){
                in 0..70->{colores= intArrayOf(
                        (Math.random()*255).toInt(),
                        (Math.random()*255).toInt(),
                        (valor).toInt())
                        }
                in 70..160->{colores= intArrayOf(
                        (Math.random()*255).toInt(),
                        (valor).toInt(),
                        (Math.random()*255).toInt())
                        }
                in 160..100000->{colores= intArrayOf(
                         (valor).toInt(),
                         (Math.random()*255).toInt(),
                         (Math.random()*255).toInt())
                        }
            }
            return colores
        }
    }

    //----------METODOS DE SONIDO---------

    var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (mMicLevelReader!!.isRunning()) {
                textdb.text = "Valor" + meterValue.toInt()
                meterValue=meterValue.toDouble()
            }
        }
    }
    override fun valueCalculated(level: Double) {
        meterValue = level
        mHandler.obtainMessage(1).sendToTarget()
    }
    override fun onPause() {
        if (mMicLevelReader != null)
            if (mMicLevelReader!!.isRunning()) {
                stopit()
            }

        super.onPause()
    }


    fun startit(): Boolean {
        if (checkMicrophoneAccess()) {
            mRecorderThread = Thread(mMicLevelReader, "AudioListener Thread")
            mRecorderThread!!.start()
            return true
        }
        return false
    }


    fun stopit() {
        if (mMicLevelReader!!.isRunning()) {
            valueCalculated(mMicLevelReader!!.getScale())
            mMicLevelReader!!.stop()
        }
    }

    override fun onStop() {
        stopit()
        super.onStop()
    }

    override fun onDestroy() {
        stopit()

        super.onDestroy()
    }

    companion object {

        val REQUEST_RECORD_AUDIO = 121
    }
}



fun withDelay(delay : Long, block : () -> Unit) {
    Handler().postDelayed(Runnable(block), delay)
}







