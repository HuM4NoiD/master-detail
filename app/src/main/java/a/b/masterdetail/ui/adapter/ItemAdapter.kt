package a.b.masterdetail.ui.adapter

import a.b.masterdetail.R
import a.b.masterdetail.data.Item
import a.b.masterdetail.databinding.LayoutItemBinding
import a.b.masterdetail.ui.viewmodel.ItemListViewModel
import android.content.ClipData
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    val isLargeScreen: Boolean,
    val items: ArrayList<Item>,
    val viewModel: ItemListViewModel,
    val triggerSelection: (Boolean ,Int, Item) -> Unit
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var selectedItem = viewModel.selectedItemPos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<LayoutItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.layout_item,
                parent,
                false
        )
        return ItemViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.item = items[position]
        if (isLargeScreen) {
            if (position == selectedItem) {
                holder.setColor()
            } else {
                holder.clearColor()
            }
        }
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(
        itemView: View,
        val binding: LayoutItemBinding
    ): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (isLargeScreen) {
                viewModel.itemSelected(adapterPosition, items[adapterPosition])
                notifyItemChanged(selectedItem)
                selectedItem = adapterPosition
                notifyItemChanged(adapterPosition)
            } else {
                triggerSelection(false, adapterPosition, items[adapterPosition])
            }
        }

        fun setColor() {
            itemView.setBackgroundColor(Color.parseColor("#4003DAC5"))
        }

        fun clearColor() {
            itemView.setBackgroundColor(Color.parseColor("#00000000"))
        }

        override fun onLongClick(p0: View?): Boolean {
            triggerSelection(true, adapterPosition, items[adapterPosition])
            return true
        }
    }
}