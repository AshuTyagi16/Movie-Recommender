package com.sasuke.recommender.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditText_OpenSans_SemiBold extends EditText {
    public EditText_OpenSans_SemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditText_OpenSans_SemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_OpenSans_SemiBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Semibold.ttf"));
        }
    }
}
