package co.lithobyte.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


interface RecyclerItemViewModelInterface {
    fun configureHolder(holder: RecyclerView.ViewHolder)
    fun newViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun viewType(): Int
}

abstract class RecyclerItemViewModel<T>(val model: T): RecyclerItemViewModelInterface

abstract class ModelViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun configure(model: T)
}

abstract class LayoutIdRecyclerItemViewModel<T>(model: T, val layoutId: Int): RecyclerItemViewModel<T>(model) {
    override fun viewType(): Int = layoutId

    override fun newViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layoutId, parent, false)
        return viewHolderWithView(view)
    }

    abstract fun viewHolderWithView(view: View): ModelViewHolder<T>
}

open class FlexAdapter (var itemViewModels: List<RecyclerItemViewModelInterface>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return itemViewModels.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewModels[position].viewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return itemViewModels.first { it.viewType() == viewType }.newViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewModel = itemViewModels[position]
        itemViewModel.configureHolder(holder)
    }
}
