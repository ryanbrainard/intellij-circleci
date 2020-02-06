package com.circleci.ui;

import com.circleci.BuildListLoader;
import com.circleci.LoadingListener;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.progress.util.ProgressWindow;
import com.intellij.ui.components.JBLoadingPanel;
import com.intellij.vcs.log.ui.frame.ProgressStripe;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class CircleCILoadingPanel extends JBLoadingPanel {

    public CircleCILoadingPanel(@NotNull Disposable parent, JComponent content, BuildListLoader buildListLoader) {
        super(new BorderLayout(), parent);

        ProgressStripe progressStripe = new ProgressStripe(content, parent, ProgressWindow.DEFAULT_PROGRESS_DIALOG_POSTPONE_TIME_MILLIS);
        add(progressStripe);

        JBLoadingPanel loadingPanel = this;
        buildListLoader.addLoadingListener(new LoadingListener() {
            @Override
            public void loadingStarted(boolean reload) {
                if (reload) {
                    loadingPanel.startLoading();
                }
                progressStripe.startLoading();
            }

            @Override
            public void loadingFinished() {
                loadingPanel.stopLoading();
                progressStripe.stopLoading();
            }
        });
    }
}
