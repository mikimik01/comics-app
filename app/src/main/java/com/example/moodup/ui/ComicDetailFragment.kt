package com.example.moodup.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import coil.load
import com.example.moodup.R
import com.example.moodup.data.Comic
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ComicDetailFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var moreButton: Button
    private lateinit var bottomSheet: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comic_detail, container, false)
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        //Init
        bottomSheet = view.findViewById(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        moreButton = view.findViewById(R.id.button)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        //Geting comic
        val comic: Comic? = arguments?.getParcelable("comic")

        //Setting layout atributes from comic data
        comic?.let {
            val comicTitle: TextView = view.findViewById(R.id.comicTitle)
            val comicAuthor: TextView = view.findViewById(R.id.comicAuthor)
            val comicDescription: TextView = view.findViewById(R.id.comicDescription)
            val comicThumbnail: ImageView = view.findViewById(R.id.comicThumbnail)

            //seting title
            comic.title?.takeIf { it.isNotBlank() }?.let {
                comicTitle.text = it
            } ?: run {
                comicTitle.text = "No Title"
            }

            //seting description
            comic.description?.takeIf { it.isNotEmpty() }?.let {
                comicDescription.text = it
            } ?: run {
                comicDescription.text = "No Description"
            }

            //seting creators
            comic.creators?.items?.takeIf { it.isNotEmpty() }?.joinToString(", ") { it.name.toString() }?.let {
                comicAuthor.text = "written by $it\n"
            } ?: run {
                comicAuthor.text = "Unknown Authors\n"
            }

            //seting image
            comic.thumbnail?.url?.let {
                val part1 = it.substring(0, 4)
                val part2 = it.substring(4)
                val charToAdd = 's'
                comicThumbnail.load("$part1$charToAdd$part2")
            }

            //setting button
            comic.urls?.let { urls ->
                val comicUrl = urls.firstOrNull()?.url
                if (comicUrl != null) {
                    moreButton.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comicUrl))
                        moreButton.context.startActivity(intent)
                    }
                } else {
                    moreButton.setOnClickListener {
                        Toast.makeText(moreButton.context, "Link to comic not found :(", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                moreButton.setOnClickListener {
                    Toast.makeText(moreButton.context, "Link to comic not found :(", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //creating new instance
    companion object {
        fun newInstance(comic: Comic): ComicDetailFragment {
            val args = Bundle()
            args.putParcelable("comic", comic)
            val fragment = ComicDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
