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
        final Player player = new Player();
        player.setId(cursor.getLong(0));
        player.setName(cursor.getString(1));

        TextView availablePlayerName = (TextView)view.findViewById(R.id.available_player_name);
        availablePlayerName.setText(player.getName());

        CheckBox checkBox = (CheckBox)view.findViewById(R.id.select_player_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (playerSelectionChangeListener != null) {
                    playerSelectionChangeListener.playerSelectionChanged(player, isChecked);
                }
            }
        });
    }

    public void setPlayerSelectionChangeListener(PlayerSelectionChangeListener playerSelectionChangeListener) {
        this.playerSelectionChangeListener = playerSelectionChangeListener;
    }

    public static interface PlayerSelectionChangeListener {
        void playerSelectionChanged(Player player, boolean isSelected);
    }
}
