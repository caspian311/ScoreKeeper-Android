package net.todd.scorekeeper;

import android.database.AbstractCursor;

import java.util.List;

public class PlayersCursor extends AbstractCursor {

    private List<Player> data;

    public PlayersCursor(List<Player> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String[] getColumnNames() {
        return new String[] { "_id", "name" };
    }

    @Override
    public String getString(int column) {
        if (column == 1) {
            return data.get(mPos).getName();
        }
        return null;
    }

    @Override
    public short getShort(int column) {
        return 0;
    }

    @Override
    public int getInt(int column) {
        if (column == 0) {
            return data.get(mPos).getId();
        }
        return 0;
    }

    @Override
    public long getLong(int column) {
        return 0;
    }

    @Override
    public float getFloat(int column) {
        return 0;
    }

    @Override
    public double getDouble(int column) {
        return 0;
    }

    @Override
    public boolean isNull(int column) {
        return false;
    }
}
