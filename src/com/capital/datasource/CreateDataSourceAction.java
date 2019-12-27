package com.capital.datasource;

import com.capital.base.AbsJavaAction;
import com.capital.utils.InputValidatorAdapter;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.JavaTemplateUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;

import org.jetbrains.annotations.Nullable;

public class CreateDataSourceAction extends AbsJavaAction {
    private static final String DATA_SOURCE_TEMPLATE_NAME = "DataSource";
    private static final String LOCAL_DATA_SOURCE_TEMPLATE_NAME = "LocalDataSource";
    private static final String REMOTE_DATA_SOURCE_TEMPLATE_NAME = "RemoteDataSource";
    private static final String Repository_TEMPLATE_NAME = "Repository";


    public CreateDataSourceAction() {
        super("", IdeBundle.message("action.create.new.class.description"), PlatformIcons.CLASS_ICON, true);
    }

    @Nullable
    @Override
    protected PsiClass doCreate(PsiDirectory dir, String className, String s1) throws IncorrectOperationException {
        PsiClass result = JavaDirectoryService.getInstance().createClass(dir, className + "DataSource", DATA_SOURCE_TEMPLATE_NAME);
        PsiClass result2 = JavaDirectoryService.getInstance().createClass(dir, className + "LocalDataSource", LOCAL_DATA_SOURCE_TEMPLATE_NAME);
        PsiClass result3 = JavaDirectoryService.getInstance().createClass(dir, className + "RemoteDataSource", REMOTE_DATA_SOURCE_TEMPLATE_NAME);
        PsiClass result4 = JavaDirectoryService.getInstance().createClass(dir, className + "Repository", Repository_TEMPLATE_NAME);
        replaceClassName(className, result2);
        replaceClassName(className, result3);
        replaceClassName(className, result4);
        return result;
    }


    @SuppressWarnings("all")
    private void replaceClassName(final String className, final PsiClass psiClass) {
        PsiFile file = psiClass.getContainingFile();
        final PsiDocumentManager manager = PsiDocumentManager.getInstance(psiClass.getProject());
        final Document document = manager.getDocument(file);
        new WriteCommandAction.Simple(psiClass.getProject()) {
            @Override
            protected void run() throws Throwable {
                manager.doPostponedOperationsAndUnblockDocument(document);
                document.setText(document.getText()
                        .replace("ORIGIN_DATA_SOURCE", className+"DataSource")
                        .replace("ORIGIN_LOCAL_DATA_SOURCE", className + "LocalDataSource")
                        .replace("ORIGIN_REMOTE_DATA_SOURCE", className + "RemoteDataSource"));
                CodeStyleManager.getInstance(psiClass.getProject()).reformat(psiClass);
            }
        }.execute();
    }


    @Override
    protected void buildDialog(Project project, PsiDirectory psiDirectory, CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle("Create DataSource and Repository")
                .addKind("Class", PlatformIcons.CLASS_ICON,
                        JavaTemplateUtil.INTERNAL_CLASS_TEMPLATE_NAME);
        builder.setValidator(new InputValidatorAdapter(project));
    }
}
