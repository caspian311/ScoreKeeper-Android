package net.todd.scorekeeper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;

public class GameHistoryAdapter extends ResourceCursorAdapter {
    public GameHistoryAdapter(Context context) {
        super(context, R.layout.game_history_entry, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
