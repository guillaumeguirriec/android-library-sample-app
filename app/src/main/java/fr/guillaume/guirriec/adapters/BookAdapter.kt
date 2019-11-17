package fr.guillaume.guirriec.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.guillaume.guirriec.R
import fr.guillaume.guirriec.data.Book

class BookAdapter(private val bookList: Array<Book>, private val onClickListener: (book: Book) -> Unit) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val bookViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(bookViewHolder)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book: Book = bookList[position]
        holder.bind(book, onClickListener)
    }

    override fun getItemCount(): Int = bookList.size

    class BookViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(book: Book, onClickListener : (book: Book) -> Unit) {
            itemView.findViewById<TextView>(R.id.tv_book_item_title).text = book.title
            itemView.findViewById<TextView>(R.id.tv_book_item_price).text = book.price
            Picasso.get().load(book.cover).into(itemView.findViewById<ImageView>(R.id.iv_book_item_cover))
            itemView.setOnClickListener{ onClickListener(book)}
        }
    }
}