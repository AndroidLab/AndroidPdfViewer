package com.github.barteksc.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.github.barteksc.sample.databinding.ActivityMainBinding
import com.shockwave.pdfium.PdfDocument.Bookmark
import com.shockwave.pdfium.PdfDocument.Meta

class PDFViewActivity : FragmentActivity(), OnPageChangeListener,
    OnLoadCompleteListener,
    OnPageErrorListener {

    private val TAG = PDFViewActivity::class.java.simpleName

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                displayFromUri(uri!!)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        displayFromAsset()
        binding.iv.setOnClickListener {
            launchPicker()
        }
    }

    private fun launchPicker() {
        resultLauncher.launch(
            Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
            }
        )
    }

    private fun displayFromAsset() {
        binding.pdfView.fromAsset("sample.pdf")
            .defaultPage(1)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // in dp
            .onPageError(this)
            .pageFitPolicy(FitPolicy.BOTH)
            .load()
    }

    private fun displayFromUri(uri: Uri) {
        binding.pdfView.fromUri(uri)
            .defaultPage(1)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // in dp
            .onPageError(this)
            .load()
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        //pageNumber = page
        title = String.format("%s %s / %s", "pdfFileName", page + 1, pageCount)
    }

    override fun loadComplete(nbPages: Int) {
        val meta: Meta = binding.pdfView.documentMeta
        Log.d(TAG, "title = " + meta.title)
        Log.d(TAG, "author = " + meta.author)
        Log.d(TAG, "subject = " + meta.subject)
        Log.d(TAG, "keywords = " + meta.keywords)
        Log.d(TAG, "creator = " + meta.creator)
        Log.d(TAG, "producer = " + meta.producer)
        Log.d(TAG, "creationDate = " + meta.creationDate)
        Log.d(TAG, "modDate = " + meta.modDate)
        printBookmarksTree(binding.pdfView.tableOfContents, "-")
        binding.tv.text = meta.title
    }

    override fun onPageError(page: Int, t: Throwable?) {
        Log.d(TAG, "Cannot load page $page")
    }

    private fun printBookmarksTree(tree: List<Bookmark>, sep: String) {
        for (b in tree) {
            Log.d(TAG, String.format("%s %s, p %d", sep, b.title, b.pageIdx))
            if (b.hasChildren()) {
                printBookmarksTree(b.children, "$sep-")
            }
        }
    }
}