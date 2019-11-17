package fr.guillaume.guirriec.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.guillaume.guirriec.R
import fr.guillaume.guirriec.data.Book

class BookDetailsFragment : Fragment() {

    companion object {
        fun newBookDetailsFragment(selectedBook : Book?) : BookDetailsFragment {
            val bookDetailsFragment = BookDetailsFragment()
            val args = Bundle()
            args.putParcelable("Book", selectedBook)
            bookDetailsFragment.arguments = args
            return bookDetailsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.book_details_fragment, container, false)
        var selectedBook : Book? = arguments?.getParcelable("Book")
        view.findViewById<TextView>(R.id.tv_book_details_title).text = selectedBook?.title
        view.findViewById<TextView>(R.id.tv_book_details_price).text = selectedBook?.price
        view.findViewById<TextView>(R.id.tv_book_details_synopsis).text = selectedBook?.synopsis?.joinToString()
        Picasso.get().load(selectedBook?.cover).into(view.findViewById<ImageView>(R.id.iv_book_details_cover))
        return view
    }
}