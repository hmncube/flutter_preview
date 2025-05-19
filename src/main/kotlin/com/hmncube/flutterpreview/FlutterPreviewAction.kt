package com.hmncube.flutterpreview

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.io.BufferedReader

class FlutterPreviewAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val flutterVersion = getFlutterVersion()
        val message = flutterVersion ?: "Flutter is not installed or not in PATH."

        Messages.showMessageDialog(
            e.project,
            message,
            "Flutter Preview",
            Messages.getInformationIcon()
        )
    }

    private fun getFlutterVersion(): String? {
        return try {
            val process = ProcessBuilder("flutter", "--version")
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().use(BufferedReader::readText)
            process.waitFor()
            if (process.exitValue() == 0) output.trim() else null
        } catch (e: Exception) {
            null
        }
    }
}
