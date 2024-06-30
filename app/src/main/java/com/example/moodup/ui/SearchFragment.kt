package com.example.moodup.ui

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodup.R
import com.example.moodup.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchEditText: EditText
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var comicsAdapter: ComicsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var cancelButton: Button
    private lateinit var notFoundTV: TextView
    private lateinit var notFoundIv: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButton = view.findViewById(R.id.cancelButton)
        searchEditText = view.findViewById(R.id.searchEditText)
        searchResultsRecyclerView = view.findViewById(R.id.searchResultsRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        notFoundTV = view.findViewById(R.id.nothingFoundTextView)
        notFoundIv = view.findViewById(R.id.nothingFoundImageView)

        searchResultsRecyclerView.layoutManager = LinearLayoutManager(context)
        comicsAdapter = ComicsAdapter(emptyList())
        searchResultsRecyclerView.adapter = comicsAdapter

        cancelButton.setOnClickListener {
            searchEditText.clearFocus()
            searchEditText.text.clear()
            cancelButton.visibility = View.GONE
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)

        }


        notFoundTV.visibility = View.VISIBLE
        notFoundIv.visibility= View.VISIBLE
        notFoundIv.setImageResource(R.drawable.book)
        notFoundTV.text = getString(R.string.start_typing_to_find_a_particular_comics)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()

                cancelButton.isEnabled = query.isNotEmpty()
                cancelButton.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE

                if (query.length >= 3) {
                    progressBar.visibility = View.VISIBLE
                    searchResultsRecyclerView.visibility = View.GONE
                    searchViewModel.searchComics(query)
                    notFoundTV.visibility = View.GONE
                    notFoundIv.visibility= View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        searchViewModel.comics.observe(viewLifecycleOwner, Observer { comics ->
            progressBar.visibility = View.GONE
            if (comics.isEmpty()) {
                searchResultsRecyclerView.visibility = View.GONE
                notFoundTV.text = getString(R.string.no_comic_found)
                notFoundTV.visibility = View.VISIBLE
                notFoundIv.visibility = View.VISIBLE
                notFoundIv.setImageResource(R.drawable.nosearches)
            } else {
                notFoundTV.visibility = View.GONE
                notFoundIv.visibility= View.GONE
                searchResultsRecyclerView.visibility = View.VISIBLE
                comicsAdapter.updateComics(comics)
            }
        })

        searchViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}
