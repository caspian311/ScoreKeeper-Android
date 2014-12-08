package net.todd.scorekeeper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class AvailablePlayerCurorAdapter extends ResourceCursorAdapter {
    private PlayerSelectionChangeListener playerSelectionChangeListener;

    public AvailablePlayerCurorAdapter(Activity activity) {
        super(activity, R.layout.available_player, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final long playerId = cursor.getLong(0);
        String playerName = cursor.getString(1);

        TextView availablePlayerName = (TextView)view.findViewById(R.id.available_player_name);
        availablePlayerName.setText(playerName);

        CheckBox checkBox = (CheckBox)view.findViewById(R.id.select_player_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (playerSelectionChangeListener != null) {
                    playerSelectionChangeListener.playerSelectionChanged(playerId, isChecked);
                }
            }
        });
    }

    public void setPlayerSelectionChangeListener(PlayerSelectionChangeListener playerSelectionChangeListener) {
        this.playerSelectionChangeListener = playerSelectionChangeListener;
    }

    public static interface PlayerSelectionChangeListener {
        void playerSelectionChanged(long playerId, boolean isSelected);
    }
}
