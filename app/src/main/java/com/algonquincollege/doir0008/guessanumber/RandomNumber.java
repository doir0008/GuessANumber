/**
 *  A guessing game app where the user guesses a number between 1 and 1000
 *  Author: Ryan Doiron (doir0008@algonquinlive.com)
 */

package com.algonquincollege.doir0008.guessanumber;

import java.util.Random;

public class RandomNumber {
    protected Random random;
    protected int currentRandomNumber;

    public RandomNumber() {
        currentRandomNumber = 0;
        random = new Random();
    }

    public int generateRandomNumber() {
        currentRandomNumber = random.nextInt(1000 - 1) + 1;
        return currentRandomNumber;
    }

    public int getCurrentRandomNumber() { return currentRandomNumber; }
}
