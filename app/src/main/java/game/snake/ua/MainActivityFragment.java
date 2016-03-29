package game.snake.ua;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnTouchListener {
    private TextView textViewGameField;

    private int countSymbolOfLine;
    private int countLine;
    private int directBody;

    private String[][] strArrGameField;
    private ArrayList <Position> snakeMap;

    Integer k;

    private boolean isRunning  = false;
    private boolean isFirstTouch = true;

    private int posHeadX;
    private int posHeadY;

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
                strArrGameField[i][j] = "&#160;";
                strArrGameField[0][j] = "#";
            }
        }
    }

    private void createSnake() {
        //***create snake
        snakeMap = new ArrayList();

        //***determine the position of the snake's head***//
        posHeadX = countLine / 2;
        posHeadY = countSymbolOfLine / 2;

       /*Position position = new Position();
        position.setPos(posHeadX, posHeadY);
        snakeMap.add(position);*/

        //***determine the position of the snake's body***
        directBody = 2;

        switch (directBody) {
            case 0:
                for (int i = 0; i < 3; i++) {
                    Position position = new Position(posHeadX + i, posHeadY);
                    snakeMap.add(position);
                }
                break;
            case 1:
                for (int i = 0; i < 3; i++) {
                    Position position = new Position(posHeadX, posHeadY + i);
                    snakeMap.add(position);
                }
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    Position position = new Position(posHeadX - i, posHeadY);
                    snakeMap.add(position);
                }
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    Position position = new Position(posHeadX, posHeadY - i);
                    snakeMap.add(position);
                }
                break;
        }
        strArrGameField[snakeMap.get(0).getPosX()][snakeMap.get(0).getPosY()] = "@";
        strArrGameField[snakeMap.get(1).getPosX()][snakeMap.get(1).getPosY()] = "@";
        strArrGameField[snakeMap.get(2).getPosX()][snakeMap.get(2).getPosY()] = "@";
    }

    private void visibleGameField() {
        String strField = "";
        for (int i = 0; i < countLine; i++) {
            for (int j = 0; j < countSymbolOfLine; j++) {
                strField += strArrGameField[i][j];
            }
        }
        textViewGameField.setText(Html.fromHtml(strField));
    }

    private void updateGameField() {
        String strField = textViewGameField.getText().toString();

    }

    //***cX - current x
    //***cY - current y

    private boolean moveSnake() {
        boolean runResult = false;
        switch (directBody) {
            case 0://***Direction down***//
                if (snakeMap.get(snakeMap.size() - 1).getPosX() < countLine - 1) {
                    Position position = new Position((snakeMap.get(snakeMap.size() - 1).getPosX() + 1),
                                                    snakeMap.get(snakeMap.size() - 1).getPosY());

                    snakeMap.remove(0);
                    snakeMap.add(position);
                    runResult = true;
                } else runResult = false;
                break;
            case 1://***Direction left***//
                if (snakeMap.get(0).getPosY() >  1) {
                    Position position = new Position(snakeMap.get(0).getPosX(),
                            snakeMap.get(0).getPosY() - 1);

                    snakeMap.remove(snakeMap.size() - 1);
                    snakeMap.add(0, position);

                    runResult = true;
                } else runResult = false;
                break;
            case 2://***Direction top***//
                if (snakeMap.get(snakeMap.size() - 1).getPosX() > 1) {
                    Position position = new Position(snakeMap.get(snakeMap.size() - 1).getPosX() - 1,
                                                    snakeMap.get(snakeMap.size() - 1).getPosY());

                    snakeMap.remove(0);
                    snakeMap.add(position);

                    runResult = true;
                } else runResult = false;
                break;
            case 3://***Direction right***//
                if (snakeMap.get(0).getPosY() > 1) {
                    Position position = new Position(snakeMap.get(snakeMap.size() - 1).getPosX(),
                            snakeMap.get(snakeMap.size() - 1).getPosY() - 1);

                    snakeMap.remove(0);
                    snakeMap.add(position);
                    runResult = true;
                } else runResult = false;
                break;
        }
       /* strArrGameField[snakeMap.get(0).getPosX()][snakeMap.get(0).getPosY()] = "@";
        strArrGameField[snakeMap.get(1).getPosX()][snakeMap.get(1).getPosY()] = "@";
        strArrGameField[snakeMap.get(2).getPosX()][snakeMap.get(2).getPosY()] = "@";*/
        return runResult;
    }
    //xT - x from touch
    //yT - y from touch
    private void defineDirection(int xT, int yT) {
        switch (directBody) {
            case 0:
                Log.w("dir", "down");
                if (yT < snakeMap.get(snakeMap.size() - 1).getPosY()) {
                    directBody = 3;
                } else directBody = 1;
                break;
            case 2:
                Log.w("dir", "top");
                if (yT < snakeMap.get(snakeMap.size() - 1).getPosY()) {
                    directBody = 3;
                } else directBody = 1;
                break;
            case 1:
                Log.w("dir", "right");
                if (xT < snakeMap.get(snakeMap.size() - 1).getPosX()) {
                    directBody = 2;
                } else directBody = 0;
                break;
            case 3:
                Log.w("dir", "left");
                if (xT < snakeMap.get(snakeMap.size() - 1).getPosX()) {
                    directBody = 2;
                } else directBody = 0;
                break;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isFirstTouch) {
            getSizeGameField();
            createGameField();
            createSnake();
            visibleGameField();
            isFirstTouch = false;
        } else {
            int xDist = (int) (event.getY() / textViewGameField.getLineHeight());
            int yDist = (int) (event.getX() / textViewGameField.getPaint().measureText(" "));
            defineDirection(xDist, yDist);
        }

            (new Thread(new Runnable() {
                @Override
                public void run() {
                    k = 0;
                    isRunning = true;
                    while (!Thread.interrupted())
                        try {
                            Thread.sleep(100);
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (isRunning) {
                                        createGameField();
                                        isRunning = moveSnake();
                                        visibleGameField();
                                    } else Thread.interrupted();
                                }
                            });
                        } catch (InterruptedException e) {

                        }
                }
            })).start();

        return false;
    }
}