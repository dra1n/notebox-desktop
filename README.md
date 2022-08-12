# Notebox App

An application for taking notes.

## Supported Operating Systems

- Windows
- MacOS
- Linux (Debian/Ubuntu)

## Installation

### Windows

- Download the `.exe` file from the [latest release](https://github.com/dra1n/notebox-desktop/releases/latest).
- Run the downloaded file. If a security dialog appears, allow the application to run anyways.
- Go through the standard install process.
- Run the application using the desktop shortcut or from the start menu.

### MacOS

- Download the `.dmg` file from the [latest release](https://github.com/dra1n/notebox-desktop/releases/latest).
- Open the `.dmg` and drag the application into the `Applications` folder.
- Open the application. If a security dialog appears, click `Cancel`, then inside settings, open `Security & Privacy` and run the application anyways.

### Linux (Debian/Ubuntu)

- Download the `.deb` file from the [latest release](https://github.com/dra1n/notebox-desktop/releases/latest).
- Install using your package manager.
- Run the application.

## Compilation

Requirements:

* [clojure](https://clojure.org/guides/getting_started)
* Java JDK 14

```bash
git clone https://github.com/dra1n/notebox-desktop.git
cd notebox
./build.sh
```