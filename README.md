### **🎯 Goal**

Allow Flutter developers using Android Studio to preview a selected Flutter widget directly in the IDE with a button click.

---

## 🥇 MVP (Minimum Viable Product)

**Objective:** Provide a basic, proof-of-concept implementation of widget preview with minimal IDE integration.

### Features:

* [ ] Add a button to the Android Studio UI (e.g., editor gutter or toolbar).
* [ ] On click, detect the currently selected widget (via cursor or selected code block).
* [ ] Extract the Dart code for the selected widget.
* [ ] Launch a simple embedded preview window or external tool (e.g., DartPad-like view or Flutter run command in preview mode).
* [ ] Display the rendered output.

### Technologies:

* IntelliJ Platform SDK (UI integration)
* Dart/Flutter Plugin APIs (for parsing and executing Dart code)
* Basic Dart parser (can use PSI or regex for MVP)
* `flutter run --preview` or `flutter test` as a temporary rendering mechanism

### Limitations:

* No error handling
* Limited or no hot reload
* UI preview may not be IDE-embedded
* Only supports stateless widgets or small snippets

---

## 🥈 Version 1 (v1)

**Objective:** Make the plugin usable in daily development with a more robust preview and better IDE integration.

### Features:

* [ ] ⚙️ Improved widget extraction: full AST parsing using PSI for Dart.
* [ ] 🧠 Context-aware: recognize full widget declarations (even when nested).
* [ ] 🖼 Embedded preview: render output in a tool window within the IDE.
* [ ] ♻️ Hot reload on code changes (optional).
* [ ] 🧪 Basic error output if widget code is invalid.
* [ ] 🧩 Flutter project validation before activation (e.g., check `pubspec.yaml`).

### Technologies:

* Dart PSI for reliable widget extraction
* Flutter daemon or Flutter tooling API for real-time previews
* ToolWindow integration for rendering
* Message bus or file watchers for auto-refresh

### Limitations:

* Might still not support deeply nested widget trees
* Limited preview customization (e.g., light/dark themes)

---

## 🥇 Final Version

**Objective:** Full-featured, production-quality widget previewer integrated into Android Studio.

### Features:

* [ ] 🧬 Full AST-based widget isolation and transformation (including wrapping in a scaffold/app if needed).
* [ ] 🧵 Live preview with stateful widget support and real hot reload.
* [ ] 🌗 Theme selector: switch between Material light/dark themes.
* [ ] 🔧 Preview configuration panel (set width, height, device orientation).
* [ ] 🧼 Intelligent error reporting and logging panel.
* [ ] 📋 History/Bookmarking of previews.
* [ ] 🔄 Integration with version control (preview past widget versions).
* [ ] 🧱 Support for multiple preview instances.
* [ ] 🎨 Custom rendering engine or Flutter Engine embedding in tool window (if feasible).

### Technologies:

* Deeper integration with the Dart and Flutter plugin internals
* Virtual display or embedded Flutter engine (if allowed by tooling)
* Full custom UI components for rendering and interactivity

---

## Notes

* **Security & sandboxing**: Ensure user code is safely run.
* **Performance**: Widget preview should not block IDE.
* **Plugin Dependencies**: Use only stable APIs to avoid breakage with Flutter/IDE updates.

---