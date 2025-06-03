package com.example.hw1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    //Default global values for change and use
    var balance = 10
    var betValue = 1
    var numOne = 0
    var numTwo = 0
    var numThree = 0

    //Cheating values tracks cheat count as well as if cheat is active
    private var cheatTime = false
    private var cheatPoints = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        //on start in here
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val result = findViewById<TextView>(R.id.resultsText)

        //Replaces to "null" text on startup as there is no current result
        result.text = ""

        //randomizeNums test on startup
        randomizeNums()

    }

    fun reset(view: View){
        //For changing values to default after
        val balanceDisplay = findViewById<TextView>(R.id.balAmmount)
        val playButton = findViewById<Button>(R.id.play)
        val results = findViewById<TextView>(R.id.resultsText)
        val betText = findViewById<TextView>(R.id.betAmmount)

        //Reset these values to the standard
        balance = 10
        betValue = 1
        cheatPoints = 0

        //sets displayed values and text to default on start
        //Other than nums on the slot machine because they are randomized on start and next play
        playButton.setEnabled(true)

        results.text = ""
        betText.text = "$$betValue"
        balanceDisplay.text = ("$$balance")
    }

    fun playBet(view: View){

        //displays that need to be changed later starting here
        val balanceDisplay = findViewById<TextView>(R.id.balAmmount)
        val playButton = findViewById<Button>(R.id.play)
        val results = findViewById<TextView>(R.id.resultsText)
        //randomize nums and values

        //You cannot lose if you cheat and that is what this does
        if (cheatTime){
            cheatWin()
        }else {
            //does this if not cheating
            randomizeNums()
        }
        //if all three numbers are the same give credit and tell user
        if(numOne == numTwo && numTwo == numThree){
            balance = balance+(betValue*5)
            balanceDisplay.text = ("$$balance")
            results.setTextColor(Color.parseColor("#77DF3E"))
            val winAmmount = betValue * 5
            results.text = "Hooray! You've won $$winAmmount!"

        }else{
            //if user does not win tell them and remove credits
            balance = balance - betValue
            balanceDisplay.text = ("$$balance")
            results.setTextColor(Color.parseColor("#FF5959"))
            results.text = "You lost. try again!"
        }

        //if balance is exceeded by current bet change current bet to balance
        if (balance < betValue){
            betValue = balance
            val betText = findViewById<TextView>(R.id.betAmmount)
            betText.text = "$$betValue"
        }
        //disable play button if 0 is reached
        // or surpassed (which should not happen but just in case)
        if (balance <= 0){
            playButton.setEnabled(false)
            results.text = "You lost, the game is over!"
        }

        cheatPoints = 0

    }

    //Changes bet value up or down and then sets the text on screen to be that value
    fun changeBet(view: View){

        //Checks which button was pressed and if that value increase or decrease would
        //break outside of the bounds

        if (view.id == R.id.addButton && betValue < balance){
            betValue++
        }else if(view.id == R.id.subtractButton && betValue != 1){
            betValue--
        }

        //Sets text to value
        val betText = findViewById<TextView>(R.id.betAmmount)
        betText.text = "$$betValue"

    }



    //Randomizes int values and sets them to displayed values
    private fun randomizeNums(){

        //Gets positions of numbers and text for later change
        val firstnum = findViewById<TextView>(R.id.num1)
        val seccondNum = findViewById<TextView>(R.id.num2)
        val thirdNum = findViewById<TextView>(R.id.num3)

        //creates random values and sets them to displayed values
        numOne = Random.nextInt(1,9)
        numTwo = Random.nextInt(1,9)
        numThree = Random.nextInt(1,9)
        firstnum.text = numOne.toString()
        seccondNum.text = numTwo.toString()
        thirdNum.text = numThree.toString()


    }

    // for dirty cheaters click the last number 3 times and get a win on the next roll
    fun cheat(view: View){
        cheatPoints++
        if (cheatPoints >= 3){
            cheatTime = true
        }

    }

    //When the cheater has done what they must this is run on next play
    //Makes the win go through and look somewhat legitimate
    private fun cheatWin(){
        var winningnum = Random.nextInt(9)
        numOne = winningnum
        numTwo = winningnum
        numThree = winningnum
        cheatTime = false
        cheatPoints = 0

        val firstnum = findViewById<TextView>(R.id.num1)
        val seccondNum = findViewById<TextView>(R.id.num2)
        val thirdNum = findViewById<TextView>(R.id.num3)
        firstnum.text = numOne.toString()
        seccondNum.text = numTwo.toString()
        thirdNum.text = numThree.toString()
    }

}