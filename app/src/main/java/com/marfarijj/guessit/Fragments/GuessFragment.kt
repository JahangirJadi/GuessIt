package com.marfarijj.guessit.Fragments

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.marfarijj.guessit.R
import com.marfarijj.guessit.databinding.FragmentGuessBinding
import douglasspgyn.com.github.circularcountdown.CircularCountdown
import douglasspgyn.com.github.circularcountdown.listener.CircularListener

private val admobUnitIt = "ca-app-pub-8944787818061216/2225654571"

class GuessFragment : Fragment() {


    lateinit var navController: NavController
    private var isSecondPlayerPlaying: Boolean = false
    private var iteration: Int = 0
    private var player1Score: Int = 0
    private var player2Score: Int = 0
    private var isCurrentCategoryUrdu: Boolean = true
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
        initBannerAd()
        player = MediaPlayer.create(context, R.raw.alarm)


        binding.btnWrong.setOnClickListener {

            if (iteration <= 10) {
                iteration++
                if (player.isPlaying) {
                    player.stop()
                }

                if (iteration < 10) {
                    resetList()
                } else {
                    stopCounter()
                }
                if (isCurrentCategoryUrdu) {
                    commonWordList.add(urduWordList[0])
                } else {
                    commonWordList.add(englishWordList[0])
                }
            }
            if (iteration == 10 && isSecondPlayerPlaying) {

                if (player1Score == player2Score) {
                    val action = GuessFragmentDirections.actionNavToResult().setWinner("It's a Tie")
                    navController.navigate(action)
                }

                if (player1Score > player2Score) {
                    val action =
                        GuessFragmentDirections.actionNavToResult().setWinner("Player 1 Won")
                    navController.navigate(action)
                }
                if (player1Score < player2Score) {
                    val action =
                        GuessFragmentDirections.actionNavToResult().setWinner("Player 2 Won")
                    navController.navigate(action)
                }
            }
            if (iteration == 10 && !isSecondPlayerPlaying) {
                isSecondPlayerPlaying = true
                guessGameStart("Player 2", isSecondPlayerPlaying, isCurrentCategoryUrdu)
            }
        }

        binding.btnCorrect.setOnClickListener {

            if (iteration <= 10) {

                iteration++
                if (player.isPlaying) {
                    player.stop()
                }
                if (iteration < 10) {
                    resetList()
                } else {
                    stopCounter()
                }
                if (isCurrentCategoryUrdu) {
                    commonWordList.add(urduWordList[0])
                } else {
                    commonWordList.add(englishWordList[0])
                }


                if (isSecondPlayerPlaying) {
                    player2Score++
                } else {
                    player1Score++
                }
                println("Player 1 score $player1Score\n player 2 score $player2Score")


            }

            if (iteration == 10 && isSecondPlayerPlaying) {

                if (player1Score == player2Score) {
                    val action = GuessFragmentDirections.actionNavToResult().setWinner("It's a Tie")
                    navController.navigate(action)
                }

                if (player1Score > player2Score) {
                    val action =
                        GuessFragmentDirections.actionNavToResult().setWinner("Player 1 Won")
                    navController.navigate(action)
                }
                if (player1Score < player2Score) {
                    val action =
                        GuessFragmentDirections.actionNavToResult().setWinner("Player 2 Won")
                    navController.navigate(action)
                }

            }

            if (iteration == 10 && !isSecondPlayerPlaying) {
                isSecondPlayerPlaying = true
                guessGameStart("Player 2", isSecondPlayerPlaying, isCurrentCategoryUrdu)
            }


        }

        binding.linearRestart.setOnClickListener {
            restartGame()
        }


