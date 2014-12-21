package net.todd.scorekeeper;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class ScoreBoardAdapter extends ResourceCursorAdapter {
    public ScoreBoardAdapter(Context context, Cursor scoreBoardCursor) {
        super(context, R.layout.score_board_entry, scoreBoardCursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView playerName = (TextView)view.findViewById(R.id.player_name);
        TextView playerScore = (TextView)view.findViewById(R.id.player_score);

        CheckBox currentPlayerIndicator = (CheckBox)view.findViewById(R.id.current_player_indicator);
        currentPlayerIndicator.setChecked(cursor.getInt(1) == 1);
        playerName.setText(cursor.getString(2));
        playerScore.setText("" + cursor.getInt(3));
    }
}
