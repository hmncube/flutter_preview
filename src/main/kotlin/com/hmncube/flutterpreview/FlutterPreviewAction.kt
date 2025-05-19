package com.hmncube.flutterpreview

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import java.io.File
import java.io.BufferedReader

class FlutterPreviewAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val flutterVersion = getFlutterVersion()
        val project = e.project
        val basePath = project?.basePath

        val message = buildString {
            appendLine(flutterVersion ?: "Flutter is not installed or not in PATH.")
            if (basePath != null) {
                val previewDir = File(basePath, ".flutter_preview")
                if (previewDir.exists()) {
                    appendLine("\nPreview project already exists at ${previewDir.path}")
                }
            }
        }.trim()

        val previewProjectExists = basePath?.let { File(it, ".flutter_preview").exists() } ?: true

        val options = if (previewProjectExists) {
            arrayOf("Close")
        } else {
            arrayOf("Create Preview Project", "Close")
        }

        val defaultOptionIndex = 0
        val choice = Messages.showDialog(
            project,
            message,
            "Flutter Preview",
            options,
            defaultOptionIndex,
            Messages.getInformationIcon(),
            null
        )

        if (!previewProjectExists && choice == 0) {
            if (project != null) {
                createPreviewProject(project)
            } else {
                Messages.showMessageDialog("No active project found.", "Error", Messages.getErrorIcon())
            }
        }
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

    private fun createPreviewProject(project: Project) {
        val basePath = project.basePath ?: return
        val previewDir = File(basePath, ".flutter_preview")
        if (previewDir.exists()) {
            Messages.showInfoMessage("Preview project already exists at ${previewDir.path}", "Info")
            return
        }

        try {
            val process = ProcessBuilder("flutter", "create", "--project-name", "flutter_preview", previewDir.path)
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().use(BufferedReader::readText)
            process.waitFor()
            if (process.exitValue() == 0) {
                Messages.showInfoMessage("Flutter preview project created successfully at:\n${previewDir.path}", "Success")
            } else {
                Messages.showErrorDialog("Failed to create preview project:\n$output", "Error")
            }
        } catch (e: Exception) {
            Messages.showErrorDialog("Exception: ${e.message}", "Error")
        }
    }
}
