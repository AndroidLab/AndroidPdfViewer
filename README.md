# Android PdfViewer

The library was taken as the basis https://github.com/barteksc/AndroidPdfViewer

Library for displaying PDF documents on Android, with `animations`, `gestures`, `zoom` and `double tap` support.
It is based on [PdfiumAndroid](https://github.com/barteksc/PdfiumAndroid) for decoding PDF files. Works on API 11 (Android 3.0) and higher.
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
	implementation 'com.github.barteksc.pdfviewer:AndroidPdfViewer:Tag'
}
```