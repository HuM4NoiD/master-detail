package a.b.masterdetail.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import a.b.masterdetail.R
import a.b.masterdetail.data.Item
import a.b.masterdetail.databinding.FragmentItemInputDialogBinding
import a.b.masterdetail.ui.viewmodel.ItemListViewModel
import a.b.masterdetail.util.dummyList
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * A simple [Fragment] subclass.
 * Use the [ItemInputDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemInputDialogFragment: BottomSheetDialogFragment() {

    private lateinit var viewModel: ItemListViewModel
    private lateinit var binding: FragmentItemInputDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ItemListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_input_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitItem.setOnClickListener {
            submitItem(it)
        }
    }

    private fun submitItem(view: View) {
        Toast.makeText(requireContext(), "Outside", Toast.LENGTH_SHORT).show()
        if (binding.nameInput.length() > 0 && binding.urlInput.length() > 0) {
            Log.d("BottomSheet", "onViewCreated: ${binding.nameInput.length()}, ${binding.urlInput.length()}")
            Toast.makeText(requireContext(), "Valid Input", Toast.LENGTH_SHORT).show()
            val item = Item(binding.nameInput.text.toString(), binding.urlInput.text.toString())
            viewModel.newItemAdded(item)
            dismiss()
        }
    }
}