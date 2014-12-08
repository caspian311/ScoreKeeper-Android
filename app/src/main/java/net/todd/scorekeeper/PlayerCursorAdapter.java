package net.todd.scorekeeper;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class PlayerCursorAdapter extends ResourceCursorAdapter {
    private DeletePlayerListener deletePlayerListener;

    public PlayerCursorAdapter(Context context) {
        super(context, R.layout.player, null);
    }

    public void setDeletePlayerListener(DeletePlayerListener deletePlayerListener) {
        this.deletePlayerListener = deletePlayerListener;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final long playerId = cursor.getLong(0);
        final String playerName = cursor.getString(1);

        TextView playerNameText = (TextView)view.findViewById(R.id.player_name);
        playerNameText.setText(playerName);

        ImageButton button = (ImageButton)view.findViewById(R.id.delete_player_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deletePlayerListener != null) {
                    deletePlayerListener.deletePlayer(playerId);
                }
            }
        });
    }

    public static interface DeletePlayerListener {
        void deletePlayer(long playerId);
    }
}
