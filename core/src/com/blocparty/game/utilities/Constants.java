package com.blocparty.game.utilities;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sfotm on 11/14/15.
 */
public class Constants {
    public static final String TITLE = "BlocParty";

    public static final List<Color> COLOR_LIST = Collections.unmodifiableList(
            new ArrayList<Color>(){{
                //add(Color.RED);
                add(Color.ORANGE);
                add(Color.YELLOW);
                add(Color.GREEN);
                //add(Color.BLUE);
                //add(Color.VIOLET);
                add(Color.PINK);
                add(Color.CYAN);
                add(Color.CORAL);
                add(Color.LIME);
                add(Color.SALMON);
            }}
    );
}
