package game.snake.ua;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private TextView textViewGameField;

    private int countSymbolOfLine;
    private int countLine;
    private int directBody;

    private String[][] strArrGameField;

    String strField = " ";
    Integer k;
    boolean isRunning  = false;
    boolean isFirstTouch = true;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textViewGameField = (TextView) view.findViewById(R.id.tv);

        textViewGameField.setOnTouchListener(this);
        textViewGameField.setText("Please, touch to screen for start game");

        return view;
    }

    private void  getSizeGameField() {
        int widthGameField = textViewGameField.getMeasuredWidth();
        int heightGameField = textViewGameField.getMeasuredHeight();

        countSymbolOfLine = (int) (widthGameField / textViewGameField.getPaint().measureText(" "));
        countLine = heightGameField / textViewGameField.getLineHeight();
    }

    private void createGameField() {
        //***create array game field***
        strArrGameField = new String[countLine][countSymbolOfLine];
        //***draw game field***
        for (int i = 0; i < countLine; i++) {
            for (int j = 0; j < countSymbolOfLine; j++) {
                strArrGameField[i][j] = ".";
                strArrGameField[0][j] = "#";
            }
        }
        //***determine the position of the snake's head***
        int posHeadX = countLine / 2;
        int posHeadY = countSymbolOfLine / 2;

        //***determine the position of the snake's body***
        directBody = new Random().nextInt(4);
        strArrGameField[posHeadX][posHeadY] = "@";
        switch (directBody) {
            case 0:
                strArrGameField[posHeadX+1][posHeadY] = "@";
                strArrGameField[posHeadX+2][posHeadY] = "@";
                break;
            case 1:
                strArrGameField[posHeadX][posHeadY+1] = "@";
                strArrGameField[posHeadX][posHeadY+2] = "@";
                break;
            case 2:
                strArrGameField[posHeadX-1][posHeadY] = "@";
                strArrGameField[posHeadX-2][posHeadY] = "@";
                break;
            case 3:
                strArrGameField[posHeadX][posHeadY-1] = "@";
                strArrGameField[posHeadX][posHeadY-2] = "@";
                break;
        }
    }

    private void visibleGameField() {
        strField = "";
        for (int i = 0; i < countLine; i++) {
            for (int j = 0; j < countSymbolOfLine; j++) {
                strField += strArrGameField[i][j];
            }
        }
        textViewGameField.setText(strField);
    }

    //***cX - current x
    //***cY - current y
    //***dX - destination x
    //***dY - destination y
    private boolean moveSnake(int cX, int cY, int dX, int dY ) {
        boolean runResult = false;
        switch (directBody) {

            case 0:
                if ((cX + k) < (countLine - 2)) {
                    strArrGameField[cX + k - 1][cY] = ".";
                    strArrGameField[cX + 2 + k][cY] = "@";
                    visibleGameField();
                    k++;
                    runResult = true;
                } else runResult = false;
                break;
            case 1:
                if ((cY + k) < (countSymbolOfLine - 2)) {
                    strArrGameField[cX][cY + k - 1] = ".";
                    strArrGameField[cX][cY + 2 + k] = "@";
                    visibleGameField();
                    k++;
                    runResult = true;
                } else runResult = false;
                break;
            case 2:
                if ((cX - k) > 3) {
                    strArrGameField[cX - k + 1][cY] = ".";
                    strArrGameField[cX - 2 - k][cY] = "@";
                    visibleGameField();
                    k++;
                    runResult = true;
                } else runResult = false;
                break;
            case 3:
                if ((cY - k) > 3) {
                    strArrGameField[cX][cY - k + 1] = ".";
                    strArrGameField[cX][cY - 2 - k] = "@";
                    visibleGameField();
                    k++;
                    runResult = true;
                } else runResult = false;
                break;
        }
        return runResult;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isFirstTouch) {
            getSizeGameField();
            createGameField();
            visibleGameField();
            isFirstTouch = false;

            final int x = countLine / 2;
            final int y = countSymbolOfLine / 2;

            (new Thread(new Runnable() {
                @Override
                public void run() {
                    k = 0;
                    isRunning = true;
                    while (!Thread.interrupted())
                        try {
                            Thread.sleep(500);
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (isRunning) {
                                        isRunning = moveSnake(x, y, x, x);
                                    } else Thread.interrupted();
                                }
                            });
                        } catch (InterruptedException e) {

                        }
                }
            })).start();
        } else {


        }
        return false;
    }
}