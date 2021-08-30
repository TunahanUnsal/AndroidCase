
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scorp.case1.model.Person


abstract class PaginationScrollListener

    (var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)


        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()



        Log.d("TAG", "visibleItemCount: "+layoutManager.childCount)
        Log.d("TAG", "totalItemCount: "+layoutManager.itemCount)
        Log.d("TAG", "firstVisibleItemPosition: "+layoutManager.findFirstVisibleItemPosition())

        if (firstVisibleItemPosition == totalItemCount - visibleItemCount) {
           loadMoreItems()
        }
    }
    abstract fun loadMoreItems()


}
