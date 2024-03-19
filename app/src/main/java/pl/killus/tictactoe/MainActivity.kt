package pl.killus.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.killus.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val b by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        Board(
            listOf(
                b.a1, b.a2, b.a3,
                b.b1, b.b2, b.b3,
                b.c1, b.c2, b.c3
            ), b.turnTV, this
        )
    }
}