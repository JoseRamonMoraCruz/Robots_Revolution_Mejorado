package mx.tecnm.cdhidalgo.robotsrevolution

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class Laboratorio : AppCompatActivity() {

    private lateinit var imgButtonRobot1: ImageButton
    private lateinit var imgButtonRobot2: ImageButton
    private lateinit var imgButtonRobot3: ImageButton
    private lateinit var imgButtonCientifico1: ImageButton
    private lateinit var imgButtonCientifico2: ImageButton
    private lateinit var imgButtonCientifico3: ImageButton
    private lateinit var imgButtonIzquierdaCentro2: ImageButton
    private lateinit var imgButtonDerechaCentro3: ImageButton

    private lateinit var imgButtonCientificoDer4: ImageButton
    private lateinit var imgButtonCientificoDer5: ImageButton
    private lateinit var imgButtonCientificoDer6: ImageButton
    private lateinit var imgButtonRobotDer7: ImageButton
    private lateinit var imgButtonRobotDer8: ImageButton
    private lateinit var imgButtonRobotDer9: ImageButton
    private lateinit var movementCounterText: TextView
    private lateinit var videoViewPerdiste: VideoView

    private var leftCentralOccupied = false
    private var rightCentralOccupied = false
    private var moveCounter = 0

    private var lab1Scientists = 3
    private var lab1Robots = 3
    private var lab2Scientists = 0
    private var lab2Robots = 0

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laboratorio)
        setupInsets()

        imgButtonRobot1 = findViewById(R.id.imgButtonRobot1)
        imgButtonRobot2 = findViewById(R.id.imgButtonRobot2)
        imgButtonRobot3 = findViewById(R.id.imgButtonRobot3)
        imgButtonCientifico1 = findViewById(R.id.imgButtonCientifico1)
        imgButtonCientifico2 = findViewById(R.id.imgButtonCientifico2)
        imgButtonCientifico3 = findViewById(R.id.imgButtonCientifico3)
        imgButtonIzquierdaCentro2 = findViewById(R.id.imgButtonIzquierdaCentro2)
        imgButtonDerechaCentro3 = findViewById(R.id.imgButtonDerechaCentro3)

        imgButtonCientificoDer4 = findViewById(R.id.imgButtonCientificoDer4)
        imgButtonCientificoDer5 = findViewById(R.id.imgButtonCientificoDer5)
        imgButtonCientificoDer6 = findViewById(R.id.imgButtonCientificoDer6)
        imgButtonRobotDer7 = findViewById(R.id.imgButtonRobotDer7)
        imgButtonRobotDer8 = findViewById(R.id.imgButtonRobotDer8)
        imgButtonRobotDer9 = findViewById(R.id.imgButtonRobotDer9)
        movementCounterText = findViewById(R.id.movementCounterText)
        videoViewPerdiste = findViewById(R.id.videoView)

        initializeGame()
        setupButtonClickListeners()
        setupCentralButtonListeners()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeGame() {
        moveCounter = 0
        movementCounterText.text = "Movimientos: $moveCounter"

        lab1Scientists = 3
        lab1Robots = 3
        lab2Scientists = 0
        lab2Robots = 0

        leftCentralOccupied = false
        rightCentralOccupied = false

        imgButtonRobot1.apply {
            visibility = View.VISIBLE
            isEnabled = true
        }
        imgButtonRobot2.apply {
            visibility = View.VISIBLE
            isEnabled = true
        }
        imgButtonRobot3.apply {
            visibility = View.VISIBLE
            isEnabled = true
        }
        imgButtonCientifico1.apply {
            visibility = View.VISIBLE
            isEnabled = true
        }
        imgButtonCientifico2.apply {
            visibility = View.VISIBLE
            isEnabled = true
        }
        imgButtonCientifico3.apply {
            visibility = View.VISIBLE
            isEnabled = true
        }

        imgButtonIzquierdaCentro2.visibility = View.GONE
        imgButtonDerechaCentro3.visibility = View.GONE

        imgButtonCientificoDer4.visibility = View.GONE
        imgButtonCientificoDer5.visibility = View.GONE
        imgButtonCientificoDer6.visibility = View.GONE
        imgButtonRobotDer7.visibility = View.GONE
        imgButtonRobotDer8.visibility = View.GONE
        imgButtonRobotDer9.visibility = View.GONE

        videoViewPerdiste.visibility = View.GONE
    }

    private fun showLossMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        val videoId = when (Random.nextInt(4)) {
            0 -> R.raw.muerte_1
            1 -> R.raw.muerte_2
            2 -> R.raw.muerte_3
            else -> R.raw.muerte_4
        }

        videoViewPerdiste.setVideoURI(Uri.parse("android.resource://$packageName/$videoId"))
        videoViewPerdiste.visibility = View.VISIBLE
        videoViewPerdiste.start()

        videoViewPerdiste.setOnCompletionListener {
            videoViewPerdiste.visibility = View.GONE
            initializeGame()
        }
    }

    private fun setupButtonClickListeners() {
        imgButtonRobot1.setOnClickListener { onRobotOrScientistClicked(it as ImageButton, "Robot", R.drawable.us_bot) }
        imgButtonRobot2.setOnClickListener { onRobotOrScientistClicked(it as ImageButton, "Robot", R.drawable.us_bot) }
        imgButtonRobot3.setOnClickListener { onRobotOrScientistClicked(it as ImageButton, "Robot", R.drawable.us_bot) }
        imgButtonCientifico1.setOnClickListener { onRobotOrScientistClicked(it as ImageButton, "Scientist", R.drawable.us_cientifico) }
        imgButtonCientifico2.setOnClickListener { onRobotOrScientistClicked(it as ImageButton, "Scientist", R.drawable.us_cientifico) }
        imgButtonCientifico3.setOnClickListener { onRobotOrScientistClicked(it as ImageButton, "Scientist", R.drawable.us_cientifico) }

        imgButtonCientificoDer4.setOnClickListener { moveFromRightToCenter(it as ImageButton, "Scientist") }
        imgButtonCientificoDer5.setOnClickListener { moveFromRightToCenter(it as ImageButton, "Scientist") }
        imgButtonCientificoDer6.setOnClickListener { moveFromRightToCenter(it as ImageButton, "Scientist") }
        imgButtonRobotDer7.setOnClickListener { moveFromRightToCenter(it as ImageButton, "Robot") }
        imgButtonRobotDer8.setOnClickListener { moveFromRightToCenter(it as ImageButton, "Robot") }
        imgButtonRobotDer9.setOnClickListener { moveFromRightToCenter(it as ImageButton, "Robot") }
    }

    private fun setupCentralButtonListeners() {
        imgButtonIzquierdaCentro2.setOnClickListener { moveToOtherSide(it as ImageButton) }
        imgButtonDerechaCentro3.setOnClickListener { moveToOtherSide(it as ImageButton) }

        imgButtonIzquierdaCentro2.setOnLongClickListener { handleLongPressToReturn(it as ImageButton, "left") }
        imgButtonDerechaCentro3.setOnLongClickListener { handleLongPressToReturn(it as ImageButton, "right") }
    }

    private fun onRobotOrScientistClicked(button: ImageButton, type: String, imageResource: Int) {
        if (leftCentralOccupied && rightCentralOccupied) {
            Toast.makeText(this, "Los dos lugares de la cápsula están siendo ocupados, vacíe al menos un lugar", Toast.LENGTH_SHORT).show()
            return
        }

        if (lab2Robots > lab2Scientists && lab2Scientists > 0) {
            showLossMessage("Perdiste, había más robots que científicos en el lado derecho.")
            return
        }

        button.visibility = View.GONE
        button.isEnabled = false

        if (!leftCentralOccupied) {
            imgButtonIzquierdaCentro2.setImageResource(imageResource)
            imgButtonIzquierdaCentro2.visibility = View.VISIBLE
            imgButtonIzquierdaCentro2.isEnabled = true // Asegurar que el botón esté habilitado
            imgButtonIzquierdaCentro2.tag = type
            leftCentralOccupied = true
        } else if (!rightCentralOccupied) {
            imgButtonDerechaCentro3.setImageResource(imageResource)
            imgButtonDerechaCentro3.visibility = View.VISIBLE
            imgButtonDerechaCentro3.isEnabled = true // Asegurar que el botón esté habilitado
            imgButtonDerechaCentro3.tag = type
            rightCentralOccupied = true
        }
    }

    private fun moveFromRightToCenter(button: ImageButton, type: String) {
        if (leftCentralOccupied && rightCentralOccupied) {
            Toast.makeText(this, "Los dos lugares de la cápsula están siendo ocupados, vacíe al menos un lugar", Toast.LENGTH_SHORT).show()
            return
        }

        if (lab1Robots > lab1Scientists && lab1Scientists > 0) {
            showLossMessage("Perdiste, había más robots que científicos en el lado izquierdo.")
            return
        }

        button.visibility = View.GONE

        if (!leftCentralOccupied) {
            imgButtonIzquierdaCentro2.setImageDrawable(button.drawable)
            imgButtonIzquierdaCentro2.tag = type
            imgButtonIzquierdaCentro2.visibility = View.VISIBLE
            imgButtonIzquierdaCentro2.isEnabled = true // Asegurar que el botón esté habilitado
            leftCentralOccupied = true
        } else if (!rightCentralOccupied) {
            imgButtonDerechaCentro3.setImageDrawable(button.drawable)
            imgButtonDerechaCentro3.tag = type
            imgButtonDerechaCentro3.visibility = View.VISIBLE
            imgButtonDerechaCentro3.isEnabled = true // Asegurar que el botón esté habilitado
            rightCentralOccupied = true
        }
    }

    private fun handleLongPressToReturn(button: ImageButton, position: String): Boolean {
        val type = button.tag as? String ?: return false

        button.visibility = View.GONE
        button.isEnabled = false

        when (type) {
            "Robot" -> {
                if (imgButtonRobot1.visibility == View.GONE) {
                    imgButtonRobot1.visibility = View.VISIBLE
                    imgButtonRobot1.isEnabled = true
                } else if (imgButtonRobot2.visibility == View.GONE) {
                    imgButtonRobot2.visibility = View.VISIBLE
                    imgButtonRobot2.isEnabled = true
                } else if (imgButtonRobot3.visibility == View.GONE) {
                    imgButtonRobot3.visibility = View.VISIBLE
                    imgButtonRobot3.isEnabled = true
                }
            }
            "Scientist" -> {
                if (imgButtonCientifico1.visibility == View.GONE) {
                    imgButtonCientifico1.visibility = View.VISIBLE
                    imgButtonCientifico1.isEnabled = true
                } else if (imgButtonCientifico2.visibility == View.GONE) {
                    imgButtonCientifico2.visibility = View.VISIBLE
                    imgButtonCientifico2.isEnabled = true
                } else if (imgButtonCientifico3.visibility == View.GONE) {
                    imgButtonCientifico3.visibility = View.VISIBLE
                    imgButtonCientifico3.isEnabled = true
                }
            }
        }

        if (position == "left") leftCentralOccupied = false else rightCentralOccupied = false

        Toast.makeText(this, "Personaje regresado al lugar original.", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun moveToOtherSide(button: ImageButton) {
        val type = button.tag as? String ?: return

        if (lab1Scientists + lab1Robots > 1 && (!leftCentralOccupied || !rightCentralOccupied)) {
            Toast.makeText(this, "Debe haber al menos un personaje en el centro para cruzar", Toast.LENGTH_SHORT).show()
            return
        }

        if (type == "Scientist") {
            if (imgButtonCientificoDer4.visibility == View.GONE) {
                imgButtonCientificoDer4.setImageDrawable(button.drawable)
                imgButtonCientificoDer4.visibility = View.VISIBLE
                lab1Scientists--
                lab2Scientists++
            } else if (imgButtonCientificoDer5.visibility == View.GONE) {
                imgButtonCientificoDer5.setImageDrawable(button.drawable)
                imgButtonCientificoDer5.visibility = View.VISIBLE
                lab1Scientists--
                lab2Scientists++
            } else if (imgButtonCientificoDer6.visibility == View.GONE) {
                imgButtonCientificoDer6.setImageDrawable(button.drawable)
                imgButtonCientificoDer6.visibility = View.VISIBLE
                lab1Scientists--
                lab2Scientists++
            }
        } else if (type == "Robot") {
            if (imgButtonRobotDer7.visibility == View.GONE) {
                imgButtonRobotDer7.setImageDrawable(button.drawable)
                imgButtonRobotDer7.visibility = View.VISIBLE
                lab1Robots--
                lab2Robots++
            } else if (imgButtonRobotDer8.visibility == View.GONE) {
                imgButtonRobotDer8.setImageDrawable(button.drawable)
                imgButtonRobotDer8.visibility = View.VISIBLE
                lab1Robots--
                lab2Robots++
            } else if (imgButtonRobotDer9.visibility == View.GONE) {
                imgButtonRobotDer9.setImageDrawable(button.drawable)
                imgButtonRobotDer9.visibility = View.VISIBLE
                lab1Robots--
                lab2Robots++
            }
        }

        button.visibility = View.GONE
        if (button == imgButtonIzquierdaCentro2) leftCentralOccupied = false else rightCentralOccupied = false

        incrementMoveCounter()
        checkWinCondition()
    }

    private fun incrementMoveCounter() {
        moveCounter++
        movementCounterText.text = "Movimientos: $moveCounter"
    }

    private fun checkWinCondition() {
        if (lab2Scientists == 3 && lab2Robots == 3) {
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "¡Felicidades! Todos han cruzado de manera segura.", Toast.LENGTH_LONG).show()

                mediaPlayer = MediaPlayer.create(this, R.raw.ganaste_audio)
                mediaPlayer.start()

                Handler(Looper.getMainLooper()).postDelayed({
                    mediaPlayer.release()
                    initializeGame()
                }, 10000)
            }, 500)
        }
    }
}