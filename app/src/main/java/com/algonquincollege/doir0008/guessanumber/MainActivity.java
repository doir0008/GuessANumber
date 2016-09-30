/**
 *  A guessing game app where the user guesses a number between 1 and 1000
 *  Author: Ryan Doiron (doir0008@algonquinlive.com)
 */

package com.algonquincollege.doir0008.guessanumber;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    RandomNumber theNumber = new RandomNumber();
    int guessCount = 0;
    private static final String ABOUT_DIALOG_TAG;

    static {
        ABOUT_DIALOG_TAG = "About Dialog";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theNumber.generateRandomNumber();

        Log.i("The random number is ", Integer.toString(theNumber.getCurrentRandomNumber()));

        // Setup button references
        Button guessButton = (Button) findViewById(R.id.btnGuess);
        Button resetButton = (Button) findViewById(R.id.btnReset);

        // Event handler for guess button
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reference the user guess field
                EditText userGuessText = (EditText) findViewById(R.id.guessInput);

                // VALIDATE the users guessed number
                // The user can only enter a whole number.
                int userGuess;
                try {
                    userGuess = Integer.parseInt(userGuessText.getText().toString());
                } catch(NumberFormatException e) {
                    userGuessText.setError("Your Guess Must be a Number.");
                    userGuessText.requestFocus();
                    return;
                }

                // VALIDATE the users guessed number is between 1 and 1000
                if (userGuess < 1 || userGuess > 1000) {
                    userGuessText.setError("You Must Guess A Number Between 1 And 1000");
                    userGuessText.requestFocus();
                    return;
                }

                guessCount++;

                // Check if the user reached the guess limit
                if (guessCount > 10) {
                    Toast.makeText(getApplicationContext(), "Please Reset.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // VALIDATE the win condition
                if (userGuess == theNumber.getCurrentRandomNumber()) {
                    if (guessCount < 5) {
                        Toast.makeText(getApplicationContext(), "You Guessed The Right Number! SUPERIOR WIN!", Toast.LENGTH_SHORT).show();
                    }

                    if (guessCount >= 6 && guessCount <= 10) {
                        Toast.makeText(getApplicationContext(), "You Guessed The Right Number! Excellent Win!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Check if the guess was too high or low
                    if (userGuess < theNumber.getCurrentRandomNumber()) {
                        Toast.makeText(getApplicationContext(), "You Guessed Too Low.", Toast.LENGTH_SHORT).show();
                        userGuessText.requestFocus();
                        // guessCount++;
                        Log.i("guessCount is ", Integer.toString(guessCount));
                    }

                    if (userGuess > theNumber.getCurrentRandomNumber()) {
                        Toast.makeText(getApplicationContext(), "You Guessed Too High.", Toast.LENGTH_SHORT).show();
                        userGuessText.requestFocus();
                        // guessCount++;
                        Log.i("guessCount is ", Integer.toString(guessCount));
                    }
                }

            }

        });

        // Event handler for reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theNumber.generateRandomNumber();
                guessCount = 0;
                Log.i("The NEW random num is ", Integer.toString(theNumber.getCurrentRandomNumber()));
                Log.i("guessCount reset to ", Integer.toString(guessCount));
            }
        });

        // Event handler for long click on reset button
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "The number to guess is " + theNumber.getCurrentRandomNumber() , Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
