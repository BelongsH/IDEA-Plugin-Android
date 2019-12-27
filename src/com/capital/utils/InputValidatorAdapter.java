package com.capital.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiNameHelper;

import org.jetbrains.annotations.Nullable;

public class InputValidatorAdapter implements InputValidatorEx {

    private Project project;

    public InputValidatorAdapter(Project project) {
        this.project = project;
    }


    @Override
    public boolean checkInput(String inputString) {
        return true;
    }


    @Override
    public boolean canClose(String inputString) {
        return !StringUtil.isEmptyOrSpaces(inputString) &&
                getErrorText(inputString) == null;
    }

    @Nullable
    @Override
    public String getErrorText(String inputString) {
        if (inputString.length() > 0 && !PsiNameHelper.getInstance(project).isQualifiedName(inputString)) {
            return "This is not a valid Java qualified name";
        }
        return null;
    }
}
