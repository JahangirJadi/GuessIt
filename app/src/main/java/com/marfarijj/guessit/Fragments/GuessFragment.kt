package com.marfarijj.guessit.Fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.airbnb.lottie.model.content.CircleShape
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.marfarijj.guessit.R
import com.marfarijj.guessit.databinding.FragmentGuessBinding
import douglasspgyn.com.github.circularcountdown.CircularCascadeCountdown
import douglasspgyn.com.github.circularcountdown.CircularCountdown
import douglasspgyn.com.github.circularcountdown.listener.CascadeListener
import douglasspgyn.com.github.circularcountdown.listener.CircularListener


class GuessFragment : Fragment() {

    lateinit var navController: NavController
    private var isSecondPlayerPlaying: Boolean = false
    private var iteration: Int = 0
    private var player1Score: Int = 0
    private var player2Score: Int = 0
    private lateinit var player: MediaPlayer
    private var commonWordList: MutableList<String> = mutableListOf()
    private lateinit var urduWordList: MutableList<String>
    private lateinit var englishWordList: MutableList<String>
    private lateinit var binding: FragmentGuessBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuessBinding.inflate(inflater, container, false)

        player = MediaPlayer.create(context, R.raw.alarm)



        binding.btnWrong.setOnClickListener {


            if (iteration <= 10) {
                iteration++
                if (player.isPlaying) {
                    player.stop()
                }

                if (iteration < 10) {
                    resetUrduList()
                } else {
                    stopCounter()
                }
                commonWordList.add(urduWordList[0])
            }
            if (iteration == 10 && isSecondPlayerPlaying) {

                if (player1Score > player2Score) {
                    if (player1Score == player2Score) {
                        val action =
                            GuessFragmentDirections.actionNavToResult().setWinner("It's a Tie")
                        navController.navigate(action)
                    }

                    if (player1Score > player2Score) {
                        val action =
                            GuessFragmentDirections.actionNavToResult().setWinner("Player1 Won")
                        navController.navigate(action)
                    } else {

                        val action =
                            GuessFragmentDirections.actionNavToResult().setWinner("Player2 Won")
                        navController.navigate(action)
                    }
                }
            }
            if (iteration == 10 && !isSecondPlayerPlaying) {
                isSecondPlayerPlaying = true
                guessGameStart("Player 2", isSecondPlayerPlaying, true)
            }
        }

        binding.btnCorrect.setOnClickListener {


            if (iteration <= 10) {

                iteration++
                if (player.isPlaying) {
                    player.stop()
                }
                if (iteration < 10) {
                    resetUrduList()
                } else {
                    stopCounter()
                }

                commonWordList.add(urduWordList[0])

                if (isSecondPlayerPlaying) {
                    player2Score++
                } else {
                    player1Score++
                }


            }

            if (iteration == 10 && isSecondPlayerPlaying) {
                if(player1Score==player2Score){
                    val action = GuessFragmentDirections.actionNavToResult().setWinner("It's a Tie")
                    navController.navigate(action)
                }

                if (player1Score > player2Score) {
                    val action = GuessFragmentDirections.actionNavToResult().setWinner("Player1 Won")
                    navController.navigate(action)
                } else {

                    val action = GuessFragmentDirections.actionNavToResult().setWinner("Player2 Won")
                    navController.navigate(action)
                }
            }

            if (iteration == 10 && !isSecondPlayerPlaying) {
                isSecondPlayerPlaying = true
                guessGameStart("Player 2", isSecondPlayerPlaying, true)
            }


        }
        guessGameStart("Player 1", isSecond = false, isUrdu = true)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun playSound() {
        player = MediaPlayer.create(context, R.raw.alarm)
        if (view?.isShown!!) {
            player.start()
            val r2 = Runnable {
                player.stop()
            }
            Handler().postDelayed(r2, 3000)
        }


    }

    override fun onResume() {
        super.onResume()
        print("Player 1 score:$player1Score")
    }


    override fun onPause() {
        super.onPause()

        if (player.isPlaying) {
            player.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (player.isPlaying) {
            player.stop()
        }
    }

    private fun startCounter() {
        binding.circularCountdown.create(1, 10, CircularCountdown.TYPE_SECOND)
            .listener(object : CircularListener {
                override fun onTick(progress: Int) {
                    if (progress == 10) {
                        Log.d("ActivityTimer", "onTick: $progress")
                        binding.circularCountdown.stop()
                        playSound()
                    }
                }

                override fun onFinish(newCycle: Boolean, cycleCount: Int) {

                }
            }).start()
    }

    private fun resetUrduList() {
        urduWordList = mutableListOf(
            "Biryani",
            "Chai",
            "Ek Chutki Sindhurr",
            "3 Idiots",
            "Aata Majhi Satakli",
            "Kaddu",
            "Chipkali",
            "Ghalib",
            "Takla",
            "Chichhora",
            "Salman Khan",
            "Shahrukh Khan",
            "Akshay Kumar",
            "Aamir Khan",
            "Ajay Devgan",
            "Munna Bhaiya",
            "Guddu Bhaiya",
            "Kaleen Bhaiya",
            "Dabangg",
            "Bandar",
            "Atif Aslam",
            "Gulab Jamun",
            "Jamaal Gota",
            "Gobar",
            "Julaap",
            "Dakaar",
            "Aam",
            "Tinday",
            "Chamchaa",
            "Pehelwaan",
            "Lattu",
            "Had Haram",
            "Badtameez Dil",
            "Dhai Kilo Ka Haath",
            "Chiku",
            "Apna Time Ayega",
            "Lungi Dance",
            "Gutter",
            "Palak Paneer",
            "Moti Choor"
        )
        urduWordList.shuffle()

        if (commonWordList != null) {
            if (commonWordList.size > 0) {
                if (commonWordList.contains(urduWordList[0])) {
                    resetUrduList()
                } else {
                    binding.tvGuessData.text = urduWordList[0]
                }
            } else {
                binding.tvGuessData.text = urduWordList[0]
            }
        } else {
            binding.tvGuessData.text = urduWordList[0]
        }
        startCounter()
    }

    private fun stopCounter() {
        binding.circularCountdown.stop()
    }

    private fun guessGameStart(name: String, isSecond: Boolean, isUrdu: Boolean) {
        lateinit var alertDialog: LottieAlertDialog
        alertDialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_SUCCESS)
            .setTitle("$name's turn")
            .setDescription("Press start when you are ready.")
            .setPositiveText("Start")
            .setPositiveButtonColor(Color.parseColor("#1c70c8"))
            .setPositiveTextColor(Color.parseColor("#ffffff"))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {

                    if (isSecond) {
                        urduWordList.clear()
                        iteration = 0
                    }
                    resetUrduList()
                    dialog.dismiss()

                }
            })

            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()


    }
}