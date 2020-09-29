package net.insi8.wiki.ui.main

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content_view.*
import net.insi8.wiki.R
import net.insi8.wiki.model.WikiSearch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeDataEvents()
        configureUI()
    }

    private fun configureUI() {
        webViewConfigurations()
        btnSearch.setOnClickListener {
            viewModel.doSearch(editSearch.text.toString())
        }
    }

    private fun webViewConfigurations() {
        val webSettings = webView.settings
        webSettings.useWideViewPort = false
        webSettings.javaScriptEnabled = false
        webSettings.loadWithOverviewMode = true
        webSettings.builtInZoomControls = false
        webSettings.domStorageEnabled = true

        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
    }

    private fun observeDataEvents() {
        viewModel.networkStateLiveData.observe(this, Observer {
            when (it) {
                is TopicSearchResult.DataReady -> setDataToWebView(it.result)
                is TopicSearchResult.Loading -> updateLoading()
                is TopicSearchResult.Error -> showErrorMessage(it.errorMessage)
            }
        })

        viewModel.grepStateLiveData.observe(this, Observer {
            when (it) {
                is GrepResult.Success -> updateCount(it.count)
                is GrepResult.Error -> showErrorMessage(it.errorMessage)
            }
        })
    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        mainContent.visibility = View.GONE
        banner.visibility = View.VISIBLE
    }

    private fun updateLoading() {
        Toast.makeText(this, R.string.loading, Toast.LENGTH_SHORT).show()
    }

    private fun updateCount(count: Int) {
        mainContent.visibility = View.VISIBLE
        banner.visibility = View.GONE
        txtCount.text = getString(R.string.content_count, count.toString())
    }

    private fun setDataToWebView(wikiSearch: WikiSearch) {
        mainContent.visibility = View.VISIBLE
        banner.visibility = View.GONE
        webView.loadData(wikiSearch.parse.htmlDataObject.htmlString , "text/html", "utf-8")
    }
}