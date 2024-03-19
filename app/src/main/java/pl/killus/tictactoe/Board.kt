package pl.killus.tictactoe

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Board (
    private val viewTiles: List<Button>,
    private val turnTextView: TextView,
    private val context: Context
){
    init {
        reset()
        if (viewTiles.size != 9)
            throw Exception("jak ...?")

        viewTiles.forEachIndexed { index, button ->
            button.text = Player.EMPTY.c
            button.setOnClickListener {
                onClick(index)
            }
        }
    }

    private fun onClick(idx: Int) {
        if (!occupyTile(idx)) return

        val p = getPlayer()
        if (checkWin()) {
            MaterialAlertDialogBuilder(context).apply {
                setPositiveButton("Reset") { _, _ ->
                    reset()
                }
                setCancelable(false)
                setTitle("${p.c} Wins!")
            }.show()
        } else if (checkDraw()) {
            MaterialAlertDialogBuilder(context).apply {
                setPositiveButton("Reset") { _, _ ->
                    reset()
                }
                setCancelable(false)
                setTitle("Draw!")
            }.show()
        }
        nextTurn()
    }

    @SuppressLint("SetTextI18n")
    private fun reset() {
        turn = 0
        turnTextView.text = "Turn ${getPlayer().c}"
        viewTiles.forEach {
            it.text = Player.EMPTY.c
        }
        tiles = Array(9) { Player.EMPTY }
    }

    private fun getPlayer () = players[turn]
    private var tiles =
        Array(9) { Player.EMPTY }

    private var turn = 0
    @SuppressLint("SetTextI18n")
    private fun nextTurn () {
        turn = (turn+1)%2
        turnTextView.text = "Turn ${getPlayer().c}"
    }

    private fun occupyTile (idx: Int): Boolean {
        val player = getPlayer()
        val result = tiles[idx] == Player.EMPTY
        if (result){
            tiles[idx] = player
            viewTiles[idx].text = player.c
        }
        return result
    }

    private fun checkWin(): Boolean {
        val player = getPlayer()
        return matches.any {
            tiles[it[0]] == player && tiles[it[1]] == player && tiles[it[2]] == player
        }
    }

    private fun checkDraw(): Boolean {
        return tiles.all { it != Player.EMPTY }
    }


    companion object {
        val players =
            listOf(
            Player.X,
            Player.O
        )

        val matches = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )
    }
}
enum class Player (
    val c: String
){
    X("X"),
    O("O"),
    EMPTY(" ")
}