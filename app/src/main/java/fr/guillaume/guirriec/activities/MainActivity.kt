package fr.guillaume.guirriec.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.guillaume.guirriec.R
import fr.guillaume.guirriec.data.Book
import fr.guillaume.guirriec.fragments.BookDetailsFragment
import fr.guillaume.guirriec.fragments.BookListFragment
import fr.guillaume.guirriec.services.HenriPotierAccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity(), BookListFragment.BookItemOnClickListener {

    private val classTag: String = MainActivity::class.java.simpleName
    private var selectedBook: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadBooksAndUpdateList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("Book", selectedBook)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        selectedBook = savedInstanceState.getParcelable("Book")
        val orientation = this.resources.configuration.orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                supportFragmentManager.beginTransaction().run {
                    replace(R.id.activity_main_details, BookDetailsFragment.newBookDetailsFragment(selectedBook), BookDetailsFragment::class.java.name)
                    commit()
                }
        }
    }

    private fun loadBooksAndUpdateList() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = HenriPotierAccess.henriPotierApiClient.getBooks().await()
                var bookList: List<Book>?
                if(webResponse.isSuccessful) {
                    bookList = webResponse.body()
                    Log.d(classTag, bookList.toString())
                    supportFragmentManager.beginTransaction().run {
                        replace(R.id.activity_main, BookListFragment.newBookListFragment(bookList?.toTypedArray()), BookListFragment::class.java.name)
                        commit()
                    }
                } else {
                    Log.e(classTag, "Error ${webResponse.code()}")
                }
            } catch (exception: IOException) {
                Log.e(classTag, "Exception " + exception.printStackTrace())
            }
        }
    }

    override fun bookItemClicked(bookItem : Book) {
        val orientation = this.resources.configuration.orientation;
        var idLayout = 0

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            idLayout = R.id.activity_main
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            idLayout = R.id.activity_main_details
        }

        supportFragmentManager.beginTransaction().run {
            replace(idLayout, BookDetailsFragment.newBookDetailsFragment(bookItem), BookDetailsFragment::class.java.name)
            addToBackStack(BookDetailsFragment::class.java.name)
            commit()
        }

    }
}
