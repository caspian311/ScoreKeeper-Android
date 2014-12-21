package net.todd.scorekeeper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ResourceCursorTreeAdapter;
import android.widget.TextView;

public class GameHistoryAdapter extends ResourceCursorTreeAdapter {
    private final Context context;

    public GameHistoryAdapter(Context context) {
        super(context, null, R.layout.game_history_entry, R.layout.game_scoreboard);
        this.context = context;
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        long gameId = groupCursor.getLong(0);
        return context.getContentResolver().query(Uri.parse("content://net.todd.scorekeeper/games/" + gameId + "/scoreboard"), null, null, null, null);
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
        TextView gameName = (TextView)view.findViewById(R.id.game_name_text);
        gameName.setText(cursor.getString(cursor.getColumnIndex("name")));
        TextView gameWinner = (TextView)view.findViewById(R.id.winner_text);
        gameWinner.setText(cursor.getString(cursor.getColumnIndex("winner")));
        TextView gameDate = (TextView)view.findViewById(R.id.game_date);
        gameDate.setText(cursor.getString(cursor.getColumnIndex("date")));
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {
        TextView playerName = (TextView)view.findViewById(R.id.player_name);
        playerName.setText(cursor.getString(cursor.getColumnIndex("name")));
        TextView playerScore = (TextView)view.findViewById(R.id.player_score);
        playerScore.setText(new Integer(cursor.getInt(cursor.getColumnIndex("score"))).toString());
    }
}
