import java.util.Random
import java.util.Scanner
import groovy.util.Eval

class TwentyFourGame {
	public static main (def args){
		def score = 0
		def timesPlayed = 0
		def playAgain = 'Y'
		Scanner getNew = new Scanner(System.in)

		println "Welcome to the game 24!"
		
		//Play loop
		while(playAgain == 'Y'){

			timesPlayed++
			score = runGame(score)
			showAverageScore(timesPlayed, score)
			println "Would you like to play again? (Y/N)"
			playAgain = getNew.nextLine()
			playAgain = playAgain.toUpperCase()		
		}
		
		//End game and show score
		println "Thanks for playing."
		showAverageScore(timesPlayed, score)
	}
	
	/**
	 * runGame handles a single run of the game.
	 * @param score going into the game
	 * @return total score after the game
	 */
	public static int runGame(score){
		Random rand = new Random()
		def numberList = []
		
		//Generate random numbers between 1-9.
		4.times{
			numberList << rand.nextInt(9) + 1
		}
		
		println "Try to make 24 from these numbers: ${numberList[0]}, ${numberList[1]}, ${numberList[2]}, ${numberList[3]}"
		Scanner get = new Scanner(System.in)
		
		//Get equation and evaluate
		def equation = get.nextLine()
		def answer = Eval.me(equation)
		def digitMultiplier = checkDigits(equation, numberList)
		println "Your equation evaluates to ${answer}"
		if(answer == 24){
			println "Congratulations, you win!"
		}
		else{
			println "Sorry, you did not win."
		}
		
		
		def pointsScored = 24 - (24 - answer).abs()
		if (pointsScored == 24){
			pointsScored = 100
			if(digitMultiplier == 4){
				println "Congratulations! You get a 4X bonus for using all digits and hitting 24"
			}
		}
		
		if(digitMultiplier == 0){
			println "Invalid equation. You get 0 points."
		}
		 
		score += pointsScored * digitMultiplier
		
		println "Your score for this round is ${pointsScored}"
		println "Your total score is ${score}"
		
		
		return score
	}
	
	/**
	 * showAverage Displays the average score.
	 * @param timesPlayed
	 * @param score
	 */
	public static void showAverageScore(timesPlayed, score){
		println "You have played ${timesPlayed} time(s) with an average score of ${score/timesPlayed}\n"
	}
	
	/**
	 * isDigit checks if a character is a digit. 
	 * @param letter
	 * @return integer value of the character or -1.
	 */
	public static int isDigit(letter){
		if (letter >= '1' && letter <= '9'){
			return Integer.valueOf(letter.toString())
		}
		else{
			return -1
		}
	}
	
	/**
	 * checkDigits checks the digits for a whole expression
	 * @param expression
	 * @param ranNums
	 * @return digit multiplier 
	 */
	public static int checkDigits(expression, ranNums){
		def i = 0
		def result = []
		while(i < expression.length()){
			def val = isDigit(expression.charAt(i))
			if(val != -1){
				result.add(Integer.valueOf(val))
			}
			i++
		}
		//List to check the correct values have been entered
		ArrayList checkList = []
		for (num in ranNums){
			checkList.add(num)
		}
		
		for(num in result){
			if(!checkList.remove(checkList.indexOf(num))){
				return 0
			}			
			
		}
		if(checkList.size() == 0){
			return 4
		}
		else{
			return 1
		}
	}
}
