# Android PdfViewer

Library for displaying PDF documents on Android, with `animations`, `gestures`, `zoom` and `double tap` support.
It is based on [AndroidPdfViewer](https://github.com/barteksc/AndroidPdfViewer) for decoding PDF files. Works on API 23 (Android 6.0) and higher.
Licensed under Apache License 2.0.

I updated gradle and dependencies, also posted the project on Jetpack.io

# To get a Git project into your build:
Add it in your root build.gradle at the end of repositories
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```
dependencies {
	implementation 'com.github.AndroidLab:AndroidPdfViewer:3.3.0-beta.1'
}
```