package com.hmncube.flutterpreview

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class FlutterPreviewAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog(e.project, "Flutter preview by Hmncube", "Flutter Preview", Messages.getInformationIcon())
    }
}