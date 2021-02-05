package a.b.masterdetail.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import a.b.masterdetail.R
import a.b.masterdetail.data.Item
import a.b.masterdetail.databinding.FragmentListBinding
import a.b.masterdetail.ui.adapter.ItemAdapter
import a.b.masterdetail.ui.viewmodel.ItemListViewModel
import a.b.masterdetail.util.dummyList
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A fragment that displays the list of items, which can be added by the user
 * Supports Phone sized and Tablet Sized Layouts, Where the Tablet Sized layout contains another
 * NavHostFragment
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ItemListViewModel
    private lateinit var binding: FragmentListBinding
    private var isLargeDevice: Boolean = false
    private lateinit var items: ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ItemListViewModel::class.java)
        isLargeDevice = resources.getBoolean(R.bool.isLargeWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isLargeDevice) {
            setupNavHost()
        }
        //Initial list is only fetched once, to avoid calling notifyDatasetChanged multiple times for the adapter
        items = viewModel.liveData.value!!
        setupFloatingActionButton()
        setupViewModelObservers()
        setupRecyclerView()
    }

    // CallBack for the Adapter
    private fun triggerSelection(isLongPressed: Boolean, pos: Int, item: Item) {
        if (isLongPressed) {
            // remove item from list
            items.removeAt(pos)
            binding.itemList.adapter!!.notifyItemRemoved(pos)
        } else {
            viewModel.itemSelected(pos, item)
            if (!isLargeDevice) {
                findNavController().navigate(ListFragmentDirections.listToDetails())
            }
        }
    }

    private fun setupFloatingActionButton() {
        addFab.setOnClickListener { _ ->
            findNavController().navigate(ListFragmentDirections.openInputDialog())
        }
    }

    // The New Item Live data is observed from here, and added to the existing list
    private fun setupViewModelObservers() {
        viewModel.newItem.observe(viewLifecycleOwner, { item ->
            if (item != null) {
                items.add(item)
                binding.itemList.adapter?.notifyItemInserted(items.size - 1)
                // A bug causes the new item to be added every time the destination fragment is changed,
                // So once a new Item is retrieved, we set it's value back to null
                viewModel.newItem.value = null
            }
        })
    }

    // Setup RecyclerView
    private fun setupRecyclerView() {
        binding.itemList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ItemAdapter(
                    isLargeDevice, items, viewModel,
                    this@ListFragment::triggerSelection
            )
        }
    }

    // Set up the NavHost Fragment for the Tablet Layout
    private fun setupNavHost() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.detailContainer) as NavHostFragment
    }
}