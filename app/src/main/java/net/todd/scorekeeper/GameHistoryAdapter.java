package net.todd.scorekeeper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ResourceCursorTreeAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

public class GameHistoryAdapter extends ResourceCursorTreeAdapter {
    private final Context context;

    public GameHistoryAdapter(Context context) {
        super(context, null, R.layout.game_history_entry, R.layout.game_scoreboard);
        this.context = context;
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        long gameId = groupCursor.getLong(groupCursor.getColumnIndex("_id"));
        return context.getContentResolver().query(Uri.parse("content://net.todd.scorekeeper/games/" + gameId + "/scoreboard"), null, null, null, null);
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
        String winner = cursor.getString(cursor.getColumnIndex("winner"));
        String gameName = cursor.getString(cursor.getColumnIndex("name"));
        String gameDate = formatDate(cursor.getString(cursor.getColumnIndex("date")));

        TextView gameSummary = (TextView)view.findViewById(R.id.game_summary);
        gameSummary.setText(String.format("%s won at %s on %s", winner, gameName, gameDate));
    }

    private String formatDate(String dateText) {
        try {
            Date date = Constants.DATE_STORAGE_FORMAT.parse(dateText);
            return Constants.DATE_DISPLAY_FORMAT.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {
        TextView playerName = (TextView)view.findViewById(R.id.player_name);
        TextView playerScore = (TextView)view.findViewById(R.id.player_score);

        playerName.setText(cursor.getString(cursor.getColumnIndex("name")));

        String points = Constants.POINT_DISPLAY_FORMAT.format(cursor.getInt(cursor.getColumnIndex("score")));
        playerScore.setText(points);
    }
}
