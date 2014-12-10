package net.todd.scorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GameConfiguration implements Parcelable {
    private final String gameName;
    private List<Long> selectedPlayerIds;

    public GameConfiguration(String gameName, List<Long> selectedPlayerIds) {
        this.gameName = gameName;
        this.selectedPlayerIds = selectedPlayerIds;
    }

    public String getGameName() {
        return gameName;
    }

    public List<Long> getSelectedPlayers() {
        return selectedPlayerIds;
    }

    private List<Long> convertIds(long[] selectedPlayerIds) {
        List<Long> ids = new ArrayList<Long>();
        for (long selectedPlayerId : selectedPlayerIds) {
            ids.add(new Long(selectedPlayerId));
        }
        return ids;
    }

    private static long[] convertIds(List<Long> selectedPlayerIds) {
        Long[] playerIds = new Long[selectedPlayerIds.size()];
        selectedPlayerIds.toArray(playerIds);
        long[] ids = new long[selectedPlayerIds.size()];
        for (int i = 0; i < selectedPlayerIds.size(); i++) {
            ids[i] = selectedPlayerIds.get(i);
        }
        return ids;
    }

    public GameConfiguration(Parcel source) {
        gameName = source.readString();
        selectedPlayerIds = convertIds(source.createLongArray());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gameName);
        dest.writeLongArray(convertIds(selectedPlayerIds));
    }

    public static final Parcelable.Creator<GameConfiguration> CREATOR = new Parcelable.Creator<GameConfiguration>() {
        public GameConfiguration createFromParcel(Parcel source) {
            return new GameConfiguration(source);
        }

        @Override
        public GameConfiguration[] newArray(int size) {
            return new GameConfiguration[0];
        }
    };
}
