package com.sasuke.recommender.util;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.rule.NotEmptyRule;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by abc on 4/24/2018.
 */

public abstract class ValidationListener implements Validator.ValidationListener {

    public abstract Context getContext();

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> list) {
        List<ValidationError> filteredError = new ArrayList<>();
        filteredError.addAll(list);

        for (ValidationError error : list) {
            View view = error.getView();
            if (view instanceof EditText) {
                if (((EditText) view).getText().toString().trim().length() == 0) {
                    boolean isRequired = false;
                    for (Rule rule : error.getFailedRules()) {
                        if (rule instanceof NotEmptyRule) {
                            isRequired = true;
                        }
                    }
                    if (!isRequired) {
                        filteredError.remove(error);
                    }
                }
            }
        }
        if (filteredError.size() == 0) {
            onValidationSucceeded();
            return;
        }

        for (ValidationError error : filteredError) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toasty.normal(getContext(), message).show();
            }
        }
    }
}
