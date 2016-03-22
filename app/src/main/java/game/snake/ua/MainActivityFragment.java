package game.snake.ua;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener{
    private TextView textViewGameField;

    private int widthGameField;
    private int heightGameField;
    private int countSybmolOfLine;

    private String[][] strArrGameField;
    Button button;

    String strField = " ";
    int k = 1;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textViewGameField = (TextView) view.findViewById(R.id.tv);
        button = (Button) view.findViewById(R.id.btn);
        button.setOnClickListener(this);
        textViewGameField.setOnFocusChangeListener(this);
        textViewGameField.setText("jjj");
      /*  DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        widthGameField = metrics.widthPixels;
        heightGameField = metrics.heightPixels;

        float c = textViewGameField.getPaint().measureText(" ");
        countSybmolOfLine = (int) (widthGameField / c);*/

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        textViewGameField.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        widthGameField = textViewGameField.getMeasuredWidth();
                        heightGameField = textViewGameField.getMeasuredHeight();
                        float c = textViewGameField.getPaint().measureText(" ");
                        countSybmolOfLine = (int) (widthGameField / c);
                    }
                });
    }

    private void createGameField() {
        strArrGameField = new String[countSybmolOfLine][countSybmolOfLine];
        for (int i = 0; i < countSybmolOfLine; i++) {

            for (int j = 0; j < countSybmolOfLine; j++) {
                strArrGameField[i][j] = " ";
                if ( i == new Random().nextInt(countSybmolOfLine)
                        && j == new Random().nextInt(countSybmolOfLine)) {
                    strArrGameField[i][j] = "@";
                    switch (new Random(3).nextInt()) {
                        case 0:
                            strArrGameField[i+1][j] = "@";
                            strArrGameField[i+2][j] = "@";
                            break;
                        case 1:
                            strArrGameField[i][j+1] = "@";
                            strArrGameField[i][j+2] = "@";
                            break;
                        case 2:
                            strArrGameField[i-1][j] = "@";
                            strArrGameField[i-2][j] = "@";
                            break;
                        case 3:
                            strArrGameField[i][j-1] = "@";
                            strArrGameField[i][j-2] = "@";
                            break;

                    }
                }
            }
        }
    }

    private void visibleGameField() {
        strField = "";
        for (int i = 0; i < countSybmolOfLine; i++) {
            for (int j = 0; j < countSybmolOfLine; j++) {
                strField += strArrGameField[i][j];
            }
        }
        textViewGameField.setText(strField);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (textViewGameField.isFocused()) {
            String y = "hhh";
        }
        //createGameField();
       // visibleGameField();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        String y = "hhh";
    }
}
