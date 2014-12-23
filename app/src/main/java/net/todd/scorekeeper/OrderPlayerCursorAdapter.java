package net.todd.scorekeeper;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class OrderPlayerCursorAdapter extends ResourceCursorAdapter {
    private MovePlayerListener movePlayerUpListener;
    private MovePlayerListener movePlayerDownListener;

    public OrderPlayerCursorAdapter(Context context) {
        super(context, R.layout.order_player, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final long playerId = cursor.getLong(0);
        Button orderUp = (Button)view.findViewById(R.id.higher_order);
        orderUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movePlayerUpListener != null) {
                    movePlayerUpListener.playerMoved(playerId);
                }
            }
        });
        Button orderDown = (Button)view.findViewById(R.id.lower_order);
        orderDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movePlayerDownListener != null) {
                    movePlayerDownListener.playerMoved(playerId);
                }
            }
        });

        String playerName = cursor.getString(1);
        TextView playerNameText = (TextView)view.findViewById(R.id.player_name);
        playerNameText.setText(playerName);
    }

    public void setMovePlayerUpListener(MovePlayerListener listener) {
        movePlayerUpListener = listener;
    }

    public void setMovePlayerDownListener(MovePlayerListener listener) {
        movePlayerDownListener = listener;
    }

    public static interface MovePlayerListener {
        void playerMoved(long playerId);
    }
}
