// Generated by view binder compiler. Do not edit!
package com.example.focoos.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.focoos.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button backToMenuButton;

  @NonNull
  public final TextView finalScoreTextView;

  @NonNull
  public final TextView gameOver;

  @NonNull
  public final TextView highScoreTextView;

  @NonNull
  public final RelativeLayout mainLayout;

  @NonNull
  public final TextView scoreTextView;

  @NonNull
  public final TextView tapToPlay;

  @NonNull
  public final TextView timerTextView;

  @NonNull
  public final LinearLayout tutorialLayout;

  @NonNull
  public final ImageView yellowDot;

  private ActivityMainBinding(@NonNull RelativeLayout rootView, @NonNull Button backToMenuButton,
      @NonNull TextView finalScoreTextView, @NonNull TextView gameOver,
      @NonNull TextView highScoreTextView, @NonNull RelativeLayout mainLayout,
      @NonNull TextView scoreTextView, @NonNull TextView tapToPlay, @NonNull TextView timerTextView,
      @NonNull LinearLayout tutorialLayout, @NonNull ImageView yellowDot) {
    this.rootView = rootView;
    this.backToMenuButton = backToMenuButton;
    this.finalScoreTextView = finalScoreTextView;
    this.gameOver = gameOver;
    this.highScoreTextView = highScoreTextView;
    this.mainLayout = mainLayout;
    this.scoreTextView = scoreTextView;
    this.tapToPlay = tapToPlay;
    this.timerTextView = timerTextView;
    this.tutorialLayout = tutorialLayout;
    this.yellowDot = yellowDot;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backToMenuButton;
      Button backToMenuButton = ViewBindings.findChildViewById(rootView, id);
      if (backToMenuButton == null) {
        break missingId;
      }

      id = R.id.finalScoreTextView;
      TextView finalScoreTextView = ViewBindings.findChildViewById(rootView, id);
      if (finalScoreTextView == null) {
        break missingId;
      }

      id = R.id.gameOver;
      TextView gameOver = ViewBindings.findChildViewById(rootView, id);
      if (gameOver == null) {
        break missingId;
      }

      id = R.id.highScoreTextView;
      TextView highScoreTextView = ViewBindings.findChildViewById(rootView, id);
      if (highScoreTextView == null) {
        break missingId;
      }

      RelativeLayout mainLayout = (RelativeLayout) rootView;

      id = R.id.scoreTextView;
      TextView scoreTextView = ViewBindings.findChildViewById(rootView, id);
      if (scoreTextView == null) {
        break missingId;
      }

      id = R.id.tapToPlay;
      TextView tapToPlay = ViewBindings.findChildViewById(rootView, id);
      if (tapToPlay == null) {
        break missingId;
      }

      id = R.id.timerTextView;
      TextView timerTextView = ViewBindings.findChildViewById(rootView, id);
      if (timerTextView == null) {
        break missingId;
      }

      id = R.id.tutorialLayout;
      LinearLayout tutorialLayout = ViewBindings.findChildViewById(rootView, id);
      if (tutorialLayout == null) {
        break missingId;
      }

      id = R.id.yellowDot;
      ImageView yellowDot = ViewBindings.findChildViewById(rootView, id);
      if (yellowDot == null) {
        break missingId;
      }

      return new ActivityMainBinding((RelativeLayout) rootView, backToMenuButton,
          finalScoreTextView, gameOver, highScoreTextView, mainLayout, scoreTextView, tapToPlay,
          timerTextView, tutorialLayout, yellowDot);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
