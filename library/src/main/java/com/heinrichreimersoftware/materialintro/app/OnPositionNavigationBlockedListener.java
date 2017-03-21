package com.heinrichreimersoftware.materialintro.app;

public abstract class OnPositionNavigationBlockedListener implements OnNavigationBlockedListener {
    private final int position;

    public OnPositionNavigationBlockedListener(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    protected abstract void onNavigationBlocked(@Direction int direction);

    @Override
    public void onNavigationBlocked(int position, @Direction int direction) {
        if (this.position == position) {
            onNavigationBlocked(direction);
        }
    }
}
