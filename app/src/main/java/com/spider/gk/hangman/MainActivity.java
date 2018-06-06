package com.spider.gk.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.text.AllCapsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public String word, letterToCheck, wrongLetters = "Wrong guesses:";
    public int wrongCnt = 0, best;
    char Word[] = new char[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word = wordToGuess();
        Word = word.toCharArray();
        SharedPreferences sp = getSharedPreferences("bestScore", Context.MODE_PRIVATE);
        best = sp.getInt("bestScore", 100);
        TextView bestscore = (TextView) findViewById(R.id.best);
        bestscore.setText("" + String.format("%02d", best));
        if(best==100){bestscore.setText("NA");}
        TextView wordShown = (TextView) findViewById(R.id.linearDash);
        wordShown.setTransformationMethod(new PasswordTransformationMethod());
        wordShown.setText(word);
        Log.i("Word", "Word is: " + word);
        Button checkGuess = (Button) findViewById(R.id.checkGuess);
        checkGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("letter", "letter: " + letterToCheck);

                EditText letter = (EditText) findViewById(R.id.letter);
                letterToCheck = letter.getText().toString().toUpperCase();
                Log.i("letter", "letter: " + letterToCheck);
                if (word.contains(letterToCheck) && letterToCheck != "") {
                    correctGuess();
                } else {
                    wrongGuess();
                }
                letter.setText("");
            }
        });

    }

    public String wordToGuess() {
        String[] wordList = getResources().getStringArray(R.array.Countries);
        return wordList[new Random().nextInt(25)];
    }

    public void correctGuess() {
        TextView wordShown = (TextView) findViewById(R.id.linearDash);
        wordShown.setTransformationMethod(null);
        wordShown.setText(word);
        updateBestScore();
        ImageView hangMan=(ImageView) findViewById(R.id.hangMan);
        hangMan.setImageResource(R.drawable.h02_0);
        Toast.makeText(this, "Thanks!!You Saved Me..", Toast.LENGTH_SHORT).show();
        EditText editText=(EditText) findViewById(R.id.letter);
        editText.setVisibility(View.INVISIBLE);
        TextView textView=(TextView) findViewById(R.id.textView3);
        textView.setText("Thanks!!You saved the Man");
        Button check=(Button) findViewById(R.id.checkGuess);
        check.setVisibility(View.INVISIBLE);
    }

    public void wrongGuess() {
        wrongCnt++;
        TextView wrongLettersText = (TextView) findViewById(R.id.lettersGuessed);
        wrongLetters += letterToCheck + ",";
        wrongLettersText.setText(wrongLetters);
        TextView score = (TextView) findViewById(R.id.score);
        score.setText("" + String.format("%02d", wrongCnt));
        Toast.makeText(this, "Wrong Guess", Toast.LENGTH_SHORT).show();
        displayHangman();
        if(wrongCnt==8){
            Toast.makeText(this,"You killed me!!!",Toast.LENGTH_LONG);
            TextView textView=(TextView) findViewById(R.id.textView3);
            textView.setText("Damn!!!You killed the Man");
            Button check=(Button) findViewById(R.id.checkGuess);
            check.setVisibility(View.INVISIBLE);
            TextView dash=(TextView) findViewById(R.id.linearDash);
            dash.setVisibility(View.INVISIBLE);
            EditText editText=(EditText) findViewById(R.id.letter);
            editText.setVisibility(View.INVISIBLE);
        }
    }

    public void updateBestScore() {
        if (wrongCnt < best) {
            TextView bestscore = (TextView) findViewById(R.id.best);
            bestscore.setText("" + String.format("%02d", wrongCnt));

            SharedPreferences sp = getSharedPreferences("bestScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("bestScore", wrongCnt);
            editor.commit();
        }
    }

    public void displayHangman() {
        ImageView hangMan = (ImageView) findViewById(R.id.hangMan);
        switch (wrongCnt) {
            case 0:
                hangMan.setImageResource(R.drawable.h02_0);
                break;

            case 1:
                hangMan.setImageResource(R.drawable.h02_1);
                break;

            case 2:
                hangMan.setImageResource(R.drawable.h02_2);
                break;
            case 3:
                hangMan.setImageResource(R.drawable.h02_3);
                break;
            case 4:
                hangMan.setImageResource(R.drawable.h02_4);
                break;
            case 5:
                hangMan.setImageResource(R.drawable.h02_5);
                break;
            case 6:
                hangMan.setImageResource(R.drawable.h02_6);
                break;
            case 7:
                hangMan.setImageResource(R.drawable.h02_7);
                break;
            case 8:
                hangMan.setImageResource(R.drawable.h02_8);
                break;
        }

    }
}
