package com.capital.base;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.actions.JavaCreateTemplateInPackageAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public abstract class AbsJavaAction extends JavaCreateTemplateInPackageAction<PsiClass> implements DumbAware {


    public AbsJavaAction(String text, String description, Icon icon, boolean inSourceOnly) {
        super(text, description, icon, inSourceOnly);
    }

    @Nullable
    @Override
    protected PsiElement getNavigationElement(@NotNull PsiClass psiClass) {
        return psiClass.getLBrace();
    }


    @Override
    protected String getActionName(PsiDirectory psiDirectory, String newName, String templateName) {
        return IdeBundle.message("progress.creating.class", StringUtil.getQualifiedName(
                JavaDirectoryService.getInstance().getPackage(psiDirectory).getQualifiedName(), newName));
    }


    protected Document getDocumentForPsiClass(PsiClass psiClass) {
        PsiFile file = psiClass.getContainingFile();
        final PsiDocumentManager manager = PsiDocumentManager.getInstance(psiClass.getProject());
        final Document document = manager.getDocument(file);
        return document;
    }


}
