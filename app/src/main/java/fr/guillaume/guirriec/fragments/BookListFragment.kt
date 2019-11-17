package fr.guillaume.guirriec.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.guillaume.guirriec.adapters.BookAdapter
import fr.guillaume.guirriec.R
import fr.guillaume.guirriec.data.Book

class BookListFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager
    private lateinit var onClickListener: BookItemOnClickListener
    private lateinit var bookList : Array<Book>

    companion object {
        fun newBookListFragment(bookList : Array<Book>?) : BookListFragment {
            val bookListFragment = BookListFragment()
            val args = Bundle()
            args.putParcelableArray("Books", bookList)
            bookListFragment.arguments = args
            return bookListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.book_list_fragment, container, false)
        bookList = arguments?.getParcelableArray("Books") as Array<Book>
        viewManager = LinearLayoutManager(context)
        viewAdapter = BookAdapter(bookList, onClickListener::bookItemClicked)

        recyclerView = view.findViewById<RecyclerView>(R.id.rv_books).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onClickListener = context as BookItemOnClickListener
    }

    interface BookItemOnClickListener {
        fun bookItemClicked(selectedBook: Book)
    }
}