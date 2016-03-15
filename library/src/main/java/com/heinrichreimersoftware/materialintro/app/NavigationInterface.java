package com.heinrichreimersoftware.materialintro.app;

/**
 * Created by juced on 15.03.2016.
 */
public interface NavigationInterface {

    /**
     * Calling before click next. If return true - show next page
     * @param position
     * @return
     */
    boolean onNextClick(int position);

    /**
     * Calling before click previous. If return true - show previous page
     * @param position
     * @return
     */
    boolean onPreviousClick(int position);

    /**
     * Calling when impossible to navigate next or previous page
     * @param position
     * @param direction
     */
    void onImpossibleToNavigate(int position, int direction);

}
