package com.example.cardassignment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class CardActivity : AppCompatActivity() {
    lateinit var cardNumber: EditText
    private lateinit var expDate: EditText
    private lateinit var username: EditText
    private lateinit var cvv: EditText

    private lateinit var cardNumbertext: TextView
    private lateinit var cardUsernameText: TextView
    private lateinit var cardexpDateText: TextView

    private lateinit var update_bttn: Button
    private lateinit var add_bttn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        cardNumber = findViewById(R.id.card_number)
        expDate = findViewById(R.id.card_exp_date)
        username = findViewById(R.id.card_username)
        cvv = findViewById(R.id.card_cvv)

        cardNumbertext = findViewById(R.id.car_number_tv)
        cardUsernameText = findViewById(R.id.username_tv)
        cardexpDateText = findViewById(R.id.expdate_tv)

        update_bttn = findViewById(R.id.update_bttn)
        add_bttn = findViewById(R.id.add_card_bttn)

        setSupportActionBar(findViewById(R.id.titleBar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_arrow_icon)
        supportActionBar!!.title = ""

        cardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

                textStyleChange(cardNumber)

                val inputlength: Int = cardNumber.text.length

                if (cardNumber.text.endsWith(" "))
                    return
                if (cardNumber.text.isNotEmpty() && ((inputlength % 5) == 0)) {
                    cardNumber.setText(
                        StringBuilder(cardNumber.text).insert(cardNumber.length() - 1, " ")
                            .toString()
                    )
                    cardNumber.setSelection(cardNumber.text.length)
                }
            }
        })
        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                textStyleChange(username)
            }
        })
        cvv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                textStyleChange(cvv)
            }
        })
        expDate.setOnClickListener() {
            val calendar: Calendar = Calendar.getInstance()
            val date = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val datePickerDialog = DatePickerDialog(
                this, AlertDialog.THEME_HOLO_DARK,
                { view, year, monthOfYear, dayOfMonth -> expDate.setText("${monthOfYear + 1}/${year % 100}") },
                year,
                month,
                date
            )
            (datePickerDialog.datePicker as ViewGroup).findViewById<View>(
                Resources.getSystem().getIdentifier("day", "id", "android")
            ).visibility = View.GONE
            datePickerDialog.show()
            expDate.typeface = Typeface.DEFAULT
        }

        update_bttn.setOnClickListener {
            if (!checkDetails()) {
                Toast.makeText(this@CardActivity, "Details are not entered !", Toast.LENGTH_SHORT)
                    .show()
            } else {
                cardNumbertext.text = stripCardnumber(cardNumber.text.toString())
                cardUsernameText.text = username.text.toString()
                cardexpDateText.text = expDate.text.toString()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun checkDetails(): Boolean {
        if ((cardNumber.text.length >= 16) && username.text.isNotEmpty() && expDate.text.isNotEmpty() && cvv.text.isNotEmpty()) return true
        return false
    }

    fun textStyleChange(textValue: EditText) {
        textValue.typeface = Typeface.DEFAULT
        if (textValue.text.isEmpty()) textValue.setTypeface(textValue.typeface, Typeface.ITALIC)
    }

    fun stripCardnumber(cardNumber: String): String {
        var stripedCardNumber = cardNumber.replace("\\s".toRegex(), "")
        for (i in 1..stripedCardNumber.length) {
            if (i == 4 || i == 10 || i == 16) {
                stripedCardNumber = StringBuilder(stripedCardNumber).insert(i, "  ").toString()
            }
        }
        return stripedCardNumber
    }
}

