package com.sasuke.recommender.util;

import com.sasuke.recommender.R;

import java.util.Random;

/**
 * Created by abc on 4/24/2018.
 */

public class ImageStubUtils {

    private static int[] arr = new int[]{R.drawable.action,
            R.drawable.adventure,
            R.drawable.animation,
            R.drawable.attractions,
            R.drawable.city_scape,
            R.drawable.comedy,
            R.drawable.disaster,
            R.drawable.documentary,
            R.drawable.drama,
            R.drawable.family,
            R.drawable.fantasy,
            R.drawable.fiction,
            R.drawable.horror,
            R.drawable.martial_arts,
            R.drawable.musical,
            R.drawable.mystery,
            R.drawable.nature,
            R.drawable.night_life,
            R.drawable.romantic,
            R.drawable.scifi,
            R.drawable.superhero,
            R.drawable.thriller,
            R.drawable.war,
            R.drawable.western,
    };

    public static int getImage() {
        Random rnd = new Random();
        return arr[rnd.nextInt(23)];
    }
}
