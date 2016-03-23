package game.snake.ua;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private TextView textViewGameField;

    private int widthGameField;
    private int heightGameField;
    private int countSymbolOfLine;
    private int countLine;
    private int directBody;

    private String[][] strArrGameField;

    String strField = " ";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textViewGameField = (TextView) view.findViewById(R.id.tv);

        textViewGameField.setOnTouchListener(this);
        textViewGameField.setText("Please, touch to screen for start game");
      /*  DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthGameField = metrics.widthPixels;
        heightGameField = metrics.heightPixels;
        float c = textViewGameField.getPaint().measureText(" ");
        countSymbolOfLine = (int) (widthGameField / c);*/

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
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

    private void moveOfSnake(final int x, final int y) {

            Timer t = new Timer(false);
            t.schedule(new TimerTask() {

                @Override
                public void run() {
                    int k = 0;
                    while (!((x + k) == (countLine - 3))) {
                    strArrGameField[x + k - 2][y] = ".";
                    strArrGameField[x + k][y] = "@";
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            visibleGameField();
                        }
                    });k++;
                }}
            }, 6000);



       /* switch (directBody) {
            case 0:
                while (!(x == countLine - 1 || y == countSymbolOfLine ||  x == 0 || y == 0)) {
                    x ++;
                    strArrGameField[x][y] = "@";
                    strArrGameField[x + 1][y] = "@";
                    strArrGameField[x + 2][y] = "@";
                    visibleGameField();
                    Thread.sleep(100000);
                }
                break;
            case 1:

                while (!(x == countLine - 1 || y == countSymbolOfLine ||  x == 0 || y == 0)) {
                    x ++;
                    strArrGameField[x][y] = "@";
                    strArrGameField[x + 1][y] = "@";
                    strArrGameField[x + 2][y] = "@";
                    visibleGameField();
                    Thread.sleep(100000);
                }
                break;
            case 2:
                while (!(x == countLine -1  || y == countSymbolOfLine ||  x == 0 || y == 0)) {
                    x ++;
                    strArrGameField[x][y] = "@";
                    strArrGameField[x + 1][y] = "@";
                    strArrGameField[x + 2][y] = "@";
                    visibleGameField();
                    Thread.sleep(1000);
                }
                break;
            case 3:
                while (!(x == countLine || y == countSymbolOfLine ||  x == 0 || y == 0)) {
                    x ++;
                    strArrGameField[x][y] = "@";
                    strArrGameField[x + 1][y] = "@";
                    strArrGameField[x + 2][y] = "@";
                    visibleGameField();
                    Thread.sleep(1000);
                }
                break;
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        widthGameField = textViewGameField.getMeasuredWidth();
        heightGameField = textViewGameField.getMeasuredHeight();
        countSymbolOfLine = (int) (widthGameField / textViewGameField.getPaint().measureText(" "));
        countLine = heightGameField / textViewGameField.getLineHeight();


        createGameField();
        visibleGameField();
        int posHeadX = countLine / 2;
        int posHeadY = countSymbolOfLine / 2;
        moveOfSnake(posHeadX, posHeadY);

        return false;
    }
}