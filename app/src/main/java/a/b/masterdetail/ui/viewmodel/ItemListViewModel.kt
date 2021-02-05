package a.b.masterdetail.ui.viewmodel

import a.b.masterdetail.data.Item
import a.b.masterdetail.util.dummyList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemListViewModel: ViewModel() {

    //initial List of Items
    var liveData = MutableLiveData<ArrayList<Item>>(dummyList)

    // Item Selected in RecyclerView
    var selectedItem = ObservableField<Item>()
    var selectedItemPos: Int = 0

    //Item That was added via Input
    var newItem = MutableLiveData<Item>()

    fun newItemAdded(item: Item) {
        newItem.postValue(item)
    }

    fun itemSelected(pos: Int, item: Item) {
        selectedItemPos = pos
        selectedItem.set(item)
    }
}