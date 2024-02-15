package com.example.focoos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import android.view.GestureDetector;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.focoos.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView tapToPlay, timerTextView, gameOver;
    private ImageView yellowDot;
    private RelativeLayout mainLayout;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30000; // 30 de secunde
    private boolean timerRunning = false; // Adăugată pentru a urmări starea timerului
    private int score = 0; // Variabilă pentru scor
    private TextView scoreTextView;
    private String currentColorState = ""; // verde, rosu, galben
    private Handler colorChangeHandler = new Handler();
    private Runnable changeToRedRunnable;
    private Runnable changeToYellowRunnable;
    private TextView finalScoreTextView;
    private boolean gameIsOver = false;
    private TextView highScoreTextView;
    private Button backToMenuButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tapToPlay = findViewById(R.id.tapToPlay);
        gameOver = findViewById(R.id.gameOver);
        yellowDot = findViewById(R.id.yellowDot);
        mainLayout = findViewById(R.id.mainLayout);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView); // Inițializează TextView pentru scor
        finalScoreTextView = findViewById(R.id.finalScoreTextView);
        highScoreTextView = findViewById(R.id.highScoreTextView);
        displayHighScore();
        backToMenuButton = findViewById(R.id.backToMenuButton);

        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        // Pentru versiunile anterioare lui API 26
                        vibrator.vibrate(50);
                    }
                }
                findViewById(R.id.tutorialLayout).setVisibility(View.VISIBLE);
                resetGame();
            }
        });



        changeToRedRunnable = new Runnable() {
            @Override
            public void run() {
                changeToRed();
            }
        };

        changeToYellowRunnable = new Runnable() {
            @Override
            public void run() {
                changeToYellow();
            }
        };


        gameOver.setVisibility(View.GONE);
        timerTextView.setVisibility(View.GONE);
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mainLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(gameIsOver) {
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    findViewById(R.id.tutorialLayout).setVisibility(View.GONE);
                    // Coordonatele atingerii
                    float x = event.getRawX();
                    float y = event.getRawY();
                    startTimer();

                    // Limitele ImageView, ajustate pentru coordonatele relative la ecran
                    int[] location = new int[2];
                    yellowDot.getLocationOnScreen(location);
                    int left = location[0];
                    int top = location[1];
                    int right = left + yellowDot.getWidth();
                    int bottom = top + yellowDot.getHeight();

                    // Verifică dacă atingerea este în interiorul ImageView
                    if (x >= left && x <= right && y >= top && y <= bottom) {

                        if (vibrator != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                vibrator.vibrate(50);
                            }
                        }

                        if (currentColorState.equals("verde")) {
                            score += 6;
                        } else if (currentColorState.equals("rosu")) {
                            score -= 10;
                        } else if (currentColorState.equals("galben")) {
                            score += 3;
                        }

                        changeDotColor();
                        updateScoreText(); // Actualizează textul pentru scor

                        if (tapToPlay.getVisibility() == View.VISIBLE) {
                            tapToPlay.setVisibility(View.GONE);
                        }
                        timerTextView.setVisibility(View.VISIBLE);
                        yellowDot.setVisibility(View.VISIBLE);
                        moveDot();
                        return true; // Atingere în zona yellowDot
                    } else if (tapToPlay.getVisibility() == View.VISIBLE) {

                        // Dacă "Tap to Play" este vizibil și se atinge ecranul în afara yellowDot
                        tapToPlay.setVisibility(View.GONE);
                        yellowDot.setVisibility(View.VISIBLE);
                        timerTextView.setVisibility(View.VISIBLE);
                        moveDot();
                        return true; // Atingere oriunde pe ecran pentru a începe jocul
                    }
                }
                return false; // Atingere în afara yellowDot, nu face nimic
            }
        });
    }

    private void startTimer() {
        if (!timerRunning) { // Verifică dacă timerul nu este deja în execuție
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished < 1000) {
                        // Finalizează timer-ul imediat
                        onFinish();
                        countDownTimer.cancel();
                    } else {
                        timeLeftInMillis = millisUntilFinished;
                        updateCountDownText();
                    }
                }

                @Override
                public void onFinish() {
                    timeLeftInMillis = 0;
                    updateCountDownText();
                    gameOver.setVisibility(View.VISIBLE);
                    yellowDot.setVisibility(View.GONE);
                    timerTextView.setVisibility(View.GONE);
                    finalScoreTextView.setText(String.format("Scor final: %d", score)); // Setează textul pentru scorul final
                    finalScoreTextView.setVisibility(View.VISIBLE); // Afișează scorul
                    scoreTextView.setVisibility(View.GONE);
                    gameIsOver = true; // Actualizăm starea jocului la finalizare
                    timerRunning = false; // Actualizează starea timerului la finalizare
                    checkForHighScore();
                    displayHighScore();
                    highScoreTextView.setVisibility(View.VISIBLE);
                    backToMenuButton.setVisibility(View.VISIBLE);

                }
            }.start();
            timerRunning = true; // Actualizează starea timerului
        }
    }

    private void addTime(long timeInMillis) {
        timeLeftInMillis += timeInMillis;
        if (timerRunning) { // Verifică dacă timerul este în execuție
            countDownTimer.cancel();
            timerRunning = false; // Resetăm starea timerului
        }
        startTimer(); // Porniți un nou cronometru cu timpul actualizat
    }


    private void updateCountDownText() {
        runOnUiThread(() -> {
            int seconds = (int) (timeLeftInMillis / 1000);
            timerTextView.setText(String.valueOf(seconds));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void moveDot() {
        if(!gameIsOver) {
            Random random = new Random();

            // Definește o margine pe care să nu o depășești. De exemplu, 10% din lățimea sau înălțimea ecranului.
            int marginWidth = mainLayout.getWidth() / 10;
            int marginHeight = mainLayout.getHeight() / 10;

            // Ajustează zona în care `yellowDot` poate fi plasat pentru a evita marginile.
            int width = mainLayout.getWidth() - yellowDot.getWidth() - marginWidth * 2;
            int height = mainLayout.getHeight() - yellowDot.getHeight() - marginHeight * 2;

            // Asigură-te că 'x' și 'y' sunt între marginile definite.
            int x = marginWidth + random.nextInt(width > 0 ? width : 1);
            int y = marginHeight + random.nextInt(height > 0 ? height : 1);

            Log.d("moveDot", "Moving dot to: x=" + x + ", y=" + y);
            yellowDot.setX(x);
            yellowDot.setY(y);

            changeDotColor();
        }
    }

    // Metoda pentru actualizarea textului scorului
    private void updateScoreText() {
        scoreTextView.setText("Scor: " + score);
    }

    private void changeDotColor() {
        if(!gameIsOver) {
            colorChangeHandler.removeCallbacks(changeToRedRunnable);
            colorChangeHandler.removeCallbacks(changeToYellowRunnable);

            changeToGreen();

            colorChangeHandler.postDelayed(changeToRedRunnable, 500); // Schimbă în roșu după 0.5 secunde
            colorChangeHandler.postDelayed(changeToYellowRunnable, 1500); // Schimbă în galben după 1.5 secunde
        }
    }


    private void changeToGreen() {
        yellowDot.setImageResource(R.drawable.green_dot);
        currentColorState = "verde";
    }

    private void changeToRed() {
        yellowDot.setImageResource(R.drawable.red_dot);
        currentColorState = "rosu";
    }

    private void changeToYellow() {
        yellowDot.setImageResource(R.drawable.yellow_dot);
        currentColorState = "galben";
    }

    private void checkForHighScore() {
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0); // 0 este valoarea implicită dacă nu există un highscore salvat
        if(score > highScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highScore", score);
            editor.apply(); // Salvează noul highscore
            // Aici poți adăuga cod pentru a notifica utilizatorul despre noul highscore
        }
    }

    private void displayHighScore() {
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0); // 0 este valoarea implicită
        // Aici poți actualiza UI-ul cu highscore-ul, de exemplu:
        TextView highScoreTextView = findViewById(R.id.highScoreTextView);
        highScoreTextView.setText("Highscore: " + highScore);
    }

    private void resetGame() {
        // Ascunde ecranele de gameOver, finalScore, backToMenuButton și highScore si fa scoreTextView visibil
        gameOver.setVisibility(View.GONE);
        finalScoreTextView.setVisibility(View.GONE);
        highScoreTextView.setVisibility(View.GONE);
        backToMenuButton.setVisibility(View.GONE);
        scoreTextView.setVisibility(View.VISIBLE);

        // Resetare scor și alte variabile de stare, dacă este necesar
        score = 0;
        updateScoreText();
        gameIsOver = false;
        timerRunning = false;
        tapToPlay.setVisibility(View.VISIBLE); // Afișează din nou "Tap to Play"

        // Opțional: Dacă ai un timer care rulează, asigură-te că este oprit și resetat
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = 30000; // Resetarea timpului la valoarea inițială
        timerTextView.setText(""); // Resetare text timer
    }

}