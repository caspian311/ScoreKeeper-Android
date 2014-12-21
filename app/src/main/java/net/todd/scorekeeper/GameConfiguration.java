package net.todd.scorekeeper;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameConfiguration implements Parcelable {
    private String gameName;
    private List<Player> selectedPlayers = new ArrayList<Player>();

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<Player> getSelectedPlayers() {
        return selectedPlayers;
    }

    public void setSelectedPlayers(List<Player> selectedPlayers) {
        this.selectedPlayers = selectedPlayers;
    }

    public Cursor getPlayersAsCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[] { "_id", "name" });
        for (Player player : getSelectedPlayers()) {
            cursor.addRow(new Object[] { player.getId(), player.getName() });
        }
        return cursor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gameName);
        dest.writeTypedList(selectedPlayers);
    }

    public GameConfiguration() {
    }

    private GameConfiguration(Parcel in) {
        this.gameName = in.readString();
        in.readTypedList(selectedPlayers, Player.CREATOR);
    }

    public static final Creator<GameConfiguration> CREATOR = new Creator<GameConfiguration>() {
        public GameConfiguration createFromParcel(Parcel source) {
            return new GameConfiguration(source);
        }

        public GameConfiguration[] newArray(int size) {
            return new GameConfiguration[size];
        }
    };

    public void movePlayerUp(long playerId) {
        int index = getSelectedPlayers().indexOf(getPlayerById(playerId));
        if (index != 0) {
            Collections.swap(getSelectedPlayers(), index, index - 1);
        }
    }

    private Player getPlayerById(long playerId) {
        for (int i=0; i<getSelectedPlayers().size(); i++) {
            if (getSelectedPlayers().get(i).getId() == playerId) {
                return getSelectedPlayers().get(i);
            }
        }
        return null;
    }

    public void movePlayerDown(long playerId) {
        int index = getSelectedPlayers().indexOf(getPlayerById(playerId));
        if (index != getSelectedPlayers().size() - 1) {
            Collections.swap(getSelectedPlayers(), index, index + 1);
        }
    }
}
