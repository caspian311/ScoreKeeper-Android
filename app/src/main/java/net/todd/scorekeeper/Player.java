package net.todd.scorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    public Player() {
    }

    private Player(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