        selectCategory()

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
//        print("Player 1 score:$player1Score")
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
        binding.circularCountdown.create(1, 15, CircularCountdown.TYPE_SECOND)
            .listener(object : CircularListener {
                override fun onTick(progress: Int) {
                    if (progress == 15) {
                        binding.circularCountdown.stop()
                        playSound()
                    }
                }

                override fun onFinish(newCycle: Boolean, cycleCount: Int) {

                }
            }).start()
    }

    private fun resetList() {
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


        englishWordList = mutableListOf(
            "Steak",
            "Tea",
            "Coffee",
            "The Expendable",
            "You can't see me",
            "What the Rock is cooking",
            "Stuart Little",
            "Man hole",
            "Bald",
            "Tom Cruise",
            "Angelina Jolie",
            "Jackie Chan",
            "Bruce Lee",
            "Alexandra Darrario",
            "Chris Hemsworth",
            "Robert Downey JR",
            "Hulk",
            "Jason Statham",
            "The Mechanic",
            "Chimpanzee",
            "Beyonce",
            "Laxative",
            "Sylvester Stallone",
            "Terminator",
            "Loose Motion",
            "Burp",
            "Fart",
            "Jason Statham",
            "Paul Walker",
            "Super Lazy",
            "Pokemon",
            "Tom & Jerry",
            "No Pain No Gain",
            "Bond. James Bond",
            "I'll Be Back",
            "Hasta la vista, baby",
            "la-la land",
            "Batman",
            "Super man",
            "Spider Man"
        )


        if (isCurrentCategoryUrdu) {
            urduWordList.shuffle()
            urduWordList.shuffle()

            if (commonWordList != null) {
                if (commonWordList.size > 0) {
                    if (commonWordList.contains(urduWordList[0])) {
                        resetList()
                    } else {
                        binding.tvGuessData.text = urduWordList[0]
                    }
                } else {
                    binding.tvGuessData.text = urduWordList[0]
                }
            } else {
                binding.tvGuessData.text = urduWordList[0]
            }
        } else {
            englishWordList.shuffle()
            englishWordList.shuffle()

            if (commonWordList != null) {
                if (commonWordList.size > 0) {
                    if (commonWordList.contains(englishWordList[0])) {
                        resetList()
                    } else {
                        binding.tvGuessData.text = englishWordList[0]
                    }
                } else {
                    binding.tvGuessData.text = englishWordList[0]
                }
            } else {
                binding.tvGuessData.text = englishWordList[0]
            }
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
                        iteration = 0
                    }
                    startQuiz()

                    dialog.dismiss()

                }
            })

            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()


    }

    private fun selectCategory() {
        lateinit var alertDialog: LottieAlertDialog
        alertDialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_LOADING)
            .setTitle("Select Category")
            .setDescription("Please select the segment category.")
            .setPositiveText("Urdu/Hindi")
            .setNegativeText("English")

            .setPositiveButtonColor(Color.parseColor("#069509"))
            .setNegativeButtonColor(Color.parseColor("#FF03DAC5"))
            .setPositiveTextColor(Color.parseColor("#ffffff"))
            .setNegativeTextColor(Color.parseColor("#ffffff"))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    isCurrentCategoryUrdu = true
                    guessGameStart("Player 1", isSecond = false, isUrdu = isCurrentCategoryUrdu)
                    dialog.dismiss()
                }
            }

            ).setNegativeListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    isCurrentCategoryUrdu = false
                    guessGameStart("Player 1", isSecond = false, isUrdu = isCurrentCategoryUrdu)
                    dialog.dismiss()

                }
            })

            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()


    }


    private fun exitDialog() {
        lateinit var alertDialog: LottieAlertDialog
        alertDialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_QUESTION)
            .setTitle("Confirmation")
            .setDescription("Are you sure you want to quit?")
            .setPositiveText("Yes")
            .setNegativeText("No")

            .setPositiveButtonColor(Color.parseColor("#069509"))
            .setNegativeButtonColor(Color.parseColor("#FF03DAC5"))
            .setPositiveTextColor(Color.parseColor("#ffffff"))
            .setNegativeTextColor(Color.parseColor("#ffffff"))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {

                    activity?.finish()
                    dialog.dismiss()
                }
            }

            ).setNegativeListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {

                    dialog.dismiss()

                }
            })

            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()


    }

    override fun onAttach(@NonNull context: Context) {
        super.onAttach(context)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }


    private fun startQuiz() {
        resetList()
    }

    private fun restartGame() {
        iteration = 0
        player2Score = 0
        player1Score = 0
        binding.circularCountdown.stop()
        binding.circularCountdown.setProgress(0)
        selectCategory()

    }

    private fun initBannerAd() {
        try {
            val adView = AdView(context)
            adView.adSize = AdSize.BANNER

            adView.adUnitId = admobUnitIt

            val adRequest = AdRequest.Builder().build()
            binding.adView.loadAd(adRequest)
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }
}