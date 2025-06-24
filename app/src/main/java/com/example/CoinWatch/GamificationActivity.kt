package com.example.CoinWatch

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class GamificationActivity : AppCompatActivity() {

    private lateinit var tvPoints: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var rgOptions: RadioGroup
    private lateinit var btnSubmit: Button
    private lateinit var ivBadge: ImageView
    private lateinit var tvLevel: TextView

    private var currentPoints = 0
    private var currentQuestionIndex = 0

    private val questions = listOf(
        QuizQuestion("What is the best way to save money?", listOf("Spend less than you earn", "Buy on credit", "Avoid budgets", "Ignore bills"), 0),
        QuizQuestion("Which one is a fixed expense?", listOf("Rent", "Eating out", "Movies", "Clothes"), 0),
        QuizQuestion("What should you do before investing?", listOf("Research the option", "Guess the market", "Ask friends only", "Ignore risks"), 0),
        QuizQuestion("What is a budget?", listOf("A plan for spending money", "A type of loan", "Credit card limit", "Savings account"), 0),
        QuizQuestion("Why save money?", listOf("For emergencies", "To spend more", "To avoid banks", "To borrow more"), 0),
        QuizQuestion("What is interest?", listOf("Money earned on savings", "A fee for borrowing", "A type of budget", "A bill to pay"), 1),
        QuizQuestion("What does debt mean?", listOf("Money you owe", "Money you own", "Money saved", "Money gifted"), 0),
        QuizQuestion("Which is a variable expense?", listOf("Electricity bill", "Rent", "Car loan", "Mortgage"), 0),
        QuizQuestion("What is an emergency fund?", listOf("Money saved for unexpected costs", "A credit card", "A type of insurance", "A bank loan"), 0),
        QuizQuestion("What is a good financial habit?", listOf("Tracking expenses", "Ignoring bills", "Spending impulsively", "Avoiding budgets"), 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamification)

        tvPoints = findViewById(R.id.tvPoints)
        tvQuestion = findViewById(R.id.tvQuestion)
        rgOptions = findViewById(R.id.rgOptions)
        btnSubmit = findViewById(R.id.btnSubmit)
        ivBadge = findViewById(R.id.ivBadge)
        tvLevel = findViewById(R.id.tvLevel)

        currentPoints = getPointsFromPrefs()
        updatePointsDisplay()
        loadQuestion()

        btnSubmit.setOnClickListener {
            val selectedId = rgOptions.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedIndex = rgOptions.indexOfChild(findViewById(selectedId))
            checkAnswer(selectedIndex)
        }
    }

    private fun loadQuestion() {
        if (currentQuestionIndex >= questions.size) {
            // Quiz finished, no more questions to load
            return
        }

        val question = questions[currentQuestionIndex]
        tvQuestion.text = question.question

        for (i in 0 until rgOptions.childCount) {
            val rb = rgOptions.getChildAt(i) as RadioButton
            rb.text = question.options[i]
        }

        rgOptions.clearCheck()

        // Clear badge and level while quiz is in progress
        ivBadge.setImageResource(R.drawable.badge_none)
        tvLevel.text = ""
    }

    private fun checkAnswer(selectedIndex: Int) {
        val correctIndex = questions[currentQuestionIndex].correctAnswerIndex
        if (selectedIndex == correctIndex) {
            currentPoints += 10
            Toast.makeText(this, "Correct! +10 points", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show()
        }

        savePointsToPrefs(currentPoints)
        updatePointsDisplay()

        currentQuestionIndex++

        if (currentQuestionIndex >= questions.size) {
            // Quiz finished
            btnSubmit.isEnabled = false
            Toast.makeText(this, "Quiz finished! You earned $currentPoints points.", Toast.LENGTH_LONG).show()
            updateBadgeAndLevel()
        } else {
            loadQuestion()
        }
    }

    private fun updatePointsDisplay() {
        tvPoints.text = "Points: $currentPoints"
    }

    private fun updateBadgeAndLevel() {
        val badgeResId = getBadgeResource(currentPoints)
        val level = getLevel(currentPoints)

        ivBadge.setImageResource(badgeResId)
        tvLevel.text = "Level: $level"
    }

    private fun getBadgeResource(points: Int): Int {
        return when {
            points >= 100 -> R.drawable.badge_gold
            points >= 60 -> R.drawable.badge_silver
            points >= 30 -> R.drawable.badge_bronze
            else -> R.drawable.badge_none
        }
    }

    private fun getLevel(points: Int): String {
        return when {
            points >= 100 -> "Gold"
            points >= 60 -> "Silver"
            points >= 30 -> "Bronze"
            else -> "No Level"
        }
    }

    private fun savePointsToPrefs(points: Int) {
        val prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("USER_POINTS", points).apply()
    }

    private fun getPointsFromPrefs(): Int {
        val prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return prefs.getInt("USER_POINTS", 0)
    }

    data class QuizQuestion(
        val question: String,
        val options: List<String>,
        val correctAnswerIndex: Int
    )
}
