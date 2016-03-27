package game.snake.ua;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
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
    private ArrayList<Position> snakeMap;

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

        Position position = new Position();
        position.setPos(posHeadX, posHeadY);
        snakeMap.add(position);

        //***determine the position of the snake's body***
        directBody = new Random().nextInt(4);
;
        switch (directBody) {
            case 0:
                position.setPos(posHeadX + 1, posHeadY);
                snakeMap.add(position);
                position.setPos(posHeadX + 2, posHeadY);
                snakeMap.add(position);
                break;
            case 1:
                position.setPos(posHeadX, posHeadY + 1);
                snakeMap.add(position);
                position.setPos(posHeadX, posHeadY + 2);
                snakeMap.add(position);
                break;
            case 2:
                position.setPos(posHeadX - 1, posHeadY);
                snakeMap.add(position);
                position.setPos(posHeadX - 2, posHeadY);
                snakeMap.add(position);
                break;
            case 3:
                position.setPos(posHeadX, posHeadY - 1);
                snakeMap.add(position);
                position.setPos(posHeadX, posHeadY - 2);
                snakeMap.add(position);
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

    //***cX - current x
    //***cY - current y

    private boolean moveSnake() {
        Position position = new Position();
        boolean runResult = false;
        switch (directBody) {
            case 0://***Direction down***//
                if (snakeMap.get(snakeMap.size() - 1).getPosX() < countLine - 1) {
                    position.setPosX(snakeMap.get(snakeMap.size() - 1).getPosX() + 1);
                    position.setPosY(snakeMap.get(snakeMap.size() - 1).getPosY());
                    // strArrGameField[cX + 2 + k][cY] = "@";
                   // strArrGameField[cX + k - 1][cY] = ".";
                    snakeMap.add(position);
                    snakeMap.remove(0);
                    runResult = true;
                } else runResult = false;
                break;
            case 1://***Direction right***//
                if (snakeMap.get(snakeMap.size() - 1).getPosY() < countSymbolOfLine - 1) {
                    position.setPosX(snakeMap.get(snakeMap.size() - 1).getPosX());
                    position.setPosY(snakeMap.get(snakeMap.size() - 1).getPosY() + 1);
                    //strArrGameField[cX][cY + 2 + k]= "@";
                    //strArrGameField[cX][cY + k - 1] = ".";
                    snakeMap.add(position);
                    snakeMap.remove(0);
                    runResult = true;
                } else runResult = false;
                break;
            case 2://***Direction top***//
                if (snakeMap.get(0).getPosX() > 1) {
                    position.setPosX(snakeMap.get(0).getPosX() - 1);
                    position.setPosX(snakeMap.get(0).getPosY());
                    //strArrGameField[cX - 2 - k][cY] = "@";
                    //strArrGameField[cX - k + 1][cY] = ".";
                    snakeMap.add(position);
                    snakeMap.remove(snakeMap.size() - 1);
                    runResult = true;
                } else runResult = false;
                break;
            //***Direction left***//
            case 3:
                if (snakeMap.get(0).getPosY() > 1) {
                    position.setPosX(snakeMap.get(0).getPosX());
                    position.setPosX(snakeMap.get(0).getPosY() - 1);
                    //strArrGameField[cX][cY - 2 - k] = "@";
                    // strArrGameField[cX][cY - k + 1] = ".";
                    snakeMap.add(position);
                    snakeMap.remove(snakeMap.size() - 1);
                    runResult = true;
                } else runResult = false;
                break;
        }
        strArrGameField[snakeMap.get(0).getPosX()][snakeMap.get(0).getPosY()] = "@";
        strArrGameField[snakeMap.get(1).getPosX()][snakeMap.get(1).getPosY()] = "@";
        strArrGameField[snakeMap.get(2).getPosX()][snakeMap.get(2).getPosY()] = "@";
        return runResult;
    }
    //xT - x from touch
    //yT - y from touch
    private void defineDirection(int xT, int yT) {
        switch (directBody) {
            case 0:case 2:
                if (yT < snakeMap.get(snakeMap.size() - 1).getPosY()) {
                    directBody = 1;
                } else directBody = 3;
                break;

            case 1:case 3:
                if (xT < snakeMap.get(snakeMap.size() - 1).getPosX()) {
                    directBody = 0;
                } else directBody = 2;
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
                            Thread.sleep(1000);
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