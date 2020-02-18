package ru.olegry.simpleimageloader

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.olegry.picloader.api.load

class MainActivity : AppCompatActivity() {

    private val images = arrayOf(
        "http://codeskulptor-demos.commondatastorage.googleapis.com/GalaxyInvaders/back02.jpg",
        "https://sun9-21.userapi.com/c7003/v7003103/2815/lNOxGlTycVg.jpg",
        "http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/nebula_blue.s2014.png",
        "http://codeskulptor-demos.commondatastorage.googleapis.com/descent/how_to.png",
        "http://commondatastorage.googleapis.com/codeskulptor-demos/pyman_assets/board.gif"
    )

    private var imageIndex = 0

    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image)
        findViewById<Button>(R.id.load).setOnClickListener {
            if (imageIndex >= images.size) {
                imageIndex = 0
            }
            image.load(images[imageIndex]) {
                placeholder(R.drawable.ic_placeholder)
                crop()
                border()
            }
            imageIndex++
        }
    }
}
