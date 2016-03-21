package game.snake.ua;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {
    TextView textView;
    Button button;
    String[][] gameField = new String[10][10];
    String strField = " ";
    int k = 1;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        textView = (TextView) view.findViewById(R.id.tv);
        button = (Button) view.findViewById(R.id.btn);
        button.setOnClickListener(this);
       // int curLine = textView.getLayout().getLineStart(0);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameField[i][j] = " ";
            }
        }
        gameField[5][0] = "@";
        gameField [5][1] = "@";
        gameField [5][2] = "@";

        textView.setText(" ");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                strField += gameField[i][j];
            }
            strField += "/n";
        }
        textView.setText(strField);
        return view;

    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameField[i][j] = " ";

            }
        }
        if (k < 8) {
            gameField[3][k] = "@";
            gameField[3][k + 1] = "@";
            gameField[3][k + 2] = "@";
            k++;
        } else {
            k = 1;


        }
        strField = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                strField += gameField[i][j];
            }
            strField += "/";
        }

        textView.setText(strField);

    }
}
